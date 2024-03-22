package com.example.bookclubapplication.models;

public class Pdf {
    private String id;
    private String title;
    private String description;
    private String category;
    private String fileUrl;

    public Pdf() {
    }

    public Pdf(String id, String title, String description, String category, String fileUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.fileUrl = fileUrl;
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

    public String getCategory() {
        return category;
    }

    public String getFileUrl() {
        return fileUrl;
    }
}




