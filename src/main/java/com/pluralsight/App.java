package com.pluralsight;

import com.pluralsight.model.Book;
import com.pluralsight.repository.BookDao;
import com.pluralsight.repository.DAO;

import java.util.List;

public class App {
    public static void main(String[] args) {

        DAO<Book> bookDao = new BookDao();

        List<Book> books = bookDao.findAll();

        for (Book b : books){
            System.out.println("Book number: " + b.id());
            System.out.println("Book title: " + b.title());
        }


    }
}   