package com.example.examen1;

public class Volume {
    private String volume;
    private String title;
    private String year;
    private String cover;

    public Volume(String volume, String title, String year, String cover) {
        this.volume = volume;
        this.title = title;
        this.year = year;
        this.cover = cover;
    }

    public String getVolume() {
        return volume;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getCover() {
        return cover;
    }
}
