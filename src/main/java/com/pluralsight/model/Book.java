package com.pluralsight.model;

public record Book(long id, String title) {

    public static class Builder {
        long id;
        String title;
        public Builder id(long id){
            this.id = id;
            return this;
        }
        public Builder title(String title) {
            this.title = title;
            return this;
        }
        public Book build(){
            return new Book(this.id, this.title);
        }

    }

}
