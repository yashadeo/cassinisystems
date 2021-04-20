package com.cassini.test.page_objects;

import com.cassini.test.framework.CommonMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class FinancePage extends CommonMethods {

    public FinancePage() {
        PageFactory.initElements(webDriver, this);
    }

    //Elements needs to be identified using CSSSelectors
    @FindBy(css = "div[title='Market Data']")
    private WebElement linkMarketData;

    @FindBy(css = "a[title='Calendar']")
    private WebElement calendar;

    @FindBy(xpath = "//span[contains(text(),'Next')]")
    private WebElement iconNext;

    @FindBy(xpath = "//*[@id='fin-cal-events']/div[2]/ul/li[4]//a")
    private List<WebElement> values;

    public void navigateToCalendarPage() throws InterruptedException {
        //moveToElement(linkMarketData);
        clickUsingJS(calendar);
    }

    public List<String> captureValues(){
        List<String>list = new ArrayList<>();
        waitTillElementVisible(iconNext).click();
        for(WebElement element:values){
            System.out.println(element.getText());
            list.add(element.getText());
        }
        return list;
}



}