package com.example.bookclubapplication.models;

public class Book {
    private String id;
    private String title;
    private String description;
    private String categoryId;
    private String fileUrl;
    private long timestamp;

    public Book() {
    }

    public Book(String id, String title, String description, String categoryId, String fileUrl, long timestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.fileUrl = fileUrl;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

