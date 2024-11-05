package tests.products;

import config.Configuracoes;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.*;
import util.BaseTest;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Delete Products Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteProductTest extends BaseTest {

    @BeforeAll
    public void setUp() {
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);
        baseURI = configuracoes.baseUri();
        enableLoggingOfRequestAndResponseIfValidationFails();
        createReport(this.getClass().getSimpleName());
    }

    @AfterAll
    public void tearDown(){
        extent.flush();
    }

    @Test
    @DisplayName("Deletar um produto")
    void deleteProductTest() throws Exception {
        createTest("Deletar um produto");

        String endpoint = "/products";
        String productId = "/20";

        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "*/*");

        Response getProduct = get(headers, endpoint+productId);
        logInfoSeparator("INFORMAÇÕES DO PRODUTO A SER REMOVIDO");
        logInfoJson(getProduct.asPrettyString());

        String message;
        logInfoSeparator("DADOS ENVIADOS");
        logInfo("URL BASE: " + baseURI);
        logInfo("ENDPOINT: " + endpoint);
        logInfo("ID DO PRODUTO: " + productId);

        Response response = delete(endpoint+productId);

        logInfoSeparator("DADOS RETORNADOS");
        logInfoAlignCenter("Headers:");
        logInfoResponseHeaders(response.headers().asList());

        logInfoAlignCenter("Response body:");
        logInfoJson(response.asPrettyString());
        logInfo("Status code " + extractStatusCode(response));

        logInfoSeparator("VALIDAÇÕES");

        try{
            assertStatusCode(response, 200);
            logPass("Status code esperado é igual a: " + extractStatusCode(response));
        }catch (AssertionError a){
            message = "Falha ao validar status code ";
            logError(message, a);
        }

        try{
            contractValidation(response, "schemas/deleteProduct.json");
            logPass("Contrato validado com sucesso!");
        }catch(Exception e){
            message = "Falha o validar o contrato! Verifique o response body retornado";
            logError(message, e);
        }

        try{
            assertEquals(getProduct.body().asString(), response.body().asString());
            logPass("Validando que o produto removido é o mesmo produto que existia na base");
        }catch (AssertionError a){
            logError("Falha ao validar que o produto removido é o mesmo produto que existia na base");
        }

    }
}
