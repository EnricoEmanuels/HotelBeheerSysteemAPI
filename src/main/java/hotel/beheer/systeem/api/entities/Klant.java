package hotel.beheer.systeem.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;



@Entity
@Table(name = "klant" , schema = "hotelbeheersysteemapi")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Klant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "voornaam", nullable = false, length = 255)
    private String voornaam;

    @Column(name = "achternaam", nullable = false, length = 255)
    private String achternaam;

    @Column(name = "telefoon", nullable = false, length = 255)
    private String telefoon;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "balans", nullable = false)
    private double balans;

    @ManyToOne( optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "betaalmethode_id")
    private Betaalmethode betaalmethode;

    public Klant() {

    }

    public Klant(Integer id, Betaalmethode betaalmethode) {
        this.id = id;
        this.betaalmethode = betaalmethode;
    }

    public Klant(int id, String voornaam, String achternaam, String telefoon, String email, double balans) {
        this.id = id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.telefoon = telefoon;
        this.email = email;
        this.balans = balans;
    }

    public Klant(int id, String voornaam, String achternaam, String telefoon, String email, double balans, Betaalmethode betaalmethode) {
        this.id = id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.telefoon = telefoon;
        this.email = email;
        this.balans = balans;
        this.betaalmethode = betaalmethode;
    }

    public Klant(String voornaam, String achternaam, String telefoon, String email, double balans) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.telefoon = telefoon;
        this.email = email;
        this.balans = balans;
    }

    public Klant(String voornaam, String achternaam, String telefoon, String email, double balans, Betaalmethode betaalmethode) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.telefoon = telefoon;
        this.email = email;
        this.balans = balans;
        this.betaalmethode = betaalmethode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return "Klant{" +
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