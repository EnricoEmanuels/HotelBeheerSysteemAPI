package hotel.beheer.systeem.api.mappers;

import hotel.beheer.systeem.api.dto.BetaalmethodeCreditcardDTO;
import hotel.beheer.systeem.api.entities.BetaalmethodeCreditcard;

public class BetaalmethodeCreditcardMapper {
    // van entiteit naar een DTO

    public BetaalmethodeCreditcardDTO toBetaalmethodeCredicardDTO(BetaalmethodeCreditcard betaalmethodeCreditcard) {
        if (betaalmethodeCreditcard == null) {
            return null;
        }
        return new BetaalmethodeCreditcardDTO(
                betaalmethodeCreditcard.getId(),
                betaalmethodeCreditcard.getVolledigeNaam(),
                betaalmethodeCreditcard.getKaartnummer(),
                betaalmethodeCreditcard.getVervaldatum(),
                betaalmethodeCreditcard.getCvv()
        );
    }

    // van DTO naar entiteit
    public BetaalmethodeCreditcard toBetaalmethodeCreditcardEntity(BetaalmethodeCreditcardDTO betaalmethodeCreditcardDTO) {
        if (betaalmethodeCreditcardDTO == null) {
            return null;
        }
        return new BetaalmethodeCreditcard(
                betaalmethodeCreditcardDTO.getId(),
                betaalmethodeCreditcardDTO.getVolledigeNaam(),
                betaalmethodeCreditcardDTO.getKaartnummer(),
                betaalmethodeCreditcardDTO.getVervaldatum(),
                betaalmethodeCreditcardDTO.getCvv()
        );
    }
}
