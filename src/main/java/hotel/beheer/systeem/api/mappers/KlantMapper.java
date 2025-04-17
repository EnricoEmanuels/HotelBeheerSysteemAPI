package hotel.beheer.systeem.api.mappers;

import hotel.beheer.systeem.api.dto.KlantDTO;
import hotel.beheer.systeem.api.entities.Klant;

public class KlantMapper {

    public KlantDTO toDTO(Klant klant) {
        if (klant == null) {
            return null;
        }

        return new KlantDTO(
                klant.getId(),
                klant.getVoornaam(),
                klant.getAchternaam(),
                klant.getTelefoon(),
                klant.getEmail(),
                klant.getBalans(),
                klant.getBetaalmethode() // mag null zijn, dat is oké
        );
    }

    public Klant toEntity(KlantDTO klantDTO) {
        if (klantDTO == null) {
            return null;
        }

        Klant klant = new Klant();
        klant.setId(klantDTO.getId());
        klant.setVoornaam(klantDTO.getVoornaam());
        klant.setAchternaam(klantDTO.getAchternaam());
        klant.setTelefoon(klantDTO.getTelefoon());
        klant.setEmail(klantDTO.getEmail());
        klant.setBalans(klantDTO.getBalans());

        // Optionele betaalmethode check
        if (klantDTO.getBetaalmethode() != null) {
            klant.setBetaalmethode(klantDTO.getBetaalmethode());
        }
        return klant;
    }



}
