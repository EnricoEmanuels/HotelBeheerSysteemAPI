package hotel.beheer.systeem.api.dto;

import hotel.beheer.systeem.api.entities.Betaalmethode;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

import java.util.Date;

public class BetaalmethodeCreditcardDTO {

    private Integer id;
    private Betaalmethode betaalmethode;
    private String volledigeNaam;
    private String kaartnummer;
    private Date vervaldatum;
    private String cvv;

    public BetaalmethodeCreditcardDTO(Integer id, String volledigeNaam, String kaartnummer, Date vervaldatum, String cvv) {
        this.id = id;
        this.volledigeNaam = volledigeNaam;
        this.kaartnummer = kaartnummer;
        this.vervaldatum = vervaldatum;
        this.cvv = cvv;
    }

    public BetaalmethodeCreditcardDTO( Betaalmethode betaalmethode, String volledigeNaam, String kaartnummer, Date vervaldatum, String cvv) {
        this.betaalmethode = betaalmethode;
        this.volledigeNaam = volledigeNaam;
        this.kaartnummer = kaartnummer;
        this.vervaldatum = vervaldatum;
        this.cvv = cvv;
    }

    public BetaalmethodeCreditcardDTO( String volledigeNaam, String kaartnummer, Date vervaldatum, String cvv) {
        this.volledigeNaam = volledigeNaam;
        this.kaartnummer = kaartnummer;
        this.vervaldatum = vervaldatum;
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

    @Override
    public String toString() {
        return "BetaalmethodeCreditcardDTO{" +
                "id=" + id +
                ", betaalmethode=" + betaalmethode +
                ", volledigeNaam='" + volledigeNaam + '\'' +
                ", kaartnummer='" + kaartnummer + '\'' +
                ", vervaldatum=" + vervaldatum +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}
