package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.bytebuddy.asm.Advice.Return;

public class Homepage {
    WebDriver driver;
    String url="https://crio-qkart-qa.vercel.app/";
    public Homepage(WebDriver driver) {
        this.driver = driver;
        
    }
    public static void logstatus(String Type,String Message,String Status){
        System.out.println(String.format("%s | %s | %s | %s", java.time.LocalDateTime.now(), Type, Message, Status));
    }

    public void LoginPage_Navigation(){
        if(!this.driver.getCurrentUrl().equals(this.url)){
            this.driver.get(this.url);
        }
    }

    public  boolean Qcark_Logo(){
    WebElement logo = driver.findElement(By.xpath("//img[@src='logo_light.svg']"));
     return logo.isDisplayed();
    }

    public boolean search_bar(){
        WebElement searchbar = driver.findElement(By.xpath("//input[@name='search'][1]"));
        return searchbar.isDisplayed();
    } 
     public  boolean Verify_LoginButton(){
        boolean status;
        WebElement Loginbutton =driver.findElement(By.xpath("//button[text()='Login']"));
         status = Loginbutton.isDisplayed();
         if(!status){
            logstatus("Testcase_001","Verify Login button is displayed on Home page","Failed");
         }
         else{
            logstatus("Testcase_001","Verify Login button is displayed on Home page","Passed");
         }
         Loginbutton.click();
         status=driver.getCurrentUrl().equals("https://crio-qkart-qa.vercel.app/login");
         if(!status){
            logstatus("Testcase_001","Verify User able to Click on Login button and Navigate to Login page","Failed");
         }
         else{
            logstatus("Testcase_001","Verify User able to Click on Login button and Navigate to Login page","Passed");
         }
         return status;
     }

     public boolean Verify_RegisterButton(){
        boolean status;
        WebElement Registerbutton =driver.findElement(By.xpath("//button[text()='Register']"));
         status = Registerbutton.isDisplayed();
         if(!status){
            logstatus("Testcase_001","Verify Register button is displayed on Home page","Failed");
         }
         else{
            logstatus("Testcase_001","Verify Register button is displayed on Home page","Passed");
         }
         Registerbutton.click();
         status=driver.getCurrentUrl().equals("https://crio-qkart-qa.vercel.app/register");
         if(!status){
            logstatus("Testcase_001","Verify User able to Click on Register button and Navigate to Register page","Failed");
         }
         else{
            logstatus("Testcase_001","Verify User able to Click on Register button and Navigate to Register page","Passed");
         }
         return status;
     }

    
}   
