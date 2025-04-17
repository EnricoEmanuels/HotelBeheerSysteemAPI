package hotel.beheer.systeem.api.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "betaalmethodecrypto" , schema = "hotelbeheersysteemapi")

public class BetaalmethodeCrypto {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) automatisch die geggenereerde id van methode hier hnadmatig plaatsen
//    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Betaalmethode betaalmethode;

    @Column(name = "walletadres", nullable = false, length = 255)
    private String walletAdres;

    @Column(name = "muntsoort", nullable = false, length = 255)
    private String muntsoort;

//    @OneToOne
//    @JoinColumn(name = "id") // want jouw foreign key is ook de primary key
//    private Betaalmethode betaalmethode;


    public BetaalmethodeCrypto() {}

    public BetaalmethodeCrypto(Integer id, String walletAdres, String muntsoort) {
        this.id = id;
        this.walletAdres = walletAdres;
        this.muntsoort = muntsoort;
    }

    // omdat ik een one to one hier heb moet ik die hele betaalmethode entiteit plaasen
    // in die betaalmethode crypto niet alleen die ID van betaalmethode dat is niet genoeg
    // voor hibernate
    public BetaalmethodeCrypto(Betaalmethode betaalmethode, String walletAdres, String muntsoort) {
        this.betaalmethode = betaalmethode;
        this.walletAdres = walletAdres;
        this.muntsoort = muntsoort;
    }


    public BetaalmethodeCrypto(String walletAdres, String muntsoort) {
        this.walletAdres = walletAdres;
        this.muntsoort = muntsoort;

    }


    public String getWalletAdres() {
        return walletAdres;
    }

    public void setWalletAdres(String walletAdres) {
        this.walletAdres = walletAdres;
    }

    public String getMuntsoort() {
        return muntsoort;
    }

    public void setMuntsoort(String muntsoort) {
        this.muntsoort = muntsoort;
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
        return "BetaalmethodeCrypto{" +
                "id=" + id +
                ", betaalmethode=" + betaalmethode +
                ", walletAdres='" + walletAdres + '\'' +
                ", muntsoort='" + muntsoort + '\'' +
                '}';
    }
}
