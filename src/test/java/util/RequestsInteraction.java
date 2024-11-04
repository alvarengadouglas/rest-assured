package util;

import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public interface RequestsInteraction {

    default Response post(Map<String, Object> headers, String url) {
        return given()
                .headers(headers)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    default Response post(Object body, String url) {
        return given()
                .body(body)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    default Response post(Map<String, Object> headers, Object body, String url) {
        return given()
                .headers(headers)
                .body(body)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    default Response post(Cookie coockie, String url) {
        return given()
                .cookie(coockie)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    default Response post(Cookie coockies, String url, Object body) {
        return given()
                .cookie(coockies)
                .body(body)
                .headers("Content-Type", ContentType.JSON)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    default Response post(Map<String, Object> headers, Cookies coockies, String url, Object body) {
        return given()
                .cookies(coockies)
                .body(body)
                .headers(headers)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    default Response postEnconded(Map<String, Object> formParams, Cookies coockies, String url) {
        return given()
                .cookies(coockies)
                .contentType("application/x-www-form-urlencoded")
                .formParams(formParams)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }
    default Response postEnconded(Map<String, Object> formParams, Cookie coockie, String url) {
        return given()
                .cookie(coockie)
                .contentType("application/x-www-form-urlencoded")
                .formParams(formParams)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    default Response get(Map<String, Object> headers, String url) {
        return given()
                .headers(headers)
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    default Response get(Cookie coockie, String url) {
        return given()
                .cookie(coockie)
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    default Response get(Cookies coockies, String url) {
        return given()
                .cookies(coockies)
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    default Response put(Map<String, Object> headers, String url, Object body) {
        return given()
                    .headers(headers)
                    .body(body)
                .when()
                    .put(url)
                .then()
                    .extract()
                    .response();
    }

    default int extractStatusCode(Response r) {
        return r.then().extract().statusCode();
    }

    default String extracStringtPath(Response r, String pathToExtract){
        return r.then().extract().path(pathToExtract);
    }

    default int extractIntPath(Response r, String pathToExtract){
        return r.then().extract().path(pathToExtract);
    }

    default Object extractPath(Response r, String pathToExtract){
        return r.then().extract().path(pathToExtract);
    }

    default Map<String, Object> extractObjectPath(Response r, String pathToExtract){
        return r.then().extract().path(pathToExtract);
    }


    default void assertStatusCode(Response r, int statusCode) throws AssertionError {
        r.then().assertThat().statusCode(statusCode);
    }

    default void contractValidation(Response r, String jsonSchemaFilePath) throws AssertionError {
        r.then().assertThat().body(matchesJsonSchemaInClasspath(jsonSchemaFilePath));
    }

    default void checkValueInResponseBody(Response r, String responseBodyFieldToCompare, Object expecteResponseBodyField) throws AssertionError {
        r.then().body(responseBodyFieldToCompare, Matchers.is(expecteResponseBodyField));
    }

}
