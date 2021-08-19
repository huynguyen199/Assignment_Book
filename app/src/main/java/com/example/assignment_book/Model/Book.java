package com.example.assignment_book.Model;

public class Book {
    private String id;
    private String name;
    private String title;
    private int price;
    private String author;
    private String image;
    private String categories;

    public Book() {
    }

    public Book(String id, String name, String title, int price, String author, String image, String categories) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.price = price;
        this.author = author;
        this.image = image;
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
