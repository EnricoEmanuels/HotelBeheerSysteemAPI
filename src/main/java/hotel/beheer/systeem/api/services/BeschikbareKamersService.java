package hotel.beheer.systeem.api.services;

import hotel.beheer.systeem.api.dao.BeschikbareKamerDao;
import hotel.beheer.systeem.api.entities.BeschikbareKamer;
import java.util.List;

public class BeschikbareKamersService {
    private BeschikbareKamerDao beschikbareKamerDao;

    public BeschikbareKamersService(BeschikbareKamerDao beschikbareKamerDao) {
        this.beschikbareKamerDao = beschikbareKamerDao;
    }
    // zoek alle beschikbarekamers die zowel "beschikbaar" als "nietbeschikbaar" zijn

    public List<BeschikbareKamer> getVolledigeBeschikbareKamerMetBeideStatussen() {
        return beschikbareKamerDao.findAll();
    }


    // find alle beschikbare kamer met status beschikbaar
    public List<BeschikbareKamer> getAllBeschikbareKamers() {
        // deze methdoe beschikbarekamers zal je alleen toegang geven tot die kamers
        // die een status hebben van beschikbaar dit is een aparte methdoe dan die methode findAll
        // omdat je bij die findAll alle beschikbare kamers krijg al is hun status op "nietbeschikbaar"
        return beschikbareKamerDao.alleBeschikbareKamers();
    }

    // findbyid
    public BeschikbareKamer findBeschikbareKamerById(int id) {
        return beschikbareKamerDao.findById(id);
    }
    // save
    public BeschikbareKamer saveBeschikbareKamer(BeschikbareKamer beschikbareKamer) {
        beschikbareKamerDao.save(beschikbareKamer);
        return beschikbareKamer;
    }
    // update
    public void updateBeschikbareKamer(BeschikbareKamer beschikbareKamer) {
        beschikbareKamerDao.update(beschikbareKamer);
    }
    // delete
    public void deleteBeschikbareKamer(int id) {
        beschikbareKamerDao.deleteById(id);
    }
}
