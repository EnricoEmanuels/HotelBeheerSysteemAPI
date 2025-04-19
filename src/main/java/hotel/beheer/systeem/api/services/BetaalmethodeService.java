package hotel.beheer.systeem.api.services;

import hotel.beheer.systeem.api.dao.BetaalmethodeDao;
import hotel.beheer.systeem.api.entities.Betaalmethode;

public class BetaalmethodeService {
    private BetaalmethodeDao betaalmethodeDao;

    public BetaalmethodeService(BetaalmethodeDao betaalmethodeDao) {
        this.betaalmethodeDao = betaalmethodeDao;
    }

    public Betaalmethode getBetaalmethodeById(Integer id) {
        return betaalmethodeDao.findById(id);
    }
}
