package com.github.ana.ifood.mp;

import io.vertx.mutiny.pgclient.PgPool;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class RestauranteCadastrado {

    @Inject
    PgPool pgPool;

    @Incoming("restaurantes")

    public void receberRestaurante(String json){
        Jsonb create =  JsonbBuilder.create();
       Restaurante restaurante = create.fromJson(json, Restaurante.class);

        System.out.println("---------------------------------");
        System.out.println(json);
        System.out.println("---------------------------------");
        System.out.println(restaurante);

        restaurante.persist(pgPool);


    }
}
