package com.example.structure;

import java.time.LocalDate;

public class Uporabnik {
    private String Ime;
    private String Priimek;
    private LocalDate DatumRojstva;
    private String Eposta;
    private String TelStevilka;

    public Uporabnik(String ime, String priimek, LocalDate datumRojstva, String eposta, String telStevilka) {
        Ime = ime;
        Priimek = priimek;
        DatumRojstva = datumRojstva;
        Eposta = eposta;
        TelStevilka = telStevilka;
    }

    public String getIme() {
        return Ime;
    }

    public void setIme(String ime) {
        Ime = ime;
    }

    public String getPriimek() {
        return Priimek;
    }

    public void setPriimek(String priimek) {
        Priimek = priimek;
    }

    public LocalDate getDatumRojstva() {
        return DatumRojstva;
    }

    public void setDatumRojstva(LocalDate datumRojstva) {
        DatumRojstva = datumRojstva;
    }

    public String getEposta() {
        return Eposta;
    }

    public void setEposta(String eposta) {
        Eposta = eposta;
    }

    public String getTelStevilka() {
        return TelStevilka;
    }

    public void setTelStevilka(String telStevilka) {
        TelStevilka = telStevilka;
    }
}
