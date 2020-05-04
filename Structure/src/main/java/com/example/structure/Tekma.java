package com.example.structure;

import java.time.LocalDate;

public class Tekma {
    private String Naslov;
    private String Opis;
    private LocalDate StartDate;
    private LocalDate EndDate;
    private Boolean Important;

    @Override
    public String toString() {
        return "Opravilo{" +
                "Naslov='" + Naslov + '\'' +
                ", Opis='" + Opis + '\'' +
                ", StartDate=" + StartDate.toString() +
                ", EndDate=" + EndDate.toString() +
                ", Important=" + Important +
                '}';
    }

    public Tekma(String naslov, String opis, LocalDate startDate, LocalDate endDate, Boolean important) {
        Naslov = naslov;
        Opis = opis;
        StartDate = startDate;
        EndDate = endDate;
        Important = important;
    }

    public String getNaslov() {
        return Naslov;
    }

    public void setNaslov(String naslov) {
        Naslov = naslov;
    }

    public String getOpis() {
        return Opis;
    }

    public void setOpis(String opis) {
        Opis = opis;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
    }

    public Boolean getImportant() {
        return Important;
    }

    public void setImportant(Boolean important) {
        Important = important;
    }
}
