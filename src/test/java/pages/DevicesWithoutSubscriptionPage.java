package pages;

import framework.core.BasePage;
import framework.core.DriverContext;
import framework.core.WebElementHelper;
import framework.steps.ScenarioContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DevicesWithoutSubscriptionPage extends BasePage {

    @FindBy(how = How.XPATH, using ="//div[@data-qa='LST_ProductCard0']")
    public WebElement firstElement;


    public void pageIsVisible(){
        WebElementHelper.WaitForPageToLoad();
        WebElementHelper.ElementIsVisible(firstElement);

    }

    public void clickOnFirstProduct(){
        WebElementHelper.ElementIsVisible(firstElement);
        firstElement.click();
    }
}
