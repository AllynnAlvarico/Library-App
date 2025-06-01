package com.pluralsight.repository;

import com.pluralsight.model.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public abstract class AbstractDAO {
    // DAO = Data Access Object
    // it is a pattern used to separate the data persistence logic in a separate layer

    protected Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library_db";
        String username = "root";
        String password = "pass";

        return DriverManager.getConnection(url, username, password);
    }
}
