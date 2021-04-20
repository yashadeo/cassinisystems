package com.cassini.test.page_objects;

import com.cassini.test.framework.CommonMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends CommonMethods {

    public LandingPage() {
        PageFactory.initElements(webDriver, this);
    }

    //Elements needs to be identified using CSSSelectors

    @FindBy(css = "button[name='agree']")
    private WebElement buttonAcceptAll;

    @FindBy(css = "#header-signin-link")
    private WebElement buttonSignIn;

    @FindBy(css = "#login-username")
    private WebElement userName;

    @FindBy(css = "#login-passwd")
    private WebElement password;

    @FindBy(css = "#login-signin")
    private WebElement buttonNext;

    // direct approach to select the link Yash 17-04
    @FindBy(css = "a[href*='https://uk.finance.yahoo.com/']")
    private WebElement linkFinance;

    @FindBy(css = "#username-error")
    private WebElement errorMessage;

    @FindBy(css = "div[title='Market Data']")
    private WebElement linkMarketData;

    @FindBy(css = "a[title='Calendar']")
    private WebElement calendar;

    public void login(String userNames, String passwords){
        waitTillElementVisible(buttonAcceptAll);
        waitTillElementVisible(buttonAcceptAll).click();
        waitTillElementVisible(buttonSignIn).click();
        waitTillElementVisible(userName).sendKeys("FirstTestLogin_12");
        waitTillElementVisible(buttonNext).click();
        waitTillElementVisible(password).sendKeys("TestAbc_12!");
        waitTillElementVisible(buttonNext).click();
    }

    public void navigateToCalendarPage() throws InterruptedException {
        //moveToElement(linkMarketData);
        clickUsingJS(calendar);
    }


    public void clickFinance(){
        waitTillElementVisible(buttonAcceptAll).click();
        waitTillElementVisible(linkFinance).click();
    }

    public void enterIncorrectLogin(){
        //waitTillElementVisible(buttonAcceptAll).click();
        waitTillElementVisible(buttonSignIn).click();
        waitTillElementVisible(userName).sendKeys("TestAbc_12!");
        waitTillElementVisible(buttonNext).click();
    }



    public String signInErrorMessage() throws InterruptedException {
        return waitTillElementVisible(errorMessage).getText();
    }
}
