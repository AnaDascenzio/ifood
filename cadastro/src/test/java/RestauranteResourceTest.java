import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import ifood.cadastro.Restaurante;
import ifood.cadastro.dto.AtualizarRestauranteDTO;
import org.junit.Assert;
import util.TokenUtils;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;

    @DBRider
    @DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
    @QuarkusTest
    @QuarkusTestResource(CadastroTestLifecycleManager.class)
    public class RestauranteResourceTest {
        private String token;

        @BeforeEach
        public void gereToken() throws Exception{
            token = TokenUtils.generateTokenString("/JWTProprietarioClaims.json", null);
        }


        @Test
        @DataSet("restaurantes-cenario-1.yml")
        public void testBuscarRestaurantes() {
            String resultado = given()
                    .when().get("/restaurantes")
                    .then()
                    .statusCode(Response.Status.OK.getStatusCode())
                    .extract().asString();
            Approvals.verifyJson(resultado);
        }

        private RequestSpecification given() {
            return RestAssured.given()
                    .contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + token));
        }


        @Test
        @DataSet("restaurantes-cenario-1.yml")
        public void testAlterarRestaurante() {
            AtualizarRestauranteDTO dto = new AtualizarRestauranteDTO();
            dto.nome = "novoNome";
            Long parameterValue = 123L;
            given()
                    .with().pathParam("id", parameterValue)
                    .body(dto)
                    .when().put("/restaurantes/{id}")
                    .then()
                    .statusCode(Response.Status.NO_CONTENT.getStatusCode())
                    .extract().asString();

            Restaurante findById = Restaurante.findById(parameterValue);

            //poderia testar todos os outros atribudos
            Assert.assertEquals(dto.nome, findById.nome);

        }

    }

