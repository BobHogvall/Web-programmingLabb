package com.example.jakartaee.artist;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/artists")
@Produces(MediaType.APPLICATION_JSON)
public class ArtistController {

    @Inject
    ArtistRepository repository;
    @Inject
    ArtistMapper mapper;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOne(@Valid Artist artist){
        repository.insertArtist(artist);
        return Response.created(URI.create("artists/" + artist.getId())).build();
    }

    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") Long id){
        var artist = repository.findOne(id);
        if(artist.isPresent())
            return Response.ok().entity(artist.get()).build();
        throw new NotFoundException("Id: " + id);
    }

    @GET
    public List<ArtistDto> getAll(@QueryParam("name") String name){
        if(name == null)
            return mapper.map(repository.findAll());
        return mapper.map(repository.findAllByName(name));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOne(@PathParam("id") Long id, ArtistDto artist){
        return Response.ok().entity(mapper.map(repository.updateArtist(id, mapper.map(artist)))).build();
    }

    @DELETE
    @Path("/{id}")
    public void deleteOne(@PathParam("id") Long id){
        repository.deleteArtist(id);
    }
}