package hotel.beheer.systeem.api.mappers;

import hotel.beheer.systeem.api.dto.KamerDTO;
import hotel.beheer.systeem.api.entities.Kamer;

public class KamerMapper {
    public KamerDTO toKamerDTO(Kamer kamer) {
        if (kamer == null) {
            return null;
        }
        return new KamerDTO(
                kamer.getId(),
                kamer.getKamertype(),
                kamer.getAantalbedden(),
                kamer.getPrijsPerMaand()
        );
    }
    public Kamer toKamerEntity(KamerDTO kamerDTO) {
        if (kamerDTO == null) {
            return null;
        }
        Kamer kamer = new Kamer();
        kamer.setId(kamerDTO.getId());
        kamer.setKamertype(kamerDTO.getKamertype());
        kamer.setAantalbedden(kamerDTO.getAantalbedden());
        kamer.setPrijsPerMaand(kamerDTO.getPrijsPerMaand());
        return kamer;
    }
}


