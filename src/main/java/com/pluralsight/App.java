package com.pluralsight;

import com.pluralsight.model.Book;
import com.pluralsight.repository.BookDao;
import com.pluralsight.repository.DAO;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class App {
    private final DAO<Book> bookDao = new BookDao();
    List<Book> books;
    Optional<Book> book;
    public void newBook(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date pubDate;
        String isbn = "978074753269-9";

        try {
            pubDate = sdf.parse("26/1997/06");
        } catch (ParseException e) {
            // handle error
            pubDate = null;
        }

        Book newBook = new Book.Builder()
                .isbn(isbn)
                .book_title("Harry Potter: Philosopher's Stone")
                .author("J. K. Rowling")
                .genre("fantasy")
                .publication_date(pubDate)
                .publisher("Bloomsburry")
                .page_count(223)
                .language("english")
                .format("Hardback")
                .avail_format("eBook & Audiobooks")
                .price(16.09F)
                .rating(9)
                .coverUrl("https://covers.openlibrary.org/b/isbn/" + isbn + "-L.jpg")
                .build();
        if (bookDao.existsByIsbn(newBook.isbn())) {
            System.out.println("A book with ISBN " + newBook.isbn() + " already exists. Not adding.");
        } else {
            bookDao.create(newBook);
            System.out.println("Book added.");
        }
    }
    public void addNewBook(
            String isbn, String title, String author, String genre, String date,
            String publisher, int pages, String lang, String format, String other_format, float price, float rate
    ){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date pubDate;
        try {
            pubDate = sdf.parse(date);
        } catch (ParseException e) {
            pubDate = null;
        }
        Book newBook = new Book.Builder()
                .isbn(isbn)
                .book_title(title)
                .author(author)
                .genre(genre)
                .publication_date(pubDate)
                .publisher(publisher)
                .page_count(pages)
                .language(lang)
                .format(format)
                .avail_format(other_format)
                .price(price)
                .rating(rate)
                .coverUrl("https://covers.openlibrary.org/b/isbn/" + isbn + "-L.jpg")
                .build();
        if (bookDao.existsByIsbn(newBook.isbn())) {
            System.out.println("A book with ISBN " + newBook.isbn() + " already exists. Not adding.");
        } else {
            bookDao.create(newBook);
            System.out.println("Book added.");
        }
    }
    public List<Book> groupByAuthor(String author){
        return books = bookDao.findAuthor(author);
//        List<Book> authorsBooks = bookDao.findAuthor(author);
//        app.displayBooks(authorsBooks);
    }
    public List<Book> groupByGenre(String genre){
        return books = bookDao.findByGenre(genre);
    }
    public List<Book> groupByRating(double rating) {
        return books = bookDao.findByRating(rating);
    }
    public void getBookByIsbn(String book_isbn){
        Optional<Book> optionalBook = bookDao.findById(book_isbn);
        optionalBook.ifPresentOrElse(
                this::displayBook,
                () -> System.out.println("Book not found.")
        );
    }
    public void getBookByTitle(String book_title){
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
            displayBook(b);
        }
    }

    public void displayBook(Book book) {
        System.out.printf(
                "\nBook ISBN: %s\nBook title: %s\nAuthor: %s\nGenre: %s",
                book.isbn(), book.book_title(), book.author(), book.genre()
        );
    }

    public void exampleUsage(){
//        App app = new App();
//        Example usage:
//        app.findAuthor("Ursa ShingField");
//        app.bookDao.displayBooks(app.findByGenre("fantasy"));
//        app.bookDao.displayBooks(app.findByRating(10));
//        app.getBookByTitle("Eggs");
//        app.getBookByIsbn("397164487-2");
//        app.displayBooks(app.bookDao.findAll());
//        app.newBook();
    }
    public static void main(String[] args) {
        App app = new App();
        app.addNewBook(
                "074753849-2", "Harry Potter and the Chamber of Secrets","J. K. Rowling", "fantasy",
                "1998-07-02", "Bloomsburry", 251, "english", "Hardback", "eBook",
                7.99f, 9.4f);
        app.displayBooks(app.groupByAuthor("J. K. Rowling"));
//        app.displayBooks(app.bookDao.findAll());
    }
}   