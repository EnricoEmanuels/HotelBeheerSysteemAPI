package hotel.beheer.systeem.api.dto;

import hotel.beheer.systeem.api.entities.Betaalmethode;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

public class BetaalmethodeContantDTO {

    private Integer id;
    private Betaalmethode betaalmethode;
    private String valuta;

    public BetaalmethodeContantDTO(Integer id, String valuta) {
        this.id = id;
        this.valuta = valuta;
    }
    public BetaalmethodeContantDTO( Betaalmethode betaalmethode, String valuta) {
        this.betaalmethode = betaalmethode;
        this.valuta = valuta;
    }

    public BetaalmethodeContantDTO(String valuta) {
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

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    @Override
    public String toString() {
        return "BetaalmethodeContantDTO{" +
                "id=" + id +
                ", betaalmethode=" + betaalmethode +
                ", valuta='" + valuta + '\'' +
                '}';
    }
}
