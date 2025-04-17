package hotel.beheer.systeem.api.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "betaalmethodecontant" , schema = "hotelbeheersysteemapi")

public class BetaalmethodeContant {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) Handmatig die ID zetten van die methode als ze kiezen voor contant in die ENUM
//    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Betaalmethode betaalmethode;

    @Column(name = "valuta", nullable = false, length = 255)
    private String valuta;

    public BetaalmethodeContant() {}

    public BetaalmethodeContant(Integer id, String valuta) {
        this.id = id;
        this.valuta = valuta;
    }

    public BetaalmethodeContant(Betaalmethode betaalmethode, String valuta) {
        this.betaalmethode = betaalmethode;
        this.valuta = valuta;
    }
    public BetaalmethodeContant(String valuta) {
        this.valuta = valuta;
    }


    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
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
        return "BetaalmethodeContant{" +
                "id=" + id +
                ", betaalmethode=" + betaalmethode +
                ", valuta='" + valuta + '\'' +
                '}';
    }
}

