package hotel.beheer.systeem.api.mappers;

import hotel.beheer.systeem.api.dto.BeschikbareKamerDTO;
import hotel.beheer.systeem.api.dto.KlantDTO;
import hotel.beheer.systeem.api.entities.BeschikbareKamer;
import hotel.beheer.systeem.api.entities.Klant;

public class BeschikbareKamerMapper {
    public BeschikbareKamerDTO beschikbareKamerDTO(BeschikbareKamer beschikbareKamer) {
        if (beschikbareKamer == null) {
            return null;
        }
        return new BeschikbareKamerDTO(
                beschikbareKamer.getId(),
                beschikbareKamer.getBeschikbareKamerAlternatief(),
                beschikbareKamer.getKamer()
        );
    }

    public BeschikbareKamer toBeschikbareKamerEntity(BeschikbareKamerDTO beschikbareKamerDTO) {
        if (beschikbareKamerDTO == null) {
            return null;
        }
        BeschikbareKamer beschikbareKamer = new BeschikbareKamer();
        beschikbareKamer.setId(beschikbareKamerDTO.getId());
        beschikbareKamer.setBeschikbareKamerAlternatief(beschikbareKamerDTO.getBeschikbareKamerAlternatief());
        beschikbareKamer.setKamer(beschikbareKamerDTO.getKamer());
        return beschikbareKamer;
    }
}


