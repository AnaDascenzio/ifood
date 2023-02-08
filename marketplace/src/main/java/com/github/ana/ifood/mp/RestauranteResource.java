package com.github.ana.ifood.mp;

import com.github.ana.ifood.mp.dto.PratoDTO;
import io.smallrye.mutiny.Multi;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestauranteResource {

    @Inject
    io.vertx.mutiny.pgclient.PgPool client;

    @GET
    @Path("{idRestaurante}/pratos")
    public Multi<PratoDTO> buscarPratos(@PathParam("idRestaurante") Long idRestaurante) {
        return Prato.findAll(client, idRestaurante);
    }



}