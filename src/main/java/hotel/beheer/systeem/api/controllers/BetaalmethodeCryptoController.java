package hotel.beheer.systeem.api.controllers;

import hotel.beheer.systeem.api.dto.BetaalmethodeCryptoDTO;
import hotel.beheer.systeem.api.entities.BetaalmethodeCrypto;
import hotel.beheer.systeem.api.mappers.BetaalmethodeCryptoMapper;
import hotel.beheer.systeem.api.services.BetaalmethodeCryptoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

// -------------- ik moet het nog testen via die frontend
@Path("/betaalmethodecryptos")
public class BetaalmethodeCryptoController {
    private BetaalmethodeCryptoService betaalmethodeCryptoService;
    private BetaalmethodeCryptoMapper betaalmethodeCryptoMapper;

    public BetaalmethodeCryptoController(BetaalmethodeCryptoService betaalmethodeCryptoService, BetaalmethodeCryptoMapper betaalmethodeCryptoMapper ) {
        this.betaalmethodeCryptoService = betaalmethodeCryptoService;
        this.betaalmethodeCryptoMapper = betaalmethodeCryptoMapper;
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BetaalmethodeCrypto> getAllBetaalmethodeCryptos() {
        return betaalmethodeCryptoService.getAllBetaalmethodeCrypto();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBetaalmethodeCryptoById(@PathParam("id") int id) {
        // betaalmethodeCryptoID zoeken en opslaan in een variabel
        BetaalmethodeCrypto betaalmethodeCryptoById = betaalmethodeCryptoService.getBetaalmethodeCryptoById(id);
        // als die id niet bestaat retourneer false
        if (betaalmethodeCryptoById == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // vanaf hier bestaat die ID dan kan je het mappen naar een DTO en sturen
        BetaalmethodeCryptoDTO betaalmethodeCryptoDTO = betaalmethodeCryptoMapper.toBetaalmethodeCryptoDTO(betaalmethodeCryptoById);
        return Response.status(Response.Status.OK).entity(betaalmethodeCryptoDTO).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBetaalmethodeCrypto(BetaalmethodeCrypto betaalmethodeCrypto) {
        // ik wil die waardes opslaan via die service layer
        BetaalmethodeCrypto saveBetaalmethodeCrypto = betaalmethodeCryptoService.saveBetaalmethodeCrypto(betaalmethodeCrypto);
        // het is al opgeslagen in de database dan kan ik het mappen van een entiteit naar een DTO en sturen naar de frontend
        BetaalmethodeCryptoDTO betaalmethodeCryptoDTO = betaalmethodeCryptoMapper.toBetaalmethodeCryptoDTO(saveBetaalmethodeCrypto);
        return Response.status(Response.Status.CREATED).entity(betaalmethodeCryptoDTO).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBetaalmethodeCrypto(@PathParam("id") int id, BetaalmethodeCrypto updatebetaalmethodeCrypto) {
        // eerst die ID zoeken in die database

        // met die referentie bestaandeBetaalmethodeCrypto kan ik toegang hebben tot al die eigenschappen en methodes van die klasse BetaalmethodeCrypto
        // met die referentie updatebetaalmethodeCrypto kan ik toegang hebben tot al die eigenschappen en methodes van die klasse BetaalmethodeCrypto

        BetaalmethodeCrypto bestaandeBetaalmethodeCrypto = betaalmethodeCryptoService.getBetaalmethodeCryptoById(id);
        // als die ID niet bestaat retourneer false
        if (bestaandeBetaalmethodeCrypto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // vanaf hier bestaat die ID well
        // dan moet ik die waardes veranderen in die bestaande object
        bestaandeBetaalmethodeCrypto.setId(updatebetaalmethodeCrypto.getId());
        bestaandeBetaalmethodeCrypto.setWalletAdres(updatebetaalmethodeCrypto.getWalletAdres());
        bestaandeBetaalmethodeCrypto.setMuntsoort(updatebetaalmethodeCrypto.getMuntsoort());
        // het is al succesvol gewijzigd in die bestaande object dus we gaan het vervolgens updaten in die database
        betaalmethodeCryptoService.updateBetaalmethodeCrypto(bestaandeBetaalmethodeCrypto);
        // netjes mappen van een Entiteit naar een DTO
        BetaalmethodeCryptoDTO betaalmethodeCryptoDTO = betaalmethodeCryptoMapper.toBetaalmethodeCryptoDTO(bestaandeBetaalmethodeCrypto);
        return Response.status(Response.Status.OK).entity(betaalmethodeCryptoDTO).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBetaalmethodeCrypto(@PathParam("id") int id) {
        // je gaat de ID eerst opzoeken
        BetaalmethodeCrypto bestaandeBetaalmethodeCryptoId = betaalmethodeCryptoService.getBetaalmethodeCryptoById(id);
        // als de id niet bestaat retourneer false
        if (bestaandeBetaalmethodeCryptoId == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // vanaf hier bestaat de ID wel dan gaan we het direl deleten
        betaalmethodeCryptoService.deleteBetaalmethodeCrypto(bestaandeBetaalmethodeCryptoId.getId());
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
