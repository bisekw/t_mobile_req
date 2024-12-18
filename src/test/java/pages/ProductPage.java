package pages;

import framework.core.BasePage;
import framework.core.WebElementHelper;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ProductPage extends BasePage {

    @FindBy(how = How.XPATH, using ="(//button[@data-qa='PRD_AddToBasket'])[2]")
    public WebElement addToBasketButton;

    @FindBy(how = How.XPATH, using ="(//div[@data-qa='PRD_TotalUpfront']/descendant::div[contains(text(), ' zł')])[2]")
    public WebElement startPrice;

    @FindBy(how = How.XPATH, using ="(//div[normalize-space(text())='Do zapłaty miesięcznie']/ancestor::div[1]/following-sibling::div//div[contains(normalize-space(text()), ' zł')])[2]")
    public WebElement monthlyPrice;


    public void pageIsVisible(){
        WebElementHelper.WaitForPageToLoad();
        WebElementHelper.ElementIsVisible(addToBasketButton);
    }

    public void addToBasket(){
        WebElementHelper.ElementIsVisible(addToBasketButton);
        addToBasketButton.click();
    }

    public Map<String, String> getPrices() {
        WebElementHelper.ElementIsVisible(startPrice);
        WebElementHelper.ElementIsVisible(monthlyPrice);
        System.out.println("Product startPrice"+ startPrice.getText());
        System.out.println("Product monthlyPrice"+ monthlyPrice.getText());
        String sPrice = startPrice.getText();
        String mPrice = monthlyPrice.getText();

        Map<String, String> prices = new HashMap<>();
        prices.put("startPrice", sPrice);
        prices.put("monthlyPrice", mPrice);

        return prices;
    }
}
