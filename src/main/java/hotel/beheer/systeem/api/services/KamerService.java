package hotel.beheer.systeem.api.services;

import hotel.beheer.systeem.api.dao.KamerDao;

import hotel.beheer.systeem.api.entities.Kamer;
import java.util.List;

public class KamerService {
    // die service is die tussenlaag tussen die controller en die DAO hierin kan je jouw validatie zetten
    private KamerDao kamerDao;

    public KamerService(KamerDao kamerDao) {
        this.kamerDao = kamerDao;
    }
    // save
    public Kamer saveKamer(Kamer kamer) {
        kamerDao.save(kamer);
        return kamer;
    }
    // getAll
    public List<Kamer> getAllKamers() {
        return kamerDao.findAll();
    }
    // findbyID
    public Kamer findKamerById(int id) {
        return kamerDao.findById(id);
    }
    // delete
    public void deleteById(int id) {
        kamerDao.deleteById(id);
    }
    // update  ik accepteer alleen die volledige object van kamer dus k hoef die
    // id niet apart te zetten
    public void updateKamer( Kamer kamer) {
        kamerDao.update(kamer);

    }

}
