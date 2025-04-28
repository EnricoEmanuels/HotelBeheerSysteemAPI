package hotel.beheer.systeem.api.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hotel.beheer.systeem.api.dto.BetaalmethodeContantDTO;
import hotel.beheer.systeem.api.entities.Betaalmethode;
import hotel.beheer.systeem.api.entities.BetaalmethodeContant;
import hotel.beheer.systeem.api.mappers.BetaalmethodeContantMapper;
import hotel.beheer.systeem.api.services.BetaalmethodeContantService;
import hotel.beheer.systeem.api.services.BetaalmethodeService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// ----------- Die betaalmethodeCryptoController werkt perfect alleen die GET ALL doet lastig via die frontend
// informatie wil niet verschijnen op die pagina

@Path("/betaalmethodecontanten")
public class BetaalmethodeContantController {
    private BetaalmethodeContantService betaalmethodeContantService;
    private BetaalmethodeContantMapper betaalmethodeContantMapper;
    private BetaalmethodeService betaalmethodeService;

    public BetaalmethodeContantController(BetaalmethodeContantService betaalmethodeContantService, BetaalmethodeContantMapper betaalmethodeContantMapper, BetaalmethodeService betaalmethodeService) {
        this.betaalmethodeContantService = betaalmethodeContantService;
        this.betaalmethodeContantMapper = betaalmethodeContantMapper;
        this.betaalmethodeService = betaalmethodeService;
    }

    // die GET geeft een 200 via die frontend maar die waardes verschijnn niet lastig het werkt wel dsu ik moet nog uit
    // puzzelen wat dat ding is maar ik ga morgen verde
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BetaalmethodeContant> getAllBetaalmethodeContant() {
        return betaalmethodeContantService.getAllBetaalmethodeContant();
    }

//    @GET
//    @Path("")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<BetaalmethodeContant> getAllBetaalmethodeContant() {
//        List<BetaalmethodeContant> betaalmethodeContants = betaalmethodeContantService.getAllBetaalmethodeContant();
//        return new ArrayList<>(betaalmethodeContants);
//    }


    // die GET By ID werkt perfect
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBetaalmethodeContantById(@PathParam("id") int id) {
        // id zoeken en opslaan in een variabel
        BetaalmethodeContant betaalmethodeContantById = betaalmethodeContantService.getBetaalmethodeContantById(id);
        // als die ID niet bestaat retourneer false
        if (betaalmethodeContantById == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(betaalmethodeContantById).build();
        }
        // netjes mappen van een Entiteit naat een DTO
        BetaalmethodeContantDTO betaalmethodeContantDTO = betaalmethodeContantMapper.toBetaalmethodeContantDTO(betaalmethodeContantById);
        return Response.status(Response.Status.OK).entity(betaalmethodeContantDTO).build();
    }

    // bij die subclassen worden die ID niet automatisch gegenereerd omdat ze subsclassen zijn die ID van betaalmethode
    // moet je handmatig zetten in die Post
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBetaalmethodeContant( BetaalmethodeContant betaalmethodeContant) {
        // ik moet die entiteit opslaan via de service
        BetaalmethodeContant saveBetaalmethodeContant = betaalmethodeContantService.saveBetaalmethodeContant( betaalmethodeContant);
        // nethjes mappen van een Entiteit naar een DTO voordat ik het terug stuur naar de server
        BetaalmethodeContantDTO betaalmethodeContantDTO = betaalmethodeContantMapper.toBetaalmethodeContantDTO(saveBetaalmethodeContant);
        return Response.status(Response.Status.CREATED).entity(betaalmethodeContantDTO).build();
    }

    // dit was die ene die niet ging lukken ik ga even naar de vorige voorbeeld
//    @POST
//    @Path("/create")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response createBetaalmethodeContant( Betaalmethode betaalmethode, BetaalmethodeContant betaalmethodeContant) {
//        Betaalmethode betaalmethodeById = betaalmethodeService.getBetaalmethodeById(betaalmethode.getId());
//        // ik moet die entiteit opslaan via de service
//        BetaalmethodeContant saveBetaalmethodeContant = betaalmethodeContantService.saveBetaalmethodeContantMetId(betaalmethodeById, betaalmethodeContant);
//        // nethjes mappen van een Entiteit naar een DTO voordat ik het terug stuur naar de server
//        BetaalmethodeContantDTO betaalmethodeContantDTO = betaalmethodeContantMapper.toBetaalmethodeContantDTO(saveBetaalmethodeContant);
//        return Response.status(Response.Status.CREATED).entity(betaalmethodeContantDTO).build();
//    }


    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBetaalmethodeContant(@PathParam("id") int id, BetaalmethodeContant updateBetaalmethodeContant) {
        // eerst die id opzoeken en opslaan in een variabel
        BetaalmethodeContant betaalmethodeContantById = betaalmethodeContantService.getBetaalmethodeContantById(id);
        // als die ID niet bestaat retourneer false
        if (betaalmethodeContantById == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(betaalmethodeContantById).build();
        }
        // sinds die varaibel betaalmethodeContantById vanaf hier al bestaat dan zal ik ga ik dat genruiken om informatie ering te plaatse van die ander parameeter
        // waarin die nieuwe informatie komt
        betaalmethodeContantById.setId(updateBetaalmethodeContant.getId());
        betaalmethodeContantById.setValuta(updateBetaalmethodeContant.getValuta());
        // informatie is al succescol veranderdt in die OBject nu moet ik het wijzigen in die database
        betaalmethodeContantService.updateBetaalmethodeContant(betaalmethodeContantById);
        // netjes mappen van een entiteit naar een DTO
        BetaalmethodeContantDTO betaalmethodeContantDTO = betaalmethodeContantMapper.toBetaalmethodeContantDTO(betaalmethodeContantById);
        return Response.status(Response.Status.OK).entity(betaalmethodeContantDTO).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBetaalmethodeContant(@PathParam("id") int id) {
        // eerst zoeken naar die id
        BetaalmethodeContant betaalmethodeContantById = betaalmethodeContantService.getBetaalmethodeContantById(id);
        // als die ID niet bestaat retourneer false
        if (betaalmethodeContantById == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(betaalmethodeContantById).build();
        }
        betaalmethodeContantService.deleteBetaalmethodeContant(betaalmethodeContantById.getId());
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
