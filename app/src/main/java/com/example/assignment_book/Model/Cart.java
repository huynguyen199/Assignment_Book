package com.example.assignment_book.Model;

public class Cart {
    private String id;
    private String name;
    private String book_id;
    private String title;
    private String price;
    private String authors;
    private String categories_id;
    private String status;
    private String message;
    private String amount;


    public Cart() {
    }

    public Cart(String id, String name, String book_id, String title, String price, String authors, String categories_id, String status, String message) {
        this.id = id;
        this.name = name;
        this.book_id = book_id;
        this.title = title;
        this.price = price;
        this.authors = authors;
        this.categories_id = categories_id;
        this.status = status;
        this.message = message;
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

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getCategories_id() {
        return categories_id;
    }

    public void setCategories_id(String categories_id) {
        this.categories_id = categories_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
