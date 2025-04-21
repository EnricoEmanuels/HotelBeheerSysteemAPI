package hotel.beheer.systeem.api.dao;

import hotel.beheer.systeem.api.entities.*;
import hotel.beheer.systeem.api.interfaces.EntityDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

public class BeschikbareKamerDao implements EntityDao<BeschikbareKamer> {
    private EntityManager entityManager;

    //constructor injection
    public BeschikbareKamerDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // hier ga je ook kamers krijgen die niet meer beschikbaar zijn dus oppassen liever gebruik je
    // deze methode alleBeschikbareKamers()  hier lrijg je alle kamers die niet meer beschikbaar zijn
    @Override
    public List<BeschikbareKamer> findAll() {
        List<BeschikbareKamer> result = new ArrayList<>();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            result = entityManager.createQuery("SELECT b FROM BeschikbareKamer b").getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // om die error te wijzenals het komt in de catch blok
        }
        System.out.println("Informatie succesvol opgehaald");
        return result;
    }


    @Override
    public void save(BeschikbareKamer beschikbareKamer) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(beschikbareKamer);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // deze code gaat je jouw error wijzen als het in die catch komt
        }
        System.out.println("Succesvol ingevoegd");
    }

    @Override
    public BeschikbareKamer findById(Integer id) {
        BeschikbareKamer beschikbareKamer = null;
        try {
            beschikbareKamer = entityManager.find(BeschikbareKamer.class, id); // Zoek de klant via ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Informatie van deze succesvol opgehaald");
        return beschikbareKamer;
    }

    @Override
    public void update(BeschikbareKamer beschikbareKamer) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(beschikbareKamer); // Update de klant
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
            BeschikbareKamer beschikbareKamer = entityManager.find(BeschikbareKamer.class, id); // Zoek de klant via ID
            if (beschikbareKamer != null) {
                entityManager.remove(beschikbareKamer); // Verwijder de klant als het bestaat
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // als er een error voorkomt gooien in die catch blok
        }
        System.out.println("Succesvol verwijderd");
    }

    // methode creeren in beschikbare kamer om te zien welke kamer allemaal beschikbaar zijn
    // het is geen findAll want er er in die eigenchap staat niet beschikbaar is het niet meer beschikbaar
    // ik heb enum gebruikt in beschikbare methode en je kan kiezen tussen beschikbaar en nietbesschikbaar maar ik ben niet
    // zeker hoe ik het moet aanreopen maar ik moet alles beschikbare kamer aanroepeb nietbeschikbaar wil ik niet

    // het werkt
    public List<BeschikbareKamer> alleBeschikbareKamers() {
        List<BeschikbareKamer> result = new ArrayList<>();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            result = entityManager
                    .createQuery("SELECT b FROM BeschikbareKamer b WHERE b.beschikbareKamerAlternatief = :status", BeschikbareKamer.class)
                    .setParameter("status", BeschikbareKamer.BeschikbareKamerAlternatief.beschikbaar)
                    .getResultList();
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        return result;
    }
}
