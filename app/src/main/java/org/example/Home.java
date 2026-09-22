package org.example;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
   WebDriver driver;
    String url = "https://crio-qkart-qa.vercel.app/";

    public Home(WebDriver driver) {
        this.driver=driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogout() throws InterruptedException {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = driver.findElement(By.className("MuiButton-text"));
            logout_button.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.className("css-1urhf6j"), "Logout"));

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    /*
     * Returns Boolean if searching for the given product name occurs without any
     * errors
     */
    public Boolean searchForProduct(String product) {
        try {
            // Clear the contents of the search box and Enter the product name in the search
            // box
            WebElement searchBox = driver.findElement(By.xpath("//input[@name='search'][1]"));
            searchBox.clear();
            searchBox.sendKeys(product);
            // TODO: CRIO_TASK_MODULE_XPATH - M0 Fix broken Xpath
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
            wait.until(ExpectedConditions.or(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@Class='MuiPaper-root MuiPaper-elevation MuiPaper-rounded MuiPaper-elevation1 MuiCard-root card css-s18byi']//p[1]"),product)));
            ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@Class='MuiPaper-root MuiPaper-elevation MuiPaper-rounded MuiPaper-elevation1 MuiCard-root card css-s18byi']//p[1]"));
            return true;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    /*
     * Returns Array of Web Elements that are search results and return the same
     */
    public List<WebElement> getSearchResults() {
        List<WebElement> searchResults = new ArrayList<WebElement>() {
        };
        try {
            // Find all webelements corresponding to the card content section of each of
            // search results
            searchResults = driver.findElements(By.xpath("//div[@Class='MuiPaper-root MuiPaper-elevation MuiPaper-rounded MuiPaper-elevation1 MuiCard-root card css-s18byi']"));
            return searchResults;
        } catch (Exception e) {
            System.out.println("There were no search results: " + e.getMessage());
            return searchResults;

        }
    }

    /*
     * Returns Boolean based on if the "No products found" text is displayed
     */
    public Boolean isNoResultFound() {
        Boolean status = false;
        try {
            // Check the presence of "No products found" text in the web page. Assign status
            // = true if the element is *displayed* else set status = false
            status = driver.findElement(By.xpath("//div[@Class='MuiGrid-root MuiGrid-container MuiGrid-spacing-xs-2 css-1msksyp']//h4")).isDisplayed();
            return status;
        } catch (Exception e) {
            return status;
        }
    }
}
