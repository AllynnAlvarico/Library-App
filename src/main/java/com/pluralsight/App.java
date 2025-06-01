package com.pluralsight;

import com.pluralsight.model.Book;
import com.pluralsight.repository.BookDao;
import com.pluralsight.repository.DAO;

import java.util.List;
import java.util.Optional;

public class App {
    private final DAO<Book> bookDao = new BookDao();
    List<Book> books;
    Optional<Book> book;
    public void findAuthor(String author){
        List<Book> authorsBooks = bookDao.findAuthor("Ursa ShingField");
//        List<Book> authorsBooks = bookDao.findAuthor(author);
//        app.displayBooks(authorsBooks);
    }
    public List<Book> findByGenre(String genre){
        return books = bookDao.findByGenre(genre);
    }
    public List<Book> findByRating(double rating) {
        return books = bookDao.findByRating(rating);
    }
    public void displayBook(String book_title){
        Optional<Book> optionalBook = bookDao.findBook(book_title);
        optionalBook.ifPresentOrElse(
//                book -> displayBook(book_title),
//                This calls itself with the same argument, over and over,
//                causing infinite recursion and eventually a stack overflow or forced exit.
//                How to Fix
                this::displayBook, // Option 1 (Best Option)
//                book -> displayBook(book), // Option 2
                () -> System.out.println("Book not found.")
        );
    }

    public void displayBooks(List<Book> bookList){
        for (Book b : bookList){
            System.out.printf("\nBook ISBN: %s Book title: %s",  b.isbn(), b.book_title());
        }
    }

    public void displayBook(Book book) {
        System.out.printf("\nBook ISBN: %s Book title: %s",  book.isbn(), book.book_title());
    }
    public static void main(String[] args) {
        App app = new App();

        // Example usage:
//        app.findAuthor("Ursa ShingField");
//        app.bookDao.displayBooks(app.findByGenre("fantasy"));
//        app.bookDao.displayBooks(app.findByRating(10));
        app.displayBook("Eggs");
    }
}   