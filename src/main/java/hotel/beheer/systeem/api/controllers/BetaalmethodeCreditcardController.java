package hotel.beheer.systeem.api.controllers;

import hotel.beheer.systeem.api.dto.BetaalmethodeCreditcardDTO;
import hotel.beheer.systeem.api.entities.BetaalmethodeCreditcard;
import hotel.beheer.systeem.api.mappers.BetaalmethodeCreditcardMapper;
import hotel.beheer.systeem.api.services.BetaalmethodeCreditcardService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
// ----------------------------- ik moet die BetaalmethodeCreditcardController nog testen via de frontend
@Path("/betaalmethodecreditcards")
public class BetaalmethodeCreditcardController {
    private BetaalmethodeCreditcardService betaalmethodeCreditcardService;
    private BetaalmethodeCreditcardMapper betaalmethodeCreditcardMapper;

    public BetaalmethodeCreditcardController(BetaalmethodeCreditcardService betaalmethodeCreditcardService, BetaalmethodeCreditcardMapper betaalmethodeCreditcardMapper ) {
        this.betaalmethodeCreditcardService = betaalmethodeCreditcardService;
        this.betaalmethodeCreditcardMapper = betaalmethodeCreditcardMapper;
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BetaalmethodeCreditcard> getAllBetaalmethodesCreditcards() {
        return betaalmethodeCreditcardService.findAllBetaalmethodeCreditcard();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBetaalmethodeCreditcardById(@PathParam("id") int id) {
        // de id opslaan in een variabel
        BetaalmethodeCreditcard bestaandeCreditcard = betaalmethodeCreditcardService.findBetaalmethodeCreditcardById(id);
        // als ide id niet bestaat retourneer false
        if (bestaandeCreditcard == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // vanaf hier is die ID niet gelijk aan null
        // netjes mappen naar een DTO
        BetaalmethodeCreditcardDTO betaalmethodeCredicardDTO = betaalmethodeCreditcardMapper.toBetaalmethodeCredicardDTO(bestaandeCreditcard);
        return Response.status(Response.Status.OK).entity(betaalmethodeCredicardDTO).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBetaalmethodeCreditcard(BetaalmethodeCreditcard betaalmethodeCreditcard) {
        BetaalmethodeCreditcard saveBetaalmethodeCreditcard = betaalmethodeCreditcardService.saveBetaalmethodeCreditcard(betaalmethodeCreditcard);
        // netjes mappen naar een dto
        BetaalmethodeCreditcardDTO betaalmethodeCredicardDTO = betaalmethodeCreditcardMapper.toBetaalmethodeCredicardDTO(saveBetaalmethodeCreditcard);
        return Response.status(Response.Status.OK).entity(betaalmethodeCredicardDTO).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBetaalmethodeCreditcard(@PathParam("id") int id, BetaalmethodeCreditcard updateBetaalmethodeCreditcard) {
        // de id zoeken in de databse
        BetaalmethodeCreditcard bestaandeBetaalmethodeCreditcard = betaalmethodeCreditcardService.findBetaalmethodeCreditcardById(id);
        // als die ID niet bestaat retourneer false
        if (bestaandeBetaalmethodeCreditcard == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // vanaf hier bestaat die ID wel en dat betekent dat de object waardes erin heeft
        // informatie veranderen van die geupdaten creditcaard in die bestaande class
        bestaandeBetaalmethodeCreditcard.setId(updateBetaalmethodeCreditcard.getId());
        bestaandeBetaalmethodeCreditcard.setVolledigeNaam(updateBetaalmethodeCreditcard.getVolledigeNaam());
        bestaandeBetaalmethodeCreditcard.setKaartnummer(updateBetaalmethodeCreditcard.getKaartnummer());
        bestaandeBetaalmethodeCreditcard.setVervaldatum(updateBetaalmethodeCreditcard.getVervaldatum());
        bestaandeBetaalmethodeCreditcard.setCvv(updateBetaalmethodeCreditcard.getCvv());
        // informatie is al succesvol veranderd in die objet en nu gaan we het opslaan in de database
        betaalmethodeCreditcardService.updateBetaalmethodeCreditcard(bestaandeBetaalmethodeCreditcard);

        // mappen  van een entiteit naar een  DTO
        BetaalmethodeCreditcardDTO betaalmethodeCreditcardDTO = betaalmethodeCreditcardMapper.toBetaalmethodeCredicardDTO(bestaandeBetaalmethodeCreditcard);
        return Response.status(Response.Status.OK).entity(betaalmethodeCreditcardDTO).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBetaalmethodeCreditcard(@PathParam("id") int id) {
        // zoeken naar ud
        BetaalmethodeCreditcard bestaandeBetaalmethodeCreditcard = betaalmethodeCreditcardService.findBetaalmethodeCreditcardById(id);
        // als die ID niet bestaat retourneer flase

        if (bestaandeBetaalmethodeCreditcard == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // hier bestaat de ID wel dan kan he het direk deleten
        betaalmethodeCreditcardService.deleteBetaalmethodeCreditcardById(bestaandeBetaalmethodeCreditcard.getId());
        return Response.status(Response.Status.NO_CONTENT).build();
    }




}
