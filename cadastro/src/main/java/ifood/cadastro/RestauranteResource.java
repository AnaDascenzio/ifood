package ifood.cadastro;

import groovy.json.JsonBuilder;
import groovy.json.JsonGenerator;
import ifood.cadastro.dto.AdicionarRestauranteDTO;
import ifood.cadastro.dto.PratoMapper;
import ifood.cadastro.dto.RestauranteMapper;
import ifood.cadastro.infra.ConstraintViolationResponse;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.MapsId;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "restaurante")
@RolesAllowed("proprietario")
@SecurityScheme(securitySchemeName = "ifood-oauth",type = SecuritySchemeType.OAUTH2, flows =@OAuthFlows(password = @OAuthFlow(tokenUrl =
        "http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token")))@SecurityRequirements(value = {@SecurityRequirement(name = "ifood-oauth", scopes = {})})
public class RestauranteResource {

    @Inject
    RestauranteMapper restauranteMapper;

    @Inject
    PratoMapper pratoMapper;

    @Inject
    @Channel("restaurantes")
    Emitter<String> emitter;

    @GET
    @Counted(name="Quantidade buscas restaurantes")
    @SimplyTimed(name="tempo simples de busca")
    @Timed(name="tempo completo de busca")
    public List<Restaurante> buscar(){
        return Restaurante.listAll();
    }



    @POST
    @Transactional
    @APIResponse(responseCode ="400", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
    @APIResponse(responseCode ="201", description = "caso o restaurante seja adicionado com sucesso")
    public Response adicionar(@Valid AdicionarRestauranteDTO dto){
        Restaurante restaurante = restauranteMapper.toRestaurante(dto);
        restaurante.persist();


        Jsonb create = JsonbBuilder.create();
        String json = create.toJson(restaurante);

        emitter.send(json);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void atualizar(@PathParam("id") Long id, Restaurante dto){
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
        if(restauranteOp.isEmpty()){
            throw new NotFoundException();
        }
        Restaurante restaurante = restauranteOp.get();
        restaurante.nome = dto.nome;
        restaurante.persist();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deletar(@PathParam("id") Long id, Restaurante dto){
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
        restauranteOp.ifPresentOrElse(Restaurante::delete, ()-> {throw new NotFoundException();} );
    }

    //pratos

    @GET
    @Path("{idRestaurante}/pratos")
    @Tag(name="prato")
    public List<Restaurante> buscarPratos(@PathParam("idRestaurante") Long idRestaurante){
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
        if (restauranteOp.isEmpty()){
            throw new NotFoundException("Classes.Restaurante não existe");

        }
        return Prato.list("Classes.Restaurante",restauranteOp.get());
    }


    @POST
    @Path("{idRestaurante}/pratos")
    @Tag(name="prato")
    @Transactional
    public Response adicionarPrato(@PathParam("idRestaurante") Long idRestaurante, Prato dto){
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
        if (restauranteOp.isEmpty()){
            throw new NotFoundException("Classes.Restaurante não existe");

        }
        //utilizando dto
        Prato prato = new Prato();
        prato.nome = dto.nome;
        prato.descricao = dto.descricao;
        prato.preco = dto.preco;
        prato.restaurante = restauranteOp.get();
        prato.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{idRestaurante}/pratos")
    @Tag(name="prato")
    @Transactional
    public void atualizarPrato(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id, Prato dto){
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
        if(restauranteOp.isEmpty()){
            throw new NotFoundException("Classes.Restaurante não existe");
        }
        Optional<Prato> pratosOp = Prato.findByIdOptional(id);
        if(pratosOp.isEmpty()){
            throw new NotFoundException("Classes.Prato não existe");
        }

        Prato prato = pratosOp.get();
        prato.preco = dto.preco;
        prato.persist();
    }

    @DELETE
    @Path("{idRestaurante}/pratos")
    @Tag(name="prato")
    @Transactional
    public void deletarPrato(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id){
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
        Optional<Prato> pratosOp = Prato.findByIdOptional(id);
        if(restauranteOp.isEmpty()){
            throw new NotFoundException("Classes.Restaurante não existe");
        }
        if(pratosOp.isEmpty()){
            throw new NotFoundException("Classes.Prato não existe");
        }
        pratosOp.ifPresentOrElse(Prato::delete, ()-> {throw new NotFoundException();} );
    }


}

