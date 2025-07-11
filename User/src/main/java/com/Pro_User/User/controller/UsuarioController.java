package com.Pro_User.User.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Pro_User.User.DTO.RedefinirSenhaDTO;
import com.Pro_User.User.DTO.UsuarioDTO;
import com.Pro_User.User.Servic.UsuarioServic;
import com.Pro_User.User.model.Usuario;
import com.Pro_User.User.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioServic usuarioServic;
    private final UsuarioRepository usuarioRepository;

    // Injeção via construtor, SEM @Autowired no campo!
    public UsuarioController(UsuarioServic usuarioServic, UsuarioRepository usuarioRepository) {
        this.usuarioServic = usuarioServic;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Usuario> listarUsuario() {
        return usuarioServic.listarTodos();
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioServic.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioServic.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        return usuarioServic.buscarPorId(id)
            .map(usuarioExistente -> {
                usuarioExistente.setNome(dto.getNome());
                usuarioExistente.setEmail(dto.getEmail());
                if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
                    usuarioExistente.setSenha(dto.getSenha());
                }
                Usuario salvo = usuarioServic.salvar(usuarioExistente, dto.getPerfis());
                return ResponseEntity.ok(salvo);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        Usuario salvo = usuarioServic.salvar(usuario, dto.getPerfis());
        return ResponseEntity.ok(salvo);
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<?> redefinirSenha(@RequestBody RedefinirSenhaDTO dto) {
        // Ajuste: aqui você deve usar usuarioServic para redefinir a senha!
        boolean ok = usuarioServic.redefinirSenha(dto.getEmail(), dto.getNovaSenha());
        if (ok) {
            return ResponseEntity.ok("Senha redefinida com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail não encontrado!");
    }

    @GetMapping("/dashboard/usuarios-por-perfil")
    public Map<String, Long> totalPorPerfil() {
        return usuarioServic.contarUsuariosPorPerfil();
    }
    // Exportação Excel
    @GetMapping("/export/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerkey = "Content-Disposition";
        String headerValue = "attachment; filename=usuarios.xlsx";
        response.setHeader(headerkey, headerValue);

        List<Usuario> usuarios = usuarioServic.listarTodos();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Usuários");

        // Cabeçalho
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Nome");
        header.createCell(1).setCellValue("E-mail");
        header.createCell(2).setCellValue("Perfis");

        int rowCount = 1;
        for (Usuario user : usuarios) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(user.getNome());
            row.createCell(1).setCellValue(user.getEmail());
            row.createCell(2).setCellValue(
                user.getPerfis().stream().map(p -> p.getNome()).collect(Collectors.joining(", "))
            );
        }
        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
