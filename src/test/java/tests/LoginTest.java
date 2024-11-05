package tests;

import config.Configuracoes;
import enums.PayloadPaths;
import factory.PojoFactory;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.*;
import pojo.Login;
import util.BaseTest;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Login Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest extends BaseTest {

    String tokenMask = "*****mask*for*sensitive*content*****";
    String script = "<script>alert('XSS')</script>";

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
    @DisplayName("Login com sucesso")
    void loginSuccessTest() throws Exception {
        createTest("Login com sucesso");
        String message;
        logInfoSeparator("DADOS ENVIADOS");

        PojoFactory<Login> factory = new PojoFactory<>();
        Login loginData = factory.createPojoFactory(Login.class, PayloadPaths.LOGIN);

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Response response = post(headers, loginData, "/auth/login");

        logInfoAlignCenter("Headers:");
        logInfoTable(headers);

        logInfoAlignCenter("Payload - body:");
        loginData.setPassword(tokenMask);
        logInfoJson(loginData.toString());

        logInfoSeparator("DADOS RETORNADOS");
        logInfoAlignCenter("Headers:");
        logInfoResponseHeaders(response.headers().asList());

        logInfoAlignCenter("Response body:");
        logInfoJson(response.asPrettyString().replaceAll("(\"token\"\\s*:\\s*\")[^\"]+\"", "$1" + tokenMask + "\""));
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
           contractValidation(response, "schemas/loginSuccess.json");
           logPass("Contrato validado com sucesso!");
        }catch(Exception e){
           message = "Falha o validar o contrato! Verifique o response body retornado";
           logError(message, e);
        }
    }

    @Test
    @DisplayName("Login com username errado")
    void loginWithWrongUsername() throws Exception {
        createTest("Login com username errado");
        String message;
        logInfoSeparator("DADOS ENVIADOS");

        PojoFactory<Login> factory = new PojoFactory<>();
        Login loginData = factory.createPojoFactory(Login.class, PayloadPaths.LOGIN);
        loginData.setUsername(loginData.getUsername().concat("aaa"));

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Response response = post(headers, loginData, "/auth/login");

        logInfoAlignCenter("Headers:");
        logInfoTable(headers);

        logInfoAlignCenter("Payload - body:");
        loginData.setPassword(tokenMask);
        logInfoJson(loginData.toString());

        logInfoSeparator("DADOS RETORNADOS");
        logInfoAlignCenter("Headers:");
        logInfoResponseHeaders(response.headers().asList());

        logInfoAlignCenter("Response body:");
        logInfo(response.asString());
        logInfo("Status code " + extractStatusCode(response));

        logInfoSeparator("VALIDAÇÕES");

        try{
            assertStatusCode(response, 401);
            logPass("Status code esperado é igual a: " + extractStatusCode(response));
        }catch (AssertionError a){
            message = "Falha ao validar status code ";
            logError(message, a);
        }

        try{
            assertEquals("username or password is incorrect", response.asString());
            logPass("Mensagem de usuário ou senha incorretos");
        }catch(Exception e){
            message = "Falha ao validar a mensagem de usuário ou senha incorretos";
            logError(message, e);
        }

    }

    @Test
    @DisplayName("Login com password errado")
    void loginWithWrongPassword() throws Exception {
        createTest("Login com password errado");
        String message;
        logInfoSeparator("DADOS ENVIADOS");

        PojoFactory<Login> factory = new PojoFactory<>();
        Login loginData = factory.createPojoFactory(Login.class, PayloadPaths.LOGIN);
        loginData.setPassword(loginData.getPassword().concat("aaa"));

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Response response = post(headers, loginData, "/auth/login");

        logInfoAlignCenter("Headers:");
        logInfoTable(headers);

        logInfoAlignCenter("Payload - body:");
        loginData.setPassword(tokenMask);
        logInfoJson(loginData.toString());

        logInfoSeparator("DADOS RETORNADOS");
        logInfoAlignCenter("Headers:");
        logInfoResponseHeaders(response.headers().asList());

        logInfoAlignCenter("Response body:");
        logInfo(response.asString());
        logInfo("Status code " + extractStatusCode(response));

        logInfoSeparator("VALIDAÇÕES");

        try{
            assertStatusCode(response, 401);
            logPass("Status code esperado é igual a: " + extractStatusCode(response));
        }catch (AssertionError a){
            message = "Falha ao validar status code ";
            logError(message, a);
        }

        try{
            assertEquals("username or password is incorrect", response.asString());
            logPass("Mensagem de usuário ou senha incorretos");
        }catch(Exception e){
            message = "Falha ao validar a mensagem de usuário ou senha incorretos";
            logError(message, e);
        }

    }

    @Test
    @DisplayName("Login com script no username errado")
    void loginWithScriptInUsername() throws Exception {
        createTest("Login com username errado");
        String message;
        logInfoSeparator("DADOS ENVIADOS");

        PojoFactory<Login> factory = new PojoFactory<>();
        Login loginData = factory.createPojoFactory(Login.class, PayloadPaths.LOGIN);
        loginData.setUsername(script);

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Response response = post(headers, loginData, "/auth/login");

        logInfoAlignCenter("Headers:");
        logInfoTable(headers);

        logInfoAlignCenter("Payload - body:");
        loginData.setPassword(tokenMask);
        logInfoJson(loginData.toString());

        logInfoSeparator("DADOS RETORNADOS");
        logInfoAlignCenter("Headers:");
        logInfoResponseHeaders(response.headers().asList());

        logInfoAlignCenter("Response body:");
        logInfo(response.asString());
        logInfo("Status code " + extractStatusCode(response));

        logInfoSeparator("VALIDAÇÕES");

        try{
            assertStatusCode(response, 401);
            logPass("Status code esperado é igual a: " + extractStatusCode(response));
        }catch (AssertionError a){
            message = "Falha ao validar status code ";
            logError(message, a);
        }

        try{
            assertEquals("username or password is incorrect", response.asString());
            logPass("Mensagem de usuário ou senha incorretos");
        }catch(Exception e){
            message = "Falha ao validar a mensagem de usuário ou senha incorretos";
            logError(message, e);
        }

    }

    @Test
    @DisplayName("Login com script no password errado")
    void loginWithScriptInPassword() throws Exception {
        createTest("Login com script no password errado");
        String message;
        logInfoSeparator("DADOS ENVIADOS");

        PojoFactory<Login> factory = new PojoFactory<>();
        Login loginData = factory.createPojoFactory(Login.class, PayloadPaths.LOGIN);
        loginData.setPassword(script);

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Response response = post(headers, loginData, "/auth/login");

        logInfoAlignCenter("Headers:");
        logInfoTable(headers);

        logInfoAlignCenter("Payload - body:");
        loginData.setPassword(tokenMask);
        logInfoJson(loginData.toString());

        logInfoSeparator("DADOS RETORNADOS");
        logInfoAlignCenter("Headers:");
        logInfoResponseHeaders(response.headers().asList());

        logInfoAlignCenter("Response body:");
        logInfo(response.asString());
        logInfo("Status code " + extractStatusCode(response));

        logInfoSeparator("VALIDAÇÕES");

        try{
            assertStatusCode(response, 401);
            logPass("Status code esperado é igual a: " + extractStatusCode(response));
        }catch (AssertionError a){
            message = "Falha ao validar status code ";
            logError(message, a);
        }

        try{
            assertEquals("username or password is incorrect", response.asString());
            logPass("Mensagem de usuário ou senha incorretos");
        }catch(Exception e){
            message = "Falha ao validar a mensagem de usuário ou senha incorretos";
            logError(message, e);
        }

    }

    @Test
    @DisplayName("Login com sql injection no username")
    void loginWithSqlInjectionInUsername() throws Exception {
        createTest("Login com sql injection no username");
        String message;
        logInfoSeparator("DADOS ENVIADOS");

        PojoFactory<Login> factory = new PojoFactory<>();
        Login loginData = factory.createPojoFactory(Login.class, PayloadPaths.LOGIN);
        loginData.setUsername(loginData.getUsername().concat("' OR '1'='1'"));

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Response response = post(headers, loginData, "/auth/login");

        logInfoAlignCenter("Headers:");
        logInfoTable(headers);

        logInfoAlignCenter("Payload - body:");
        loginData.setPassword(tokenMask);
        logInfoJson(loginData.toString());

        logInfoSeparator("DADOS RETORNADOS");
        logInfoAlignCenter("Headers:");
        logInfoResponseHeaders(response.headers().asList());

        logInfoAlignCenter("Response body:");
        logInfo(response.asString());
        logInfo("Status code " + extractStatusCode(response));

        logInfoSeparator("VALIDAÇÕES");

        try{
            assertStatusCode(response, 401);
            logPass("Status code esperado é igual a: " + extractStatusCode(response));
        }catch (AssertionError a){
            message = "Falha ao validar status code ";
            logError(message, a);
        }

        try{
            assertEquals("username or password is incorrect", response.asString());
            logPass("Mensagem de usuário ou senha incorretos");
        }catch(Exception e){
            message = "Falha ao validar a mensagem de usuário ou senha incorretos";
            logError(message, e);
        }

    }

    @Test
    @DisplayName("Login com sql injection no password")
    void loginWithSqlInjectionInPassword() throws Exception {
        createTest("Login com sql injection no password");
        String message;
        logInfoSeparator("DADOS ENVIADOS");

        PojoFactory<Login> factory = new PojoFactory<>();
        Login loginData = factory.createPojoFactory(Login.class, PayloadPaths.LOGIN);
        loginData.setPassword(loginData.getPassword().concat("' OR '1'='1'"));

        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Response response = post(headers, loginData, "/auth/login");

        logInfoAlignCenter("Headers:");
        logInfoTable(headers);

        logInfoAlignCenter("Payload - body:");
        loginData.setPassword(tokenMask);
        logInfoJson(loginData.toString());

        logInfoSeparator("DADOS RETORNADOS");
        logInfoAlignCenter("Headers:");
        logInfoResponseHeaders(response.headers().asList());

        logInfoAlignCenter("Response body:");
        logInfo(response.asString());
        logInfo("Status code " + extractStatusCode(response));

        logInfoSeparator("VALIDAÇÕES");

        try{
            assertStatusCode(response, 401);
            logPass("Status code esperado é igual a: " + extractStatusCode(response));
        }catch (AssertionError a){
            message = "Falha ao validar status code ";
            logError(message, a);
        }

        try{
            assertEquals("username or password is incorrect", response.asString());
            logPass("Mensagem de usuário ou senha incorretos");
        }catch(Exception e){
            message = "Falha ao validar a mensagem de usuário ou senha incorretos";
            logError(message, e);
        }

    }

    @Test
    @DisplayName("Login sem enviar content-type")
    void loginWithoutContentType() throws Exception {
        createTest("Login sem enviar content-typed");
        String message;
        logInfoSeparator("DADOS ENVIADOS");

        PojoFactory<Login> factory = new PojoFactory<>();
        Login loginData = factory.createPojoFactory(Login.class, PayloadPaths.LOGIN);

        Map<String, Object> headers = new HashMap<>();

        Response response = post(headers, loginData, "/auth/login");

        logInfoAlignCenter("Headers:");
        logInfoTable(headers);

        logInfoAlignCenter("Payload - body:");
        loginData.setPassword(tokenMask);
        logInfoJson(loginData.toString());

        logInfoSeparator("DADOS RETORNADOS");
        logInfoAlignCenter("Headers:");
        logInfoResponseHeaders(response.headers().asList());

        logInfoAlignCenter("Response body:");
        logInfo(response.asString());
        logInfo("Status code " + extractStatusCode(response));

        logInfoSeparator("VALIDAÇÕES");

        try{
            assertStatusCode(response, 400);
            logPass("Status code esperado é igual a: " + extractStatusCode(response));
        }catch (AssertionError a){
            message = "Falha ao validar status code ";
            logError(message, a);
        }

        try{
            assertEquals("username and password are not provided in JSON format", response.asString());
            logPass("Mensagem de usuário ou senha fora do formato JSON");
        }catch(Exception e){
            message = "Falha ao validar a mensagem de usuário ou senha fora do formato JSON";
            logError(message, e);
        }

    }

}
