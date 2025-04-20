package hotel.beheer.systeem.api.controllers;

import hotel.beheer.systeem.api.dto.KlantDTO;
import hotel.beheer.systeem.api.entities.Betaalmethode;
import hotel.beheer.systeem.api.entities.Klant;
import hotel.beheer.systeem.api.mappers.KlantMapper;
import hotel.beheer.systeem.api.services.BetaalmethodeService;
import hotel.beheer.systeem.api.services.KlantService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
@Path("/klanten")
public class KlantController {
    private KlantService klantService;
    private KlantMapper klantMapper;
    private BetaalmethodeService betaalmethodeService;
    // ik hoefde die getBetaalmethodeById niet te maken in die klantDAO ik dan gebruiken in die service layer
    // en vervolgens in die controller want ik had al een BetaalmethodeDAO gecreerd waarbij ik een eigen service layer hebt
    // gemaakt daarvoor en vervolgens die betaalmethode service layer gekoppeld aan die klantController en via contructor
    // injectie kon ik toegang krijgen tot al die eigenschappen en methodes van die dao

    private static final List<Klant> klantenDTO = new ArrayList<>();


    public KlantController(KlantService klantService, KlantMapper klantMapper, BetaalmethodeService betaalmethodeService) {
        this.klantService = klantService;
        this.klantMapper = klantMapper;
        this.betaalmethodeService = betaalmethodeService;
    }

//    http://localhost:8080/api/klanten werkt op die server
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Klant> getKlanten() {
        return klantService.getAllKlants();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKlantenById(@PathParam("id") int id) {
        // stap 1 zoeken naar de ID
        Klant klantID = klantService.findKlantById(id);
        // stap 2 als de ID niet bestaat retourneer NOT FOUND defensieve codering
        if ( klantID == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // als de ID wel gevonden is moet je het mappen vsn Entiteit naar DTO en retournern
        KlantDTO klantDTO = klantMapper.toDTO(klantID);
        return Response.status(Response.Status.OK).entity(klantDTO).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createKlant(Klant klant) {
        Klant klantopslaan = klantService.SaveKlant(klant);
        KlantDTO klantDTO = klantMapper.toDTO(klantopslaan);
        return Response.status(Response.Status.OK).entity(klantDTO).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateKlant(@PathParam("id") int id, Klant geupdateKlant) {
        // met die parameter die je hebt gemaakt van klant kan je ook toegang hebben
        // tot alle eigenschappen en methodes van klant

        // die ID ophalen en opslaan in een klant variabel en met deze variabel heb je ook toegang
        // tot al die eigenschappen en methodes van die classe klant
        Klant bestaandeKlant = klantService.findKlantById(id);
        // als id niet bestaat retourneert het die http status not found
//        if (bestaandeKlant == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
        // als die id wel bestaat zet die waardes via die setter in die bestaande klant
        if (bestaandeKlant != null) {
            bestaandeKlant.setVoornaam(geupdateKlant.getVoornaam());
            bestaandeKlant.setAchternaam(geupdateKlant.getAchternaam());
            bestaandeKlant.setTelefoon(geupdateKlant.getTelefoon());
            bestaandeKlant.setEmail(geupdateKlant.getEmail());
            bestaandeKlant.setBalans(geupdateKlant.getBalans());
            // die betaalmethode_id is optioneel dus het mag leeg zijn
            // moet ik het toch zo doen

            /*

            if (geupdateKlant.getBetaalmethode() != null && geupdateKlant.getBetaalmethode().getId() != null) {
                Betaalmethode bestaandeBetaalmethode = klantService.getBetaalmethodeById(geupdateKlant.getBetaalmethode().getId());
                bestaandeKlant.setBetaalmethode(bestaandeBetaalmethode);
            } else {
                bestaandeKlant.setBetaalmethode(null);
            }

            */

            // als die betaalemthode leeg is moet je je null zetten erin
//            if (geupdateKlant.getBetaalmethode() == null && geupdateKlant.getBetaalmethode().getId() == null) {
//                 bestaandeKlant.setBetaalmethode(null);
//            }
//
//            // als die methode leeg is moet je informatie erin zetten
//            if (geupdateKlant.getBetaalmethode() != null) {
//                bestaandeKlant.setBetaalmethode(geupdateKlant.getBetaalmethode());
//            }

            // veilige check
            if (geupdateKlant.getBetaalmethode() == null || geupdateKlant.getBetaalmethode().getId() == null) {
                bestaandeKlant.setBetaalmethode(null);
            } else {

                Betaalmethode bestaandeBetaalmethode = betaalmethodeService.getBetaalmethodeById(geupdateKlant.getBetaalmethode().getId());
                bestaandeKlant.setBetaalmethode(bestaandeBetaalmethode);
            }

//            bestaandeKlant.setBetaalmethode(geupdateKlant.getBetaalmethode()); // dit uitgecomment
            // nu gaan we officieel alles updaten in die DATABASE
            klantService.updateKlant(bestaandeKlant);
            // mappen van entiteit naar DTO
            KlantDTO klantDTO = klantMapper.toDTO(bestaandeKlant);
            return Response.ok(klantDTO).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKlant(@PathParam("id") int id) {
        // die ID zoeken en zetten in een klant variabel
        Klant bestaandeKlant = klantService.findKlantById(id);
        // als je ID bestaat dan delete je het direk
        if (bestaandeKlant != null) {
            klantService.deleteKlantById(id);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
}
