package hotel.beheer.systeem.api.services;

import hotel.beheer.systeem.api.dao.BetaalmethodeCryptoDao;
import hotel.beheer.systeem.api.entities.BetaalmethodeCrypto;

import java.util.List;

public class BetaalmethodeCryptoService {
    private BetaalmethodeCryptoDao betaalmethodeCryptoDao;

    public BetaalmethodeCryptoService(BetaalmethodeCryptoDao betaalmethodeCryptoDao) {
        this.betaalmethodeCryptoDao = betaalmethodeCryptoDao;
    }

    // save
    public BetaalmethodeCrypto saveBetaalmethodeCrypto(BetaalmethodeCrypto betaalmethodeCrypto) {
          betaalmethodeCryptoDao.save(betaalmethodeCrypto);
          return betaalmethodeCrypto;
    }
    // delete
    public void deleteBetaalmethodeCrypto(int id) {
         betaalmethodeCryptoDao.deleteById(id);
    }
    // update
    public void updateBetaalmethodeCrypto(BetaalmethodeCrypto betaalmethodeCrypto) {
        betaalmethodeCryptoDao.update(betaalmethodeCrypto);
    }
    // get all
    public List<BetaalmethodeCrypto> getAllBetaalmethodeCrypto() {
        return betaalmethodeCryptoDao.findAll();
    }
    // find by id
    public BetaalmethodeCrypto getBetaalmethodeCryptoById(int id) {
        return betaalmethodeCryptoDao.findById(id);
    }
}
