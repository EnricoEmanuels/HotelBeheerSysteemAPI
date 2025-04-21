package hotel.beheer.systeem.api.controllers;

import hotel.beheer.systeem.api.dto.BetaalmethodeDTO;
import hotel.beheer.systeem.api.entities.Betaalmethode;
import hotel.beheer.systeem.api.entities.Klant;
import hotel.beheer.systeem.api.mappers.BetaalmethodeMapper;
import hotel.beheer.systeem.api.mappers.KamerMapper;
import hotel.beheer.systeem.api.services.BetaalmethodeService;
import hotel.beheer.systeem.api.services.KlantService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

// ------------------------------------------NOG niet uitgeprobeert via die frontend

@Path("/betaalmethodes")
public class BetaalmethodeController {
    private BetaalmethodeService betaalmethodeService;
    private BetaalmethodeMapper betaalmethodeMapper;
    private KlantService klantService;

    public BetaalmethodeController(BetaalmethodeService betaalmethodeService, BetaalmethodeMapper betaalmethodeMapper, KlantService klantService) {
        this.betaalmethodeService = betaalmethodeService;
        this.betaalmethodeMapper = betaalmethodeMapper;
        this.klantService = klantService;
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Betaalmethode> getAllBetaalmethodes() {
        return betaalmethodeService.getAllBetaalmethodes();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBetaalmethodeById(@PathParam("id") int id) {
        // wanner die ID binnekomt ga ik het zoeken en opslaan in een variabel
        Betaalmethode betaalmethodeById = betaalmethodeService.getBetaalmethodeById(id);
        // als die betaalmethode er niet is retourneer not found
        if ( betaalmethodeById == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // id bestaat we gaan het zeteen van een entiteit naar DTO dus netjes dus retourneren we het
        BetaalmethodeDTO betaalmethodeDTO = betaalmethodeMapper.toDTO(betaalmethodeById);
        return Response.status(Response.Status.OK).entity(betaalmethodeDTO).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBetaalmethode(Betaalmethode betaalmethode) {
        // ik ga die betaalmethode opslaan via die service
        Betaalmethode betaalmethodeOpslaan = betaalmethodeService.saveBetaalmethode(betaalmethode);
        // die response wil ik vilig sturen  dus zal ik het zetten van een entiteit naar dto
        BetaalmethodeDTO betaalmethodeDTO = betaalmethodeMapper.toDTO(betaalmethodeOpslaan);
        return Response.status(Response.Status.OK).entity(betaalmethodeDTO).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBetaalmethode(@PathParam("id") int id, Betaalmethode updateBetaalmethode ) {
        // id zoeken
        Betaalmethode betaalmethodeById = betaalmethodeService.getBetaalmethodeById(id);
        // als id niet bestaat  404
        if (betaalmethodeById == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // id bestaat infor,atie opslaan
        betaalmethodeById.setId(updateBetaalmethode.getId());
        betaalmethodeById.setMethode(updateBetaalmethode.getMethode());
        betaalmethodeById.setDatum(updateBetaalmethode.getDatum());
        // alleen een ID retournern van klant

        // 0 zet je voor cijfers
        // null zet je voor letters

        if ( updateBetaalmethode.getKlant().getId() != 0) {
            Klant klant = klantService.findKlantById(updateBetaalmethode.getKlant().getId());
            betaalmethodeById.setKlant(klant);

        }
        // updaten in de database
        betaalmethodeService.updateBetaalmethode(betaalmethodeById);
        // mappen van entiteit naar dto
        betaalmethodeMapper.toDTO(betaalmethodeById);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBetaalmethode(@PathParam("id") int id) {
        // id zoeken
        Betaalmethode betaalmethodeById = betaalmethodeService.getBetaalmethodeById(id);
        // als id niet bestaat retourneer 404
        if (betaalmethodeById == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // id bestaat wel dus delete het
        betaalmethodeService.deleteBetaalmethode(id);
        return Response.status(Response.Status.OK).build();
    }
}
