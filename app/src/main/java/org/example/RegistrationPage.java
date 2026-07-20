package org.example;

import java.sql.Timestamp;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {
    WebDriver driver;
    String url="https://crio-qkart-qa.vercel.app/register"; 
    String lastgeneratedusername;
    WebElement toster;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void logstatus(String Type,String Message, String Status){
        System.out.println(String.format("%s | %s | %s | %s", java.time.LocalDateTime.now(), Type, Message, Status));
    }

    public void RegistrationPage_Navigation(){
        if(!this.driver.getCurrentUrl().equals(this.url)){
            this.driver.get(this.url);
        }
    }
    public boolean user_registration(String username,  String password , boolean MakeuserDynamic ){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        WebElement user_txt_box = driver.findElement(By.id("username"));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String test_data_user;
        if(MakeuserDynamic){
            test_data_user="Test_User"+"_"+String.valueOf(timestamp.getTime());
        }
        else{
            test_data_user=username;
        }
        user_txt_box.sendKeys(test_data_user);
        WebElement password_txt_box = driver.findElement(By.id("password"));
        password_txt_box.sendKeys(password);
        WebElement confirm_password_txt_box = driver.findElement(By.id("confirmPassword"));
        confirm_password_txt_box.sendKeys(password);
        WebElement Submit_button = driver.findElement(By.xpath("//button[text()='Register Now']"));
        Submit_button.click();
        toster=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='notistack-snackbar']")));
        String Alert_Message=toster.getText();
        if(Alert_Message.equals("Registered Successfully"))
            {  logstatus("Testcase_002","Registered Successfully Toster message should be shown","PASS" );
        }
        else{
            logstatus("Testcase_002","User not able to Registeration","Fail");
        }
        try {
            wait.until(ExpectedConditions.urlToBe("https://crio-qkart-qa.vercel.app/login"));
        } catch (Exception e) {
            logstatus("ERROR", "Registration page did not redirect to login", e.getMessage());
            return false;
        }
        this.lastgeneratedusername = test_data_user;
        return true;
    }
    
    
    
}
