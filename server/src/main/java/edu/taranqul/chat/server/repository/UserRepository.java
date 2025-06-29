package edu.taranqul.chat.server.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public void createUser(Connection conn, String username, String passwordHash) throws SQLException {
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            stmt.executeUpdate();
        }
    }

    public boolean existsByUsername(Connection conn, String username) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean credentialsAreValid(Connection conn, String username, String passwordHash) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE username = ? AND password_hash = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}