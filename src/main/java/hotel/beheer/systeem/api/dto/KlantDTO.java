package hotel.beheer.systeem.api.dto;

import hotel.beheer.systeem.api.entities.Betaalmethode;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

public class KlantDTO {
    private Integer id;
    private String voornaam;
    private String achternaam;
    private String telefoon;
    private String email;
    private double balans;
    private Betaalmethode betaalmethode;

    public KlantDTO(Integer id, String voornaam, String achternaam, String telefoon, String email, double balans, Betaalmethode betaalmethode) {
        this.id = id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.telefoon = telefoon;
        this.email = email;
        this.balans = balans;
        this.betaalmethode = betaalmethode;
    }

    public KlantDTO(String voornaam, String achternaam, String telefoon, String email, double balans, Betaalmethode betaalmethode) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.telefoon = telefoon;
        this.email = email;
        this.balans = balans;
        this.betaalmethode = betaalmethode;
    }

    public KlantDTO(Integer id, String voornaam, String achternaam, String telefoon, String email, double balans) {
        this.id = id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.telefoon = telefoon;
        this.email = email;
        this.balans = balans;
    }

    public KlantDTO(String voornaam, String achternaam, String telefoon, String email, double balans) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.telefoon = telefoon;
        this.email = email;
        this.balans = balans;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getTelefoon() {
        return telefoon;
    }

    public void setTelefoon(String telefoon) {
        this.telefoon = telefoon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalans() {
        return balans;
    }

    public void setBalans(double balans) {
        this.balans = balans;
    }

    public Betaalmethode getBetaalmethode() {
        return betaalmethode;
    }

    public void setBetaalmethode(Betaalmethode betaalmethode) {
        this.betaalmethode = betaalmethode;
    }

    @Override
    public String toString() {
        return "KlantDTO{" +
                "id=" + id +
                ", voornaam='" + voornaam + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", telefoon='" + telefoon + '\'' +
                ", email='" + email + '\'' +
                ", balans=" + balans +
                ", betaalmethode=" + betaalmethode +
                '}';
    }
}
