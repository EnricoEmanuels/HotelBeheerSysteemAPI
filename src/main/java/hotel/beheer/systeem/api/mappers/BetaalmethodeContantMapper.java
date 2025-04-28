package hotel.beheer.systeem.api.mappers;

import hotel.beheer.systeem.api.dto.BetaalmethodeContantDTO;
import hotel.beheer.systeem.api.entities.BetaalmethodeContant;

public class BetaalmethodeContantMapper {
    // van entiteit naar DTO

    public BetaalmethodeContantDTO toBetaalmethodeContantDTO(BetaalmethodeContant betaalmethodeContant) {
        if (betaalmethodeContant == null) {
            return null;
        }

        return new BetaalmethodeContantDTO(
                betaalmethodeContant.getId(),
                betaalmethodeContant.getValuta()
        );
    }

    // van DTO naar entitet
    public BetaalmethodeContant toBetaalmethodeContantEntity(BetaalmethodeContantDTO betaalmethodeContantDTO) {
        if (betaalmethodeContantDTO == null) {
            return null;
        }
        return new BetaalmethodeContant(
                betaalmethodeContantDTO.getId(),
                betaalmethodeContantDTO.getValuta()
        );
    }
}


