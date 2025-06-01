package com.pluralsight.model;

import java.util.Date;

public record Book(
        String isbn,
        String book_title,
        String author,
        String genre,
        Date publication_date,
        String publisher,
        int page_count,
        String language,
        String format,
        String avail_format,
        float price,
        float rating) {

    public static class Builder {
        String isbn;
        String book_title, author, genre, publisher, language, format, avail_format;
        Date publication_date;
        int page_count;
        float price, rating;

        public Builder id(String isbn){
            this.isbn = isbn;
            return this;
        }
        public Builder title(String book_title) {
            this.book_title = book_title;
            return this;
        }
        public Builder author(String author){
            this.author = author;
            return this;
        }
        public Builder genre(String genre) {
            this.book_title = book_title;
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

        public Book build(){
            return new Book(
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
                    this.rating);
        }

    }

}
