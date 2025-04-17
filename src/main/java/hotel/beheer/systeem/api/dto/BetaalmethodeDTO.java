package hotel.beheer.systeem.api.dto;

import hotel.beheer.systeem.api.entities.Betaalmethode.MethodeType;
import hotel.beheer.systeem.api.entities.Klant;
import jakarta.persistence.*;



import java.util.Date;

public class BetaalmethodeDTO {
    private Integer id;
    private MethodeType methode;
    private Date datum;
    private Klant klant;

    public BetaalmethodeDTO(Integer id, MethodeType methode, Date datum, Klant klant) {
        this.id = id;
        this.methode = methode;
        this.datum = datum;
        this.klant = klant;
    }

    public BetaalmethodeDTO(MethodeType methode, Date datum, Klant klant) {
        this.methode = methode;
        this.datum = datum;
        this.klant = klant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MethodeType getMethode() {
        return methode;
    }

    public void setMethode(MethodeType methode) {
        this.methode = methode;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Klant getKlant() {
        return klant;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    @Override
    public String toString() {
        return "BetaalmethodeDTO{" +
                "id=" + id +
                ", methode=" + methode +
                ", datum=" + datum +
                ", klant=" + klant +
                '}';
    }
}
