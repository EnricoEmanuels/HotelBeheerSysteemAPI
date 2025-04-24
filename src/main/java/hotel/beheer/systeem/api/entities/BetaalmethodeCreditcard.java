package hotel.beheer.systeem.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Date;


@Entity
@Table(name = "betaalmethodecreditcard" , schema = "hotelbeheersysteemapi")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class BetaalmethodeCreditcard {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) je moet handmatig die gegenereerde ID van betaalmethode plaatsen als ze voor creditcard kiezen bij die betaalmethode ENUM
//    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Betaalmethode betaalmethode;

    @Column(name = "volledigenaam", nullable = false, length = 255)
    private String volledigeNaam;

    @Column(name = "kaartnummer", nullable = false, length = 255)
    private String kaartnummer;

    @Column(name = "vervaldatum", nullable = false)
    private Date vervaldatum;

    @Column(name = "cvv", nullable = false, length = 4)
    private String cvv;

    public BetaalmethodeCreditcard() {}

    public BetaalmethodeCreditcard(Integer id, String volledigeNaam, String kaartnummer, Date vervaldatum, String cvv) {
        this.id = id;
        this.volledigeNaam = volledigeNaam;
        this.kaartnummer = kaartnummer;
        this.vervaldatum = vervaldatum;
        this.cvv = cvv;
    }

    public BetaalmethodeCreditcard(Betaalmethode betaalmethode, String volledigeNaam, String kaartnummer, Date vervaldatum, String cvv) {
        this.betaalmethode = betaalmethode;
        this.volledigeNaam = volledigeNaam;
        this.kaartnummer = kaartnummer;
        this.vervaldatum = vervaldatum;
        this.cvv = cvv;
    }
    public BetaalmethodeCreditcard(String volledigeNaam, String kaartnummer, Date vervaldatum, String cvv) {
        this.volledigeNaam = volledigeNaam;
        this.kaartnummer = kaartnummer;
        this.vervaldatum = vervaldatum;
        this.cvv = cvv;
    }

    public String getVolledigeNaam() {
        return volledigeNaam;
    }

    public void setVolledigeNaam(String volledigeNaam) {
        this.volledigeNaam = volledigeNaam;
    }

    public String getKaartnummer() {
        return kaartnummer;
    }

    public void setKaartnummer(String kaartnummer) {
        this.kaartnummer = kaartnummer;
    }

    public Date getVervaldatum() {
        return vervaldatum;
    }

    public void setVervaldatum(Date vervaldatum) {
        this.vervaldatum = vervaldatum;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Betaalmethode getBetaalmethode() {
        return betaalmethode;
    }

    public void setBetaalmethode(Betaalmethode betaalmethode) {
        this.betaalmethode = betaalmethode;
    }



    @Override
    public String toString() {
        return "BetaalmethodeCreditcard{" +
                "id=" + id +
                ", betaalmethode=" + betaalmethode +
                ", volledigeNaam='" + volledigeNaam + '\'' +
                ", kaartnummer='" + kaartnummer + '\'' +
                ", vervaldatum=" + vervaldatum +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}

