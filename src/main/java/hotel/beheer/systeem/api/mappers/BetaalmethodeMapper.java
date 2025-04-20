package hotel.beheer.systeem.api.mappers;

import hotel.beheer.systeem.api.dto.BetaalmethodeDTO;
import hotel.beheer.systeem.api.dto.KlantDTO;
import hotel.beheer.systeem.api.entities.Betaalmethode;
import hotel.beheer.systeem.api.entities.Klant;


public class BetaalmethodeMapper {
    // DTO => Entity
    public Betaalmethode toEntity(BetaalmethodeDTO betaalmethodeDTO) {
        if (betaalmethodeDTO == null) return null;

        if (betaalmethodeDTO.getId() == null) {
            throw new IllegalArgumentException("klantId mag niet null zijn bij het aanmaken van een betaalmethode.");
        }

        Klant klant = new Klant();
        klant.setId(betaalmethodeDTO.getId()); // alleen ID instellen, rest wordt via DB opgehaald

        Betaalmethode betaalmethode = new Betaalmethode();
        betaalmethode.setId(betaalmethodeDTO.getId());
        betaalmethode.setMethode(betaalmethodeDTO.getMethode()); // enum case-sensitive
        betaalmethode.setDatum(betaalmethodeDTO.getDatum());
        betaalmethode.setKlant(klant); // koppel aan bestaande klant

        return betaalmethode;
    }

    // Entity => DTO
    public BetaalmethodeDTO toDTO(Betaalmethode betaalmethode) {
        if (betaalmethode == null) return null;

        return new BetaalmethodeDTO(
                betaalmethode.getId(),
                betaalmethode.getMethode(),
                betaalmethode.getDatum(),
                betaalmethode.getKlant()
        );

//        BetaalmethodeDTO betaalmethodeDTO = new BetaalmethodeDTO();
//        betaalmethodeDTO.setId(betaalmethode.getId());
//        betaalmethodeDTO.setMethode(betaalmethode.getMethode()); // consistentie met enum 'crypto'
//        betaalmethodeDTO.setDatum(betaalmethode.getDatum());
//        betaalmethodeDTO.getId(betaalmethode.getKlant().getId());
//
//        return betaalmethodeDTO;
    }

}
