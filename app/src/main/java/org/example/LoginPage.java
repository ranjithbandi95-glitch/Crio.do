package org.example;

import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;
    String url="https://crio-qkart-qa.vercel.app/";
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        
    }
    public void LoginPage_Navigation(){
        if(!this.driver.getCurrentUrl().equals(this.url)){
            this.driver.get(this.url);
        }
    }
}
