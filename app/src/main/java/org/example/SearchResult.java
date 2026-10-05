package org.example;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResult {
    WebElement parentElement;

    public SearchResult(WebElement SearchResultElement) {
        this.parentElement = SearchResultElement;
    }

    /*
     * Return title of the parentElement denoting the card content section of a
     * search result
     */
    public String getTitleofResult() {
        String titleOfSearchResult = "";
        // Find the element containing the title (product name) of the search result and
        // assign the extract title text to titleOfSearchResult
        WebElement element = parentElement.findElement(By.className("css-yg30e6"));
        titleOfSearchResult = element.getText();
        return titleOfSearchResult;
    }

    public boolean VerifythattheSizeChartLinkExists(){
        boolean Status = false;
        try{
            //Verify that the size chart link exists in the search result card
            //if element name should be "Size Chart" then return true else return false
            WebElement element=parentElement.findElement(By.tagName("button"));
            Status=element.getText().equals("Size Chart");

        }
        catch(Exception e){
            System.out.println("Size chart link not found");
           
        }
        return Status;

    }

    public boolean verifyExistenceofSizeDropdown(){
        boolean Status = false;
        try{
            WebElement element=parentElement.findElement(By.className("css-1hwfws3"));
            Status = true;
        }
        catch(Exception e){
            System.out.println("Size dropdown not found");
            return false;
        }
        return Status;
    }

    public boolean openSizechart(WebDriver driver){
        boolean Status = false;
        try{
            WebElement element=parentElement.findElement(By.tagName("button"));
            element.click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement sizeChartTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Shoe's Size Chart']")));
            Status = sizeChartTitle.isDisplayed();

        }
        catch(Exception e){
            System.out.println("Size chart link not found");
        }
        return Status;
    }
}
