package com.dotcms.qa.selenium.pages.backend.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dotcms.qa.selenium.pages.backend.*;
import com.dotcms.qa.selenium.pages.common.BasePage;
import com.dotcms.qa.selenium.util.SeleniumPageManager;

import org.apache.log4j.Logger;

public class PortletMenu extends BasePage implements IPortletMenu {
    private static final Logger logger = Logger.getLogger(PortletMenu.class);

	public PortletMenu(WebDriver driver) {
		super(driver);
	}

	public IContentSearchPage getContentSearchPage() throws Exception {
	    hoverOverElement(getPortletElement("Content"));
	    sleep(2);
	    getWebElement(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_11"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IContentSearchPage.class);		
	}

	public ILicenseManagerPage getLicenseManagerPage() throws Exception {
	    hoverOverElement(getPortletElement("System"));
	    sleep(2);
	    getWebElement(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_LICENSE_MANAGER"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(ILicenseManagerPage.class);
	}
	
	public IStructuresPage getStructuresPage() throws Exception {
	    hoverOverElement(getPortletElement("com.dotcms.repackage.javax.portlet.title.EXT_STRUCTURE"));
	    sleep(2);
	    getWebElement(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_STRUCTURE"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IStructuresPage.class);
	}

	public IVanityURLsPage getVanityURLsPage() throws Exception {
	    hoverOverElement(getPortletElement("com.dotcms.repackage.javax.portlet.title.EXT_BROWSER"));
	    sleep(2);
	    getWebElement(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_VIRTUAL_LINKS"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IVanityURLsPage.class);
	}
	
	public IHostPage getHostPage() throws Exception {
	    hoverOverElement(getPortletElement("System"));
	    sleep(2);
	    getWebElementClickable(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_HOSTADMIN"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IHostPage.class);
	}

	public ISiteBrowserPage getSiteBrowserPage() throws Exception {
	    hoverOverElement(getPortletElement("com.dotcms.repackage.javax.portlet.title.EXT_BROWSER"));
	    sleep(2);
	    getWebElementClickable(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_BROWSER"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(ISiteBrowserPage.class);		
	}
	
	/**
	 * Get the User manager page
	 * @return IUsersPage
	 * @throws Exception
	 */
	public IUsersPage getUsersPage() throws Exception {
	    hoverOverElement(getPortletElement("System"));
	    sleep(2);
	    getWebElementClickable(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_USER_ADMIN"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IUsersPage.class);		
	}
	
	/**
	 * Get the role manager page
	 * @return IRolesPage
	 * @throws Exception
	 */
	public IRolesPage getRolesPage() throws Exception {
	    hoverOverElement(getPortletElement("System"));
	    sleep(2);
	    getWebElementClickable(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_ROLE_ADMIN"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IRolesPage.class);		
	}
	
	/**
	 * Get the MailingList manager page
	 * @return IMailingListPage
	 * @throws Exception
	 */
	public IMailingListPage getMailingListPage() throws Exception {
	    hoverOverElement(getPortletElement("Mailing-List"));
	    sleep(2);
	    getPortletElement("Mailing-List").click();
	    sleep(3);
	    return SeleniumPageManager.getBackEndPageManager().getPageObject(IMailingListPage.class);		
	}
	
	/**
	 * Get the Templates manager page
	 * @return IMailingListPage
	 * @throws Exception
	 */
	public ITemplatesPage getTemplatesPage() throws Exception {
	    hoverOverElement(getPortletElement("com.dotcms.repackage.javax.portlet.title.EXT_BROWSER"));
	    sleep(2);
	    getWebElementClickable(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_13"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(ITemplatesPage.class);		
	}
	
	/**
	 * Get the configuration manager page
	 * @return IConfigurationPage
	 * @throws Exception
	 */
	public IConfigurationPage getConfigurationPage() throws Exception {
	    hoverOverElement(getPortletElement("System"));
	    sleep(2);
	    getWebElementClickable(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.9"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IConfigurationPage.class);	
	}
	
	/**
	 * Get the containers manager page
	 * @return IContainersPage
	 * @throws Exception
	 */
	public IContainersPage getContainersPage() throws Exception{
		hoverOverElement(getPortletElement("com.dotcms.repackage.javax.portlet.title.EXT_BROWSER"));
		sleep(2);
	    getWebElementClickable(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_12"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IContainersPage.class);	
	}
	
	/**
	 * Get the publishing queue manager page
	 * @return IPublishingQueuePage
	 * @throws Exception
	 */
	public IPublishingQueuePage getPublishingQueuePage() throws Exception {
		hoverOverElement(getPortletElement("com.dotcms.repackage.javax.portlet.title.EXT_BROWSER"));
		sleep(2);
	    getWebElementClickable(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_CONTENT_PUBLISHING_TOOL"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IPublishingQueuePage.class);
	}
	
	public WebElement getPortletElement(String portletTextKey) {
		WebElement retValue = null;
		String portletText = getLocalizedString(portletTextKey);
		logger.debug("portletTextKey=" + portletTextKey + "|portletText=" + portletText);
		List<WebElement> allElements = getWebElements(By.className("navMenu-title")); 
		for (WebElement element: allElements) {
			try {
				if(portletText.equals(element.getText())){
					retValue = element;
				    break;
				}
				else {
					logger.trace("portletText=" + portletText + "|element.getText()=" + element.getText() +"|element.getTagName()=" + element.getTagName());				
				}
			}
			catch (StaleElementReferenceException e) {
				// do nothing - keep iterating
				logger.info("Stale element exception - " + e.getMessage() + e.getStackTrace());
			}
		}
		if(retValue == null)
			throw new NullPointerException("Active element not found:  portletTextKey=" + portletTextKey + " portletText=" + portletText);
		return retValue;
	}
	
	/**
	 * Get the Menu links manager page
	 * @return IMenuLinkPage
	 * @throws Exception
	 */
	public IMenuLinkPage getMenuLinkPage() throws Exception{
		hoverOverElement(getPortletElement("com.dotcms.repackage.javax.portlet.title.EXT_BROWSER"));
		sleep(2);
	    getWebElementClickable(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_18"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IMenuLinkPage.class);
	}
	
	/**
	 * Get the Workflow schemes page
	 * @return IWorkflowSchemesPage
	 * @throws Exception
	 */
	public IWorkflowSchemesPage getWorkflowSchemesPage() throws Exception{
		hoverOverElement(getPortletElement("com.dotcms.repackage.javax.portlet.title.EXT_STRUCTURE"));
		sleep(2);
	    getWebElementClickable(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.WORKFLOW_SCHEMES"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IWorkflowSchemesPage.class);
	}
	
	/**
	 * Get the Workflow tasks page
	 * @return IWorkflowTasksPage
	 * @throws Exception
	 */
	public IWorkflowTasksPage getWorkflowTasksPage() throws Exception{
		hoverOverElement(getPortletElement(getLocalizedString("Home")));
		sleep(2);
	    getWebElementClickable(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_21"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(IWorkflowTasksPage.class);
	}
	
	/**
	 * Get the system languages page
	 * @return ILanguagesPage
	 * @throws Exception
	 */
	public ILanguagesPage getLanguagesPage() throws Exception{
		hoverOverElement(getPortletElement("System"));
		sleep(2);
	    getWebElementClickable(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_LANG"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(ILanguagesPage.class);		
	}
	
	/**
	 * Get the categories page
	 * @return ICategoriesPage
	 * @throws Exception
	 */
	public ICategoriesPage getCategoriesPage() throws Exception{
		hoverOverElement(getPortletElement("com.dotcms.repackage.javax.portlet.title.EXT_STRUCTURE"));
		sleep(2);
	    getWebElement(By.linkText(getLocalizedString("com.dotcms.repackage.javax.portlet.title.EXT_4"))).click();
	    sleep(3);
		return SeleniumPageManager.getBackEndPageManager().getPageObject(ICategoriesPage.class);	
	}
}
