package com.pluralsight.repository;

import com.pluralsight.model.Book;

import java.sql.*;
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
            inputBooks(books, resultSet);
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        } finally {
            System.out.println("Retrieving Books Done");
            System.out.println("Books Found " + books.size());
        }
        return books;
    }

    @Override
    public List<Book> findByGenre(String inputGenre) {
        List<Book> books = Collections.emptyList();
        String sql = "SELECT * FROM BOOK WHERE genre = ?";
        try(
                // Auto-Closeable resource
                Connection conn = getConnection();
                PreparedStatement prepStmt = conn.prepareStatement(sql);
        ) {
            prepStmt.setString(1, inputGenre);
            try (
                    // Auto-Closeable resource
                    ResultSet resultSet = prepStmt.executeQuery()
            ){
                books = new ArrayList<>();
                inputBooks(books, resultSet);
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
    public Optional<Book>  findBook(String book) {
        String sql = "SELECT * FROM BOOK WHERE book_title = ?";
        try(
                // Auto-Closeable resource
                Connection conn = getConnection();
                PreparedStatement prepStmt = conn.prepareStatement(sql);
        ) {
            prepStmt.setString(1, book);
            try (
                    // Auto-Closeable resource
                    ResultSet resultSet = prepStmt.executeQuery()
            ){
                if (resultSet.next()) {
                    return Optional.ofNullable(inputBook(resultSet));
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        } finally {
            System.out.println("Retrieving Books Done");
            System.out.println("Book Found");
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAuthor(String findAuthor) {
        List<Book> books = Collections.emptyList();
        String sql = "SELECT * FROM BOOK WHERE author = ?";
        try(
                // Auto-Closeable resource
                Connection conn = getConnection();
                PreparedStatement prepStmt = conn.prepareStatement(sql);
        ) {
            prepStmt.setString(1, findAuthor);
            try (
                    // Auto-Closeable resource
                    ResultSet resultSet = prepStmt.executeQuery()
            ){
                books = new ArrayList<>();
                inputBooks(books, resultSet);
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
    public List<Book> findByRating(double rate) {
        List<Book> books = Collections.emptyList();
        String sql = "SELECT * FROM BOOK WHERE rating >= ?";
        try(
                // Auto-Closeable resource
                Connection conn = getConnection();
                PreparedStatement prepStmt = conn.prepareStatement(sql);
        ) {
            prepStmt.setDouble(1, rate);
            try (
                    // Auto-Closeable resource
                    ResultSet resultSet = prepStmt.executeQuery()
            ){
                books = new ArrayList<>();
                inputBooks(books, resultSet);
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

    public Book inputBook(ResultSet resultSet) throws SQLException {
            return new Book.Builder()
                            .id(resultSet.getString("isbn"))
                            .title(resultSet.getString("book_title"))
                            .author(resultSet.getString("author"))
                            .genre(resultSet.getString("genre"))
                            .publication_date(resultSet.getDate("publication_date"))
                            .publisher(resultSet.getString("publisher"))
                            .page_count(resultSet.getInt("page_count"))
                            .language(resultSet.getString("language"))
                            .format(resultSet.getString("format"))
                            .avail_format(resultSet.getString("avail_format"))
                            .price(resultSet.getFloat("price"))
                            .rating(resultSet.getFloat("rating"))
                            .build();

    }
    public void inputBooks(List<Book> books, ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            Book book =
                    new Book.Builder()
                            .id(resultSet.getString("isbn"))
                            .title(resultSet.getString("book_title"))
                            .author(resultSet.getString("author"))
                            .genre(resultSet.getString("genre"))
                            .publication_date(resultSet.getDate("publication_date"))
                            .publisher(resultSet.getString("publisher"))
                            .page_count(resultSet.getInt("page_count"))
                            .language(resultSet.getString("language"))
                            .format(resultSet.getString("format"))
                            .avail_format(resultSet.getString("avail_format"))
                            .price(resultSet.getFloat("price"))
                            .rating(resultSet.getFloat("rating"))
                            .build();
            books.add(book);
        }
    }
}
