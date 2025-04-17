package hotel.beheer.systeem.api.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "beschikbarekamers" , schema = "hotelbeheersysteemapi")

public class BeschikbareKamer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    // in plaats om te typen of het beschikbaar is kan ik een constante maken
    // met enum = beschikbaar of nier meer beschikbaar
    @Enumerated(EnumType.STRING)
    @Column(name = "beschikbarekameralternatief", nullable = false)
    private BeschikbareKamerAlternatief beschikbareKamerAlternatief;

//    @Column(name = "beschikbaar", nullable = false, length = 255)
//    private String beschikbaar;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "kamer_id", referencedColumnName = "id")
    private Kamer kamer;

    public enum BeschikbareKamerAlternatief {
        beschikbaar,
        nietbeschikbaar
    }

    public BeschikbareKamer() {}

    public BeschikbareKamer(Integer id, BeschikbareKamerAlternatief beschikbareKamerAlternatief, Kamer kamer) {
        this.id = id;
        this.beschikbareKamerAlternatief = beschikbareKamerAlternatief;
        this.kamer = kamer;
    }

    public BeschikbareKamer(BeschikbareKamerAlternatief beschikbareKamerAlternatief, Kamer kamer) {
        this.beschikbareKamerAlternatief = beschikbareKamerAlternatief;
        this.kamer = kamer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public String isBeschikbaar() {
//        return beschikbaar;
//    }
//
//    public void setBeschikbaar(String beschikbaar) {
//        this.beschikbaar = beschikbaar;
//    }

    public Kamer getKamer() {
        return kamer;
    }

    public void setKamer(Kamer kamer) {
        this.kamer = kamer;
    }

    public BeschikbareKamerAlternatief getBeschikbareKamerAlternatief() {
        return beschikbareKamerAlternatief;
    }

    public void setBeschikbareKamerAlternatief(BeschikbareKamerAlternatief beschikbareKamerAlternatief) {
        this.beschikbareKamerAlternatief = beschikbareKamerAlternatief;
    }

    @Override
    public String toString() {
        return "BeschikbareKamer{" +
                "id=" + id +
                ", beschikbareKamerAlternatief=" + beschikbareKamerAlternatief +
                ", kamer=" + kamer +
                '}';
    }
}