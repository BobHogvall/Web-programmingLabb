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
    @SuppressWarnings("redundant")
    public Response addOne(@Valid ArtistDto artist){
        var inputReceivedByDto = artist;
        var convertFromDtoToEntity = mapper.map(inputReceivedByDto);
        repository.insertArtist(convertFromDtoToEntity);
        return Response.created(URI.create("artists/" + artist.getId())).build();
    }

    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") Long id){
        var artistAsEntityObject = repository.findOne(id);
        if(artistAsEntityObject.isPresent()){
            var convertFromEntityToDto = mapper.map(artistAsEntityObject.get());
            return Response.ok().entity(convertFromEntityToDto).build();
        } else
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
    @SuppressWarnings("redundant")
    public Response updateOne(@PathParam("id") Long id, @Valid ArtistDto artist){
        var idInputCanBeEntity = id;
        var inputReceivedAsDto = artist;
        var ifFoundArtist = repository.findOne(idInputCanBeEntity);
        if(ifFoundArtist.isPresent()) {
            var inputConvertedFromDtoToEntity = mapper.map(inputReceivedAsDto);
            var updateDatabaseWithEntityViaRep = repository.updateArtist(idInputCanBeEntity, inputConvertedFromDtoToEntity);
            var convertUpdatedObjectBackToDto = mapper.map(updateDatabaseWithEntityViaRep);
            return Response.ok().entity(convertUpdatedObjectBackToDto).build();
        }
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") Long id){
        var artist = repository.findOne(id);
        if(artist.isPresent()){
            repository.deleteArtist(id);
            return Response.ok().build();
        }
        return Response.noContent().build();
    }
}