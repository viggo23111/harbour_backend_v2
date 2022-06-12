package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDTO;
import dtos.HarbourDTO;
import dtos.OwnerDTO;
import entities.Boat;
import facades.SystemFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/system")
public class HarbourResource {
    //Todo Remove or change relevant parts before ACTUAL use

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final SystemFacade FACADE = SystemFacade.getSystemFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/owner/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllOwners() {
        List<OwnerDTO> ownerDTOS = FACADE.getAllOwners();
        return Response.ok().entity(GSON.toJson(ownerDTOS)).build();
    }

    @GET
    @Path("/boat/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllBoats() {
        List<BoatDTO> boatDTOS = FACADE.getAllBoats();
        return Response.ok().entity(GSON.toJson(boatDTOS)).build();
    }

    @GET
    @Path("/boatowners/{boatID}/")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllOwnersByBoatID(@PathParam("boatID") int boatID) {
        List<OwnerDTO> ownerDTOS = FACADE.getAllOwnersByBoatID(boatID);
        return Response.ok().entity(GSON.toJson(ownerDTOS)).build();
    }

    @GET
    @Path("/boat/{boatID}/")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBoatByBoatID(@PathParam("boatID") int boatID) {
        BoatDTO boatDTO = FACADE.getBoatByID(boatID);
        return Response.ok().entity(GSON.toJson(boatDTO)).build();
    }

    @GET
    @Path("/harbour/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllHarbours() {
        List<HarbourDTO> harbourDTOS = FACADE.getAllHarbours();
        return Response.ok().entity(GSON.toJson(harbourDTOS)).build();
    }


    @GET
    @Path("/harbour/{harbourID}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllBoatsByHarbourID(@PathParam("harbourID") int harbourID) {
        List<BoatDTO> boatDTOS = FACADE.getAllBoatsByHarbourID(harbourID);
        return Response.ok().entity(GSON.toJson(boatDTOS)).build();
    }

    @PUT
    @Path("/boatconnectharbour/{boatID}/{harbourID}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response connectBoatToHarbour(@PathParam("boatID") int boatID, @PathParam("harbourID") int harbourID) {
        BoatDTO updated = FACADE.connectBoatToHarbour(boatID,harbourID);
        return Response.ok().entity(GSON.toJson(updated)).build();
    }


    @PUT
    @Path("/boataddowner/{boatID}/{ownerID}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addOwnerToBoat(@PathParam("boatID") int boatID, @PathParam("ownerID") int ownerID) {
        BoatDTO updated = FACADE.addOwnerToBoat(boatID,ownerID);
        return Response.ok().entity(GSON.toJson(updated)).build();
    }

    @PUT
    @Path("/boatremoveowner/{boatID}/{ownerID}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response removeOwnerFromBoat(@PathParam("boatID") int boatID, @PathParam("ownerID") int ownerID) {
        BoatDTO updated = FACADE.removeOwnerFromBoat(boatID,ownerID);
        return Response.ok().entity(GSON.toJson(updated)).build();
    }

    @DELETE
    @Path("/deleteboat/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteBoat(@PathParam("id") int id) throws NotFoundException {
        FACADE.deleteBoat(id);
        return Response.ok().entity("Deleted").build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createBoat(String content){
        BoatDTO pdto = GSON.fromJson(content, BoatDTO.class);
        BoatDTO newPdto = FACADE.createBoat(pdto);
        return Response.ok().entity(GSON.toJson(newPdto)).build();
    }

}
