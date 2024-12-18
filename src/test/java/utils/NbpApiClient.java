package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
public class NbpApiClient {

    private static final String BASE_URL = "http://api.nbp.pl/api/exchangerates/tables/A";

    public static Response getExchangeRatesTableA() {
        return RestAssured
                .given()
                .queryParam("format", "json")
                .when()
                .get(BASE_URL)
                .then()
                .extract().response();
    }
}
