package hotel.beheer.systeem.api.controllers;

import hotel.beheer.systeem.api.dto.BeschikbareKamerDTO;
import hotel.beheer.systeem.api.entities.BeschikbareKamer;
import hotel.beheer.systeem.api.mappers.BeschikbareKamerMapper;
import hotel.beheer.systeem.api.services.BeschikbareKamersService;
import hotel.beheer.systeem.api.services.KamerService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

// ----------------------------------------nog niet geprobeert via die frontend

@Path("/beschikbarekamers")
public class BeschikbareKamersController {
    private BeschikbareKamersService beschikbareKamersService;
    private BeschikbareKamerMapper beschikbareKamerMapper;
    private KamerService kamerService;

    public BeschikbareKamersController(BeschikbareKamersService beschikbareKamersService, BeschikbareKamerMapper beschikbareKamerMapper, KamerService kamerService) {
        this.beschikbareKamersService = beschikbareKamersService;
        this.beschikbareKamerMapper = beschikbareKamerMapper;
        this.kamerService = kamerService;
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BeschikbareKamer> getAllBeschikbareKamers() {
        return beschikbareKamersService.getAllBeschikbareKamers();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBeschikbareKamerById(@PathParam("id") int id) {
        // id zoeken en opslaan in een variabel
        BeschikbareKamer beschikbareKamerById = beschikbareKamersService.findBeschikbareKamerById(id);
        // als die ID niet bestaat retourneer not found
        if (beschikbareKamerById == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // hier is je ID != null dus het bestaat hier (defensieve codering)
        // dan wil je het netjes mappen naar een DTO en sturen naar de server dan de frontend
        BeschikbareKamerDTO beschikbareKamerDTO = beschikbareKamerMapper.beschikbareKamerDTO(beschikbareKamerById);
        return Response.status(Response.Status.OK).entity(beschikbareKamerDTO).build();
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBeschikbareKamer(BeschikbareKamer beschikbareKamer) {
        // wanner die informatie komt via die parameters wil ik ze direk opslaan
        BeschikbareKamer beschikbareKamerOpslaan = beschikbareKamersService.saveBeschikbareKamer(beschikbareKamer);
        // netjes informatie mappen naar een DTO
        BeschikbareKamerDTO beschikbareKamerDTO = beschikbareKamerMapper.beschikbareKamerDTO(beschikbareKamerOpslaan);
        // retourneren naar de server via de server gaat het naar de frontend
        return Response.status(Response.Status.CREATED).entity(beschikbareKamerDTO).build(); // je mag CREATED of OK zetten

    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBeschikbareKamer(int id , BeschikbareKamer updatebeschikbareKamer) {
        // eerst id zoeken en opslaa in een variabel
        BeschikbareKamer beschikbareKamer = beschikbareKamersService.findBeschikbareKamerById(id);
        // als die variabel niet besstaat == false
        if (beschikbareKamer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // id != null informatie updaten via die bestaande entiteit
        beschikbareKamer.setId(updatebeschikbareKamer.getId());
        beschikbareKamer.setBeschikbareKamerAlternatief(updatebeschikbareKamer.getBeschikbareKamerAlternatief());
        beschikbareKamer.setKamer(updatebeschikbareKamer.getKamer());

        // informatie is al opgeslagen dus id die object nu moeten we het soplaan in die database
        beschikbareKamersService.updateBeschikbareKamer(beschikbareKamer);
        // netjes mappen naar een DTO en versturen
        BeschikbareKamerDTO beschikbareKamerDTO = beschikbareKamerMapper.beschikbareKamerDTO(beschikbareKamer);
        return Response.status(Response.Status.OK).entity(beschikbareKamerDTO).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBeschikbareKamer(@PathParam("id") int id) {
        // id zoeken
        BeschikbareKamer beschikbareKamerById = beschikbareKamersService.findBeschikbareKamerById(id);
        // als het niet bestaat == false
        if (beschikbareKamerById == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        beschikbareKamersService.deleteBeschikbareKamer(id);
        return Response.status(Response.Status.NO_CONTENT).build();

    }
}
