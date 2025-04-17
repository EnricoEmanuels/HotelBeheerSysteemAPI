package hotel.beheer.systeem.api.dao;

import hotel.beheer.systeem.api.entities.*;
import hotel.beheer.systeem.api.interfaces.EntityDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

public class BetaalmethodeCryptoDao implements EntityDao<BetaalmethodeCrypto> {
    private EntityManager entityManager;

    public BetaalmethodeCryptoDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<BetaalmethodeCrypto> findAll() {
        List<BetaalmethodeCrypto> result = new ArrayList<>();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            result = entityManager.createQuery("SELECT b FROM BetaalmethodeCrypto b").getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        System.out.println("Informatie succesvol opgehaald");
        return result;
    }

    @Override
    public void save(BetaalmethodeCrypto betaalmethodeCrypto) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(betaalmethodeCrypto);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // deze code gaat je jouw error wijzen als het in die catch komt
        }
        System.out.println("Succesvol ingevoegd");
    }

    @Override
    public BetaalmethodeCrypto findById(Integer id) {
        BetaalmethodeCrypto betaalmethodeCrypto = null;
        try {
            betaalmethodeCrypto = entityManager.find(BetaalmethodeCrypto.class, id); // Zoek de klant via ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Informatie van deze succesvol opgehaald");
        return betaalmethodeCrypto;
    }

    @Override
    public void update(BetaalmethodeCrypto betaalmethodeCrypto) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(betaalmethodeCrypto); // Update de klant
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // error printen als het in die catch komt
        }
        System.out.println("Succesvol gewijzigd");
    }

    @Override
    public void deleteById(Integer id) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            BetaalmethodeCrypto betaalmethodeCrypto = entityManager.find(BetaalmethodeCrypto.class, id); // Zoek de klant via ID
            if (betaalmethodeCrypto != null) {
                entityManager.remove(betaalmethodeCrypto); // Verwijder de klant als het bestaat
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // als er een error voorkomt gooien in die catch blok
        }
        System.out.println("Succesvol verwijderd");
    }

}
