package steps.gui;


import framework.core.Base;
import framework.config.Settings;
import framework.core.CurrentPageContext;
import framework.core.DriverContext;
import framework.core.WebElementHelper;
import framework.steps.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import pages.BasketPage;
import pages.DevicesWithoutSubscriptionPage;
import pages.ProductPage;
import pages.TmobilePage;

import java.util.Map;

@Slf4j
public class TmobileSteps extends Base {

    private ScenarioContext scenarioContext;

    public TmobileSteps(ScenarioContext scenarioContext){
        this.scenarioContext = scenarioContext;
    }

    @When("Przechodzę do strony T-mobile")
    public void przechodzęDoStrony() {
        WebElementHelper.GoToUrl(Settings.TESTED_APP_URL);
        WebElementHelper.MaximizeWindow();
        log.info(DriverContext.getRemoteWebDriver().toString());
        CurrentPageContext.setCurrentPage(GetInstance(TmobilePage.class));
    }

    @And("Weryfikuję czy strona wczytała się poprawnie")
    public void applicationIsOpened() {
        CurrentPageContext.getCurrentPage().As(TmobilePage.class).pageIsVisible();

    }

    @And("Wybieram Urządzenia z górnego menu")
    public void wybieramZGórnegoMenu() {
        CurrentPageContext.getCurrentPage().As(TmobilePage.class).clickOnDivices();
    }

    @And("Wybieram Bez abonamentu z sekcji Smartwatche i opaski")
    public void wybieramZSekcji() {
        CurrentPageContext.getCurrentPage().As(TmobilePage.class).goTosmartwatchSubcategory();
        CurrentPageContext.setCurrentPage(GetInstance(DevicesWithoutSubscriptionPage.class));
        CurrentPageContext.getCurrentPage().As(DevicesWithoutSubscriptionPage.class).pageIsVisible();

    }

    @And("Klikam w pierwszy produkt z listy")
    public void klikamWPierwszyProduktZListy() {
        CurrentPageContext.getCurrentPage().As(DevicesWithoutSubscriptionPage.class).clickOnFirstProduct();
        CurrentPageContext.setCurrentPage(GetInstance(ProductPage.class));
        CurrentPageContext.getCurrentPage().As(ProductPage.class).pageIsVisible();

    }

    @And("Dodaję produkt do koszyka")
    public void dodajęProduktDoKoszyka() {
        Map<String, String> prices = CurrentPageContext.getCurrentPage().As(ProductPage.class).getPrices();
        scenarioContext.setData("startPrice", prices.get("startPrice"));
        scenarioContext.setData("monthlyPrice", prices.get("monthlyPrice"));
        CurrentPageContext.getCurrentPage().As(ProductPage.class).addToBasket();
        CurrentPageContext.setCurrentPage(GetInstance(BasketPage.class));
        CurrentPageContext.getCurrentPage().As(BasketPage.class).pageIsVisible();
    }

    @Then("Wyświetlana jest strona koszyka z poprawną ceną")
    public void wyświetlanaJestStronaKoszykaZPoprawnąCeną() {
        String expectedStartPrice = (String) scenarioContext.getData("startPrice");
        String expectedMonthlyPrice = (String) scenarioContext.getData("monthlyPrice");
        if (expectedStartPrice != null) {
            expectedStartPrice = expectedStartPrice.replace(" zł", "");
        }

        if (expectedMonthlyPrice != null) {
            expectedMonthlyPrice = expectedMonthlyPrice.replace(" zł", "");
        }
        Map<String, String> pricesBasket=  CurrentPageContext.getCurrentPage().As(BasketPage.class).getPrices();
        Assert.assertEquals(pricesBasket.get("startPrice"), expectedStartPrice, "Start Price mismatch!");
        Assert.assertEquals(pricesBasket.get("monthlyPrice"), expectedMonthlyPrice, "Monthly Price mismatch!");


    }

    @When("Wracam na stronę główną")
    public void wracamNaStronęGłówną() {
        CurrentPageContext.getCurrentPage().As(BasketPage.class).goToMainPage();

    }

    @Then("Strona główna jest widoczna z ikoną koszyka wyświetlającą liczbę produktów")
    public void stronaGłównaJestWidocznaZIkonąKoszykaZLiczbąProduktów() {
        CurrentPageContext.setCurrentPage(GetInstance(TmobilePage.class));
        CurrentPageContext.getCurrentPage().As(TmobilePage.class).basketWithOneProductVisible();
    }

}
