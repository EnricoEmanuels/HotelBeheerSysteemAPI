package hotel.beheer.systeem.api.dao;


import hotel.beheer.systeem.api.entities.*;
import hotel.beheer.systeem.api.interfaces.EntityDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;


public class KamersBoekenDao implements EntityDao<KamersBoeken> {
    private EntityManager entityManager;

    public KamersBoekenDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<KamersBoeken> findAll() {
        List<KamersBoeken> result = new ArrayList<>();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            result = entityManager.createQuery("SELECT k FROM KamersBoeken k").getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        System.out.println("Informatie succesvol opgehaald");
        return result;
    }


    @Override
    public void save(KamersBoeken kamersBoeken) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();


//            Klant klantID = entityManager.find(Klant.class , kamersBoeken.getKlant().getId());
//            kamersBoeken.setKlant(klantID);
//
//            BeschikbareKamer beschikbareKamerID = entityManager.find(BeschikbareKamer.class, kamersBoeken.getBeschikbareKamer().getId());
//            kamersBoeken.setBeschikbareKamer(beschikbareKamerID);
//
//            Betaalmethode betaalmethodeID = entityManager.find(Betaalmethode.class , kamersBoeken.getBetaalmethodes().getDatum());
//            kamersBoeken.setBetaalmethodes(betaalmethodeID);


            entityManager.persist(kamersBoeken);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // deze code gaat je jouw error wijzen als het in die catch komt
        }
        System.out.println("Succesvol ingevoegd");

    }

    @Override
    public KamersBoeken findById(Integer id) {
        KamersBoeken kamersBoeken = null;
        try {
            kamersBoeken = entityManager.find(KamersBoeken.class, id); // Zoek de klant via ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Informatie van deze succesvol opgehaald");
        return kamersBoeken;

    }

    @Override
    public void update(KamersBoeken kamersBoeken) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(kamersBoeken); // Update de klant
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
            KamersBoeken kamersBoeken = entityManager.find(KamersBoeken.class, id); // Zoek de klant via ID
            if (kamersBoeken != null) {
                entityManager.remove(kamersBoeken); // Verwijder de klant als het bestaat
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // als er een error voorkomt gooien in die catch blok
        }
        System.out.println("Succesvol verwijderd");
    }
}
