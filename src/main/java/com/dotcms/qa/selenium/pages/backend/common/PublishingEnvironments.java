package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.IPublishingEnvironments;
import com.dotcms.qa.selenium.pages.common.BasePage;

/**
 * This class implements the methods defined in the IPublishingEnvironments interface
 * to do the pushpublishing TestRail validations
 * @author Oswaldo Gallango
 * @since 03/06/2015
 * @version 1.0
 * 
 */
public class PublishingEnvironments extends BasePage implements IPublishingEnvironments {

	public PublishingEnvironments(WebDriver driver) {
		super(driver);
	}


	/**
	 * Generates a new Send to environment
	 * @param environmentName String name of the new environment
	 * @param whocanUse       String list with the User or roles who can use the push publish
	 * @param pushMode        String indicating if the push option is: "pushToOne" or "pushToAll"
	 * @throws Exception
	 */
	public void createEnvironment(String environmentName, List<String> whocanUse, String pushMode) throws Exception{

		//Add Environment
		WebElement addEnvironment = getAddEnvironmentButton();
		addEnvironment.click();

		//Fill the Add environment Dialog options
		WebElement AddEnvironmentDialog = getWebElement(By.id("addEnvironment"));
		AddEnvironmentDialog.findElement(By.id("environmentName")).sendKeys(environmentName);
		for(String user : whocanUse){
			WebElement select = AddEnvironmentDialog.findElement(By.id("whoCanUseSelect"));
			select.clear();
			select.sendKeys(user);
			sleep(2);
			getWebElement(By.id("whoCanUseSelect_popup")).findElement(By.id("whoCanUseSelect_popup0")).findElement(By.tagName("span")).click();			
		}
		AddEnvironmentDialog.findElement(By.id(pushMode)).click();
		AddEnvironmentDialog.findElement(By.id("save_label")).click();
	}

	/**
	 * Add a server to the specified environment
	 * @param environmentName String name of the new environment 
	 * @param serverName      Receiver Server Name 
	 * @param address         Receiver Server address 
	 * @param port            Port number (as a String)
	 * @param protocal        Protocol to use http or https
	 * @param key			  Receiver authorization key
	 * @throws Exception
	 */
	public void addServerToEnvironment(String environmentName, String serverName, String address, String port, String protocol, String key) throws Exception{
		//get the environment
		WebElement row = findEnvironment(environmentName);
		List<WebElement> columns = row.findElements(By.tagName("td"));
		List<WebElement>  buttons = columns.get(4).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement button : buttons){
			if(button.getText().equals(getLocalizedString("publisher_Add_Endpoint"))){
				button.click();
				break;
			}
		}

		//Setting receiver server connection info
		WebElement AddNewServerDialog = getWebElement(By.id("addEndpoint"));
		AddNewServerDialog.findElement(By.id("serverName")).sendKeys(serverName);
		sleep(1);
		AddNewServerDialog.findElement(By.id("address")).sendKeys(address);
		AddNewServerDialog.findElement(By.id("port")).clear();
		AddNewServerDialog.findElement(By.id("port")).sendKeys(port);
		if(!protocol.equals("http")){
			AddNewServerDialog.findElement(By.id("protocol")).sendKeys(protocol);
		}
		AddNewServerDialog.findElement(By.id("authKey")).sendKeys(key);
		AddNewServerDialog.findElement(By.id("save_label")).click();
	}

	/**
	 * Return the environment row
	 * @param environmentName Name of the environment
	 * @return WebElement
	 */
	public WebElement findEnvironment(String environmentName) throws Exception{
		List<WebElement> tables = getWebElement(By.id("remotePublishingTabContent")).findElements(By.tagName("table"));
		WebElement environmentRow=null;
		WebElement table = tables.get(0);
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for(WebElement row : rows){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			if(columns.size() == 5){
				if(columns.get(1).getText().equals(environmentName)){
					environmentRow=row;
					break;
				}
			}
		}
		return environmentRow;
	}

	/**
	 * Get the Add Environment button
	 * @return WebElement
	 * @throws Exception
	 */
	private WebElement getAddEnvironmentButton() throws Exception{
		WebElement button = null;
		List<WebElement> spans = getWebElement(By.id("remotePublishingTabContent")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement span : spans){
			if(span.getText().equals(getLocalizedString("publisher_Add_Environment"))){
				button = span;
				break;
			}
		}
		return button;
	}

	/**
	 * Get the Add Server button
	 * @return WebElement
	 * @throws Exception
	 */
	private WebElement getAddServerButton() throws Exception{
		WebElement button = null;
		List<WebElement> spans = getWebElement(By.id("remotePublishingTabContent")).findElements(By.cssSelector("span[class='dijitReset dijitInline dijitButtonText']"));
		for(WebElement span : spans){
			if(span.getText().equals(getLocalizedString("publisher_Add_Endpoint"))){
				button = span;
				break;
			}
		}
		return button;
	}

	/**
	 * Add a receive from server
	 * @param serverName      Receiver Server Name 
	 * @param address         Receiver Server address 
	 * @param key			  Receiver authorization key
	 * @throws Exception
	 */
	public void addReceiveFrom(String serverName, String address,String key) throws Exception{
		getAddServerButton().click();
		WebElement receiveFromDialog = getWebElement(By.id("addEndpoint"));
		receiveFromDialog.findElement(By.id("serverName")).sendKeys(serverName);
		sleep(1);
		receiveFromDialog.findElement(By.id("address")).sendKeys(address);
		receiveFromDialog.findElement(By.id("authKey")).sendKeys(key);
		receiveFromDialog.findElement(By.id("save_label")).click();
	}

	/**
	 * Delete the specified environment
	 * @param environmentName Name of the environment
	 * @throws Exception
	 */
	public void deleteEnvironment(String environmentName) throws Exception{
		WebElement environment = findEnvironment(environmentName);
		List<WebElement> columns = environment.findElements(By.tagName("td"));
		columns.get(0).findElement(By.cssSelector("span[class='deleteIcon']")).click(); 
		switchToAlert().accept();
	}

	/**
	 * Return the environment row
	 * @param environmentName Name of the environment
	 * @return WebElement
	 */
	public WebElement findReceiveFromServer(String serverName) throws Exception{
		List<WebElement> tables = getWebElement(By.id("remotePublishingTabContent")).findElements(By.tagName("table"));
		WebElement serverRow=null;
		WebElement table = tables.get(1);
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for(WebElement row : rows){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			if(columns.size() == 2){
				if(columns.get(1).getText().contains(serverName)){
					serverRow=row;
					break;
				}
			}
		}
		return serverRow;
	}

	/**
	 * Delete the specified receive from server
	 * @param serverName  Receiver Server Name 
	 * @throws Exception
	 */
	public void deleteReceiveFromServer(String serverName) throws Exception{
		WebElement receiver = findReceiveFromServer(serverName);
		List<WebElement> columns = receiver.findElements(By.tagName("td"));
		columns.get(0).findElement(By.cssSelector("span[class='deleteIcon']")).click(); 
		switchToAlert().accept();
	}

	/**
	 * Validate if a publish environment exist
	 * @param environmentName Name of the environment
	 * @return true if exist, false if not
	 * @throws Exception
	 */
	public boolean existPublishingEnvironment(String environmentName) throws Exception{
		boolean exist=false;
		List<WebElement> tables = getWebElement(By.id("remotePublishingTabContent")).findElements(By.tagName("table"));
		WebElement table = tables.get(0);
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for(WebElement row : rows){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			if(columns.size() == 5){
				if(columns.get(1).getText().equals(environmentName)){
					exist=true;
					break;
				}
			}
		}
		return exist;
	}

	/**
	 * Validate if a Receiver from server entry exist
	 * @param serverName Name of the server
	 * @return true if the server exist, false if not
	 * @throws Exception
	 */
	public boolean existReceiveFromServer(String serverName) throws Exception{
		boolean exist=false;
		List<WebElement> tables = getWebElement(By.id("remotePublishingTabContent")).findElements(By.tagName("table"));
		WebElement table = tables.get(1);
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for(WebElement row : rows){
			List<WebElement> columns = row.findElements(By.tagName("td"));
			if(columns.size() == 2){
				if(columns.get(1).getText().contains(serverName)){
					exist=true;
					break;
				}
			}
		}
		return exist;
	}

}
