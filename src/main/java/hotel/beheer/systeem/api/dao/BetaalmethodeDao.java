package hotel.beheer.systeem.api.dao;

import hotel.beheer.systeem.api.entities.*;
import hotel.beheer.systeem.api.interfaces.EntityDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;


public class BetaalmethodeDao implements EntityDao<Betaalmethode> {
    private EntityManager entityManager;

    public BetaalmethodeDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Betaalmethode> findAll() {
        List<Betaalmethode> result = new ArrayList<>();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            result = entityManager.createQuery("SELECT b FROM Betaalmethode b").getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        System.out.println("Informatie succesvol opgehaald");
        return result;
    }

    @Override
    public void save(Betaalmethode betaalmethode) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(betaalmethode);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // deze code gaat je jouw error wijzen als het in die catch komt
        }
        System.out.println("Succesvol ingevoegd");

    }

    @Override
    public Betaalmethode findById(Integer id) {
        Betaalmethode betaalmethode = null;
        try {
            betaalmethode = entityManager.find(Betaalmethode.class, id); // Zoek de klant via ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Informatie van deze succesvol opgehaald");
        return betaalmethode;

    }

    @Override
    public void update(Betaalmethode betaalmethode) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(betaalmethode); // Update de klant
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
            Betaalmethode betaalmethode = entityManager.find(Betaalmethode.class, id); // Zoek de klant via ID
            if (betaalmethode != null) {
                entityManager.remove(betaalmethode); // Verwijder de klant als het bestaat
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // als er een error voorkomt gooien in die catch blok
        }
        System.out.println("Succesvol verwijderd");
    }

    // methode creeren om te kijken welke betalingsmethode de klant allemaal heeeft
    // dus ik ben momenteel in die betaalmethdoe classe en ik will weten als een persoon die ID
    // van een klant geeft welke methodes deze klant heeft gerbuikt om te betalen bij methode
    // ik heb een enum gebruikt voor crypto, contant , creditcaard als die id van die kaltn voorkomt bij
    // verschillende methodes can moet ik ze zieb
    public List<Betaalmethode> alleBetaalmethodesVanKlant(Integer klantId) {
        List<Betaalmethode> result = new ArrayList<>();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            result = entityManager.createQuery(
                            "SELECT b FROM Betaalmethode b WHERE b.klant.id = :klantId", Betaalmethode.class)
                    .setParameter("klantId", klantId)
                    .getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return result;
    }
}
