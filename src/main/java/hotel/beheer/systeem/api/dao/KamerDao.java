package hotel.beheer.systeem.api.dao;

import hotel.beheer.systeem.api.entities.*;
import hotel.beheer.systeem.api.interfaces.EntityDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;


public class KamerDao implements EntityDao<Kamer> {
    private EntityManager entityManager;

    public KamerDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Kamer> findAll() {
        List<Kamer> result = new ArrayList<>();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            result = entityManager.createQuery("SELECT k FROM Kamer k").getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        System.out.println("Informatie succesvol opgehaald");
        return result;
    }


    @Override
    public void save(Kamer kamer) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(kamer);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // deze code gaat je jouw error wijzen als het in die catch komt
        }
        System.out.println("Succesvol ingevoegd");
    }

    @Override
    public void deleteById(Integer id) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Kamer kamer = entityManager.find(Kamer.class, id); // Zoek de klant via ID
            if (kamer != null) {
                entityManager.remove(kamer); // Verwijder de klant als het bestaat
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        System.out.println("Succesvol verwijderd");
    }

    @Override
    public Kamer findById(Integer id) {
        Kamer kamer = null;
        try {
            kamer = entityManager.find(Kamer.class, id); // Zoek de klant via ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Informatie van deze succesvol opgehaald");
        return kamer;

    }

    @Override
    public void update(Kamer kamer) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(kamer); // Update de klant
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // error printen als het in die catch komt
        }
        System.out.println("Succesvol gewijzigd");
    }
}
