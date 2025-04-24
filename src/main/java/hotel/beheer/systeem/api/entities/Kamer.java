package hotel.beheer.systeem.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;



@Entity
@Table(name = "kamer" , schema = "hotelbeheersysteemapi")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Kamer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "kamertype", nullable = false)
    private KamerType kamertype;

    @Column(name = "aantalbedden", nullable = false)
    private int aantalbedden;

    @Column(name = "prijspermaand", nullable = false)
    private double prijsPerMaand;

    public enum KamerType {
        goedkoop, normaal, deftig
    } // enums met kleinletter want ze worden met kleinletters opgeslagen dus dan moet je ook kleinletters gebruiken in JAVA
    // want als je die informatie wilt ophalen kan je errors krijgem als van java met hoofdletter is en
    // van sql kleinletter is

    public Kamer() {}


    public Kamer(int id, KamerType kamertype, int aantalbedden, double prijsPerMaand) {
        this.id = id;
        this.kamertype = kamertype;
        this.aantalbedden = aantalbedden;
        this.prijsPerMaand = prijsPerMaand;
    }

    public Kamer(KamerType kamertype, int aantalbedden, double prijsPerMaand) {
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
        return "Kamer{" +
                "id=" + id +
                ", kamertype=" + kamertype +
                ", aantalbedden=" + aantalbedden +
                ", prijsPerMaand=" + prijsPerMaand +
                '}';
    }
}