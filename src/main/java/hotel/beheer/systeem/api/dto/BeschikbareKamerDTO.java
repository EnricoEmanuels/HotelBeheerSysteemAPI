package hotel.beheer.systeem.api.dto;

import hotel.beheer.systeem.api.entities.BeschikbareKamer.BeschikbareKamerAlternatief;
import hotel.beheer.systeem.api.entities.Kamer;
import jakarta.persistence.*;

public class BeschikbareKamerDTO {
    private Integer id;
    private BeschikbareKamerAlternatief beschikbareKamerAlternatief;
    private Kamer kamer;

    public BeschikbareKamerDTO(Integer id, BeschikbareKamerAlternatief beschikbareKamerAlternatief, Kamer kamer) {
        this.id = id;
        this.beschikbareKamerAlternatief = beschikbareKamerAlternatief;
        this.kamer = kamer;
    }

    public BeschikbareKamerDTO( BeschikbareKamerAlternatief beschikbareKamerAlternatief, Kamer kamer) {
        this.beschikbareKamerAlternatief = beschikbareKamerAlternatief;
        this.kamer = kamer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BeschikbareKamerAlternatief getBeschikbareKamerAlternatief() {
        return beschikbareKamerAlternatief;
    }

    public void setBeschikbareKamerAlternatief(BeschikbareKamerAlternatief beschikbareKamerAlternatief) {
        this.beschikbareKamerAlternatief = beschikbareKamerAlternatief;
    }

    public Kamer getKamer() {
        return kamer;
    }

    public void setKamer(Kamer kamer) {
        this.kamer = kamer;
    }

    @Override
    public String toString() {
        return "BeschikbareKamerDTO{" +
                "id=" + id +
                ", beschikbareKamerAlternatief=" + beschikbareKamerAlternatief +
                ", kamer=" + kamer +
                '}';
    }
}
