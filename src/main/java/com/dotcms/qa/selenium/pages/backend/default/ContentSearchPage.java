package com.dotcms.qa.selenium.pages.backend.default;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.default.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

public class ContentSearchPage extends BasePage implements IContentSearchPage {

	private WebElement structure_inode;
	private WebElement dijit_form_ComboButton_0_label;
	
	public ContentSearchPage(WebDriver driver) {
		super(driver);
	}

	public IContentAddOrEdit_ContentPage getAddContentPage(String structureName) throws Exception {
	    structure_inode.clear();
	    structure_inode.sendKeys(structureName);
	    Thread.sleep(250);
	    structure_inode.sendKeys(Keys.TAB);
	    dijit_form_ComboButton_0_label.click();
		return SeleniumPageManager.getPageManager().getPageObject(IContentAddOrEdit_ContentPage.class);
	}
}
