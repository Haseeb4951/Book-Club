package com.example.bookclubapplication.models;

public class Category {
    private String id;
    private String category;
    private String uid;
    private long timestamp;
    private String bookTitle;
    private String bookDescription;

    public Category() {
    }

    public Category(String id, String category, String uid, long timestamp, String bookTitle, String bookDescription) {
        this.id = id;
        this.category = category;
        this.uid = uid;
        this.timestamp = timestamp;
        this.bookTitle = bookTitle;
        this.bookDescription = bookDescription;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getUid() {
        return uid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }
}




