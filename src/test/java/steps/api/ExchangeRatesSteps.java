package steps.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import utils.NbpApiClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ExchangeRatesSteps {

    private static Response response;
    private static List<Map<String, Object>> rates;

    @Given("Pobierz kursy walut z tabeli A")
    public void pobierz_kursy_walut_z_tabeli_A() {
        response = NbpApiClient.getExchangeRatesTableA();
        Assert.assertNotNull("Response is null", String.valueOf(response));
        log.info("HTTP Status Code: " + response.statusCode());

        Assert.assertEquals(response.statusCode(), 200, "Status code is not 200");

        List<Object> body = response.jsonPath().getList("$");
        Map<String, Object> first = (Map<String, Object>) body.get(0);
        rates = (List<Map<String, Object>>) first.get("rates");
    }

    @When("Wyświetl kurs dla waluty o kodzie: {}")
    public void wyswietl_kurs_dla_waluty_o_kodzie(String code) {
        Map<String, Object> currency = rates.stream()
                .filter(r -> r.get("code").equals(code))
                .findFirst()
                .orElse(null);
        if (currency != null) {
            Number midNumber = (Number) currency.get("mid");
            double mid = midNumber.doubleValue();
            log.info("Kurs dla waluty o kodzie " + code + ": " + mid);
        } else {
            log.info("Nie znaleziono waluty o kodzie " + code);
        }
    }

    @When("Wyświetl kurs dla waluty o nazwie: {}")
    public void wyswietl_kurs_dla_waluty_o_nazwie(String currencyName) {
        Map<String, Object> currency = rates.stream()
                .filter(r -> ((String) r.get("currency")).equalsIgnoreCase(currencyName))
                .findFirst()
                .orElse(null);
        if (currency != null) {
            Number midNumber = (Number) currency.get("mid");
            double mid = midNumber.doubleValue();
            log.info("Kurs dla waluty o nazwie " + currencyName + ": " + mid);
        } else {
            log.info("Nie znaleziono waluty o nazwie " + currencyName);
        }
    }

    @When("Wyświetl waluty o kursie powyżej: {double}")
    public void wyswietl_waluty_o_kursie_powyzej(double value) {
        List<Map<String, Object>> filtered = rates.stream()
                .filter(r -> ((Number)r.get("mid")).doubleValue() > value)
                .collect(Collectors.toList());
        log.info("Waluty o kursie powyżej " + value + ":");
        for (Map<String, Object> c : filtered) {
            Number midNumber = (Number) c.get("mid");
            double mid = midNumber.doubleValue();
            log.info(c.get("currency") + " (" + c.get("code") + "): " + mid);
        }
    }

    @When("Wyświetl waluty o kursie poniżej: {double}")
    public void wyswietl_waluty_o_kursie_ponizej(double value) {
        List<Map<String, Object>> filtered = rates.stream()
                .filter(r -> ((Number)r.get("mid")).doubleValue() < value)
                .collect(Collectors.toList());
        log.info("Waluty o kursie poniżej " + value + ":");
        for (Map<String, Object> c : filtered) {
            Number midNumber = (Number) c.get("mid");
            double mid = midNumber.doubleValue();
            log.info(c.get("currency") + " (" + c.get("code") + "): " + mid);
        }
    }
}
