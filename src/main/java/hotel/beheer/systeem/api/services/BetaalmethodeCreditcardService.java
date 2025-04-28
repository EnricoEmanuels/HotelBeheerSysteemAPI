package hotel.beheer.systeem.api.services;

import hotel.beheer.systeem.api.dao.BetaalmethodeCreditcardDao;
import hotel.beheer.systeem.api.entities.BetaalmethodeCreditcard;

import java.util.List;

public class BetaalmethodeCreditcardService {
    private BetaalmethodeCreditcardDao betaalmethodeCreditcardDao;

    public BetaalmethodeCreditcardService(BetaalmethodeCreditcardDao betaalmethodeCreditcardDao) {
        this.betaalmethodeCreditcardDao = betaalmethodeCreditcardDao;
    }

    public BetaalmethodeCreditcard saveBetaalmethodeCreditcard(BetaalmethodeCreditcard betaalmethodeCreditcard) {
        betaalmethodeCreditcardDao.save(betaalmethodeCreditcard);
        return betaalmethodeCreditcard;
    }
    // upcate
    public void updateBetaalmethodeCreditcard(BetaalmethodeCreditcard betaalmethodeCreditcard) {
        betaalmethodeCreditcardDao.update(betaalmethodeCreditcard);
    }
    // delete
    public void deleteBetaalmethodeCreditcardById(int id) {
        betaalmethodeCreditcardDao.deleteById(id);
    }
    // find all
    public List<BetaalmethodeCreditcard> findAllBetaalmethodeCreditcard() {
        return betaalmethodeCreditcardDao.findAll();
    }
    // find by id
    public BetaalmethodeCreditcard findBetaalmethodeCreditcardById(int id) {
        return betaalmethodeCreditcardDao.findById(id);
    }

}
