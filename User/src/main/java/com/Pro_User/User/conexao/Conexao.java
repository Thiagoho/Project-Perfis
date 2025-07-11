package com.Pro_User.User.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/db_usuarios";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Coloque sua senha se houver

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
	// Teste a conexão esta OK!
	public static void main(String[] args) {
		try (Connection conn = getConexao()) {
			System.out.println("Conexão bem-sucedida!");
		} catch (SQLException e) {
			System.out.println("Erro ao Conectar:" + e.getMessage());
		}
	}
}
