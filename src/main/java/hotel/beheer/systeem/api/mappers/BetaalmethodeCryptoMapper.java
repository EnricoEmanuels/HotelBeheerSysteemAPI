package hotel.beheer.systeem.api.mappers;

import hotel.beheer.systeem.api.dto.BetaalmethodeCryptoDTO;
import hotel.beheer.systeem.api.entities.BetaalmethodeCrypto;

public class BetaalmethodeCryptoMapper {
    // van entiteit naar een DTO
    public BetaalmethodeCryptoDTO toBetaalmethodeCryptoDTO(BetaalmethodeCrypto betaalmethodeCrypto) {
        if (betaalmethodeCrypto == null) {
            return null;
        }

        return new BetaalmethodeCryptoDTO(
                betaalmethodeCrypto.getId(),
                betaalmethodeCrypto.getWalletAdres(),
                betaalmethodeCrypto.getMuntsoort());
    }

    // van DTO naar entiteit
    public BetaalmethodeCrypto toBetaalmethodeCryptoEntity(BetaalmethodeCryptoDTO betaalmethodeCryptoDTO) {
        if (betaalmethodeCryptoDTO == null) {
            return null;
        }
        return new BetaalmethodeCrypto(
                betaalmethodeCryptoDTO.getId(),
                betaalmethodeCryptoDTO.getWalletAdres(),
                betaalmethodeCryptoDTO.getMuntsoort());
    }
}
