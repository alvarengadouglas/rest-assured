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

@DisplayName("Get Products Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetProductsTest extends BaseTest{

    int limitAmountProducts = 3;

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
    @DisplayName("Listar todos os produtos")
    void getAllProductsTest() throws Exception {
        createTest("Listar todos os produtos");
        String message;
        logInfoSeparator("DADOS ENVIADOS");


        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "*/*");

        Response response = get(headers, "/products");

        logInfoAlignCenter("Headers:");
        logInfoTable(headers);

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
            contractValidation(response, "schemas/getAllProducts.json");
            logPass("Contrato validado com sucesso!");
        }catch(Exception e){
            message = "Falha o validar o contrato! Verifique o response body retornado";
            logError(message, e);
        }

    }

    @Test
    @DisplayName("Listar produtos com limit")
    void listProductsWithLimitTest() throws Exception {
        createTest("Listar produtos com limit");
        String message;
        logInfoSeparator("DADOS ENVIADOS");


        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "*/*");

        Response response = get(headers, "/products?limit=5");

        logInfoAlignCenter("Headers:");
        logInfoTable(headers);

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
            contractValidation(response, "schemas/getAllProducts.json");
            logPass("Contrato validado com sucesso!");
        }catch(Exception e){
            message = "Falha o validar o contrato! Verifique o response body retornado";
            logError(message, e);
        }

        int amountProductsReturned = response.jsonPath().getList("$").size();

        try {
            assertEquals(limitAmountProducts, amountProductsReturned);
            logPass("Validar que a lista possui " + limitAmountProducts + " produtos.");
        }catch (AssertionError a){
            message = "Erro ao validar quantidade de produtos na lista, tamanho esperado [" + limitAmountProducts + "] - tamanho atual [" + amountProductsReturned + "]";
            logError(message, a);
        }
    }



}
