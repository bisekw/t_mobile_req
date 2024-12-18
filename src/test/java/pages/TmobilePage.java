package pages;

import framework.core.BasePage;
import framework.core.WebElementHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class TmobilePage  extends BasePage {

    @FindBy(how = How.ID, using ="didomi-notice-agree-button")
    public WebElement cookeButton;
    @FindBy(how = How.XPATH, using ="//button[text()=\'Urządzenia\']")
    public WebElement devicesButton;

    @FindBy(how = How.XPATH, using ="//a[@data-ga-ea='nav-links - Urządzenia/Bez abonamentu/Smartwatche']")
    public WebElement smartwatchSubcategoryLink;

    @FindBy(how = How.XPATH, using ="//span[text()='Przejdź do strony koszyka']/following-sibling::div[text()='1']")
    public WebElement bascketIconOneProduct;


    public void basketWithOneProductVisible(){
        WebElementHelper.ElementIsVisible(bascketIconOneProduct);
    }
    public void pageIsVisible() {
        WebElementHelper.WaitForPageToLoad();
        WebElementHelper.WaitForElementVisible(cookeButton);
        WebElementHelper.ClickByAction(cookeButton);
        WebElementHelper.WaitForElementVisible(devicesButton);
    }

    public void clickOnDivices() {
        WebElementHelper.WaitForElementClickable(devicesButton);
        devicesButton.click();
        WebElementHelper.WaitForElementVisible(smartwatchSubcategoryLink);
    }

    public void goTosmartwatchSubcategory(){
        WebElementHelper.WaitForElementVisible(smartwatchSubcategoryLink);
        smartwatchSubcategoryLink.click();
    }
}
