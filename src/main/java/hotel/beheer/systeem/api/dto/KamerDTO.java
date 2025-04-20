package hotel.beheer.systeem.api.dto;

import hotel.beheer.systeem.api.entities.Kamer;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import hotel.beheer.systeem.api.entities.Kamer.KamerType;

public class KamerDTO {
    private int id;
    private KamerType kamertype;
    private int aantalbedden;
    private double prijsPerMaand;

    public KamerDTO(int id, KamerType kamertype, int aantalbedden, double prijsPerMaand) {
        this.id = id;
        this.kamertype = kamertype;
        this.aantalbedden = aantalbedden;
        this.prijsPerMaand = prijsPerMaand;
    }

    public KamerDTO(KamerType kamertype, int aantalbedden, double prijsPerMaand) {
        this.kamertype = kamertype;
        this.aantalbedden = aantalbedden;
        this.prijsPerMaand = prijsPerMaand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public KamerType getKamertype() {
        return kamertype;
    }

    public void setKamertype(KamerType kamertype) {
        this.kamertype = kamertype;
    }

    public int getAantalbedden() {
        return aantalbedden;
    }

    public void setAantalbedden(int aantalbedden) {
        this.aantalbedden = aantalbedden;
    }

    public double getPrijsPerMaand() {
        return prijsPerMaand;
    }

    public void setPrijsPerMaand(double prijsPerMaand) {
        this.prijsPerMaand = prijsPerMaand;
    }

    @Override
    public String toString() {
        return "KamerDTO{" +
                "id=" + id +
                ", kamertype=" + kamertype +
                ", aantalbedden=" + aantalbedden +
                ", prijsPerMaand=" + prijsPerMaand +
                '}';
    }
}
