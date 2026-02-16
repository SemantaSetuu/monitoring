package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MapPage {
    WebDriver driver;
    WebDriverWait wait;
    By mapContainer = By.id("map");
    By popupContent = By.className("leaflet-popup-content");

    public MapPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10L));
    }

    public void verifyMapIsLive() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(this.mapContainer));
        System.out.println("HEALTH LOG: Map UI is visible and responsive.");
    }

    public void clickOnMap() {
        this.driver.findElement(this.mapContainer).click();
    }

    public String getCapturedCoordinates() {
        WebElement popup = (WebElement)this.wait.until(ExpectedConditions.visibilityOfElementLocated(this.popupContent));
        return popup.getText();
    }
}