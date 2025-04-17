package hotel.beheer.systeem.api.dao;

import hotel.beheer.systeem.api.entities.*;
import hotel.beheer.systeem.api.interfaces.EntityDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

public class BetaalmethodeContantDao implements EntityDao<BetaalmethodeContant> {
    private EntityManager entityManager;

    public BetaalmethodeContantDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<BetaalmethodeContant> findAll() {
        List<BetaalmethodeContant> result = new ArrayList<>();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            result = entityManager.createQuery("SELECT b FROM BetaalmethodeContant b").getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        System.out.println("Informatie succesvol opgehaald");
        return result;
    }


    @Override
    public void save(BetaalmethodeContant betaalmethodeContant) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(betaalmethodeContant);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // deze code gaat je jouw error wijzen als het in die catch komt
        }
        System.out.println("Succesvol ingevoegd");
    }

    @Override
    public BetaalmethodeContant findById(Integer id) {
        BetaalmethodeContant betaalmethodeContant = null;
        try {
            betaalmethodeContant = entityManager.find(BetaalmethodeContant.class, id); // Zoek de klant via ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Informatie van deze succesvol opgehaald");
        return betaalmethodeContant;

    }

    @Override
    public void update(BetaalmethodeContant betaalmethodeContant) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(betaalmethodeContant); // Update de klant
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
            BetaalmethodeContant betaalmethodeContant = entityManager.find(BetaalmethodeContant.class, id); // Zoek de klant via ID
            if (betaalmethodeContant != null) {
                entityManager.remove(betaalmethodeContant); // Verwijder de klant als het bestaat
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // als er een error voorkomt gooien in die catch blok
        }
        System.out.println("Succesvol verwijderd");
    }

}
