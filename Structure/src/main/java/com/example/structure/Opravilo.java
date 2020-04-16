package com.example.structure;

import java.time.LocalDate;

public class Opravilo {
    private String Naslov;
    private String Opis;
    private LocalDate StartDate;
    private LocalDate EndDate;
    private Boolean Imporatant;

    public Opravilo(String naslov, String opis, LocalDate startDate, LocalDate endDate, Boolean imporatant) {
        Naslov = naslov;
        Opis = opis;
        StartDate = startDate;
        EndDate = endDate;
        Imporatant = imporatant;
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

    public Boolean getImporatant() {
        return Imporatant;
    }

    public void setImporatant(Boolean imporatant) {
        Imporatant = imporatant;
    }
}
