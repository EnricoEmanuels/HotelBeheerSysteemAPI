package hotel.beheer.systeem.api.services;

import hotel.beheer.systeem.api.dao.BetaalmethodeDao;
import hotel.beheer.systeem.api.entities.Betaalmethode;

import java.util.List;

public class BetaalmethodeService {
    private BetaalmethodeDao betaalmethodeDao;

    public BetaalmethodeService(BetaalmethodeDao betaalmethodeDao) {
        this.betaalmethodeDao = betaalmethodeDao;
    }

    // specifieke ID ophalen
    public Betaalmethode getBetaalmethodeById(Integer id) {
        return betaalmethodeDao.findById(id);
    }

    // save
    public Betaalmethode saveBetaalmethode(Betaalmethode betaalmethode) {
        betaalmethodeDao.save(betaalmethode);
        return betaalmethode;
    }

    // update
    public void updateBetaalmethode(Betaalmethode betaalmethode) {
        betaalmethodeDao.update(betaalmethode);
    }
    // delete
    public void deleteBetaalmethode(int id) {
        betaalmethodeDao.deleteById(id);
    }
    // get all
    public List<Betaalmethode> getAllBetaalmethodes() {
        return betaalmethodeDao.findAll();
    }


}
