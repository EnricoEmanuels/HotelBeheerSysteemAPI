package hotel.beheer.systeem.api.mappers;

import hotel.beheer.systeem.api.dto.KamersBoekenDTO;
import hotel.beheer.systeem.api.dto.KlantDTO;
import hotel.beheer.systeem.api.entities.KamersBoeken;
import hotel.beheer.systeem.api.entities.Klant;

public class KamersBoekenMapper {

    public KamersBoekenDTO toKamersBoekenDTO(KamersBoeken kamersBoeken) {
        if (kamersBoeken == null) {
            return null;
        }

        return new KamersBoekenDTO(
                kamersBoeken.getId(),
                kamersBoeken.getStartdatum(),
                kamersBoeken.getEinddatum(),
                kamersBoeken.getKlant(),
                kamersBoeken.getBeschikbareKamer(),
                kamersBoeken.getBetaalmethodes()
        );
    }

    public KamersBoeken toKamersBoekenEntity(KamersBoekenDTO kamersBoekenDTO) {
        if (kamersBoekenDTO == null) {
            return null;
        }

        return new KamersBoeken(
                kamersBoekenDTO.getId(),
                kamersBoekenDTO.getStartdatum(),
                kamersBoekenDTO.getEinddatum(),
                kamersBoekenDTO.getKlant(),
                kamersBoekenDTO.getBeschikbareKamer(),
                kamersBoekenDTO.getBetaalmethodes()
        );
    }
}

