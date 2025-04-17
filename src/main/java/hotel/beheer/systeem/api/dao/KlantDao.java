package hotel.beheer.systeem.api.dao;
import hotel.beheer.systeem.api.entities.Klant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import hotel.beheer.systeem.api.interfaces.EntityDao;

import hotel.beheer.systeem.api.entities.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KlantDao implements EntityDao<Klant> {
    private EntityManager entityManager;

    public KlantDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Klant> findAll() {
        List<Klant> result = new ArrayList<>();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            result = entityManager.createQuery("SELECT k FROM Klant k").getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        System.out.println("Informatie succesvol opgehaald");
        return result;
    }



//    public void saveMetBetaalmethode(Klant klant, Betaalmethode betaalmethodeId) {
//        EntityTransaction transaction = entityManager.getTransaction();
//
//        try {
//            transaction.begin();
//            entityManager.persist(klant);
//            entityManager.persist(betaalmethodeId);
//            transaction.commit();
//        } catch (Exception e) {
//            transaction.rollback();
//            e.printStackTrace(); // deze code gaat je jouw error wijzen als het in die catch komt
//        }
//        System.out.println("Succesvol ingevoegd");
//
//    }

    @Override
    public void save(Klant klant) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(klant);
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
            Klant klant = entityManager.find(Klant.class, id); // Zoek de klant via ID
            if (klant != null) {
                entityManager.remove(klant); // Verwijder de klant als het bestaat
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // als er een error voorkomt gooien in die catch blok
        }
        System.out.println("Succesvol verwijderd");
    }


    @Override
    public Klant findById(Integer id) {
        Klant klant = null;
        try {
            klant = entityManager.find(Klant.class, id); // Zoek de klant via ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Informatie van deze succesvol opgehaald");
        return klant;

    }

    @Override
    public void update(Klant klant) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(klant); // Update de klant
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // error printen als het in die catch komt
        }
        System.out.println("Succesvol gewijzigd");
    }

    // void gezet omdat ik geen waarde retourneer naar de gebruiker die de applicatie rent
    // als je wilt opwaardern moet ik eerst weten op welke klant ID ik moet storten daarom gevn wij het aan in de parameter de
    // gebruiker moet het aangeven en de persoon moet ook aangeven hoeveel ze willen storten om het losjes te coderen
    public void opwaarderen(Integer id ,Double usdOpwaarderen) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {

            // dit zet je altijd als je met een transactie begint zonder dit zal het nooit gaan
            transaction.begin();
            //je zoekt naar die id die je hebt meegegeven in die parameter (id)
            // met deze variabel van klant kan je toegang krijgen tot die eigenschappen en methodes van d classe KLANT
            // Alleen omdat je een object ervan heb geinstantieerd in de applicatie classe als je helemaal geen object ervan
            // had gemaakt zou het niet werken
            Klant klant = entityManager.find(Klant.class, id);

//            klant.getId()

            // klanten beginnen vanaf 1 na boven dus als je klant gelijk is aan null bestaat hij niet
            if (klant == null) {
                System.out.println("klant ID: " + id + " niet gevonden");
            } else {
                // je else is als je id wel bestaat
                System.out.println("Klant ID: " + id + " wel gevonden");

                // die klant. met deze referentie kan je toegang hebben tot al die eigenchappen
                // en methodes van de klasse klant die getBalans retourneert een waarde in DOUBLE
                // dus als je het wilt opslaan in een variabel moet die variabel dezelfde datatype hebben om die
                // waarde te accepteren en dat is DOUBLE en je geeft het een naam HUIDIGEBALANS
                double huidigeBalans = klant.getBalans(); // die variabel klant heeft toegang tot al
                // die eigenschappen en methodes avn die klasse KLANT
                System.out.println("Dit is uw huidige balans : " + huidigeBalans);

                // die variabelen huidigeBalans en die usdOpwaarderen hebben beide dezelfde datatypr double
                // dus dan kan je ze optellen |||  huidigeBalans + usdOpwaarderen;
                // maar als je het uiteindelijk wilt hergenruiken moet je het opslaan in een variabel
                // omdat het een double retourneert moet je het opslaan in een variabel die ook dezelfde datatype heeft DOUBLE
                // en dan roep je het nieuwBalans
                double nieuwBalans = huidigeBalans + usdOpwaarderen;
                // met die variabel van die andere class klant kan je toegang hebben tot al die eiegenschappen en methodes van die
                // classe klant
                // met die .setBalans kan je een balans plaatsen in die specifieke object
                // en wat je zet tussen die haakjes dat ga je uiteindelijk zetten via die methode in die object.

                klant.setBalans(nieuwBalans);

                // die entity manager zorgt ervoor dat je kan communiceren met je database
                //het heeft een methode merge() met deze methode kan je bestaande informatie verplaatsen met huidige
                // of te wel nieuwe informatie || deze methode gerbuik je ook bij updaten van informatie
                entityManager.merge(klant); // vergeet deze niet, anders wordt het niet opgeslagen
                // sinds je met die bovenstaande regel info succesvol hebt gezet in de database kan je het
                // gewoon halen met die referentie KLANT en die .getBalans() om die geupdate waarde te retournerne
                System.out.println("Uw saldo na het opwaarderen: " + klant.getBalans());

            }
            // dit zet je altijd nadat je iets opslaat of update (dus na je persist() / merge() zet je dit )
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // zal errors printen als je hebt
        }
        System.out.println("Opwaarderen proces afgerond");
    }

    // je gaat met je id boeken

//    public void kamerboeken(Integer id, BeschikbareKamer beschikbareKamer) {
//        EntityTransaction transaction = entityManager.getTransaction();
//
//        try {
//            transaction.begin();
//            Klant klant = entityManager.find(Klant.class, id);
//            if (klant == null) {
//                System.out.println("klant ID: " + id + " niet gevonden");
//            } else {
//                System.out.println("Klant ID: " + id + " wel gevonden");
//
//                // ik wil die beschikbare class bestellen
//
//                // maar die beschibare class heeft een id in wil in die id van die kamer gaan om die prijs te halen
//                // en die prij af te trekken met die  balans die klant heeft en vervlgens
//                // wil ik dat die beschikbare kamer boeken mar het moet dan verwijderdt worden in de Kamerdao
//                // want ik heb het al gekocht als alles successvol is
//                // maar als mij balans te laag is moet het ook zeggen dat ik het niet kan kopen
//            }
//
//        } catch (Exception e) {
//            transaction.rollback();
//            e.printStackTrace();
//        }
//        System.out.println("Kamerboeken proces afgerond");
//    }


    // ik zet void omdat ik geen waarde terug tetourneer deze mt=ethode is geschikt om kamers te boeken
    // je moet een kamer boeken via de ID van de klamt daarom zet in het in die parameter
    // Niet alle kamers zijn beschikbaar daarom heb ik een aparte klass gemaakt voor kamers die beschikbaar zijn
    // dus dan moeten ze die ID invullen van die beschikbare kamer

    public void kamerboeken(Integer klantId, BeschikbareKamer beschikbareKamer) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            // dit is nodig om een transacttie te maken zonder dit zal het nooit lukken
            transaction.begin();

            // 1. Vind de klant

            // je creert een variabel genaamd klant omdat je uiteindelijk die ID die je hebt gegeven in die parameter op wilt
            // slaan in die variabel omdat het een ID is van de klasse klant kan je het alleen opslaan in een variabel die
            // dezelfde datatype heeft van de klasse klant

            // we hebben een nummer ingevuld dus die klantID is al aangegeven dus we hoeven naast die KlantId geen .getId() te doen
            Klant klant = entityManager.find(Klant.class, klantId);
            // je ID van klant beginnen altijd bij vanaf 1 naar boven
            // dus als je ID gelijk is aan null dan bestaat het niet

            if (klant == null) {
                System.out.println("Klant met ID " + klantId + " niet gevonden.");
                return; // waar je return voorkomt daar stopt je methode
            }
            // maar even een vraag hoe het klant == null heeft gedaan in die if  als die klant niet gelijk aan null is !=
            // dan gaat die methode wel door  want ik zie geen else ervan waar die != zal plaatsvinden
            // of hoef ik die != niet te zetten omdat dit defensieve codering is.

            // 2. Vind de beschikbare kamer

            // die id die je geeft is van die beschikbare kamer maar die moet je eerst zoeken
            // dus je zal een variabel creeren met die zelfde datatype van die beschikbare klass zodat je het erin kan opslaan

            // we hebben een variabel gezet die de nummer van die beschikbare kamer heeeft maar we moeten toch die naam
            // gebruiken van die variabel die die ID representeert en die  getId() om die iD eruit je halen


            BeschikbareKamer bestaatdezebeschikbarekamer = entityManager.find(BeschikbareKamer.class, beschikbareKamer.getId());

            if (bestaatdezebeschikbarekamer == null) {
                System.out.println("Beschikbare kamer met ID " + beschikbareKamer + " niet gevonden.");
                return; // waar die return voorkomt daar zal die code stoppen
            }
            // maar wat als die beschikbarekamer ID wel bestaat het het wel verder want ik zie geen else die aangeeft !=
            // dat die id wel bestaat of gaat het gewoon door omdat dit defensieve codering is

            // 3. Haal de echte kamer op via kamerId van BeschikbareKamer

            // dus hier gebruiken we die entitymanager.find() om iets te zoeken je kan niks doen zonder die entitymanager
            // je wil in de kamer classe gaan daarom zet je Kamer.class , na je comma begint je logica
            // die id die je hebt gegeven is die van van de beschikbarekamer dus dan zet je
            // bestaatdezebeschikbarekamer.getKamer() om via die ID van beschikbare kameer te gaan in die classe kamer
            // want die getKamer() retourneert die kamer eigenschap
            // of te wel na die bestaatdezebeschikbarekamer.getKamer() ga je in die kamer classe zelf en dan doe je die getId()
            // en die getId() is die methode in Kamer die je die ID zal geven en dan sla je deze id op in die vaiabel
            // kamer

            // wat er hier gebuert is dat we die bestaatdezebeschikbarekamer die de id representeerdt van de beschikbare kamer
            // met dit willen wij toegang tot krijgen tot die id van kamer of die foreign key id dus we ebruiken de getter
            // van de foreign key (getKamer())  om toegang te krijgen tot die veld of eigenschap kamer en dan doen we een getID()
            // om die ID te krijgen van die foreign key die we hebben gekoppeld aan die beschikbare kamer !!
            // dan hebben we dezelfde nummer avn die foreign key genomen of gerbuikt die we hebben gegeven aan die kamer die beschukbaar is

            // we willen die ID van die kamer halen die beschikbaar is , aar we krijgen maar 2 dingen in die paraeter dat is die iD van
            // klant en beschikbare kamer die id van klant is niet gekoppeld aan die kamer tabel dus we kunnen niet daar gaaan '
            // maar die beschikbare kamer heeft wel een foreign key van kamer ering , dus we kunne via die id van beschikbare kamer
            // toch die ID van kamer krijgen door bestaatdezebeschikbarekamer. getKamer() dir gaat ons toegang geven tot die goreign key
            // in die beschikbare klasse en dan doen we die getID om toegang te krijgen tot die ID van die foreign key die is
            // gekoppeld aan die beschikbare kamer. die getID is wel van die andere klasse Kamer want je kan ook doen getPrijsPerMaand
            // getAantalBedden getKamerType dit zijn allemaal methodes van kamer
            Kamer kamer = entityManager.find(Kamer.class, bestaatdezebeschikbarekamer.getKamer().getId());
            if (kamer == null) {
                System.out.println("Kamer niet gevonden.");
                return;
            }

            // 4. Check of klant genoeg geld heeft

            // met die variabel van naam van klant kan je toegang hebben tot al die eignschappen en methodes van die klasse klant
            // dus die klant.getBalans zal de huidige balans ophalen en als je huidige balans kleiner is < dan je kamer prijs per maand
            // dan zal het een error geven je zal die prijs van die kamer krijgen door die referentie van
            // die variabel kamer.getPrijsPerMaand()  (die getter)
            if (klant.getBalans() < kamer.getPrijsPerMaand()) {
                System.out.println("Onvoldoende saldo. Klant heeft: $" + klant.getBalans() + ", Kamer kost: $" + kamer.getPrijsPerMaand());
                return;
            }

            // maar wat als je klant balans hoger is dan die kamer prijs per maand ik zie die else niet of
            // wordt het automatisch gebruikt als die if clause niet werkt is dit dfensieve programmerne

            // 5. Trek prijs af van klantbalans

            // omdat balans in double is aangegevn en prijspermaand ook in double is aangegevn kan je ze aftrekken
            // door klant.getBalans() - kamer.getPrijsPerMaand(); die referentie klant zorgt ervoor dat je methodes en
            // eigenschappen kan gebruiken van de klasse klant en die variabel referentie van kamer zorgt ervoor dat
            // je methdodes kan gebruiken van de klasse kamer en dan wil je de uitkomst opslaan in een variabel nieuwebalns
            // je moet het dezelfde datatype geven als de waarde die het retourneert
            double nieuweBalans = klant.getBalans() - kamer.getPrijsPerMaand();
            // met de methode setBalans kan je de balans toevoegen dus dit is heel goed
            // en wat je zet als argumenten of waardee in je setBalans() dat wordt uiteindelijk gezet via die eigenschap van die klass
            // gezet in die methode en vervolgens in die object
            klant.setBalans(nieuweBalans);
            // dit is om die informatie oop te daten in principe die ouwe baln te vervangen met die nieuwe balans in
            // die database
            entityManager.merge(klant);

            // 6. Maak een boeking aan
            // je zal eerst een object maken van KamersBoeken om die informatie erin te plaatsen
            KamersBoeken boeking = new KamersBoeken();
            // je gebruikt die referentie van die kamersboeken om toegang te krijgen tot al die
            // eigenschappen en methodes van die kamersboeken
            // die referentie.setKlant() hierin ga je een klant id zetten in die boeking
            // die klant referentie refereert naar die specifieke ID die werdt gegeveen in die parameter
            //die specefieke referentie ga je opslaan via die setter in die object
            boeking.setKlant(klant);

            Date vandaag = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(vandaag);
            c.add(Calendar.MONTH, 1);
            Date eindDatum = (Date) c.getTime();

            boeking.setStartdatum(vandaag);
            boeking.setEinddatum(eindDatum);

//            Kamer totaalbedragvankamer = entityManager.find(Kamer.class, bestaatdezebeschikbarekamer.getKamer().getPrijsPerMaand());
//            boeking.setTotaalbedrag(totaalbedragvankamer); // ik heb die eigenschappen van totaakbedrag en betaalmethode verwijderdt
            boeking.setBeschikbareKamer(bestaatdezebeschikbarekamer);



            // klant moet en betaalmethode id zetten want het is not null

            // hier hebben we een foreign key gezet in klant die refereert naar betaalmethode we hebben een klantId. getBetaalmethode
            // dan komen we terecht in die betaalmethode klass en dan kunne we GetID doen en ander methdodes gebruiken van betaalmethode
            // maar het is heel belangrijk om te weten dat als je in die foreign key van klant geen ID zet van klant dan ga
            // je geen elkele waarde krijgen want uiteindelijk kan je alleen de waardes krijgen in je klant tabel die je hebt gekoppeld
            // aand ei foreign keys in die klant tabel

            Betaalmethode bestaalmethode = entityManager.find(Betaalmethode.class, klant.getBetaalmethode().getId());
            boeking.setBetaalmethodes(bestaalmethode); //ik heb die betaalmethode eigeschap verwijderdt

            entityManager.persist(boeking);

            // 7. Pas de kamer uit de lijst van beschikbare kamers van beschikbaar naar niet meer beschikbaar

//            entityManager.remove(bestaatdezebeschikbarekamer); // je kan het niet meer verwijderne omdat
            // het een foreign key heeft tussen beschikbarekamer en kamer en je kan
            // geen informatite van een foreing key verwijderne of helemaal aanpassen
            // dus dan passen we kleine dingne toe die wel mogen

            bestaatdezebeschikbarekamer.setId(bestaatdezebeschikbarekamer.getId());
            bestaatdezebeschikbarekamer.setBeschikbareKamerAlternatief(BeschikbareKamer.BeschikbareKamerAlternatief.nietbeschikbaar);
            bestaatdezebeschikbarekamer.setKamer(kamer);

            entityManager.merge(bestaatdezebeschikbarekamer);


            transaction.commit();

            System.out.println("Kamer succesvol geboekt door klant ID: " + klantId);
            System.out.println("Nieuwe balans: $" + klant.getBalans());
//            System.out.println("Boeking geldig tot: " + eindDatum);

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            System.out.println("Kamerboeken mislukt.");
        }
    }

    // methode maken die een in klant die een ID invoegt en direk een Betaalmethode kan aangeven !!
    // maar in deze methode moeten ze wel an hun gegeven stieken nemen en weer invulleb en vervolgens die betaalmethode of id aangeven

    public void klantUpdatenMetBetaalmethode(Integer klantId, Integer betaalmethodeId) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Klant klant = entityManager.find(Klant.class, klantId);
            if (klant == null) {
                System.out.println("Klant met ID " + klantId + " bestaat niet.");
                transaction.rollback();
                return;
            }

            Betaalmethode betaalmethode = entityManager.find(Betaalmethode.class, betaalmethodeId);
            if (betaalmethode == null) {
                System.out.println("Betaalmethode met ID " + betaalmethodeId + " bestaat niet.");
                transaction.rollback();
                return;
            }

            klant.setBetaalmethode(betaalmethode);
            entityManager.merge(klant);
            transaction.commit();

            System.out.println("Betaalmethode succesvol gekoppeld aan klant.");
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }



}
