package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    WebDriver driver;
    String url="https://crio-qkart-qa.vercel.app/login"; 
    
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
 public void logstatus(String Type,String Message, String Status){
        System.out.println(String.format("%s | %s | %s | %s", java.time.LocalDateTime.now(), Type, Message, Status));
    }

 
    public void LoginPage_Navigation(){
        if(!this.driver.getCurrentUrl().equals(this.url)){
            this.driver.get(this.url);
        }
    }
    public void verify_back_to_explore_button(){
        WebElement backtoexplore = driver.findElement(By.xpath("//button[text()='Back to explore']"));
        backtoexplore.click();
    }

   
}
