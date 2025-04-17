package hotel.beheer.systeem.api.dto;

import hotel.beheer.systeem.api.entities.Betaalmethode;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

public class BetaalmethodeCryptoDTO {

    private Integer id;
    private Betaalmethode betaalmethode;
    private String walletAdres;
    private String muntsoort;

    public BetaalmethodeCryptoDTO(Integer id, String walletAdres, String muntsoort) {
        this.id = id;
        this.walletAdres = walletAdres;
        this.muntsoort = muntsoort;
    }
    public BetaalmethodeCryptoDTO(Betaalmethode betaalmethode, String walletAdres, String muntsoort) {
        this.betaalmethode = betaalmethode;
        this.walletAdres = walletAdres;
        this.muntsoort = muntsoort;
    }

    public BetaalmethodeCryptoDTO(String walletAdres, String muntsoort) {
        this.walletAdres = walletAdres;
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

    @Override
    public String toString() {
        return "BetaalmethodeCryptoDTO{" +
                "id=" + id +
                ", betaalmethode=" + betaalmethode +
                ", walletAdres='" + walletAdres + '\'' +
                ", muntsoort='" + muntsoort + '\'' +
                '}';
    }
}
