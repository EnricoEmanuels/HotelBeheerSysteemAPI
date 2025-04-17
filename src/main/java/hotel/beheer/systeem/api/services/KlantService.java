package hotel.beheer.systeem.api.services;

import hotel.beheer.systeem.api.dao.KlantDao;
import hotel.beheer.systeem.api.entities.Klant;

import java.util.List;

public class KlantService {

    private KlantDao klantDao;

    public KlantService(KlantDao klantDao) {
        this.klantDao = klantDao;
    }

    public Klant SaveKlant(Klant klant) {
        klantDao.save(klant);
        return klant;
    }

    public List<Klant> getAllKlants() {
        return klantDao.findAll();
    }

    public Klant findKlantById(int id) {
        return klantDao.findById(id);
    }

    public void deleteKlantById(int id) {
        klantDao.deleteById(id);
    }

    public void updateKlant(Klant klant) {
        klantDao.update(klant);
    }
}
