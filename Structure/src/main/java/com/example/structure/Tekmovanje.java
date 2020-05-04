package com.example.structure;

import java.time.LocalDate;
import java.util.List;

public class Tekmovanje {
    private Boolean Esport;
    private String Naslov;
    private String Opis;
    private String Lokacija;
    private String StEkip;
    private LocalDate Zacetek;
    private LocalDate Konec;
    private List<Tekma> Tekme;

    @Override
    public String toString() {
        String IzpisTekem = "";
        for(int i=0; i<Tekme.size();i++) {
            IzpisTekem = Tekme.get(i).toString() + "\n";
        }
        return "Tekmovanje{" +
                "Esport=" + Esport +
                ", Naslov='" + Naslov + '\'' +
                ", Opis='" + Opis + '\'' +
                ", Lokacija='" + Lokacija + '\'' +
                ", StEkip='" + StEkip + '\'' +
                ", Zacetek=" + Zacetek +
                ", Konec=" + Konec +
                ", Tekme=" + IzpisTekem +
                    '}';
        }

    public void setEsport(Boolean esport) {
        Esport = esport;
    }

    public Tekmovanje(Boolean esport, String naslov, String opis, String lokacija, String stEkip, LocalDate zacetek, LocalDate konec, List<Tekma> tekme) {
        Esport = esport;
        this.Naslov = naslov;
        Opis = opis;
        Lokacija = lokacija;
        this.StEkip = stEkip;
        Zacetek = zacetek;
        Konec = konec;
        Tekme = tekme;
    }

    public void setNaslov(String naslov) {
        this.Naslov = naslov;
    }

    public void setOpis(String opis) {
        Opis = opis;
    }

    public void setLokacija(String lokacija) {
        Lokacija = lokacija;
    }

    public void setStEkip(String stEkip) {
        this.StEkip = stEkip;
    }

    public void setZacetek(LocalDate zacetek) {
        Zacetek = zacetek;
    }

    public void setKonec(LocalDate konec) {
        Konec = konec;
    }

    public void setTekme(List<Tekma> tekme) {
        Tekme = tekme;
    }

    public Boolean getEsport() {
        return Esport;
    }

    public String getNaslov() {
        return Naslov;
    }

    public String getOpis() {
        return Opis;
    }

    public String getLokacija() {
        return Lokacija;
    }

    public String getStEkip() {
        return StEkip;
    }

    public LocalDate getZacetek() {
        return Zacetek;
    }

    public LocalDate getKonec() {
        return Konec;
    }

    public List<Tekma> getTekme() {
        return Tekme;
    }
}
