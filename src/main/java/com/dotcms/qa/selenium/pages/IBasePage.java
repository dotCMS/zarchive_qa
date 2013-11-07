package com.dotcms.qa.selenium.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface IBasePage {
    public void sendText(By by, String text);
    public void sendText(String cssSelector, String text);
    public boolean isTextPresent(String text);
    public boolean isElementPresent(By by);
    public boolean isElementPresent(String cssSelector);
    public boolean isElementPresentAndDisplay(By by);
    public WebElement getWebElement(By by);
    public List<WebElement> getWebElements(By by);
    public void hoverOverElement(WebElement element);
    public void switchToFrame(String frameName);
    public void switchToDefaultContent();
}