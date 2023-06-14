package org.hposadas.steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Base64;

import static io.restassured.RestAssured.given;

public class HttpMethodsRestAssuredTests {

    public void getRequest(){
        given()
                .baseUri("https://api.github.com")
        .when()
                .get("users/PosadasHumberto/repos")
                .statusCode();
    }

    public void postRequest(){
        given()
                .baseUri("")
        .when()
                .post("","");

    }

    public void putRequest(){
        given()
                .baseUri("")
        .when()
                .put("","");
    }

    public void deleteRequest(){
        given()
                .baseUri("https://api.blogEjemplo.com/posts/texto")
        .when()
                .delete();
    }

    public void soapRequest() {
        String baseUri = "http://example.com/soap-endpoint"; // Reemplaza con la URL del punto final SOAP correcta
        String soapBody = "<SOAP-ENV:Envelope>...</SOAP-ENV:Envelope>"; // Cuerpo de la solicitud SOAP

        given()
                .baseUri(baseUri)
                .header("Content-Type", "text/xml") // Establecer el encabezado "Content-Type" como "text/xml"
                .body(soapBody) // Establecer el cuerpo de la solicitud con el mensaje SOAP
        .when()
                .post()
        .then()
                .statusCode(200); // Validar el código de estado de la respuesta SOAP
    }

    public void restRequest() {
        String baseUri = "http://example.com/api/endpoint"; // Reemplaza con la URL del punto final REST correcta
        String requestBody = "{\"name\": \"John\", \"age\": 30}"; // Cuerpo de la solicitud REST en formato JSON

        given()
                .baseUri(baseUri)
                .header("Content-Type", "application/json") // Establecer el encabezado "Content-Type" como "application/json"
                .body(requestBody) // Establecer el cuerpo de la solicitud con el contenido JSON
        .when()
                .post()
        .then()
                .statusCode(201); // Validar el código de estado de la respuesta REST
    }

    public void basicAuthentication() {
        String username = "yourUsername";
        String password = "yourPassword";
        String baseUri = "http://example.com/api/endpoint";

        given()
                .baseUri(baseUri)
                .auth()
                .basic(username, password)
        .when()
                .get()
        .then()
                .statusCode(200);
    }

    public void formAuth(String username, String password){
        given()
                .auth()
                .form(username, password)
        .when()
                .get("SERVICE")
        .then()
                .assertThat().statusCode(200);

    }

    /**
     * Autenticacion con OAuth para obtener un token
     *
     * 1.-Obtener el codigo del servicio original para obtener el token.
     * 2.-Obtener el token, intercambiando el codigo que obtuvimos.
     * 3.-Acceder al recurso protegido con ayuda de nuestro token.
     */
    public static String clientId = "";
    public static String redirectUri = "";
    public static String scope = "";
    public static String username = "";
    public static String password = "";
    public static String grantType = "";
    public static String accessToken ="";

    private static String encode(String str1, String str2){
        return new String(Base64.getEncoder().encode((str1 + ":" + str2).getBytes()));
    }

    public static Response getCode(){
        String authorization = encode(username, password);
        return
                given()
                        .header("authorization", "Basic" + authorization)
                        .contentType(ContentType.URLENC)
                        .formParam("response_type", "code")
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_uri", redirectUri)
                        .queryParam("scope", scope)
                        .post("/oauth/authorize")
                .then()
                        .statusCode(200)
                        .extract()
                        .response();
    }

    public static String parseForOAuthCode(Response response) {
        return response.jsonPath().getString("code");
    }

    public static Response getToken(String authCode){
        String authorization = encode(username, password);

        return
                given()
                        .header("authorization", "Basic" + authorization)
                        .contentType(ContentType.URLENC)
                        .formParam("response_type", authCode)
                        .queryParam("redirect_uri", redirectUri)
                        .queryParam("grant_type", grantType)
                        .post("/oauth/token")
                .then()
                        .statusCode(200)
                        .extract()
                        .response();
    }

    public static String parseForToken(Response loginResponse) {
        return loginResponse.jsonPath().getString("access_token");
    }

    /**
     * Accediendo al recurso protegido usando el token obtenido
     */

    //pasando el token mediante oauth() y oauth2()
    public static void getFinalService(){
        given()
                .auth()
                .oauth2(accessToken)
        .when()
                .get("/service")
        .then()
                .statusCode(200);
    }

    //pasando el token mediante los headers
    public static void getFinalService2(){
        given()
                .header("Authorization", "Bearer"+accessToken)
        .when()
                .get("/service")
        .then()
                .statusCode(200);
    }

}
