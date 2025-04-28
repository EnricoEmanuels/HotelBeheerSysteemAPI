package hotel.beheer.systeem.api.services;

import hotel.beheer.systeem.api.dao.BetaalmethodeContantDao;
import hotel.beheer.systeem.api.entities.Betaalmethode;
import hotel.beheer.systeem.api.entities.BetaalmethodeContant;
import java.util.List;

// hierin zet je je business logica
// die service layer is je tussenlaag tussen de controller en de DAO of de repository
public class BetaalmethodeContantService {
    private BetaalmethodeContantDao betaalmethodeContantDao;

    // cnstructor injectie
    public BetaalmethodeContantService(BetaalmethodeContantDao betaalmethodeContantDao) {
        this.betaalmethodeContantDao = betaalmethodeContantDao;
    }

    // save
    public BetaalmethodeContant saveBetaalmethodeContant( BetaalmethodeContant betaalmethodeContant) {
        betaalmethodeContantDao.save( betaalmethodeContant);
        return betaalmethodeContant;
    }


    // save met ID
    public BetaalmethodeContant saveBetaalmethodeContantMetId(Betaalmethode betaalmethodeId, BetaalmethodeContant betaalmethodeContant) {
        betaalmethodeContantDao.saveMetBetaalmethodeId(betaalmethodeId, betaalmethodeContant);
        return betaalmethodeContant;
    }



    // put id zal ik niet veanderen dus ik hoef het niet te zetten als parameter
    public void updateBetaalmethodeContant(BetaalmethodeContant updateBetaalmethodeContant) {
        betaalmethodeContantDao.update(updateBetaalmethodeContant);
    }
    // delete
    public void deleteBetaalmethodeContant(int id) {
        betaalmethodeContantDao.deleteById(id);
    }

    // Find All
    public List<BetaalmethodeContant> getAllBetaalmethodeContant() {
        return betaalmethodeContantDao.findAll();
    }
    // Find By Id
    public BetaalmethodeContant getBetaalmethodeContantById(int id) {
        return betaalmethodeContantDao.findById(id);
    }
}
