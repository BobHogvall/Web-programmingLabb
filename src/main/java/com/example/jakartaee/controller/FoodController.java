package com.example.jakartaee.controller;

import com.example.jakartaee.dto.FoodDto;
import com.example.jakartaee.entity.Food;
import com.example.jakartaee.exception.IdNotFoundException;
import com.example.jakartaee.mapper.Mapper;
import com.example.jakartaee.repository.FoodRepository;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/foods")
public class FoodController {

    @Inject
    FoodRepository repository;

    @Inject
    Mapper mapper;

//    @Inject
//    Client client;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FoodDto> getAll(@QueryParam("name") String name) { //lista vill aldrig kasta exception, skickar bara empty
        if (name == null)
            return mapper.map(repository.findAll());

        return mapper.map(repository.findAllByName(name));
    }

    @GET //READ
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Returns food object",
//                    content = @Content(schema = @Schema(implementation = FoodDto.class))),
//            @ApiResponse(responseCode = "404", description = "Id not found")})
    public Response getOne(@PathParam("id") Long id) { //här ber jag databasen om info/objekt
        var foodAsEntityObject = repository.findOne(id);
        if (foodAsEntityObject.isPresent()){
            var convertFromEntityToDto = mapper.map(foodAsEntityObject.get());
            return Response.ok().entity(convertFromEntityToDto).build();
        }
//            return Response.ok().entity(foodAsEntityObject.get()).build(); //wrappad i mappning
//        return Response.status(404).build();
//        throw new IdNotFoundException("Id: " + id);
        throw new NotFoundException("Id: " + id);  //färdigskapat felmeddelande
        //om vi vill ha kattbild kan vi gör det med egenskapade exceptions
    }

    @POST //CREATE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOne(@Valid FoodDto food) { //jag lägger till, dto-entit, borde vara dtoFood
        var inputReceivedByDto = food;
        var convertFromDtoToEntity = mapper.map(inputReceivedByDto);
        repository.insertFood(convertFromDtoToEntity);


//        repository.insertFood(mapper.map(food)); //som mappas till food
        return Response.created(URI.create("foods/" + food.getId())).build();
    }

    @DELETE //D
    @Path("/{id}")
    public void deleteOne(@PathParam("id") Long id) {
        repository.deleteFood(id);
    }
    //alla som har One = felmeddelanden
    //delete kan vara okej att det går igenom om tom - finns ändå inget "är ju borttagen"


    @PUT //UPDATE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updDate(@PathParam("id") Long id, FoodDto food) {
        var idInputCanBeEntity = id;
        var inputReceivedAsDto = food;
        var inputConvertedFromDtoToEntity = mapper.map(inputReceivedAsDto);
        var updateDatabaseWithEntityViaRep = repository.update(idInputCanBeEntity, inputConvertedFromDtoToEntity);
        var convertUpdatedObjectBackToDto = mapper.map(updateDatabaseWithEntityViaRep);

        return Response.ok().entity(convertUpdatedObjectBackToDto).build();
    }

//    @GET
//    @Path("/extra")
//    @Produces(MediaType.APPLICATION_JSON)
//    public FoodDto extraEndpoint(){
//        return client.target("http://localhost:8080/api/foods/17")
//                .request(MediaType.APPLICATION_JSON)
//                .get(FoodDto.class);
//    }
}
