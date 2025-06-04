package com.pluralsight.model;

import java.util.Date;

public record Book(long id, String isbn, String book_title, String author, String genre, Date publication_date,
        String publisher, int page_count, String language, String format, String avail_format,
                   float price, float rating, String coverUrl) {
    public static class Builder {
        long id;
        String isbn;
        String book_title, author, genre, publisher, language, format, avail_format, coverUrl;
        Date publication_date;
        int page_count;
        float price, rating;

        public Builder id(long id){
            this.id = id;
            return this;
        }
        public Builder isbn(String isbn){
            this.isbn = isbn;
            return this;
        }
        public Builder book_title(String book_title) {
            this.book_title = book_title;
            return this;
        }
        public Builder author(String author){
            this.author = author;
            return this;
        }
        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }
        public Builder publication_date(Date publication_date){
            this.publication_date = publication_date;
            return this;
        }
        public Builder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }
        public Builder page_count(int page_count){
            this.page_count = page_count;
            return this;
        }
        public Builder language(String language) {
            this.language = language;
            return this;
        }
        public Builder format(String format) {
            this.format = format;
            return this;
        }
        public Builder avail_format(String avail_format){
            this.avail_format = avail_format;
            return this;
        }
        public Builder price(float price){
            this.price = price;
            return this;
        }
        public Builder rating(float rating) {
            this.rating = rating;
            return this;
        }
        public Builder coverUrl(String coverUrl){
            this.coverUrl = coverUrl;
            return this;
        }

        public Book build(){
            return new Book(
                    this.id,
                    this.isbn,
                    this.book_title,
                    this.author,
                    this.genre,
                    this.publication_date,
                    this.publisher,
                    this.page_count,
                    this.language,
                    this.format,
                    this.avail_format,
                    this.price,
                    this.rating,
                    this.coverUrl
            );
        }
        public static Builder from(Book book) {
            return new Builder()
                    .id(book.id())
                    .isbn(book.isbn())
                    .book_title(book.book_title())
                    .author(book.author())
                    .genre(book.genre())
                    .publication_date(book.publication_date())
                    .publisher(book.publisher())
                    .page_count(book.page_count())
                    .language(book.language())
                    .format(book.format())
                    .avail_format(book.avail_format())
                    .price(book.price())
                    .rating(book.rating())
                    .coverUrl(book.coverUrl());
        }


    }

}
