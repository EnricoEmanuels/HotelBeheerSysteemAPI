package hotel.beheer.systeem.api.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "betaalmethode" , schema = "hotelbeheersysteemapi")

@Inheritance(strategy = InheritanceType.JOINED)
public class Betaalmethode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "methode", nullable = false)
    private MethodeType methode;

    @Column(name = "datum", nullable = false)
    private Date datum;


    @ManyToOne( optional = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "klant_id", nullable = false)
    private Klant klant;

//    @ManyToOne( optional = false)
//    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
//    @JoinColumn(name = "betaalmethode_id", nullable = false)
//    private Betaalmethode betaalmethode;

//    @OneToMany(mappedBy = "klant", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Bestelling> bestellingen = new HashSet<>();

    @OneToMany(mappedBy = "betaalmethodes", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<KamersBoeken> kamersBoeken = new HashSet<>();

    public enum MethodeType {
        crypto, creditcard, contant
    } // je moet klein letter gebruiken voor je enums anders want het wordt als kleinletter opgeslagen en als je ze
    // weer wilt oproepen ga je errors krijgen want java is CASE Sensitive SQL (crypto) en JAVA (CRYPTO) is niet hetzelfde

    public Betaalmethode() {

    }


    public Betaalmethode(Integer id, MethodeType methode, Date datum, Klant klant) {
        this.id = id;
        this.methode = methode;
        this.datum = datum;
        this.klant = klant;
    }

    public Betaalmethode(MethodeType methode, Date datum, Klant klant) {
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



    public Set<KamersBoeken> getKamersBoeken() {
        return kamersBoeken;
    }

    public void setKamersBoeken(Set<KamersBoeken> kamersBoeken) {
        this.kamersBoeken = kamersBoeken;
    }

    @Override
    public String toString() {
        return "Betaalmethode{" +
                "id=" + id +
                ", methode=" + methode +
                ", datum=" + datum +
                ", klant=" + klant +
                ", kamersBoeken=" + kamersBoeken +
                '}';
    }
}

