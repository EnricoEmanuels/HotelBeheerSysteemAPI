package hotel.beheer.systeem.api.controllers;

import hotel.beheer.systeem.api.mappers.KamerMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import hotel.beheer.systeem.api.entities.Kamer;
import hotel.beheer.systeem.api.dto.KamerDTO;


import hotel.beheer.systeem.api.services.KamerService;

// werkt lekker
// http://localhost:8080/api/kamers/

@Path("/kamers")
public class KamerController {
    private KamerService kamerService;
    private KamerMapper kamerMapper;

    public KamerController(KamerService kamerService, KamerMapper kamerMapper) {
        this.kamerService = kamerService;
        this.kamerMapper = kamerMapper;
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Kamer> getAlleKamers() {
        return kamerService.getAllKamers();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getkamerById(@PathParam("id") int id) {
        // eerst die binnen komende ID opslaan
        Kamer kamerById = kamerService.findKamerById(id);
        // als die ID niet bestaa retourneert bestaat niet
        // devnesieve cedering
        if (kamerById == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // als die ID wel bestaat retourneer het bestaat wel
        // sinds we al devensieve codering hebben gebruikt als die ID niet bestaat
        // dan hoeven we geen if te make als die ID wel bestaat want dat wordt automatisch gedaan

        // mappen van een entiteit naar een DTO
        KamerDTO kamerDTO = kamerMapper.toKamerDTO(kamerById);
        return Response.status(Response.Status.OK).entity(kamerDTO).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createKamer(Kamer kamer) {
        // informatie opslaan in via de service kamer en opslaan in een kamer variabel
        Kamer kameropslaan = kamerService.saveKamer(kamer);
        // van een entiteit zetten naar een DTO
        KamerDTO kamerDTO = kamerMapper.toKamerDTO(kameropslaan);
        return Response.status(Response.Status.OK).entity(kamerDTO).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateKamer(@PathParam("id") int id, Kamer kamer) {
        // met die parameter van die kamer of die referentie
        // kan ik ook toegang hebben tot al die eigenschappen en methodes
        // van die kamer classe vb
        // kamer.getId() of kamer.setId()

        // id zoekeb opslaan in een variabel
        Kamer bestaatDezeKamer = kamerService.findKamerById(id);
        // als die ID niet bestaat retourneer dat die Uitkomst
        if (bestaatDezeKamer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // vanaf hier je bestaatDezeKamer != null
        // je hoeft het niet meer te schrijven

        // informatie vervangen met nieuwe informatie
//        bestaatDezeKamer.setId(kamer.getId()); // id hoef ik niet te doen
        bestaatDezeKamer.setKamertype(kamer.getKamertype());
        bestaatDezeKamer.setAantalbedden(kamer.getAantalbedden());
        bestaatDezeKamer.setPrijsPerMaand(kamer.getPrijsPerMaand());

        // informatie nu updaten in die database
        kamerService.updateKamer(bestaatDezeKamer);
        // mappen van entiteit naar DTO
        KamerDTO kamerDTO = kamerMapper.toKamerDTO(bestaatDezeKamer);
        // retourneer de correcte response
        return Response.ok(kamerDTO).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKamerById(@PathParam("id") int id) {
        // id eerst zoeken en opslaan in een variabel
        Kamer bestaatDezeId = kamerService.findKamerById(id);
        // als je id niet bestaat retourneert bestaat niet
        if (bestaatDezeId == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // als die ID != aan null gaat die code gewoon verder je hoef
        // het niet opnieuw te definieren
        // Nu is ID != nulll dus dan bestaat het dus vervolgens zullen wij het
        // gerust verwijderen
        kamerService.deleteById(id);
        return Response.status(Response.Status.OK).build();
    }
}
