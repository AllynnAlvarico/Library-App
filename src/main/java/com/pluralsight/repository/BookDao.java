package com.pluralsight.repository;

import com.pluralsight.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookDao extends AbstractDAO implements DAO<Book>{

    @Override
    public Optional<Book> findById(String isbn) {
        Optional<Book> book = Optional.empty();
        String sql = "SELECT * FROM BOOK WHERE isbn = ?";
        try(
            Connection conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ) {
                preparedStatement.setString(1, isbn);
                try (ResultSet rst = preparedStatement.executeQuery();){
                    if (rst.next()){
                        book = Optional.of(aBook(rst));
                    }
                }
        } catch (SQLException e) {
        e.printStackTrace();
            throw new RuntimeException(e);
        }
        return book;
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
                    return Optional.ofNullable(aBook(resultSet));
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
    public boolean existsByIsbn(String isbn) {
        String sql = "SELECT 1 FROM BOOK WHERE isbn = ?";
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, isbn);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // returns true if any row exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book create(Book b) {
        Book book = b;
        java.util.Date utilDate = book.publication_date();
        String sql =
                "INSERT INTO BOOK (isbn, book_title, author, genre, publication_date, publisher, page_count, language, format, avail_format, price, rating, book_cover)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ) {
            preparedStatement.setString(1, book.isbn());
            preparedStatement.setString(2, book.book_title());
            preparedStatement.setString(3, book.author());
            preparedStatement.setString(4, book.genre());
            if (utilDate != null) {
                preparedStatement.setDate(5, new java.sql.Date(utilDate.getTime()));
            } else {
                preparedStatement.setDate(5, null);
            }
            preparedStatement.setString(6, book.publisher());
            preparedStatement.setInt(7, book.page_count());
            preparedStatement.setString(8, book.language());
            preparedStatement.setString(9, book.format());
            preparedStatement.setString(10, book.avail_format());
            preparedStatement.setDouble(11, book.price());
            preparedStatement.setDouble(12, book.rating());
            preparedStatement.setString(13, book.coverUrl());
            preparedStatement.executeUpdate();
            try (ResultSet genKeys = preparedStatement.getGeneratedKeys()){
                if (genKeys.next()) {
                    long generatedId = genKeys.getLong(1);

                    book = new Book.Builder()
                            .id(generatedId)
                            .isbn(b.isbn())
                            .book_title(b.book_title())
                            .author(b.author())
                            .genre(b.genre())
                            .publication_date(b.publication_date())
                            .publisher(b.publisher())
                            .page_count(b.page_count())
                            .language(b.language())
                            .format(b.format())
                            .avail_format(b.avail_format())
                            .price(b.price())
                            .rating(b.rating())
                            .coverUrl(b.coverUrl())
                            .build();
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw new RuntimeException(sqe);
        }
        return book;
    }

    @Override
    public void save(Book book) {

    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE Book SET book_title = ? WHERE id = ?";
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, book.book_title());
            pstmt.setLong(2, book.id());
            pstmt.executeUpdate();

        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw new RuntimeException(sqe);
        }
        return null;
    }

    @Override
    public int[] update(List<Book> books) {
        int [] records = {};
        String sql = "UPDATE Book SET book_title = ?, rating = ? WHERE id = ?";
        try (
                Connection connection = getConnection();
                PreparedStatement pst = connection.prepareStatement(sql);
                ) {
            for (Book book : books) {
                pst.setString(1, book.book_title());
                pst.setFloat(2, book.rating());
                pst.setLong(3, book.id());

                pst.addBatch();
            }
            records = pst.executeBatch();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw new RuntimeException(sqe);
        }
        return records;
    }


    @Override
    public int delete(Book book) {
        int rowsAffected = 0;
        String sql = "DELETE FROM Book WHERE id = ?";
        try (
                Connection connection = getConnection();
                PreparedStatement pst = connection.prepareStatement(sql);
        ) {
            pst.setLong(1, book.id());
            rowsAffected = pst.executeUpdate();

        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw new RuntimeException(sqe);
        }
        return rowsAffected;
    }
    public int delete(List<Book> books) {
        int [] records = {};
        int numberOfAffectedRows = records.length;
        String sql = "DELETE FROM Book WHERE id = ?";
        try (
                Connection connection = getConnection();
                PreparedStatement pst = connection.prepareStatement(sql);
        ) {
            for (Book book : books) {
                pst.setLong(1, book.id());

                pst.addBatch();
            }
            records = pst.executeBatch();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw new RuntimeException(sqe);
        }
        return numberOfAffectedRows;
    }

    public Book aBook(ResultSet resultSet) throws SQLException {
            return
                    new Book.Builder()
                            .id(resultSet.getLong("id")) // 1
                            .isbn(resultSet.getString("isbn")) // 2
                            .book_title(resultSet.getString("book_title")) // 3
                            .author(resultSet.getString("author")) // 4
                            .genre(resultSet.getString("genre")) // 5
                            .publication_date(resultSet.getDate("publication_date")) // 6
                            .publisher(resultSet.getString("publisher")) // 7
                            .page_count(resultSet.getInt("page_count")) // 8
                            .language(resultSet.getString("language")) // 9
                            .format(resultSet.getString("format")) // 10
                            .avail_format(resultSet.getString("avail_format")) // 11
                            .price(resultSet.getFloat("price")) // 12
                            .rating(resultSet.getFloat("rating")) // 13
                            .coverUrl(resultSet.getString("book_cover")) // 14
                            .build();
    }
    public void inputBooks(List<Book> books, ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            Book book = aBook(resultSet);
            books.add(book);
        }
    }
}
