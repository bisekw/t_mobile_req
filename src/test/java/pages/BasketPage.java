package pages;

import framework.core.BasePage;
import framework.core.WebElementHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.HashMap;
import java.util.Map;

public class BasketPage extends BasePage {
    @FindBy(how = How.XPATH, using ="//h1[text()='Twój koszyk']")
    public WebElement pageTitle;

    @FindBy(how = How.XPATH, using ="//span[@data-qa='BKT_TotalupFront']")
    public WebElement startPrice;

    @FindBy(how = How.XPATH, using ="//span[@data-qa='BKT_TotalMonthly']")
    public WebElement monthlyPrice;

    @FindBy(how = How.XPATH, using ="//img[@alt='T-Mobile - przejdź na stronę główną']")
    public WebElement mainPageButton;


    public void pageIsVisible(){
        WebElementHelper.WaitForPageToLoad();
        WebElementHelper.ElementIsVisible(pageTitle);
    }

    public void goToMainPage(){
        WebElementHelper.ElementIsVisible(mainPageButton);
        mainPageButton.click();
        WebElementHelper.WaitForPageToLoad();
    }

    public Map<String, String> getPrices() {
        WebElementHelper.ElementIsVisible(startPrice);
        WebElementHelper.ElementIsVisible(monthlyPrice);

        System.out.println("Basket startPrice "+ startPrice.getText());
        System.out.println("Basket monthlyPrice "+ monthlyPrice.getText());

        String sPrice = startPrice.getText();
        String mPrice = monthlyPrice.getText();

        Map<String, String> prices = new HashMap<>();
        prices.put("startPrice", sPrice);
        prices.put("monthlyPrice", mPrice);

        return prices;
    }
}
