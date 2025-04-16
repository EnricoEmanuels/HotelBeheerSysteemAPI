package hotel.beheer.systeem.api.entities;

import jakarta.persistence.*;


import java.util.Date;


@Entity
@Table(name = "kamersboeken" , schema = "hotelbeheersysteemapi")
public class KamersBoeken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "startdatum", nullable = false)
    private Date startdatum;

    @Column(name = "einddatum", nullable = false)
    private Date einddatum;

//    @Column(name = "totaalbedrag", nullable = false)
//    private double totaalbedrag;
//
//    @Column(name = "betaald", nullable = false, length = 10)
//    private String betaald;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "klant_id", referencedColumnName = "id")
//    @Column(name = "klant_id", nullable = false)
    private Klant klant;

//    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "beschikbarekamer_id", nullable = false , referencedColumnName = "id")
    private BeschikbareKamer beschikbareKamer;

//    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
//    @JoinColumn(name = "betaalmethode_id", referencedColumnName = "id")


    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn(name = "betaalmethode_id", nullable = false)
    private Betaalmethode betaalmethodes;

    public KamersBoeken() {}


    public KamersBoeken(Integer id, Date startdatum, Date einddatum,  Klant klant , BeschikbareKamer beschikbareKamer, Betaalmethode betaalmethodes) {
        this.id = id;
        this.startdatum = startdatum;
        this.einddatum = einddatum;

        this.klant = klant;
        this.beschikbareKamer = beschikbareKamer;
        this.betaalmethodes = betaalmethodes;
    }

    public KamersBoeken(Date startdatum, Date einddatum,  Klant klant , BeschikbareKamer beschikbareKamer, Betaalmethode betaalmethodes) {
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



//    public void setTotaalbedragViaKamer(Kamer totaalbedrag) {
//        this.totaalbedrag = totaalbedrag;
//    }



    public Klant getKlant() {
        return klant;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }

//    public Kamer getKamer() {
//        return kamer;
//    }
//
//    public void setKamer(Kamer kamer) {
//        this.kamer = kamer;
//    }

    public BeschikbareKamer getBeschikbareKamer() {
        return beschikbareKamer;
    }

    public void setBeschikbareKamer(BeschikbareKamer beschikbareKamer) {
        this.beschikbareKamer = beschikbareKamer;
    }

//    public Betaalmethode getHuidigebetaalmethodes() {
//        return huidigebetaalmethodes;
//    }
//
//    public void setHuidigebetaalmethodes(Betaalmethode huidigebetaalmethodes) {
//        this.huidigebetaalmethodes = huidigebetaalmethodes;
//    }


    public Betaalmethode getBetaalmethodes() {
        return betaalmethodes;
    }

    public void setBetaalmethodes(Betaalmethode betaalmethodes) {
        this.betaalmethodes = betaalmethodes;
    }



//    @Override
//    public String toString() {
//        return "KamersBoeken{" +
//                "id=" + id +
//                ", startdatum=" + startdatum +
//                ", einddatum=" + einddatum +
//                ", totaalbedrag=" + totaalbedrag +
//                ", betaald='" + betaald + '\'' +
//                ", klant=" + klant +
//                ", beschikbareKamer=" + beschikbareKamer +
//                ", betaalmethodes=" + betaalmethodes +
//                '}';
//    }



    @Override
    public String toString() {
        return "KamersBoeken{" +
                "id=" + id +
                ", startdatum=" + startdatum +
                ", einddatum=" + einddatum +
                ", klant=" + (klant != null ? klant.getId() : null) +
                ", beschikbareKamer=" + (beschikbareKamer != null ? beschikbareKamer.getId() : null)  +
                ", betaalmethodes=" + (betaalmethodes != null ? betaalmethodes.getId() : null) +
                '}';
    }


}