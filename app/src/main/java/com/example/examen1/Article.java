package com.example.examen1;

public class Article {
    private String title;
    private String section;
    private String doi;
    private String abstractText;

    public Article(String title, String section, String doi, String abstractText) {
        this.title = title;
        this.section = section;
        this.doi = doi;
        this.abstractText = abstractText;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getDoi() {
        return doi;
    }

    public String getAbstractText() {
        return abstractText;
    }
}
