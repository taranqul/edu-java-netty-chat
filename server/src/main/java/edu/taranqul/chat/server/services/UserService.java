package edu.taranqul.chat.server.services;
import edu.taranqul.chat.server.repository.UserRepository;
import edu.taranqul.chat.server.utils.PassHasher;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class UserService {
    private final DataSource dataSource;
    private final UserRepository repository;

    public UserService(DataSource dataSource, UserRepository repository) {
        this.dataSource = dataSource;
        this.repository = repository;
    }

    public boolean register(String username, String password) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            if (repository.existsByUsername(conn, username)) {
                return false; // уже существует
            }

            String hash = PassHasher.hashPassword(password);
            repository.createUser(conn, username, hash);

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkCredentials(String username, String password) {
        try (Connection conn = dataSource.getConnection()) {
            String hash = PassHasher.hashPassword(password);
            return repository.credentialsAreValid(conn, username, hash);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
