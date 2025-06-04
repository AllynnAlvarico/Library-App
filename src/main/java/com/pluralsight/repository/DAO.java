package com.pluralsight.repository;

import com.pluralsight.model.Book;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> findById(String id);
    List<T> findAll();
    Optional<T> findBook(String s);
    List<T> findByGenre(String s);
    List<T> findAuthor(String s);
    List<T> findByRating(double d);
    boolean existsByIsbn(String isbn);
    T create(T t);
    void save(T t);
    T update(T t);
    int[] update(List<T> t);
    int delete(T t);
    int delete(List<T> t);


}
