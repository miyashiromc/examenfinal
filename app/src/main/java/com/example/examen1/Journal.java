package com.example.examen1;

public class Journal {
    private String journalId;
    private String name;
    private String abbreviation;
    private String description;
    private String portada;

    public Journal(String journalId, String name, String abbreviation, String description, String portada) {
        this.journalId = journalId;
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
        this.portada = portada;
    }

    public String getJournalId() {
        return journalId;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public String getPortada() {
        return portada;
    }
}
