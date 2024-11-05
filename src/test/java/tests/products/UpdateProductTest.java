package tests.products;


import config.Configuracoes;
import enums.PayloadPaths;
import factory.PojoFactory;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.*;
import pojo.UpdateProducts;
import util.BaseTest;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Update Products Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UpdateProductTest extends  BaseTest{
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
    @DisplayName("Atualizar Produto")
    void updateProductTest() throws Exception {
        createTest("Atualizar Produto");


        String message;
        Map<String, Object> expectedValues = new HashMap<>();
        expectedValues.put("title", "test product");
        expectedValues.put("price", 13.5);
        expectedValues.put("description", "lorem ipsum set");
        expectedValues.put("image", "https://i.pravatar.cc");
        expectedValues.put("category", "electronic");

        logInfoSeparator("DADOS ENVIADOS");

        PojoFactory<UpdateProducts> factory = new PojoFactory<>();
        UpdateProducts updateProducts = factory.createPojoFactory(UpdateProducts.class, PayloadPaths.UPDATE_PRODUCT);

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Response response = put(headers, "/products/20", updateProducts);

        logInfoAlignCenter("Headers:");
        logInfoTable(headers);

        logInfoAlignCenter("Payload - body:");
        logInfoJson(updateProducts.toString());

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
            contractValidation(response, "schemas/updateProduct.json");
            logPass("Contrato validado com sucesso!");
        }catch(Exception e){
            message = "Falha o validar o contrato! Verifique o response body retornado";
            logError(message, e);
        }

        try{
            assertTrue(logAssertJsonValues(response.asString(), expectedValues));
        }catch (AssertionError a){
            logError("Falha ao validar os valores retornados nos campos do response body");
        }
    }
}
