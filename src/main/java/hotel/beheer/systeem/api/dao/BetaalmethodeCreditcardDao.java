package hotel.beheer.systeem.api.dao;

import hotel.beheer.systeem.api.entities.*;
import hotel.beheer.systeem.api.interfaces.EntityDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

public class BetaalmethodeCreditcardDao implements EntityDao<BetaalmethodeCreditcard> {
    private EntityManager entityManager;

    public BetaalmethodeCreditcardDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<BetaalmethodeCreditcard> findAll() {
        List<BetaalmethodeCreditcard> result = new ArrayList<>();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            result = entityManager.createQuery("SELECT b FROM BetaalmethodeCreditcard b").getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        System.out.println("Informatie succesvol opgehaald");
        return result;
    }


    @Override
    public void save(BetaalmethodeCreditcard betaalmethodeCreditcard) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(betaalmethodeCreditcard);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // deze code gaat je jouw error wijzen als het in die catch komt
        }
        System.out.println("Succesvol ingevoegd");
    }

    @Override
    public BetaalmethodeCreditcard findById(Integer id) {
        BetaalmethodeCreditcard betaalmethodeCreditcard = null;
        try {
            betaalmethodeCreditcard = entityManager.find(BetaalmethodeCreditcard.class, id); // Zoek de klant via ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Informatie van deze succesvol opgehaald");
        return betaalmethodeCreditcard;
    }

    @Override
    public void update(BetaalmethodeCreditcard betaalmethodeCreditcard) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(betaalmethodeCreditcard); // Update de klant
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
            BetaalmethodeCreditcard betaalmethodeCreditcard = entityManager.find(BetaalmethodeCreditcard.class, id); // Zoek de klant via ID
            if (betaalmethodeCreditcard != null) {
                entityManager.remove(betaalmethodeCreditcard); // Verwijder de klant als het bestaat
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // als er een error voorkomt gooien in die catch blok
        }
        System.out.println("Succesvol verwijderd");
    }
}
