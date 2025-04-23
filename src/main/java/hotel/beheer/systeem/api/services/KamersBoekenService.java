package hotel.beheer.systeem.api.services;

import hotel.beheer.systeem.api.dao.KamersBoekenDao;
import hotel.beheer.systeem.api.entities.KamersBoeken;

import java.util.List;

public class KamersBoekenService {
    private KamersBoekenDao kamersBoekenDao;

    public KamersBoekenService(KamersBoekenDao kamersBoekenDao) {
        this.kamersBoekenDao = kamersBoekenDao;
    }

    // find ALL

    public List<KamersBoeken> findAllKamersBoeken() {
        return kamersBoekenDao.findAll();
    }

    // find by ID
    public KamersBoeken findKamersBoekenById(int id) {
        return kamersBoekenDao.findById(id);
    }

    // save
    public KamersBoeken saveKamersBoeken(KamersBoeken kamersBoeken) {
        kamersBoekenDao.save(kamersBoeken);
        return kamersBoeken;
    }

    // update
    public void updateKamersBoeken(KamersBoeken kamersBoeken) { // je update allen die gegevens niet die ID
        // dus je hoef dat niet te zeggen
        kamersBoekenDao.update(kamersBoeken);
    }

    // delete
    public void deleteKamersBoeken(int id) {
        kamersBoekenDao.deleteById(id);
    }

    // find ALL als ik die response wil maken moet ik die DAO ook veranderen in die datatype of return type van een
    // List<KamersBoeken> naar een KamersBoeken
//    public KamersBoeken findAlleKamersBoekenResponse() {
//        return kamersBoekenDao.findAll();
//    }
}
