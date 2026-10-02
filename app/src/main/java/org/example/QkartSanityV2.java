package org.example;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class QkartSanityV2 {

    public static String lastGeneratedUserName;

    public static WebDriver createDriver(String browser) throws MalformedURLException {
        WebDriver driver = null ;
        try{
            switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
                case "FireFox":
                    WebDriverManager.firefoxdriver().setup();
                    driver =new FirefoxDriver();
                    break;
                    case "Edge":
                        WebDriverManager.edgedriver().setup();
                        driver = new EdgeDriver();
                         break;
            default:
                  throw new IllegalArgumentException(
                "Unsupported browser: " + browser
            );           
        }
        }
        catch(Exception e){
             e.printStackTrace();
        }
        return driver;
    }
        
    
        

    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    public static void takeScreenshot(WebDriver driver, String screenshotType, String description) {
        try {
            File theDir = new File("/screenshots");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            String timestamp = String.valueOf(java.time.LocalDateTime.now());
            String fileName = String.format("screenshot_%s_%s_%s.png", timestamp, screenshotType,
                    description);
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile = new File("screenshots/" + fileName);
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Testcase01: Verify the functionality of Login button on the Home page
     */
    public static Boolean TestCase01(WebDriver driver) throws InterruptedException {
        Boolean status;
        logStatus("Start TestCase", "Test Case 1: Verify User Registration", "DONE");

        // Visit the Registration page and register a new user
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);
        if (!status) {
            logStatus("TestCase 1", "Test Case Fail. User Registration Fail", "FAIL");
            logStatus("End TestCase", "Test Case 1: Verify user Registration : ",
                    status ? "PASS" : "FAIL");

            // Return False as the test case Fails
            return false;
        } else {
            logStatus("TestCase 1", "Test Case Pass. User Registration Pass", "PASS");
        }

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the login page and login with the previuosly registered user
        Login login = new Login(driver);
        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@123");
        logStatus("Test Step", "User Perform Login: ", status ? "PASS" : "FAIL");
        if (!status) {
            logStatus("End TestCase", "Test Case 1: Verify user Registration : ",
                    status ? "PASS" : "FAIL");
            return false;
        }

        // Visit the home page and log out the logged in user
        Home home = new Home(driver);
        status = home.PerformLogout();
        logStatus("End TestCase", "Test Case 1: Verify user Registration : ",
                status ? "PASS" : "FAIL");

        return status;
    }

    /*
     * Verify that an existing user is not allowed to re-register on QKart
     */
    public static Boolean TestCase02(WebDriver driver) throws InterruptedException {
        Boolean status;
        logStatus("Start Testcase",
                "Test Case 2: Verify User Registration with an existing username ", "DONE");

        // Visit the Registration page and register a new user
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);
        logStatus("Test Step", "User Registration : ", status ? "PASS" : "FAIL");
        if (!status) {
            logStatus("End TestCase", "Test Case 2: Verify user Registration : ",
                    status ? "PASS" : "FAIL");
            return false;

        }

        // Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Visit the Registration page and try to register using the previously
        // registered user's credentials
        registration.navigateToRegisterPage();
        status = registration.registerUser(lastGeneratedUserName, "abc@123", false);

        // If status is true, then registration succeeded, else registration has
        // failed. In this case registration failure means Success
        logStatus("End TestCase", "Test Case 2: Verify user Registration : ",
                status ? "FAIL" : "PASS");
        return !status;
    }

    /*
     * Verify the functinality of the search text box
     */
    public static Boolean TestCase03(WebDriver driver) throws InterruptedException {
        logStatus("TestCase 3", "Start test case : Verify functionality of search box ", "DONE");
        boolean status;

        // Visit the home page
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // Search for the "yonex" product
        status = homePage.searchForProduct("YONEX");
        if(!status){
            logStatus("Testcase 3","Testcase Failure.Unable to search for given product","FAIL");
            return  false;
        }
        //fuch the search result
        List<WebElement>searchresult=homePage.getSearchResults();
        // Verify the search results are available
        if(searchresult.size()==0){
            logStatus("TestCase 3","Test Case Failure.There were no results for the given search string","FAIL");
            return false;
        }

        for(WebElement WebElement :searchresult){
              // Create a SearchResult object from the parent element
            SearchResult searchResult = new SearchResult(WebElement);
             // Verify that all results contain the searched text
            String elementText= searchResult.getTitleofResult();
            if(!elementText.toUpperCase().contains("YONEX")){
                logStatus("TestCase 3", "Test Case Failure. Test Results contains un-expected values:"+elementText, "FAIL");
                return false;
            }
            logStatus("Step Success", "Successfully validated the search results ", "PASS");

        }
            status=homePage.searchForProduct("Gesundheit");
              if (status) {
            logStatus("TestCase 3", "Test Case Failure. Invalid keyword returned results", "FAIL");
            return false;
        }
        searchresult = homePage.getSearchResults();
        if(searchresult.size()==0){
            if(homePage.isNoResultFound()){
                  logStatus("Step Success",
                        "Successfully validated that no products found message is displayed",
                        "PASS");
            }
            logStatus("TestCase 3",
                    "Test Case PASS. Verified that no search results were found for the given text",
                    "PASS");
        }
        else{
            logStatus("TestCase 3",
                    "Test Case Fail. Expected: no results , actual: Results were available",
                    "FAIL");
            return false;
        }

        return true;
    }

    public static boolean Testcase04(WebDriver driver) throws InterruptedException{
        boolean Status;
        logStatus("Start Testcase ","Size Chart verification","Done");
        Home home= new Home(driver);
        // Vist home page
        home.navigateToHome();

        //search for the product
        Status=home.searchForProduct("Running Shoes");
        List<WebElement> searchResults = home.getSearchResults();
        List<String> expectedTableHeaders = Arrays.asList("Size", "UK/INDIA", "EU", "HEEL TO TOE");
        List<List<String>> expectedTableBody = Arrays.asList(Arrays.asList("6", "6", "40", "9.8"),
                Arrays.asList("7", "7", "41", "10.2"), Arrays.asList("8", "8", "42", "10.6"),
                Arrays.asList("9", "9", "43", "11"), Arrays.asList("10", "10", "44", "11.5"),
                Arrays.asList("11", "11", "45", "12.2"), Arrays.asList("12", "12", "46", "12.6"));
                for(WebElement webElement : searchResults){
                    SearchResult result = new SearchResult(webElement);

                }
//verify that the size chart link exists in the search result card
//contect should be match to the each search result card
for(WebElement webElement : searchResults){
    SearchResult result = new SearchResult(webElement);
if(result.VerifythattheSizeChartLinkExists()){
    logstatus("Step Success","Successfully Validate  pransent of Size Chart link ","PASS");
    Status=result.verifyExistenceofSizeDropdown();
    if(Status){
        logstatus("Step Success","Successfully Validate  pransent of Size Dropdown in the search result card","PASS");
    }
    else{
        logstatus("Step Failure","Size Dropdown not found in the search result card","FAIL");
        return false;
    }
    if(result.openSizechart()){
        logstatus("Step Success","Successfully Validate  Size Chart link is clickable and size chart is opened","PASS");
    }
    else{
        logstatus("Step Failure","Size Chart link is not clickable or size chart is not opened","FAIL");
        return false;
    }
}
return Status;
}
    } 

    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        int totalTests = 0;
        int passedTests = 0;
        Boolean status;
        WebDriver driver= createDriver("chrome");
        // Maximize and Implicit Wait for things to initailize
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        try {
            // // // Execute Test Case 1
            // totalTests += 1;
            // status = TestCase01(driver);
            // if (status) {
            // passedTests += 1;
            // }

            // System.out.println("");

            // // // Execute Test Case 2
            // totalTests += 1;
            // status = TestCase02(driver);
            // if (status) {
            // passedTests += 1;
            // }

            // System.out.println("");

            // // Execute Test Case 3
            // totalTests += 1;
            // status = TestCase03(driver);
            // if (status) {
            //     passedTests += 1;
            // }

            //       System.out.println("");

                    // Execute Test Case 4
            totalTests += 1;
            status = TestCase04(driver);
            if (status) {
                passedTests += 1;
            }

            System.out.println("");


        } catch (Exception e) {
            throw e;
        } finally {
            // quit Chrome Driver
            driver.quit();

            System.out.println(String.format("%s out of %s test cases Passed ",
                    Integer.toString(passedTests), Integer.toString(totalTests)));
        }

    }
}
