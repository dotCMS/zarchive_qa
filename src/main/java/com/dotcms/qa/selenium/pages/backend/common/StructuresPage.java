package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.dotcms.qa.selenium.pages.backend.IPushPublishDialogPage;
import com.dotcms.qa.selenium.pages.backend.IStructureAddOrEdit_FieldsPage;
import com.dotcms.qa.selenium.pages.backend.IStructureAddOrEdit_PropertiesPage;
import com.dotcms.qa.selenium.pages.backend.IStructuresPage;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.WebKeys;

public class StructuresPage extends BasePage implements IStructuresPage {
	private static final Logger logger = Logger.getLogger(StructuresPage.class);

	private WebElement dijit_form_ComboButton_0_label;
	@FindBy(how = How.CLASS_NAME, using = "listingTable")
	private WebElement tableOfStructures;

	public StructuresPage(WebDriver driver) {
		super(driver);
	}

	public void deleteStructureAndContent(String structureName, boolean confirm) throws Exception {
		WebElement structureElement = null;
		WebElement textBox = getWebElement(By.cssSelector("input[type='text'][name='query']"));
		textBox.clear();
		textBox.sendKeys(structureName);
		getSearchButton().click();
		sleep(1);
		List<WebElement> results = getWebElement(By.cssSelector("table[class='listingTable']")).findElements(By.tagName("tr"));
		for(WebElement result : results){
			List<WebElement> cols =  result.findElements(By.tagName("td"));
			if(cols.size() > 2){
				try {
					if(cols.get(0).getText().trim().equals(structureName)) {
						structureElement = cols.get(0);
						break;
					}
				}
				catch(Exception e) {
					logger.error("Unexpected error attempting to iterate over structures - structureName =" + structureName, e);
					// Move on to next row and keep going
				}
			}
		}
		if(structureElement == null) {
			throw new Exception("Unable to locate structure by name - structureName = " + structureName);
		}

		moveToElement(structureElement);

		rightClickElement(structureElement);
		String menuString = getLocalizedString("Delete-Structure-and-Content");
		WebElement popup = this.getWebElement(By.className("dijitMenuPopup"));
		List<WebElement> menuRows = popup.findElements(By.tagName("tr"));
		boolean done = false;
		for(WebElement menuRow : menuRows) {
			List<WebElement> menuCols = menuRow.findElements(By.tagName("td"));
			for(WebElement menuCol : menuCols) {
				if(menuCol.getText().trim().equals(menuString)) {
					menuCol.click();
					Alert alert = this.switchToAlert();
					if(confirm) {
						alert.accept();
					}
					else {
						alert.dismiss();
					}
					done = true;
					break;
				}
			}
			if(done)
				break;
		}
	}

	/**
	 * return the structure Row if exist
	 * @param structureName Name of the structure
	 * @return WebElement
	 * @throws Exception
	 */
	private WebElement findStructureRow(String structureName) throws Exception{
		WebElement structureRow = null;
		WebElement textBox = getWebElement(By.cssSelector("input[type='text'][name='query']"));
		textBox.clear();
		textBox.sendKeys(structureName);
		getSearchButton().click();
		sleep(1);
		List<WebElement> results = getWebElement(By.cssSelector("table[class='listingTable']")).findElements(By.tagName("tr"));
		for(WebElement result : results){
			List<WebElement> tds =  result.findElements(By.tagName("td"));
			if(tds.size() > 2){
				if(tds.get(0).getText().equals(structureName)){
					structureRow = result;
					break;
				}
			}
		}
		return structureRow; 
	}
	/**
	 * Validate if the structure exist
	 * @param structureName Name of the structure
	 * @return true if the structure exist, false if not
	 */
	public boolean doesStructureExist(String structureName) {
		boolean retValue = false;
		//search in the structures filter
		try{
			WebElement result = findStructureRow(structureName);
			if(result != null){
				List<WebElement> tds =  result.findElements(By.tagName("td"));
				if(tds.size() > 2){
					if(tds.get(0).getText().equals(structureName)){
						retValue = true;
					}
				}
			}	
		}catch(Exception e){
			//structure not found
		}
		return retValue;
	}

	public IStructureAddOrEdit_PropertiesPage getAddNewStructurePage() throws Exception {
		dijit_form_ComboButton_0_label.click();	// Add new structure button
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_PropertiesPage.class);
	}

	/**
	 * Get the field tab page
	 * @return IStructureAddOrEdit_FieldsPage
	 * @throws Exception
	 */
	public IStructureAddOrEdit_FieldsPage getFieldsPage() throws Exception {
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_FieldsPage.class);
	}
	/**
	 * Validate if the structure exist
	 * @param structureName Name of the structure
	 * @return true if the structure exist, false if not
	 */
	public IStructureAddOrEdit_PropertiesPage getStructurePage(String structureName) throws Exception{
		WebElement result = findStructureRow(structureName);
		List<WebElement> tds = result.findElements(By.tagName("td"));
		for(WebElement td : tds){
			if(td.getText().equals(structureName)){
				td.click();
				break;
			}
		}
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructureAddOrEdit_PropertiesPage.class);
	}

	/**
	 * get the search button because the dynamic id changes
	 * @return WebElement
	 */
	public WebElement getSearchButton(){
		WebElement button=null;
		List<WebElement> buttons =getWebElement(By.id("fm")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement span : buttons){
			if(span.getText().trim().equals(getLocalizedString("Search"))){
				button=span;
				break;
			}
		}
		return button;
	}

	/**
	 * Push the selected structure
	 * @param structureName Name of the structure
	 * @throws Exception
	 */
	public void pushStructure(String structureName) throws Exception{
		WebElement result = findStructureRow(structureName);
		List<WebElement> columns = result.findElements(By.tagName("td"));
		selectRightClickPopupMenuOption(columns.get(0),getLocalizedString("Remote-Publish"));
		sleep(2);
		IPushPublishDialogPage pushingDialog = SeleniumPageManager.getBackEndPageManager().getPageObject(IPushPublishDialogPage.class);
		pushingDialog.push(WebKeys.PUSH_TO_ADD, null, null, null, null, false);
	}
}
