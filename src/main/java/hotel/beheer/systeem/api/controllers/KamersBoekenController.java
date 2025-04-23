package hotel.beheer.systeem.api.controllers;

import hotel.beheer.systeem.api.dto.KamersBoekenDTO;
import hotel.beheer.systeem.api.entities.BeschikbareKamer;
import hotel.beheer.systeem.api.entities.Betaalmethode;
import hotel.beheer.systeem.api.entities.KamersBoeken;
import hotel.beheer.systeem.api.entities.Klant;
import hotel.beheer.systeem.api.mappers.KamersBoekenMapper;
import hotel.beheer.systeem.api.services.BeschikbareKamersService;
import hotel.beheer.systeem.api.services.BetaalmethodeService;
import hotel.beheer.systeem.api.services.KamersBoekenService;
import hotel.beheer.systeem.api.services.KlantService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

@Path("/kamersboeken")
public class KamersBoekenController {
    private KamersBoekenService kamersBoekenService;
    private KamersBoekenMapper kamersBoekenMapper;
    private KlantService klantService;
    private BeschikbareKamersService beschikbareKamersService;
    private BetaalmethodeService betaalmethodeService;


    public KamersBoekenController(KamersBoekenService kamersBoekenService, KamersBoekenMapper kamersBoekenMapper, KlantService klantService, BeschikbareKamersService beschikbareKamersService, BetaalmethodeService betaalmethodeService) {
        this.kamersBoekenService = kamersBoekenService;
        this.kamersBoekenMapper = kamersBoekenMapper;
        this.klantService = klantService;
        this.beschikbareKamersService = beschikbareKamersService;
        this.betaalmethodeService = betaalmethodeService;
    }

    // GET
//    @GET
//    @Path("")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getAllKamersBoeken() {
//        KamersBoeken kamersBoeken = kamersBoekenService.findAllKamersBoeken();
//        kamersBoekenMapper.toKamersBoekenDTO(kamersBoeken);
//        return Response.status(Response.Status.OK).entity(kamersBoeken).build();
//    }
//


    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<KamersBoeken> getAllKamersBoeken() {
        return kamersBoekenService.findAllKamersBoeken();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKamersBoekenById(@PathParam("id") int id) {
        // (@PathParam("id") int id) deze parameter is essentieel
        // variabel komt naar binnen we moeten het zoeken en opslaan
        KamersBoeken kamersBoekenById = kamersBoekenService.findKamersBoekenById(id);
        // als die kamerID niet bestaat retourneer false
        if (kamersBoekenById == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // vanaf hier bestaat die ID wel
        // eerst mappen van een entiteit naar een dto
        KamersBoekenDTO kamersBoekenDTO = kamersBoekenMapper.toKamersBoekenDTO(kamersBoekenById);
        return Response.status(Response.Status.OK).entity(kamersBoekenDTO).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createKamersBoeken(KamersBoeken kamersBoeken) {
        // eerst die waardes opslaan via die service layer
        KamersBoeken saveKamersBoeken = kamersBoekenService.saveKamersBoeken(kamersBoeken);
        // we moeten deze variabel mappen naar een DTO om het veilig te sturen
        KamersBoekenDTO kamersBoekenDTO = kamersBoekenMapper.toKamersBoekenDTO(saveKamersBoeken);
        return Response.status(Response.Status.CREATED).entity(kamersBoekenDTO).build();

    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateKamersBoeken(@PathParam("id") int id, KamersBoeken updatekamersBoeken ) {
        // @PathParam("id") int id dit is heel belangrijk als je dit weghaalt ga je een error krijgen
        // die ID komt na binnen je wilt het eerst opzoeken en daarna opslaan in een variabel
        KamersBoeken kamersBoeken = kamersBoekenService.findKamersBoekenById(id);
        // als je ID niet bestaat retourneer false
        if (kamersBoeken == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // hier bestaat de kamersID wel
        // je via die parameter updatekamersBoeken toegang hebben tot die eigenscappen en methodes van die classe kamersboeken
        // je via die parameter kamersBoeken toegang hebben tot die eigenscappen en methodes van die classe kamersboeken

        // informatie updaten
        kamersBoeken.setId(updatekamersBoeken.getId());
        kamersBoeken.setStartdatum(updatekamersBoeken.getStartdatum());
        kamersBoeken.setEinddatum(updatekamersBoeken.getEinddatum());

        // als je klant_id ingevuld is trouwens het moet ingevuld zijn
        // het is op (optional = false)
        // en die ID zoeken via die Klantservice Layer
        if (updatekamersBoeken.getKlant().getId() != 0) {
            // die ID zoeken
            Klant klantById = klantService.findKlantById(updatekamersBoeken.getKlant().getId());
            kamersBoeken.setKlant(klantById);
        }

        // beschikbarekamer_ID moet ingevuld zijn en dan gaan we die id opzoeken via die
        // beschikbarekamer service
        if (updatekamersBoeken.getBeschikbareKamer().getId() != 0) {
            // id zoeken
            BeschikbareKamer beschikbareKamerById = beschikbareKamersService.findBeschikbareKamerById(updatekamersBoeken.getBeschikbareKamer().getId());
            kamersBoeken.setBeschikbareKamer(beschikbareKamerById);
        }
        // betaalmethode_ID moet ingevuld zijn en we gaan die specieke id opzoeken die niewe
        // ID die omt binnen die methode via die parameter

        // als er een waarde is gekomen in die betaalmethode dan krijgen we dit
        // hier ben je in die kamersboeken classe je doet getBetaalethodes() om die
        // betaalmethode te retourneren je bent nogsteeds in die classe betaalmethode
        // dan doe je die getID() om die waarde te krijgen van die foreign key in die klassee
        // kamersboeken in die eigenschap betaalmethode_ID
        if (updatekamersBoeken.getBetaalmethodes().getId() != 0) {
            // sinds er een waarde is in die betaalmethode_ID gaan we die waarde
            // zoeken via die service layer vand die betaalmethodes
            Betaalmethode betaalmethodeById = betaalmethodeService.getBetaalmethodeById(updatekamersBoeken.getBetaalmethodes().getId());
            // waarde zetten via de setter
            kamersBoeken.setBetaalmethodes(betaalmethodeById);
        }

        // we hebben informatie succesvol upgedate in die object maarr nog niet in die database
        kamersBoekenService.updateKamersBoeken(kamersBoeken);

        // mappen van entiteit naar een DTO
        KamersBoekenDTO kamersBoekenDTO = kamersBoekenMapper.toKamersBoekenDTO(kamersBoeken);
        return Response.status(Response.Status.OK).entity(kamersBoekenDTO).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKamersBoeken(@PathParam("id") int id) {
        // je gaat die ID zoeken
        // kamersBoekenById deze variabel heeft toegang tot al die eigenschappen en methodes
        // van die klasse kamersboeken
        KamersBoeken kamersBoekenById = kamersBoekenService.findKamersBoekenById(id);
        // als ID niet bestaar retourneer false
        if (kamersBoekenById == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // vanf hier bestaat je ID wel
        kamersBoekenService.deleteKamersBoeken(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
