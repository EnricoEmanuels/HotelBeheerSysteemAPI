package hotel.beheer.systeem.api.dto;

import hotel.beheer.systeem.api.entities.BeschikbareKamer;
import hotel.beheer.systeem.api.entities.Betaalmethode;
import hotel.beheer.systeem.api.entities.Klant;
import jakarta.persistence.*;

import java.util.Date;

public class KamersBoekenDTO {
    private Integer id;
    private Date startdatum;
    private Date einddatum;
    private Klant klant;
    private BeschikbareKamer beschikbareKamer;
    private Betaalmethode betaalmethodes;

    public KamersBoekenDTO(Integer id, Date startdatum, Date einddatum, Klant klant, BeschikbareKamer beschikbareKamer, Betaalmethode betaalmethodes) {
        this.id = id;
        this.startdatum = startdatum;
        this.einddatum = einddatum;
        this.klant = klant;
        this.beschikbareKamer = beschikbareKamer;
        this.betaalmethodes = betaalmethodes;
    }

    public KamersBoekenDTO( Date startdatum, Date einddatum, Klant klant, BeschikbareKamer beschikbareKamer, Betaalmethode betaalmethodes) {
        this.startdatum = startdatum;
        this.einddatum = einddatum;
        this.klant = klant;
        this.beschikbareKamer = beschikbareKamer;
        this.betaalmethodes = betaalmethodes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(Date startdatum) {
        this.startdatum = startdatum;
    }

    public Date getEinddatum() {
        return einddatum;
    }

    public void setEinddatum(Date einddatum) {
        this.einddatum = einddatum;
    }

    public Klant getKlant() {
        return klant;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    public BeschikbareKamer getBeschikbareKamer() {
        return beschikbareKamer;
    }

    public void setBeschikbareKamer(BeschikbareKamer beschikbareKamer) {
        this.beschikbareKamer = beschikbareKamer;
    }

    public Betaalmethode getBetaalmethodes() {
        return betaalmethodes;
    }

    public void setBetaalmethodes(Betaalmethode betaalmethodes) {
        this.betaalmethodes = betaalmethodes;
    }

    @Override
    public String toString() {
        return "KamersBoekenDTO{" +
                "id=" + id +
                ", startdatum=" + startdatum +
                ", einddatum=" + einddatum +
                ", klant=" + klant +
                ", beschikbareKamer=" + beschikbareKamer +
                ", betaalmethodes=" + betaalmethodes +
                '}';
    }
}
