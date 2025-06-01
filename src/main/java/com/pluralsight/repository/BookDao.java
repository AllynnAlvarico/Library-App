package com.pluralsight.repository;

import com.pluralsight.model.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookDao extends AbstractDAO implements DAO<Book>{

    @Override
    public Optional<Book> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = Collections.emptyList();
        String sql = "SELECT * FROM BOOK";
        try(
                // Auto-Closeable resource
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery(sql);
        ) {
            books = new ArrayList<>();

            while(resultSet.next()){
                Book book =
                        new Book.Builder()
                                .id(resultSet.getLong("id"))
                                .title(resultSet.getString("title"))
                                .build();
                books.add(book);
            }

        } catch (SQLException sqe) {
            sqe.printStackTrace();
        } finally {
            System.out.println("Retrieving Books Done");
            System.out.println("Books Found " + books.size());
        }
        return books;
    }

    @Override
    public void save(Book book) {

    }

    @Override
    public void update(Book book, String[] params) {

    }

    @Override
    public void delete(Book book) {

    }
}
