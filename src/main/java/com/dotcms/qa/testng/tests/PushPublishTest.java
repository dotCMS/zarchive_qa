package com.dotcms.qa.testng.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import com.dotcms.qa.selenium.pages.backend.IBackendSideMenuPage;
import com.dotcms.qa.selenium.pages.backend.IConfigurationPage;
import com.dotcms.qa.selenium.pages.backend.IContainerAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.IContainersPage;
import com.dotcms.qa.selenium.pages.backend.IContentAddOrEdit_ContentPage;
import com.dotcms.qa.selenium.pages.backend.IContentSearchPage;
import com.dotcms.qa.selenium.pages.backend.IHTMLPageAddOrEdit_ContentPage;
import com.dotcms.qa.selenium.pages.backend.ILoginPage;
import com.dotcms.qa.selenium.pages.backend.IMenuLinkAddOrEdit_Page;
import com.dotcms.qa.selenium.pages.backend.IMenuLinkPage;
import com.dotcms.qa.selenium.pages.backend.IPortletMenu;
import com.dotcms.qa.selenium.pages.backend.IPreviewHTMLPage_Page;
import com.dotcms.qa.selenium.pages.backend.IPublishingEnvironments;
import com.dotcms.qa.selenium.pages.backend.IPublishingQueuePage;
import com.dotcms.qa.selenium.pages.backend.IRolesPage;
import com.dotcms.qa.selenium.pages.backend.ISiteBrowserPage;
import com.dotcms.qa.selenium.pages.backend.IStructureAddOrEdit_FieldsPage;
import com.dotcms.qa.selenium.pages.backend.IStructureAddOrEdit_PropertiesPage;
import com.dotcms.qa.selenium.pages.backend.IStructuresPage;
import com.dotcms.qa.selenium.pages.backend.ITemplateAddOrEditAdvanceTemplatePage;
import com.dotcms.qa.selenium.pages.backend.ITemplateAddOrEditDesignTemplatePage;
import com.dotcms.qa.selenium.pages.backend.ITemplatesPage;
import com.dotcms.qa.selenium.pages.backend.IUsersPage;
import com.dotcms.qa.selenium.pages.backend.IWorkFlowStepsAddOrEdit_Page;
import com.dotcms.qa.selenium.pages.backend.IWorkflowActionAddOrEdit_Page;
import com.dotcms.qa.selenium.pages.backend.IWorkflowSchemeAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.IWorkflowSchemesPage;
import com.dotcms.qa.selenium.pages.backend.IWorkflowTasksPage;
import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.UsersPageUtil;
import com.dotcms.qa.util.WebKeys;
import com.dotcms.qa.util.WorkflowPageUtil;

/**
 * This class manage the TestRail suite of test for Push Publishing
 * @author Oswaldo Gallango
 * @since 03/06/2015
 * @version 1.0
 */
public class PushPublishTest {

	//General properties
	private static final Logger logger = Logger.getLogger(HostTest.class);
	private SeleniumConfig config = null;
	private String backendUserEmail = null;
	private String backendUserPassword = null;
	private String demoServer = "qademo.dotcms.com";


	//Push publishing general properties
	private String serversProtocol=null;
	private String serversKey=null;
	private String environmentName="Sender1";

	//Authoring server properties
	private SeleniumPageManager authoringBackendMgr = null;
	private String authoringServer = null;
	private String authoringServerPort = null;
	private ILoginPage authoringLoginPage = null;
	private IPortletMenu authoringPortletMenu = null;
	private IConfigurationPage authoringConfigurationPage=null;
	private IPublishingEnvironments authoringPublishingEnvironments = null;

	//Receiver server properties
	private SeleniumPageManager receiverBackendMgr = null;
	private String receiverServer = null;
	private String receiverServerPort = null;
	private ILoginPage receiverLoginPage = null;
	private IPortletMenu receiverPortletMenu = null;
	private IConfigurationPage receiverConfigurationPage=null;
	private IPublishingEnvironments receiverPublishingEnvironments = null;

	/**
	 * Container test variables
	 */
	private String containerTitle="Test 559 Container";
	private String containerCode ="<h2>Test 559</h2><br/><p>This is a test for push publishing</p>";
	private String containerCode2 ="<h2>Test 559 and 560</h2><br/><p>This is a test for push publishing</p>";
	private String containerTitle2="Test 562 Container";
	private String structureName = "Test562";
	private String containerTitle3="Test 569 Container";
	private String containerCode3 ="<h2>Test 569</h2><br/><p>This is a test for limited user push publishing</p>";
	private String containerCode3a ="<h2>Test 569 and 571</h2><br/><p>This is a test edited for limited user push publishing</p>";

	private String limitedRole="limitedRole";
	private String limitedUserName="MyLimited";
	private String limitedUserLastName="User";
	private String limitedUserEmail="limited.user@dotcms.com";
	private String limitedUserPaswword="limited123";
	//test 555
	private String templateTitle1="Test-555";
	private String templateContainer1="Default 1";
	private String pageTitle1="Test-555";
	private String pageUrl1="test-555.html";
	//test 556
	private String templateTitle2="Test-556";
	private String templateTheme2="quest";
	private String templateContainer2="Default 1";
	private String templateContainer22="Default 2";

	private String templateTitle3="Test-556-2";
	private String templateTheme3="quest";
	private String templateContainer3="Default 1";
	private String pageTitle2="Test 556";
	private String pageUrl2="test-556.html";
	//test 558
	private String folderName1="test558";
	private String templateTitle4="Test-558";
	private String templateTheme4="quest";
	private String templateContainer4="Default 1";
	private String pageTitle3="Test-558";
	private String pageUrl3="test-558.html";
	//test 523
	private String templateTitle5="Test-523A";
	private String templateContainer5="Default 1";
	private String templateTitle6="Test-523B";
	private String templateTheme6="quest";
	private String templateContainer6="Default 1";
	//test 568
	private String templateTitle7="Test-568";
	private String templateContainer7="Default 1";
	private String templateContainer72="Default 2";
	private String pageTitle4="Test-568";
	private String pageUrl4="test-568.html";
	//test 507
	private String containerTitle4="Test 507 Container";
	private String containerCode4 ="<h2>Test 507</h2><br/><p>This is a test for push publishing</p>";
	private String templateTitle8="Test-507";
	private String pageTitle5="Test-507";
	private String pageUrl5="test-507.html";
	//test 577
	private String pageTitle52="Test 507 and 577";
	//test 578
	private String pageTitle53 = "Test 507, 577 and 578";
	//test 582
	private String pageTitle6="Test-582";
	private String pageUrl6="test-582.html";
	//test 625
	private String pageTitle7="Test-625";
	private String pageUrl7="test-625.tml";
	private String template625="Quest - 1 Column (With Content Padding)";
	//test 524
	private String linkTitle1="Test-524";
	private String linkFolder1="home";
	private String linkInternalHost1="qademo.dotcms.com";
	private String linkInternalFolder1="about-us"; 
	private String linkInternalUrl1="what-we-do.html";
	private int linkOrder1=1;
	private boolean linkShowOnMenu1=true;
	//test 574
	private String linkExternalUrl1="www.dotcms.com";
	//test 575
	private String linkTitle2="Test-575";
	private String linkFolder2="services";
	private String linkCode2="<a href='http://www.google.com'>Google</a>";
	private int linkOrder2=1;
	private boolean linkShowOnMenu2=true;
	//test 575
	private String linkCode22="<a href='http://www.google.com'>Google</a><a href='http://www.dotcms.com'>DotCMS</a>";
	private boolean linkShowOnMenu22=false;
	//test 
	private String linkTitle3="Test-589A";
	private String linkFolder3="services";
	private String linkInternalHost3="qademo.dotcms.com";
	private String linkInternalFolder3="about-us"; 
	private String linkInternalUrl3="what-we-do.html";
	private int linkOrder3=1;
	private boolean linkShowOnMenu3=true;

	private String linkTitle4="Test-589B";
	private String linkFolder4="services";
	private String linkCode4="<a href='http://www.google.com'>Google</a>";
	private int linkOrder4=1;
	private boolean linkShowOnMenu4=true;
	//test 520
	private String contentStructureName1="Content ";
	private String contentTitle1="Test-520";
	private String contentWYSIWYG1="Test 520";
	//test 496
	private String contentStructureName2="Test-496";
	private String contentStructureName2Field1="Headline";
	private String contentStructureName2Field2="Description";
	private String contentTitle2="Test-496";
	private String contentTextArea2="Test 496";
	//test 519
	private String contentStructureName3="Test-519-A";
	private String contentStructureName3Field1="Headline";
	private String contentStructureName3Field2="Description";
	private String contentTitle3="Test-519-A";
	private String contentTextArea3="Test 519A";
	private String contentStructureName4="Test-519-B";
	private String contentStructureName4Field1="Headline";
	private String contentStructureName4Field2="Description";
	private String contentTitle4="Test-519-B";
	private String contentTextArea4="Test 519B";
	private String contentStructureName5="Test-519-C";
	private String contentStructureName5Field1="Headline";
	private String contentStructureName5Field2="Description";
	private String contentTitle5="Test-519-C";
	private String contentTextArea5="Test 519C";
	private String contentSearchFilterKey="Test-519";
	//test 532
	private String contentStructureName6="Test-532-#!%&*";
	private String contentStructureName6Field1="Title";
	private String contentStructureName6Field2="Description";
	private String contentTitle6="Test-532";
	private String contentTextArea6="Test 532";
	//test 528
	private String contentStructureName7="Content";
	private String contentStructureName7Field1="Title";
	private String contentTitle7="Test-528";
	private String contentStructureName7Field2="Body";
	private String contentTextArea7="Test 528";
	//test 572
	private String contentStructureName8="Test-572";
	private String contentStructureName8Field1="Title";
	private String contentTitle8="Test-572";
	private String contentStructureName8Field2="Body";
	private String contentTextArea8="Test 572";
	//test 573
	private String contentTextArea82="Test 572 and 573";
	//test 652
	private String contentStructureName9="Test-652";
	private String contentStructureName9Field1="Title";
	private String contentStructureName9Field2="Body";
	private String workflowSchemeName1="Test-652";
	private String workflowSchemeStep1="Test652Assign";
	private String workflowActionName1="Assign";
	private String worflowSubaction1="Unlock content"; 

	//test 653
	private String contentStructureName10="Test-653";
	private String contentStructureName10Field1="Title";
	private String contentTitle10="Test-653";
	private String contentStructureName10Field2="Body";
	private String contentTextArea10="Test 653";
	private String workflowSchemeName2="Test-653";
	private String workflowSchemeStep2="Test653Assign";
	private String workflowActionName2="Assign";
	private String worflowSubaction2="Lock content"; 
	private String workflowSchemeStep3="Test653Review";
	private String workflowActionName3="Review";
	private String worflowSubaction3="Unlock content"; 
	//test-623
	private String folderName2="test623";
	private String contentStructureName11="Test-623";
	private String contentStructureName11Field1="title";
	private String contentTitle11="Test-623";
	private String fileName11="test623.jpg";
	private String contentStructureName11Field2="binary1FileUpload";
	private String contentTextArea11="/src/main/resources/test623.jpg";
	private String workflowSchemeName3="Test-623";
	private String workflowSchemeStep4="Test623Assign";
	private String workflowActionName4="Assign";
	private String worflowSubaction4="Lock content"; 

	@BeforeGroups (groups = {"PushPublishing"})
	public void init() throws Exception {
		try {
			logger.info("**PushPublishTests.init() beginning**");

			config = SeleniumConfig.getConfig();
			backendUserEmail = config.getProperty("backend.user.Email");
			backendUserPassword = config.getProperty("backend.user.Password");

			serversProtocol=config.getProperty("pushpublising.server.protocol");
			serversKey=config.getProperty("pushpublising.server.key");

			authoringServer = config.getProperty("pushpublising.autoring.server");
			authoringServerPort = config.getProperty("pushpublising.autoring.server.port");
			logger.info("Authoring server = " + authoringServer+":"+authoringServerPort);

			receiverServer = config.getProperty("pushpublising.receiver.server");
			receiverServerPort = config.getProperty("pushpublising.receiver.server.port");
			logger.info("Receiver server = " + authoringServer+":"+receiverServerPort);

			//cleaning previous test values
			//deletePreviousTest();

			//login Receiver server
			receiverPortletMenu = callReceiverServer();    

			//create limited user for testing
			//createLimitedUser(receiverPortletMenu);

			//Validate if push publishing servers are configured
			receiverConfigurationPage = receiverPortletMenu.getConfigurationPage();
			receiverPublishingEnvironments = receiverConfigurationPage.getPublishingEnvironmentsTab();
			if(!receiverPublishingEnvironments.existReceiveFromServer(authoringServer)){
				receiverPublishingEnvironments.addReceiveFrom(authoringServer, authoringServer, serversKey);
				receiverPublishingEnvironments.sleep(3);
			}
			logoutReceiverServer();

			//login Authoring server
			authoringPortletMenu = callAuthoringServer();

			//create limited user for testing
			createLimitedUser(authoringPortletMenu);

			//Validate if push publishing servers are configured
			authoringConfigurationPage = authoringPortletMenu.getConfigurationPage();

			//load the Push publish environment tab
			authoringPublishingEnvironments = authoringConfigurationPage.getPublishingEnvironmentsTab();
			if(!authoringPublishingEnvironments.existPublishingEnvironment(environmentName)){
				List<String> whoCanUse = new ArrayList<String>();
				whoCanUse.add("Admin User");
				whoCanUse.add(limitedRole);
				authoringPublishingEnvironments.createEnvironment(environmentName, whoCanUse, "pushToOne");
				//Adding receiver server to the environment
				authoringPublishingEnvironments.addServerToEnvironment(environmentName, receiverServer, receiverServer, receiverServerPort, serversProtocol, serversKey);
			}
			logoutAuthoringServer();
			logger.info("**PushPublishTests.init() ending**");
		}catch(Exception e) {
			logger.error("ERROR - PushPublishTests.init()", e);
			throw(e);
		}
	}

	@AfterGroups (groups = {"PushPublishing"})
	public void teardown() throws Exception {
		try {
			logger.info("**PushPublishTests.teardown() beginning**");

			//Remove old test data
			deletePreviousTest();

			//cleaning authoring server push publishing registers
			authoringPortletMenu =callAuthoringServer();

			IPublishingQueuePage publishingQueuePage = authoringPortletMenu.getPublishingQueuePage();
			publishingQueuePage.getStatusHistoryTab();
			publishingQueuePage.deleteAllHistoryStatus();

			//Validate if push publishing servers are configured
			authoringConfigurationPage = authoringPortletMenu.getConfigurationPage();

			//load the Push publish environment tab and delete it
			authoringPublishingEnvironments = authoringConfigurationPage.getPublishingEnvironmentsTab();
			authoringPublishingEnvironments.deleteEnvironment(environmentName);
			authoringPublishingEnvironments.sleep(3);
			authoringBackendMgr.logoutBackend();

			//cleaning receiver server push publishing registers
			receiverPortletMenu = callReceiverServer();

			publishingQueuePage = receiverPortletMenu.getPublishingQueuePage();
			publishingQueuePage.getStatusHistoryTab();
			publishingQueuePage.deleteAllHistoryStatus();

			//Validate if push publishing servers are configured and delete it
			receiverConfigurationPage = receiverPortletMenu.getConfigurationPage();
			receiverPublishingEnvironments = receiverConfigurationPage.getPublishingEnvironmentsTab();
			receiverPublishingEnvironments.deleteReceiveFromServer(authoringServer);
			receiverBackendMgr.logoutBackend();
			receiverBackendMgr.shutdown();
			logger.info("**PushPublishTests.teardown() ending**");
		}catch(Exception e) {
			logger.error("ERROR - PushPublishTests.teardown()", e);
			throw(e);
		}
	}

	/**
	 * Activate in the browser the Authoring server
	 * @return IPortletMenu
	 * @throws Exception
	 */
	private IPortletMenu callAuthoringServer() throws Exception{
		return callAuthoringServer(backendUserEmail, backendUserPassword);
	}

	/**
	 * Activate in the browser the Authoring server
	 * @param userEmail  User email
	 * @param userPassword User password
	 * @return IPortletMenu
	 * @throws Exception
	 */
	private IPortletMenu callAuthoringServer(String userEmail,String userPassword) throws Exception{
		authoringBackendMgr = RegressionSuiteEnv.getBackendPageManager(serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
		authoringLoginPage = authoringBackendMgr.getPageObject(ILoginPage.class);
		try{
			authoringLoginPage.login(userEmail, userPassword);
		}catch(Exception e){
			//already logged in
		}
		return authoringBackendMgr.getPageObject(IPortletMenu.class);
	}

	/**
	 * logout user from authoring server
	 */
	private void logoutAuthoringServer() throws Exception{
		authoringBackendMgr.logoutBackend(serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
	}

	/**
	 * Activate in the browser the Receiver server
	 * @return IPortletMenu
	 * @throws Exception
	 */
	private IPortletMenu callReceiverServer() throws Exception{
		return callReceiverServer(backendUserEmail, backendUserPassword);
	}

	/**
	 * Activate in the browser the Receiver server
	 * @param userEmail  User email
	 * @param userPassword User password
	 * @return IPortletMenu
	 * @throws Exception
	 */
	private IPortletMenu callReceiverServer(String userEmail,String userPassword) throws Exception{
		receiverBackendMgr = RegressionSuiteEnv.getBackendPageManager(serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
		receiverLoginPage = receiverBackendMgr.getPageObject(ILoginPage.class);
		try{
			receiverLoginPage.login(userEmail,userPassword);
		}catch(Exception e){
			//already logged in
		}
		return receiverBackendMgr.getPageObject(IPortletMenu.class);
	}

	/**
	 * logout user from receiver server
	 */
	private void logoutReceiverServer() throws Exception{
		receiverBackendMgr.logoutBackend(serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
	}

	/**
	 * Delete the previous test to avoid issue with next test runs
	 * on authoring and receiver servers
	 * @throws Exception
	 */
	private void deletePreviousTest() throws Exception{
		//Authoring Server
		try{
			/*Containers Test*/
			IPortletMenu portletMenu = callAuthoringServer();

			/* Delete pages*/
			ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
			if(browserPage.doesElementExist(pageUrl1)){
				browserPage.unPublishElement(pageUrl1);
				browserPage.archiveElement(pageUrl1);
				browserPage.deletePage(pageUrl1);
			}

			if(browserPage.doesElementExist(pageUrl2)){
				browserPage.unPublishElement(pageUrl2);
				browserPage.archiveElement(pageUrl2);
				browserPage.deletePage(pageUrl2);
			}

			if(browserPage.doesElementExist(pageUrl3)){
				browserPage.unPublishElement(pageUrl3);
				browserPage.archiveElement(pageUrl3);
				browserPage.deletePage(pageUrl3);
			}

			if(browserPage.doesElementExist(pageUrl4)){
				browserPage.unPublishElement(pageUrl4);
				browserPage.archiveElement(pageUrl4);
				browserPage.deletePage(pageUrl4);
			}

			if(browserPage.doesElementExist(pageUrl5)){
				browserPage.unPublishElement(pageUrl5);
				browserPage.archiveElement(pageUrl5);
				browserPage.deletePage(pageUrl5);
			}

			if(browserPage.doesElementExist(pageUrl6)){
				browserPage.unPublishElement(pageUrl6);
				browserPage.archiveElement(pageUrl6);
				browserPage.deletePage(pageUrl6);
			}

			if(browserPage.doesElementExist(pageUrl7)){
				browserPage.unPublishElement(pageUrl7);
				browserPage.archiveElement(pageUrl7);
				browserPage.deletePage(pageUrl7);
			}

			if(browserPage.doesFolderExist(folderName1)){
				browserPage.deleteFolder(folderName1);
			}

			/* Delete template*/
			ITemplatesPage templatesPage = portletMenu.getTemplatesPage();
			if(templatesPage.doesTemplateExist(templateTitle1)){
				templatesPage.deleteTemplate(templateTitle1);
			}

			if(templatesPage.doesTemplateExist(templateTitle2)){
				templatesPage.deleteTemplate(templateTitle2);
			}

			if(templatesPage.doesTemplateExist(templateTitle3)){
				templatesPage.deleteTemplate(templateTitle3);
			}

			if(templatesPage.doesTemplateExist(templateTitle4)){
				templatesPage.deleteTemplate(templateTitle4);
			}

			if(templatesPage.doesTemplateExist(templateTitle5)){
				templatesPage.deleteTemplate(templateTitle5);
			}

			if(templatesPage.doesTemplateExist(templateTitle6)){
				templatesPage.deleteTemplate(templateTitle6);
			}

			if(templatesPage.doesTemplateExist(templateTitle7)){
				templatesPage.deleteTemplate(templateTitle7);
			}

			if(templatesPage.doesTemplateExist(templateTitle8)){
				templatesPage.deleteTemplate(templateTitle8);
			}

			/*Delete containers*/
			IContainersPage containersPage = portletMenu.getContainersPage();
			if(containersPage.existContainer(containerTitle)){
				containersPage.deleteContainer(containerTitle);
			}
			if(containersPage.existContainer(containerTitle2)){
				containersPage.deleteContainer(containerTitle2);
			}
			if(containersPage.existContainer(containerTitle3)){
				containersPage.deleteContainer(containerTitle3);
			}

			if(containersPage.existContainer(containerTitle4)){
				containersPage.deleteContainer(containerTitle4);
			}

			/* Delete Menu link*/
			IMenuLinkPage menuLinkPage= portletMenu.getMenuLinkPage();
			if(menuLinkPage.doesLinkExist(linkTitle1)){
				menuLinkPage.deleteLink(linkTitle1);
			}

			if(menuLinkPage.doesLinkExist(linkTitle2)){
				menuLinkPage.deleteLink(linkTitle2);
			}

			if(menuLinkPage.doesLinkExist(linkTitle3)){
				menuLinkPage.deleteLink(linkTitle3);
			}

			if(menuLinkPage.doesLinkExist(linkTitle4)){
				menuLinkPage.deleteLink(linkTitle4);
			}

			/* Delete structure*/
			IStructuresPage structurePage = portletMenu.getStructuresPage();
			if(structurePage.doesStructureExist(structureName)){
				structurePage.deleteStructureAndContent(structureName, true);
			}

			if(structurePage.doesStructureExist(contentStructureName2)){
				structurePage.deleteStructureAndContent(contentStructureName2, true);
			}

			if(structurePage.doesStructureExist(contentStructureName3)){
				structurePage.deleteStructureAndContent(contentStructureName3, true);
			}

			if(structurePage.doesStructureExist(contentStructureName4)){
				structurePage.deleteStructureAndContent(contentStructureName4, true);
			}

			if(structurePage.doesStructureExist(contentStructureName5)){
				structurePage.deleteStructureAndContent(contentStructureName5, true);
			}

			if(structurePage.doesStructureExist(contentStructureName6)){
				structurePage.deleteStructureAndContent(contentStructureName6, true);
			}

			if(structurePage.doesStructureExist(contentStructureName8)){
				structurePage.deleteStructureAndContent(contentStructureName8, true);
			}

			if(structurePage.doesStructureExist(contentStructureName9)){
				structurePage.deleteStructureAndContent(contentStructureName9, true);
			}

			if(structurePage.doesStructureExist(contentStructureName10)){
				structurePage.deleteStructureAndContent(contentStructureName10, true);
			}
			
			if(structurePage.doesStructureExist(contentStructureName11)){
				structurePage.deleteStructureAndContent(contentStructureName11, true);
			}

			/* Delete content*/
			IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
			if(contentSearchPage.doesContentExist(contentTitle1, contentStructureName1)){
				contentSearchPage.unpublish(contentTitle1, contentStructureName1);
				contentSearchPage.archive(contentTitle1, contentStructureName1);
				contentSearchPage.delete(contentTitle1, contentStructureName1);
			}

			if(contentSearchPage.doesContentExist(contentTitle7, contentStructureName7)){
				contentSearchPage.unpublish(contentTitle7, contentStructureName7);
				contentSearchPage.archive(contentTitle7, contentStructureName7);
				contentSearchPage.delete(contentTitle7, contentStructureName7);
			}

			/* Delete workflows*/
			IWorkflowSchemesPage schemesPage = portletMenu.getWorkflowSchemesPage();
			if(schemesPage.doesWorkflowSchemeExist(workflowSchemeName1)){
				schemesPage = portletMenu.getWorkflowSchemesPage();
				IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName1);
				stepsPage.deleteStep(workflowSchemeStep1);
				WorkflowPageUtil.deleteWorkflow(workflowSchemeName1,serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
			}
			schemesPage = portletMenu.getWorkflowSchemesPage();
			if(schemesPage.doesWorkflowSchemeExist(workflowSchemeName2)){
				schemesPage = portletMenu.getWorkflowSchemesPage();
				IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName2);
				stepsPage.deleteStep(workflowSchemeStep3);
				stepsPage.deleteStep(workflowSchemeStep2);
				WorkflowPageUtil.deleteWorkflow(workflowSchemeName2,serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
			}
			
			schemesPage = portletMenu.getWorkflowSchemesPage();
			if(schemesPage.doesWorkflowSchemeExist(workflowSchemeName3)){
				schemesPage = portletMenu.getWorkflowSchemesPage();
				IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName2);
				stepsPage.deleteStep(workflowSchemeStep4);
				WorkflowPageUtil.deleteWorkflow(workflowSchemeName3,serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
			}

			/*Delete limited user*/
			/*IUsersPage usersPage = portletMenu.getUsersPage();
			Map<String, String> fakeUser = usersPage.getUserProperties(limitedUserEmail);
			String fakeUserId = fakeUser.get("userId");
			if(fakeUserId != null && !fakeUserId.equals("")){
				UsersPageUtil.deleteUser(fakeUserId,serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
			}*/

			/* Delete limited role*/
			/*IRolesPage rolePage = portletMenu.getRolesPage();
			if(rolePage.doesRoleExist(limitedRole)){
				rolePage.deleteRole(limitedRole);
			}*/
			logoutAuthoringServer();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}

		//Receiver Server
		try{
			/*Containers Test*/
			IPortletMenu portletMenu = callReceiverServer();

			/* Delete pages*/
			ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
			if(browserPage.doesElementExist(pageUrl1)){
				browserPage.unPublishElement(pageUrl1);
				browserPage.archiveElement(pageUrl1);
				browserPage.deletePage(pageUrl1);
			}

			if(browserPage.doesElementExist(pageUrl2)){
				browserPage.unPublishElement(pageUrl2);
				browserPage.archiveElement(pageUrl2);
				browserPage.deletePage(pageUrl2);
			}

			if(browserPage.doesElementExist(pageUrl3)){
				browserPage.unPublishElement(pageUrl3);
				browserPage.archiveElement(pageUrl3);
				browserPage.deletePage(pageUrl3);
			}

			if(browserPage.doesElementExist(pageUrl4)){
				browserPage.unPublishElement(pageUrl4);
				browserPage.archiveElement(pageUrl4);
				browserPage.deletePage(pageUrl4);
			}

			if(browserPage.doesElementExist(pageUrl5)){
				browserPage.unPublishElement(pageUrl5);
				browserPage.archiveElement(pageUrl5);
				browserPage.deletePage(pageUrl5);
			}

			if(browserPage.doesElementExist(pageUrl6)){
				browserPage.unPublishElement(pageUrl6);
				browserPage.archiveElement(pageUrl6);
				browserPage.deletePage(pageUrl6);
			}

			if(browserPage.doesElementExist(pageUrl7)){
				browserPage.unPublishElement(pageUrl7);
				browserPage.archiveElement(pageUrl7);
				browserPage.deletePage(pageUrl7);
			}

			if(browserPage.doesFolderExist(folderName1)){
				browserPage.deleteFolder(folderName1);
			}
			
			if(browserPage.doesFolderExist(folderName2)){
				browserPage.deleteFolder(folderName2);
			}

			/* Delete template*/
			ITemplatesPage templatesPage = portletMenu.getTemplatesPage();
			if(templatesPage.doesTemplateExist(templateTitle1)){
				templatesPage.deleteTemplate(templateTitle1);
			}

			templatesPage = portletMenu.getTemplatesPage();
			if(templatesPage.doesTemplateExist(templateTitle2)){
				templatesPage.deleteTemplate(templateTitle2);
			}

			if(templatesPage.doesTemplateExist(templateTitle3)){
				templatesPage.deleteTemplate(templateTitle3);
			}

			if(templatesPage.doesTemplateExist(templateTitle4)){
				templatesPage.deleteTemplate(templateTitle4);
			}

			if(templatesPage.doesTemplateExist(templateTitle5)){
				templatesPage.deleteTemplate(templateTitle5);
			}

			if(templatesPage.doesTemplateExist(templateTitle6)){
				templatesPage.deleteTemplate(templateTitle6);
			}

			if(templatesPage.doesTemplateExist(templateTitle7)){
				templatesPage.deleteTemplate(templateTitle7);
			}

			if(templatesPage.doesTemplateExist(templateTitle8)){
				templatesPage.deleteTemplate(templateTitle8);
			}

			/*Delete containers*/
			IContainersPage containersPage = portletMenu.getContainersPage();
			if(containersPage.existContainer(containerTitle)){
				containersPage.deleteContainer(containerTitle);
			}
			if(containersPage.existContainer(containerTitle2)){
				containersPage.deleteContainer(containerTitle2);
			}
			if(containersPage.existContainer(containerTitle3)){
				containersPage.deleteContainer(containerTitle3);
			}

			if(containersPage.existContainer(containerTitle4)){
				containersPage.deleteContainer(containerTitle4);
			}

			/* Delete Menu link*/
			IMenuLinkPage menuLinkPage= portletMenu.getMenuLinkPage();
			if(menuLinkPage.doesLinkExist(linkTitle1)){
				menuLinkPage.deleteLink(linkTitle1);
			}

			if(menuLinkPage.doesLinkExist(linkTitle2)){
				menuLinkPage.deleteLink(linkTitle2);
			}

			if(menuLinkPage.doesLinkExist(linkTitle3)){
				menuLinkPage.deleteLink(linkTitle3);
			}

			if(menuLinkPage.doesLinkExist(linkTitle4)){
				menuLinkPage.deleteLink(linkTitle4);
			}

			/* Delete structure*/
			IStructuresPage structurePage = portletMenu.getStructuresPage();
			if(structurePage.doesStructureExist(structureName)){
				structurePage.deleteStructureAndContent(structureName, true);
			}

			if(structurePage.doesStructureExist(contentStructureName2)){
				structurePage.deleteStructureAndContent(contentStructureName2, true);
			}

			if(structurePage.doesStructureExist(contentStructureName3)){
				structurePage.deleteStructureAndContent(contentStructureName3, true);
			}

			if(structurePage.doesStructureExist(contentStructureName4)){
				structurePage.deleteStructureAndContent(contentStructureName4, true);
			}

			if(structurePage.doesStructureExist(contentStructureName5)){
				structurePage.deleteStructureAndContent(contentStructureName5, true);
			}

			if(structurePage.doesStructureExist(contentStructureName6)){
				structurePage.deleteStructureAndContent(contentStructureName6, true);
			}

			if(structurePage.doesStructureExist(contentStructureName8)){
				structurePage.deleteStructureAndContent(contentStructureName8, true);
			}

			if(structurePage.doesStructureExist(contentStructureName9)){
				structurePage.deleteStructureAndContent(contentStructureName9, true);
			}

			if(structurePage.doesStructureExist(contentStructureName10)){
				structurePage.deleteStructureAndContent(contentStructureName10, true);
			}
			
			if(structurePage.doesStructureExist(contentStructureName11)){
				structurePage.deleteStructureAndContent(contentStructureName11, true);
			}

			/* Delete content*/
			IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
			if(contentSearchPage.doesContentExist(contentTitle1, contentStructureName1)){
				contentSearchPage.unpublish(contentTitle1, contentStructureName1);
				contentSearchPage.archive(contentTitle1, contentStructureName1);
				contentSearchPage.delete(contentTitle1, contentStructureName1);
			}

			if(contentSearchPage.doesContentExist(contentTitle7, contentStructureName7)){
				contentSearchPage.unpublish(contentTitle7, contentStructureName7);
				contentSearchPage.archive(contentTitle7, contentStructureName7);
				contentSearchPage.delete(contentTitle7, contentStructureName7);
			}

			/* Delete workflows*/
			IWorkflowSchemesPage schemesPage = portletMenu.getWorkflowSchemesPage();
			if(schemesPage.doesWorkflowSchemeExist(workflowSchemeName1)){
				schemesPage = portletMenu.getWorkflowSchemesPage();
				IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName1);
				stepsPage.deleteStep(workflowSchemeStep1);
				WorkflowPageUtil.deleteWorkflow(workflowSchemeName1,serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
			}
			schemesPage = portletMenu.getWorkflowSchemesPage();
			if(schemesPage.doesWorkflowSchemeExist(workflowSchemeName2)){
				schemesPage = portletMenu.getWorkflowSchemesPage();
				IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName2);
				stepsPage.deleteStep(workflowSchemeStep3);
				stepsPage.deleteStep(workflowSchemeStep2);
				WorkflowPageUtil.deleteWorkflow(workflowSchemeName2,serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
			}
			
			schemesPage = portletMenu.getWorkflowSchemesPage();
			if(schemesPage.doesWorkflowSchemeExist(workflowSchemeName3)){
				schemesPage = portletMenu.getWorkflowSchemesPage();
				IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName2);
				stepsPage.deleteStep(workflowSchemeStep4);
				WorkflowPageUtil.deleteWorkflow(workflowSchemeName3,serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
			}

			logoutReceiverServer();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Create a limited user for push publishing test
	 * @throws Exception
	 */
	private void createLimitedUser(IPortletMenu portletMenu) throws Exception{
		IRolesPage rolePage = portletMenu.getRolesPage();
		if(!rolePage.doesRoleExist(limitedRole)){
			rolePage.createRole(limitedRole, limitedRole, "", true, true, true);
			rolePage.checkUncheckCMSTab(limitedRole, "Site Browser");
			rolePage.checkUncheckCMSTab(limitedRole, "Structures");
			rolePage.checkUncheckCMSTab(limitedRole, "Content");

			List<Map<String,Object>> subpermissions = new ArrayList<Map<String,Object>>();
			Map<String, Object> property= new HashMap<String, Object>();
			property.put("name","folders");
			property.put("view",true);
			property.put("addChildren",true);
			property.put("edit",true);
			property.put("publish",false);
			property.put("editPermission",false);
			property.put("vanityUrl",false);
			subpermissions.add(property);

			property= new HashMap<String, Object>();
			property.put("name","Pages");
			property.put("view",true);
			property.put("addChildren",true);
			property.put("edit",true);
			property.put("publish",true);
			property.put("editPermission",false);
			property.put("vanityUrl",false);
			subpermissions.add(property);

			property= new HashMap<String, Object>();
			property.put("name","Files-Legacy");
			property.put("view",true);
			property.put("addChildren",false);
			property.put("edit",true);
			property.put("publish",true);
			property.put("editPermission",false);
			property.put("vanityUrl",false);
			subpermissions.add(property);

			property= new HashMap<String, Object>();
			property.put("name","links");
			property.put("view",true);
			property.put("addChildren",false);
			property.put("edit",true);
			property.put("publish",true);
			property.put("editPermission",false);
			property.put("vanityUrl",false);
			subpermissions.add(property);

			property= new HashMap<String, Object>();
			property.put("name","Structures");
			property.put("view",true);
			property.put("addChildren",false);
			property.put("edit",true);
			property.put("publish",true);
			property.put("editPermission",false);
			property.put("vanityUrl",false);
			subpermissions.add(property);

			property= new HashMap<String, Object>();
			property.put("name","Content-Files");
			property.put("view",true);
			property.put("addChildren",false);
			property.put("edit",true);
			property.put("publish",true);
			property.put("editPermission",false);
			property.put("vanityUrl",false);
			subpermissions.add(property);

			property= new HashMap<String, Object>();
			property.put("name","Containers");
			property.put("view",true);
			property.put("addChildren",false);
			property.put("edit",true);
			property.put("publish",true);
			property.put("editPermission",false);
			property.put("vanityUrl",false);
			subpermissions.add(property);

			property= new HashMap<String, Object>();
			property.put("name","Templates");
			property.put("view",true);
			property.put("addChildren",false);
			property.put("edit",true);
			property.put("publish",true);
			property.put("editPermission",false);
			property.put("vanityUrl",false);
			subpermissions.add(property);

			property= new HashMap<String, Object>();
			property.put("name","Template-Layouts");
			property.put("view",true);
			property.put("addChildren",false);
			property.put("edit",true);
			property.put("publish",true);
			property.put("editPermission",false);
			property.put("vanityUrl",false);
			subpermissions.add(property);

			rolePage.addPermissionOnHost(limitedRole, demoServer, subpermissions,true, false, true, false, false, false);
		}
		IUsersPage userPage = portletMenu.getUsersPage();
		if(!userPage.doesUserEmailExist(limitedUserEmail)){
			userPage.addUser(limitedUserName, limitedUserLastName, limitedUserEmail, limitedUserPaswword);
			userPage.addRoleToUser(limitedRole, limitedUserEmail);
		}
	}

	/**
	 * CONTAINERS PUSH PUBLISHING TESTS
	 */

	/**
	 * 	Add a new Container and push to remote server:
	 * http://qa.dotcms.com/index.php?/cases/view/559
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc559_AddNewContainerAndPush() throws Exception {
		//connect to authoring server
		IPortletMenu portletMenu = callAuthoringServer();

		IContainersPage containersPage = portletMenu.getContainersPage();
		IContainerAddOrEditPage addContainerPage = containersPage.getAddContainerPage();

		//simple container
		Map<String, String> container = new HashMap<String,String>();
		container.put("titleField", containerTitle);
		container.put("friendlyNameField", containerTitle);
		container.put("ace", containerCode);
		//create test container to push
		addContainerPage.setFields(container);
		containersPage = addContainerPage.saveAndPublish();
		Assert.assertTrue(containersPage.existContainer(containerTitle), "ERROR - Container ('"+containerTitle+"')  should exist at this moment in authoring server.");

		//add container to bundle
		String bundleName = "test559";
		containersPage.addToBundle(containerTitle, bundleName);

		//push container
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		publishingQueuePage.getBundlesTab();
		String authoringServerBundleId = publishingQueuePage.pushPublishBundle(bundleName);

		//wait until 5 minutes to check if the container was pushed
		boolean isPushed = publishingQueuePage.isBundlePushed(authoringServerBundleId,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Container push should not be in pending list.");
		logoutAuthoringServer();

		//connect to receiver server
		portletMenu = callReceiverServer();

		//getting bundleId
		publishingQueuePage = portletMenu.getPublishingQueuePage();
		publishingQueuePage.getStatusHistoryTab();
		List<Map<String,String>> receiverBundle = publishingQueuePage.getBundleHistoryStatus(bundleName);
		Assert.assertTrue(authoringServerBundleId != null,"ERROR - the authoring server doesn't have the bundle registered");
		Assert.assertTrue(receiverBundle.size() != 0,"ERROR - the receiver server doesn't have the bundle registered");
		String receiverServerBundleId = receiverBundle.get(0).get("bundleId");
		Assert.assertTrue(authoringServerBundleId.equals(receiverServerBundleId),"ERROR - The bundle should have the same Id");

		//validate and delete container from receiver server
		containersPage = portletMenu.getContainersPage();
		Assert.assertTrue(containersPage.existContainer(containerTitle), "ERROR - Receiver Server: Container ('"+containerTitle+"') should exist at this moment in receiver server.");
		logoutReceiverServer();
	}

	/**
	 * 	Edit an existing pushed Container and push to remote server:
	 * http://qa.dotcms.com/index.php?/cases/view/560
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc560_EditContainerAndPush() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();

		//Getting and editing container
		IContainersPage containersPage = portletMenu.getContainersPage();
		IContainerAddOrEditPage editContainerPage = containersPage.getEditContainerPage(containerTitle);

		Map<String, String> container = new HashMap<String,String>();
		container.put("titleField", containerTitle);
		container.put("friendlyNameField", containerTitle);
		container.put("ace", containerCode2);
		editContainerPage.setFields(container);
		containersPage = editContainerPage.saveAndPublish();

		//Push modified container
		containersPage.pushContainer(containerTitle);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the container was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(containerTitle,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Container push should not be in pending list.");

		//delete containers
		//Authoring server
		containersPage = portletMenu.getContainersPage();
		containersPage.deleteContainer(containerTitle);
		Assert.assertFalse(containersPage.existContainer(containerTitle), "ERROR - Authoring Server: Container ('"+containerTitle+"') should not exist at this moment in authoring server.");
		logoutAuthoringServer();

		//connect to receiver server
		portletMenu = callReceiverServer();
		containersPage = portletMenu.getContainersPage();
		editContainerPage = containersPage.getEditContainerPage(containerTitle);
		String codeValue= editContainerPage.getFieldValue("ace");
		editContainerPage.cancel();
		Assert.assertTrue(codeValue.equals(containerCode2),"ERROR - Container ('"+containerTitle+"') in receiver server doesn't match the version in the authoring server");

		//delete containers
		//Receiver server
		containersPage.deleteContainer(containerTitle);
		Assert.assertFalse(containersPage.existContainer(containerTitle), "ERROR - Receiver Server: Container ('"+containerTitle+"') should exist at this moment in receiver server.");
		logoutReceiverServer();
	}

	/**
	 * Set a container with a new structure, to check pushing dependencies into remote server:
	 * http://qa.dotcms.com/index.php?/cases/view/562
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc562_FormatContainerToAddNewStructureDependency() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		IStructuresPage structurePage = portletMenu.getStructuresPage();

		//create new structure
		Assert.assertFalse(structurePage.doesStructureExist(structureName),"ERROR - The Structure ("+structureName+") shoudl not exist at this time.");

		IStructureAddOrEdit_PropertiesPage addStructurePage = structurePage.getAddNewStructurePage();
		IStructureAddOrEdit_FieldsPage fieldsPage = addStructurePage.createNewStructure(structureName, "Content",structureName, demoServer);

		//Test that the field doesn't exist
		String titleField = "Title";
		String descriptionField = "Description";
		Assert.assertFalse(fieldsPage.doesFieldExist(titleField),"ERROR - The field ("+titleField+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(titleField, false, false, false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(titleField),"ERROR - The field ("+titleField+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(descriptionField),"ERROR - The field ("+descriptionField+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(descriptionField, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(descriptionField),"ERROR - The field ("+descriptionField+") shoudl exist at this time");

		//add container
		IContainersPage containersPage = portletMenu.getContainersPage();
		IContainerAddOrEditPage addContainerPage = containersPage.getAddContainerPage();

		Map<String, String> container = new HashMap<String,String>();
		container.put("titleField", containerTitle2);
		container.put("friendlyNameField", containerTitle2);
		container.put("maxContentlets", "1");
		container.put("structureSelect", structureName);
		container.put("AddVariables", titleField+","+descriptionField);
		//create test container to push
		addContainerPage.setFields(container);
		containersPage = addContainerPage.saveAndPublish();

		Assert.assertTrue(containersPage.existContainer(containerTitle2), "ERROR - Container ('"+containerTitle2+"') should exist at this moment in authoring server.");

		//Pushing Container
		containersPage.pushContainer(containerTitle2);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the container was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(containerTitle2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Container push should not be in pending list.");
		logoutAuthoringServer();

		//connect to receiver server
		portletMenu = callReceiverServer();
		containersPage = portletMenu.getContainersPage();
		Assert.assertTrue(containersPage.existContainer(containerTitle2),"ERROR - Container ('"+containerTitle2+"') in receiver server doesn't exist");

		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(structureName),"ERROR - Structure ('"+structureName+"') doesn't exist in receiver server");
		//delete structure and containers
		//Receiver server
		containersPage = portletMenu.getContainersPage();
		containersPage.deleteContainer(containerTitle2);
		Assert.assertFalse(containersPage.existContainer(containerTitle2), "ERROR - Receiver Server: container ('"+containerTitle2+"') should not exist at this moment in receiver server.");

		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(structureName, true);
		Assert.assertFalse(structurePage.doesStructureExist(structureName), "ERROR - Receiver Server: structure ('"+structureName+"') should not exist at this moment in receiver server.");
		logoutReceiverServer();

		//Authoring server
		portletMenu = callAuthoringServer();
		containersPage = portletMenu.getContainersPage();
		containersPage.deleteContainer(containerTitle2);
		Assert.assertFalse(containersPage.existContainer(containerTitle2), "ERROR - Receiver Server: container ('"+containerTitle2+"') should not exist at this moment in receiver server.");

		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(structureName, true);
		Assert.assertFalse(structurePage.doesStructureExist(structureName), "ERROR - Receiver Server: structure ('"+structureName+"') should not exist at this moment in receiver server.");
		logoutAuthoringServer();
	}

	/**
	 * Push a new containers with a limited user
	 * http://qa.dotcms.com/index.php?/cases/view/569
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc569_PushContainerWithLimitedUser() throws Exception {
		//connect to authoring server
		IPortletMenu portletMenu = callAuthoringServer();

		IContainersPage containersPage = portletMenu.getContainersPage();
		IContainerAddOrEditPage addContainerPage = containersPage.getAddContainerPage();

		//simple container
		Map<String, String> container = new HashMap<String,String>();
		container.put("titleField", containerTitle3);
		container.put("friendlyNameField", containerTitle3);
		container.put("ace", containerCode3);
		//create test container to push
		addContainerPage.setFields(container);
		containersPage = addContainerPage.saveAndPublish();

		Assert.assertTrue(containersPage.existContainer(containerTitle3), "ERROR - Container ('"+containerTitle3+"') should exist at this moment in authoring server.");
		logoutAuthoringServer();

		//Connecting as a limited user
		portletMenu = callAuthoringServer(limitedUserEmail,limitedUserPaswword);
		containersPage = portletMenu.getContainersPage();

		Assert.assertTrue(containersPage.existContainer(containerTitle3), "ERROR - Container ('"+containerTitle3+"') is not visible for limited user.");
		containersPage.pushContainer(containerTitle3);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the container was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(containerTitle3,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Container ('"+containerTitle3+"') push should not be in pending list.");
		logoutAuthoringServer();

		//connect to receiver server
		portletMenu = callReceiverServer();
		containersPage = portletMenu.getContainersPage();
		//validate that the container was pushed
		Assert.assertTrue(containersPage.existContainer(containerTitle3), "ERROR - Container ('"+containerTitle3+"') should exist at this moment in authoring server.");
		logoutReceiverServer();
	}

	/**
	 * Push a edited containers with a limited user
	 * http://qa.dotcms.com/index.php?/cases/view/571
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc571_PushEditedContainerWithLimitedUser() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer(limitedUserEmail,limitedUserPaswword);

		//Getting and editing container
		IContainersPage containersPage = portletMenu.getContainersPage();
		IContainerAddOrEditPage editContainerPage = containersPage.getEditContainerPage(containerTitle3);

		Map<String, String> container = new HashMap<String,String>();
		container.put("titleField", containerTitle3);
		container.put("friendlyNameField", containerTitle3);
		container.put("ace", containerCode3a);
		editContainerPage.setFields(container);
		containersPage = editContainerPage.saveAndPublish();

		//Push modified container
		containersPage.pushContainer(containerTitle3);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the container was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(containerTitle3,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Container ('"+containerTitle3+"') push should not be in pending list.");
		logoutAuthoringServer();

		//delete containers
		//Authoring server
		portletMenu = callAuthoringServer();
		containersPage = portletMenu.getContainersPage();
		containersPage.deleteContainer(containerTitle3);
		Assert.assertFalse(containersPage.existContainer(containerTitle3), "ERROR - Authoring Server: Container ('"+containerTitle3+"') should not exist at this moment in authoring server.");
		logoutAuthoringServer();

		//connect to receiver server
		portletMenu = callReceiverServer();
		containersPage = portletMenu.getContainersPage();
		editContainerPage = containersPage.getEditContainerPage(containerTitle3);
		String codeValue= editContainerPage.getFieldValue("ace");
		editContainerPage.cancel();
		Assert.assertTrue(codeValue.equals(containerCode3a),"ERROR - Container ('"+containerTitle3+"') in receiver server doesn't match the version in the authoring server");

		//delete containers
		//Receiver server
		containersPage.deleteContainer(containerTitle3);
		Assert.assertFalse(containersPage.existContainer(containerTitle3), "ERROR - Receiver Server: Container ('"+containerTitle3+"') should exist at this moment in receiver server.");
		logoutReceiverServer();
	}

	/**
	 * TEMPLATES PUSH PUBLISHING TESTS
	 */

	/**
	 * Create an advance template and push it to remote server
	 * http://qa.dotcms.com/index.php?/cases/view/555
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc555_AddNewAdvanceTemplateAndPush() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		ITemplatesPage templatesPage = portletMenu.getTemplatesPage();

		ITemplateAddOrEditAdvanceTemplatePage advanceTemplate = templatesPage.addAdvanceTemplate();
		Map<String,String> template = new HashMap<String, String>();
		template.put("titleField",templateTitle1);
		template.put("friendlyNameField", templateTitle1);
		template.put("AddContainers", templateContainer1);

		advanceTemplate.setTemplateFields(template);
		templatesPage= advanceTemplate.saveAndPublish();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle1), "ERROR - Authoring Server: Template ('"+templateTitle1+"') should exist at this moment in authoring server.");

		//create test page
		templatesPage.sleep(3);
		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		browserPage.createHTMLPage(pageTitle1, templateTitle1, pageUrl1);

		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();

		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl1), "ERROR - Authoring Server: Page ('"+pageUrl1+"') should exist at this moment in authoring server.");

		browserPage.pushElement(pageUrl1);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(pageTitle1,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ('"+pageUrl1+"') push should not be in pending list.");

		//delete template and page
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(pageUrl1);
		browserPage.archiveElement(pageUrl1);
		browserPage.deletePage(pageUrl1);
		Assert.assertFalse(browserPage.doesElementExist(pageUrl1), "ERROR - Authoring Server: Page ('"+pageUrl1+"') should not exist at this moment in authoring server.");
		browserPage.sleep(2);
		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(templateTitle1);
		Assert.assertFalse(templatesPage.doesTemplateExist(templateTitle1), "ERROR - Authoring Server: Template ('"+templateTitle1+"') should not exist at this moment in authoring server.");	
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl1), "ERROR - Receiver Server: Page ('"+pageUrl1+"') should exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle1), "ERROR - Receiver Server: Template ('"+templateTitle1+"') should exist at this moment in receiver server.");

		//Delete template and page in receiver
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(pageUrl1);
		browserPage.archiveElement(pageUrl1);
		browserPage.deletePage(pageUrl1);
		Assert.assertFalse(browserPage.doesElementExist(pageUrl1), "ERROR - Receiver Server: Page ('"+pageUrl1+"') should not exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(templateTitle1);
		Assert.assertFalse(templatesPage.doesTemplateExist(templateTitle1), "ERROR - Receiver Server: Template ('"+templateTitle1+"') should not exist at this moment in receiver server.");
		logoutReceiverServer();
	}

	/**
	 * Create a design template and push it to remote server
	 * http://qa.dotcms.com/index.php?/cases/view/556
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc556_AddNewDesignTemplateAndPush() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		ITemplatesPage templatesPage = portletMenu.getTemplatesPage();

		ITemplateAddOrEditDesignTemplatePage designTemplate = templatesPage.addDesignTemplate();
		designTemplate.setTemplateTitle(templateTitle2);
		designTemplate.setTheme(templateTheme2);
		designTemplate.addContainer(templateContainer2);

		templatesPage = designTemplate.saveAndPublish();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle2), "ERROR - Authoring Server: Template ('"+templateTitle2+"') should exist at this moment in authoring server.");

		templatesPage.pushTemplate(templateTitle2);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the template was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(templateTitle2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Template ('"+templateTitle2+"') push should not be in pending list.");

		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		templatesPage= portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle2), "ERROR - Receiver Server: Template ('"+templateTitle2+"') should exist at this moment in receiver server.");

		logoutReceiverServer();

		//Second part of the test push a template as a page dependency
		//Calling authoring Server
		portletMenu = callAuthoringServer();
		templatesPage = portletMenu.getTemplatesPage();

		designTemplate = templatesPage.addDesignTemplate();
		designTemplate.setTemplateTitle(templateTitle3);
		designTemplate.setTheme(templateTheme3);
		designTemplate.addContainer(templateContainer3);

		templatesPage = designTemplate.saveAndPublish();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle3), "ERROR - Authoring Server: Template ('"+templateTitle3+"') should exist at this moment in authoring server.");

		//create test page
		templatesPage.sleep(3);
		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		browserPage.createHTMLPage(pageTitle2, templateTitle3, pageUrl2);

		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();

		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl2), "ERROR - Authoring Server: Page ('"+pageUrl2+"') should exist at this moment in authoring server.");

		browserPage.pushElement(pageUrl2);
		publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		isPushed = publishingQueuePage.isObjectBundlePushed(pageTitle2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ('"+pageUrl2+"') push should not be in pending list.");

		//delete template and page
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(pageUrl2);
		browserPage.archiveElement(pageUrl2);
		browserPage.deletePage(pageUrl2);
		Assert.assertFalse(browserPage.doesElementExist(pageUrl2), "ERROR - Authoring Server: Page ('"+pageUrl2+"') should not exist at this moment in authoring server.");

		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(templateTitle3);
		Assert.assertFalse(templatesPage.doesTemplateExist(templateTitle3), "ERROR - Authoring Server: Template ('"+templateTitle3+"') should not exist at this moment in authoring server.");	
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		browserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl2), "ERROR - Receiver Server: Page ('"+pageUrl2+"') should exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle3), "ERROR - Receiver Server: Template ('"+templateTitle3+"') should exist at this moment in receiver server.");

		//Delete template and page in receiver
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(pageUrl2);
		browserPage.archiveElement(pageUrl2);
		browserPage.deletePage(pageUrl2);
		Assert.assertFalse(browserPage.doesElementExist(pageUrl2), "ERROR - Receiver Server: Page ('"+pageUrl2+"') should not exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(templateTitle3);
		Assert.assertFalse(templatesPage.doesTemplateExist(templateTitle3), "ERROR - Receiver Server: Template ('"+templateTitle3+"') should not exist at this moment in receiver server.");
		logoutReceiverServer();
	}

	/**
	 * Edit a design template and push it to remote server
	 * http://qa.dotcms.com/index.php?/cases/view/557
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc557_EditDesignTemplateAndPush() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		ITemplatesPage templatesPage = portletMenu.getTemplatesPage();

		//Add a new template
		templatesPage.editTemplate(templateTitle2);
		ITemplateAddOrEditDesignTemplatePage designTemplate = SeleniumPageManager.getBackEndPageManager().getPageObject(ITemplateAddOrEditDesignTemplatePage.class);
		designTemplate.addContainer(templateContainer22);
		List<Map<String,String>> authoringContainers = designTemplate.getTemplateContainers();
		templatesPage = designTemplate.saveAndPublish();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle2), "ERROR - Authoring Server: Template ('"+templateTitle2+"') should exist at this moment in authoring server.");

		templatesPage.pushTemplate(templateTitle2);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the template was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(templateTitle2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Template ('"+templateTitle2+"') push should not be in pending list.");
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(3);
		templatesPage= portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle2), "ERROR - Receiver Server: Template ('"+templateTitle2+"') should exist at this moment in receiver server.");

		templatesPage.editTemplate(templateTitle2);
		designTemplate = SeleniumPageManager.getBackEndPageManager().getPageObject(ITemplateAddOrEditDesignTemplatePage.class);
		List<Map<String,String>> containers = designTemplate.getTemplateContainers();
		designTemplate.cancel();
		Assert.assertTrue(containers.size()==authoringContainers.size(), "ERROR - Receiver Server: Template ('"+templateTitle2+"') doesn't have the same number of containers as the authoring server in receiver server.");
		for(Map<String,String> map : authoringContainers){
			boolean containerFound = false;
			for(Map<String,String> map2 : containers){
				if(map.get("name").equals(map2.get("name"))){
					containerFound=true;
					break;
				}
			}
			Assert.assertTrue(containerFound, "ERROR - Receiver Server: Template ('"+templateTitle2+"') doesn't have this container ("+map.get("name")+") in receiver server.");
		}
		logoutReceiverServer();
	}

	/**
	 * Add a design template, include a page using the template and push the folder
	 * where the page was added to remote server
	 * http://qa.dotcms.com/index.php?/cases/view/558
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc558_AddDesignTemplateAndPushFolder() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		ITemplatesPage templatesPage = portletMenu.getTemplatesPage();

		ITemplateAddOrEditDesignTemplatePage designTemplate = templatesPage.addDesignTemplate();
		designTemplate.setTemplateTitle(templateTitle4);
		designTemplate.setTheme(templateTheme4);
		designTemplate.addContainer(templateContainer4);

		templatesPage = designTemplate.saveAndPublish();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle4), "ERROR - Authoring Server: Template ('"+templateTitle4+"') should exist at this moment in authoring server.");

		//create test page
		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		browserPage.createFolder(null, folderName1);
		browserPage.selectFolder(folderName1);
		browserPage.createHTMLPage(pageTitle3, templateTitle4, pageUrl3);

		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();

		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl3), "ERROR - Authoring Server: Page ('"+pageUrl3+"') should exist at this moment in authoring server.");

		browserPage.pushFolder(folderName1);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the folder was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(folderName1,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Folder ('"+folderName1+"') push should not be in pending list.");

		//delete template and page
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(pageUrl3);
		browserPage.archiveElement(pageUrl3);
		browserPage.deletePage(pageUrl3);
		Assert.assertFalse(browserPage.doesElementExist(pageUrl3), "ERROR - Authoring Server: Page ('"+pageUrl3+"') should not exist at this moment in authoring server.");
		browserPage.deleteFolder(folderName1);
		Assert.assertFalse(browserPage.doesFolderExist(folderName1), "ERROR - Authoring Server: Folder ('"+folderName1+"') should not exist at this moment in authoring server.");

		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(templateTitle4);
		Assert.assertFalse(templatesPage.doesTemplateExist(templateTitle4), "ERROR - Authoring Server: Template ('"+templateTitle4+"') should not exist at this moment in authoring server.");	
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		browserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesFolderExist(folderName1), "ERROR - Receiver Server: Folder ('"+folderName1+"') should exist at this moment in receiver server.");
		browserPage.selectFolder(folderName1);
		Assert.assertTrue(browserPage.doesElementExist(pageUrl3), "ERROR - Receiver Server: Page ('"+pageUrl3+"') should exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle4), "ERROR - Receiver Server: Template ('"+templateTitle4+"') should exist at this moment in receiver server.");

		//Delete template and page in receiver
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.selectFolder(folderName1);
		browserPage.unPublishElement(pageUrl3);
		browserPage.archiveElement(pageUrl3);
		browserPage.deletePage(pageUrl3);
		Assert.assertFalse(browserPage.doesElementExist(pageUrl3), "ERROR - Receiver Server: Page ('"+pageUrl3+"') should not exist at this moment in receiver server.");

		browserPage.deleteFolder(folderName1);
		Assert.assertFalse(browserPage.doesFolderExist(folderName1), "ERROR - Receiver Server: Folder ('"+folderName1+"') should not exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(templateTitle4);
		Assert.assertFalse(templatesPage.doesTemplateExist(templateTitle4), "ERROR - Receiver Server: Template ('"+templateTitle4+"') should not exist at this moment in receiver server.");
		logoutReceiverServer();
	}

	/**
	 * Create an advance template unpublished and push it to remote server
	 * http://qa.dotcms.com/index.php?/cases/view/523
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc523_AddNewAdvanceTemplateAndPush() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		ITemplatesPage templatesPage = portletMenu.getTemplatesPage();

		//create advance template
		ITemplateAddOrEditAdvanceTemplatePage advanceTemplate = templatesPage.addAdvanceTemplate();
		Map<String,String> template = new HashMap<String, String>();
		template.put("titleField",templateTitle5);
		template.put("friendlyNameField", templateTitle5);
		template.put("AddContainers", templateContainer5);

		advanceTemplate.setTemplateFields(template);
		templatesPage= advanceTemplate.save();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle5), "ERROR - Authoring Server: Template ('"+templateTitle5+"') should exist at this moment in authoring server.");
		templatesPage.sleep(3);

		templatesPage.pushTemplate(templateTitle5);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the template was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(templateTitle5,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Template ('"+templateTitle5+"') push should not be in pending list.");
		publishingQueuePage.sleep(3);

		//create design template
		templatesPage = portletMenu.getTemplatesPage();
		ITemplateAddOrEditDesignTemplatePage designTemplate = templatesPage.addDesignTemplate();
		designTemplate.setTemplateTitle(templateTitle6);
		designTemplate.setTheme(templateTheme6);
		designTemplate.addContainer(templateContainer6);
		templatesPage = designTemplate.save();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle6), "ERROR - Authoring Server: Template ('"+templateTitle6+"') should exist at this moment in authoring server.");

		templatesPage.pushTemplate(templateTitle6);
		publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the template was pushed
		isPushed = publishingQueuePage.isObjectBundlePushed(templateTitle6,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Template ('"+templateTitle6+"') push should not be in pending list.");

		//delete template and page
		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(templateTitle5);
		Assert.assertFalse(templatesPage.doesTemplateExist(templateTitle5), "ERROR - Authoring Server: Template ('"+templateTitle5+"') should not exist at this moment in authoring server.");	

		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(templateTitle6);
		Assert.assertFalse(templatesPage.doesTemplateExist(templateTitle6), "ERROR - Authoring Server: Template ('"+templateTitle6+"') should not exist at this moment in authoring server.");	
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);

		templatesPage = portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle5), "ERROR - Receiver Server: Template ('"+templateTitle5+"') should exist at this moment in receiver server.");
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle6), "ERROR - Receiver Server: Template ('"+templateTitle6+"') should exist at this moment in receiver server.");

		//Delete template and page in receiver
		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.sleep(3);
		templatesPage.deleteTemplate(templateTitle5);
		Assert.assertFalse(templatesPage.doesTemplateExist(templateTitle5), "ERROR - Receiver Server: Template ('"+templateTitle5+"') should not exist at this moment in receiver server.");

		templatesPage.deleteTemplate(templateTitle6);
		Assert.assertFalse(templatesPage.doesTemplateExist(templateTitle6), "ERROR - Receiver Server: Template ('"+templateTitle6+"') should not exist at this moment in receiver server.");
		logoutReceiverServer();
	}

	/**
	 * Create an advance template and push it as a limited user to remote server
	 * http://qa.dotcms.com/index.php?/cases/view/568
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc568_PushAdvanceTemplateAsLimitedUser() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		ITemplatesPage templatesPage = portletMenu.getTemplatesPage();

		ITemplateAddOrEditAdvanceTemplatePage advanceTemplate = templatesPage.addAdvanceTemplate();
		Map<String,String> template = new HashMap<String, String>();
		template.put("titleField",templateTitle7);
		template.put("friendlyNameField", templateTitle7);
		template.put("AddContainers", templateContainer7);

		advanceTemplate.setTemplateFields(template);
		templatesPage= advanceTemplate.saveAndPublish();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle7), "ERROR - Authoring Server: Template ('"+templateTitle7+"') should exist at this moment in authoring server.");

		//create test page
		templatesPage.sleep(3);
		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		browserPage.createHTMLPage(pageTitle4, templateTitle7, pageUrl4);
		browserPage.sleep(2);
		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();
		logoutAuthoringServer();

		//login as limited user
		portletMenu =callAuthoringServer(limitedUserEmail, limitedUserPaswword);
		portletMenu.sleep(4);
		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl4), "ERROR - Authoring Server: Page ('"+pageUrl4+"') should exist at this moment in authoring server.");

		browserPage.pushElement(pageUrl4);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(pageTitle4,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ('"+pageUrl4+"') push should not be in pending list.");
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl4), "ERROR - Receiver Server: Page ('"+pageUrl4+"') should exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle7), "ERROR - Receiver Server: Template ('"+templateTitle7+"') should exist at this moment in receiver server.");
		logoutReceiverServer();
	}

	/**
	 * Edit an advance template and push it as a limited user to remote server
	 * http://qa.dotcms.com/index.php?/cases/view/570
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc570_EditAdvanceTemplateAndPushAsLimitedUser() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer(limitedUserEmail, limitedUserPaswword);
		ITemplatesPage templatesPage = portletMenu.getTemplatesPage();

		templatesPage.editTemplate(templateTitle7);
		ITemplateAddOrEditAdvanceTemplatePage advanceTemplate=  SeleniumPageManager.getBackEndPageManager().getPageObject(ITemplateAddOrEditAdvanceTemplatePage.class);
		Map<String,String> template = new HashMap<String, String>();
		template.put("titleField",templateTitle7);
		template.put("friendlyNameField", templateTitle7);
		template.put("AddContainers", templateContainer72);
		advanceTemplate.setTemplateFields(template);

		String originalTemplateBody = advanceTemplate.getFieldValue("ace");
		templatesPage=advanceTemplate.saveAndPublish();
		templatesPage.sleep(2);
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle7), "ERROR - Authoring Server: Template ('"+templateTitle7+"') should exist at this moment in authoring server.");

		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl4), "ERROR - Authoring Server: Page ('"+pageUrl4+"') should exist at this moment in authoring server.");

		browserPage.pushElement(pageUrl4);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(pageTitle1,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ('"+pageUrl4+"') push should not be in pending list.");

		//delete test 
		browserPage = portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(pageUrl4);
		browserPage.archiveElement(pageUrl4);
		browserPage.deletePage(pageUrl4);
		Assert.assertFalse(browserPage.doesElementExist(pageUrl4), "ERROR - Authoring Server: Page ('"+pageUrl4+"') should not exist at this moment in authoring server.");


		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.sleep(3);
		templatesPage.deleteTemplate(templateTitle7);
		Assert.assertFalse(templatesPage.doesTemplateExist(templateTitle7), "ERROR - Authoring Server: Template ('"+templateTitle7+"') should not exist at this moment in authoring server.");
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl4), "ERROR - Receiver Server: Page ('"+pageUrl4+"') should exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle7), "ERROR - Receiver Server: Template ('"+templateTitle7+"') should exist at this moment in receiver server.");
		templatesPage.editTemplate(templateTitle7);
		advanceTemplate=  SeleniumPageManager.getBackEndPageManager().getPageObject(ITemplateAddOrEditAdvanceTemplatePage.class);
		String receiverTemplateBody = advanceTemplate.getFieldValue("ace");
		advanceTemplate.cancel();
		Assert.assertTrue(originalTemplateBody.equals(receiverTemplateBody), "ERROR - Receiver Server: Template ('"+templateTitle7+"') body is not the same in both authoring and receiver server.");

		//delete test 
		browserPage = portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(pageUrl4);
		browserPage.archiveElement(pageUrl4);
		browserPage.deletePage(pageUrl4);
		Assert.assertFalse(browserPage.doesElementExist(pageUrl4), "ERROR - Receiver Server: Page ('"+pageUrl4+"') should not exist at this moment in receiver server.");


		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.sleep(3);
		templatesPage.deleteTemplate(templateTitle7);
		Assert.assertFalse(templatesPage.doesTemplateExist(templateTitle7), "ERROR - Receiver Server: Template ('"+templateTitle7+"') should not exist at this moment in receiver server.");


		logoutReceiverServer();
	}

	/**
	 * HTMLPAGE TESTS
	 */
	/**
	 * Push a template and validate that templates, containers dependencies were send to remote server
	 * http://qa.dotcms.com/index.php?/cases/view/507
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc507_PusHPageAndValidateDependencies() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		IContainersPage containersPage = portletMenu.getContainersPage();
		IContainerAddOrEditPage addContainerPage = containersPage.getAddContainerPage();

		//simple container
		Map<String, String> container = new HashMap<String,String>();
		container.put("titleField", containerTitle4);
		container.put("friendlyNameField", containerTitle4);
		container.put("ace", containerCode4);
		//create test container to push
		addContainerPage.setFields(container);
		containersPage = addContainerPage.saveAndPublish();
		Assert.assertTrue(containersPage.existContainer(containerTitle4), "ERROR - Container ('"+containerTitle4+"')  should exist at this moment in authoring server.");

		ITemplatesPage templatesPage = portletMenu.getTemplatesPage();
		ITemplateAddOrEditAdvanceTemplatePage advanceTemplate = templatesPage.addAdvanceTemplate();
		Map<String,String> template = new HashMap<String, String>();
		template.put("titleField",templateTitle8);
		template.put("friendlyNameField", templateTitle8);
		template.put("AddContainers", containerTitle4);

		advanceTemplate.setTemplateFields(template);
		templatesPage= advanceTemplate.saveAndPublish();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle8), "ERROR - Authoring Server: Template ('"+templateTitle8+"') should exist at this moment in authoring server.");

		//create test page
		templatesPage.sleep(3);
		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		browserPage.createHTMLPage(pageTitle5, templateTitle8, pageUrl5);

		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();

		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl5), "ERROR - Authoring Server: Page ('"+pageUrl5+"') should exist at this moment in authoring server.");

		browserPage.pushElement(pageUrl5);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(pageTitle5,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ('"+pageUrl5+"') push should not be in pending list.");

		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl5), "ERROR - Receiver Server: Page ('"+pageUrl5+"') should exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle8), "ERROR - Receiver Server: Template ('"+templateTitle8+"') should exist at this moment in receiver server.");

		containersPage = portletMenu.getContainersPage();
		Assert.assertTrue(containersPage.existContainer(containerTitle4), "ERROR - Authoring Server: Container ('"+containerTitle4+"') should not exist at this moment in authoring server.");

		logoutReceiverServer();
	}

	/**
	 * Edit a page and push to remote server
	 * http://qa.dotcms.com/index.php?/cases/view/577
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc577_UpdatePageAndPush() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		browserPage.editHTMLPageProperties(pageUrl5);
		IHTMLPageAddOrEdit_ContentPage htmlAddPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IHTMLPageAddOrEdit_ContentPage.class);
		if(htmlAddPage.isLocked()){
			htmlAddPage.unlock();
		}
		htmlAddPage.setTitle(pageTitle52);
		htmlAddPage.saveAndPublish();

		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();

		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.pushElement(pageUrl5);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(pageTitle52,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ('"+pageUrl5+"') push should not be in pending list.");

		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl5), "ERROR - Receiver Server: Page ('"+pageUrl5+"') should exist at this moment in receiver server.");

		browserPage.editHTMLPageProperties(pageUrl5);
		htmlAddPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IHTMLPageAddOrEdit_ContentPage.class);
		String title = htmlAddPage.getPageTitle();
		htmlAddPage.cancel();

		Assert.assertTrue(title.equals(pageTitle52), "ERROR - Receiver Server: Page ('"+pageUrl5+"') title doesn't match between authoring and receiver servers.");		
		logoutReceiverServer();
	}

	/**
	 * Test pushing/updating pages as a limited user
	 * http://qa.dotcms.com/index.php?/cases/view/578
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc578_UpdatePageAndPushAsLimitedUser() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer(limitedUserEmail,limitedUserPaswword);
		portletMenu.sleep(2);
		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		browserPage.editHTMLPageProperties(pageUrl5);
		IHTMLPageAddOrEdit_ContentPage htmlAddPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IHTMLPageAddOrEdit_ContentPage.class);
		if(htmlAddPage.isLocked()){
			htmlAddPage.unlock();
		}
		htmlAddPage.setTitle(pageTitle53);
		htmlAddPage.saveAndPublish();

		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();

		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl5), "ERROR - Authoring Server: Page ('"+pageUrl5+"') should exist at this moment in authoring server.");

		browserPage.pushElement(pageUrl5);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(pageTitle53,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ('"+pageUrl5+"') push should not be in pending list.");
		publishingQueuePage.sleep(2);
		//Delete template and page in receiver
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(pageUrl5);
		browserPage.archiveElement(pageUrl5);
		browserPage.deletePage(pageUrl5);
		Assert.assertFalse(browserPage.doesElementExist(pageUrl5), "ERROR - Authoring Server: Page ('"+pageUrl5+"') should not exist at this moment in authoring server.");

		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl5), "ERROR - Receiver Server: Page ('"+pageUrl5+"') should exist at this moment in receiver server.");

		browserPage.editHTMLPageProperties(pageUrl5);
		htmlAddPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IHTMLPageAddOrEdit_ContentPage.class);
		String title = htmlAddPage.getPageTitle();
		htmlAddPage.cancel();

		Assert.assertTrue(title.equals(pageTitle53), "ERROR - Receiver Server: Page ('"+pageUrl5+"') title doesn't match between authoring and receiver servers.");		

		//Delete template and page in receiver
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(pageUrl5);
		browserPage.archiveElement(pageUrl5);
		browserPage.deletePage(pageUrl5);
		Assert.assertFalse(browserPage.doesElementExist(pageUrl5), "ERROR - Receiver Server: Page ('"+pageUrl5+"') should not exist at this moment in receiver server.");
		logoutReceiverServer();
	}

	/**
	 * Test adding an HTML Page to Bundle on "Open Preview" view
	 * http://qa.dotcms.com/index.php?/cases/view/582
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc582_AddHTMLPageToBundle() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		portletMenu.sleep(2);
		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		browserPage.createHTMLPage(pageTitle6, templateTitle8, pageUrl6);

		IPreviewHTMLPage_Page pagePreview = SeleniumPageManager.getBackEndPageManager().getPageObject(IPreviewHTMLPage_Page.class);
		String bundleName = "test582";
		pagePreview.addToBundle(bundleName);

		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();

		//push container
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		publishingQueuePage.getBundlesTab();
		String authoringServerBundleId = publishingQueuePage.pushPublishBundle(bundleName);

		//wait until 5 minutes to check if the page was pushed
		boolean isPushed = publishingQueuePage.isBundlePushed(authoringServerBundleId,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ("+pageUrl6+") push should not be in pending list.");
		logoutAuthoringServer();


		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl6), "ERROR - Receiver Server: Page ('"+pageUrl6+"') should exist at this moment in receiver server.");

		//Delete template and page in receiver
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(pageUrl6);
		browserPage.archiveElement(pageUrl6);
		browserPage.deletePage(pageUrl6);
		Assert.assertFalse(browserPage.doesElementExist(pageUrl6), "ERROR - Receiver Server: Page ('"+pageUrl6+"') should not exist at this moment in receiver server.");
		logoutReceiverServer();
	}

	/**
	 * Test pushing an HTML Page with SAVED only content and Published content
	 * http://qa.dotcms.com/index.php?/cases/view/625
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc625_PushHTMLPageWithSavedAndPublishContent() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		portletMenu.sleep(2);
		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		browserPage.createUnpublishHTMLPage(pageTitle7, template625, pageUrl7);

		IPreviewHTMLPage_Page pagePreview = SeleniumPageManager.getBackEndPageManager().getPageObject(IPreviewHTMLPage_Page.class);
		try{
			if(pagePreview.isLocked()){
				pagePreview.unLockPage();
			}
		}catch(Exception e){

		}
		pagePreview.selectEditModeView();

		String containerName="Default 1 (Page Content)";
		String content1="What We Do";
		String containerInode = pagePreview.getContainerInode(containerName);
		pagePreview.reuseContent(containerInode, content1,null);

		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();

		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.pushElement(pageUrl7);
		//push page
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(pageTitle7,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ("+pageUrl7+") push should not be in pending list.");
		logoutAuthoringServer();


		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl7), "ERROR - Receiver Server: Page ('"+pageUrl7+"') should exist at this moment in receiver server.");

		Assert.assertFalse(browserPage.isElementPublish(pageUrl7), "ERROR - Receiver Server: Page ('"+pageUrl7+"') should exist at this moment in receiver server.");

		Assert.assertTrue(browserPage.isElementUnpublish(pageUrl7), "ERROR - Receiver Server: Page ('"+pageUrl7+"') should exist at this moment in receiver server.");
		logoutReceiverServer();

		//connecting to authoring server
		portletMenu = callAuthoringServer();
		browserPage =portletMenu.getSiteBrowserPage();
		browserPage.publishElement(pageUrl7);

		browserPage.pushElement(pageUrl7);
		//push page
		publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the container was pushed
		isPushed = publishingQueuePage.isObjectBundlePushed(pageTitle7,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ("+pageUrl7+") push should not be in pending list.");

		//Delete template and page in authoring
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(pageUrl7);
		browserPage.archiveElement(pageUrl7);
		browserPage.deletePage(pageUrl7);
		Assert.assertFalse(browserPage.doesElementExist(pageUrl7), "ERROR - Authoring Server: Page ('"+pageUrl7+"') should not exist at this moment in authoring server.");
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(pageUrl7), "ERROR - Receiver Server: Page ('"+pageUrl7+"') should exist at this moment in receiver server.");

		Assert.assertFalse(browserPage.isElementUnpublish(pageUrl7), "ERROR - Receiver Server: Page ('"+pageUrl7+"') should exist at this moment in receiver server.");

		Assert.assertTrue(browserPage.isElementPublish(pageUrl7), "ERROR - Receiver Server: Page ('"+pageUrl7+"') should exist at this moment in receiver server.");

		//Delete template and page in receiver
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(pageUrl7);
		browserPage.archiveElement(pageUrl7);
		browserPage.deletePage(pageUrl7);
		Assert.assertFalse(browserPage.doesElementExist(pageUrl7), "ERROR - Receiver Server: Page ('"+pageUrl7+"') should not exist at this moment in receiver server.");
		logoutReceiverServer();
	}

	/**
	 * MENU LINKS TESTS
	 */

	/**
	 * Remote push of a menu link 
	 * http://qa.dotcms.com/index.php?/cases/view/524
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc524_RemotePushAMenuLink() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		portletMenu.sleep(2);
		IMenuLinkPage menuLinkPage= portletMenu.getMenuLinkPage();
		IMenuLinkAddOrEdit_Page editPage = menuLinkPage.addLink();
		editPage.setLinkTitle(linkTitle1); 
		editPage.setLinkFolder(linkFolder1);
		editPage.setLinkType(IMenuLinkAddOrEdit_Page.INTERNAL_LINK);
		editPage.setLinkTarget(IMenuLinkAddOrEdit_Page.SAME_TARGET);
		editPage.setLinkOrder(linkOrder1);
		editPage.setLinkShowOnMenu(linkShowOnMenu1);
		editPage.setLinkInternalCode(linkInternalHost1, linkInternalFolder1, linkInternalUrl1);
		editPage.saveAndPublish();
		menuLinkPage= portletMenu.getMenuLinkPage();
		Assert.assertTrue(menuLinkPage.doesLinkExist(linkTitle1), "ERROR - Authoring Server: Menu Link ('"+linkTitle1+"') should not exist at this moment in authoring server.");

		menuLinkPage.pushLink(linkTitle1);
		menuLinkPage.sleep(1);
		//push link
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the link was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(linkTitle1,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Menu link ("+linkTitle1+") push should not be in pending list.");
		logoutAuthoringServer();

		//Calling receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		menuLinkPage= portletMenu.getMenuLinkPage();

		Assert.assertTrue(menuLinkPage.doesLinkExist(linkTitle1), "ERROR - Receiver Server: Menu Link ('"+linkTitle1+"') should not exist at this moment in receiver server.");
		logoutReceiverServer();
	}

	/**
	 *Edit and push menu link to update menu link on remote server 
	 * http://qa.dotcms.com/index.php?/cases/view/574
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc574_EditAndPushAMenuLink() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		portletMenu.sleep(2);
		IMenuLinkPage menuLinkPage= portletMenu.getMenuLinkPage();
		IMenuLinkAddOrEdit_Page editPage = menuLinkPage.editLink(linkTitle1);
		editPage.setLinkTitle(linkTitle1); 
		editPage.setLinkType(IMenuLinkAddOrEdit_Page.EXTERNAL_LINK);
		editPage.setLinkTarget(IMenuLinkAddOrEdit_Page.NEW_TARGET);
		editPage.setLinkShowOnMenu(linkShowOnMenu1);
		editPage.setLinkExternalCode(IMenuLinkAddOrEdit_Page.HTTP_PROTOCOL, linkExternalUrl1);
		editPage.saveAndPublish();
		menuLinkPage= portletMenu.getMenuLinkPage();
		Assert.assertTrue(menuLinkPage.doesLinkExist(linkTitle1), "ERROR - Authoring Server: Menu Link ('"+linkTitle1+"') should not exist at this moment in authoring server.");

		menuLinkPage.pushLink(linkTitle1);
		//push link
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the link was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(linkTitle1,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Menu link ("+linkTitle1+") push should not be in pending list.");

		//delete Link
		portletMenu.sleep(2);
		menuLinkPage= portletMenu.getMenuLinkPage();
		menuLinkPage.deleteLink(linkTitle1);
		Assert.assertFalse(menuLinkPage.doesLinkExist(linkTitle1), "ERROR - Authoring Server: Menu Link ('"+linkTitle1+"') should not exist at this moment in authoring server.");

		logoutAuthoringServer();

		//Calling receiver server
		portletMenu = callReceiverServer();
		menuLinkPage= portletMenu.getMenuLinkPage();
		Assert.assertTrue(menuLinkPage.doesLinkExist(linkTitle1), "ERROR - Receiver Server: Menu Link ('"+linkTitle1+"') should not exist at this moment in receiver server.");

		editPage = menuLinkPage.editLink(linkTitle1);
		Assert.assertTrue(editPage.getLinkType().equals(IMenuLinkAddOrEdit_Page.EXTERNAL_LINK), "ERROR - Receiver Server: Menu Link ('"+linkTitle1+"') type doesn't match in authoring and receiver servers.");
		Assert.assertTrue(editPage.getLinkTarget().equals(IMenuLinkAddOrEdit_Page.NEW_TARGET), "ERROR - Receiver Server: Menu Link ('"+linkTitle1+"') target doesn't match in authoring and receiver servers.");
		Assert.assertTrue(editPage.getLinkExternalCode().equals(IMenuLinkAddOrEdit_Page.HTTP_PROTOCOL+linkExternalUrl1), "ERROR - Receiver Server: Menu Link ('"+linkTitle1+"') external link doesn't match in authoring and receiver servers.");
		editPage.cancel();

		//delete Link
		menuLinkPage= portletMenu.getMenuLinkPage();
		menuLinkPage.deleteLink(linkTitle1);
		Assert.assertFalse(menuLinkPage.doesLinkExist(linkTitle1), "ERROR - Receiver Server: Menu Link ('"+linkTitle3+"') should not exist at this moment in receiver server.");

		logoutReceiverServer();
	}

	/**
	 * Push menu link as limited user
	 * http://qa.dotcms.com/index.php?/cases/view/575
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc575_PushAMenuLinkAsLimitedUser() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer(limitedUserEmail,limitedUserPaswword);
		portletMenu.sleep(2);
		ISiteBrowserPage browserPage = portletMenu.getSiteBrowserPage();
		IMenuLinkAddOrEdit_Page editPage = browserPage.addMenuLinkInFolder(linkFolder2);
		editPage.setLinkTitle(linkTitle2); 
		editPage.setLinkType(IMenuLinkAddOrEdit_Page.CODE_LINK);
		editPage.setLinkOrder(linkOrder2);
		editPage.setLinkShowOnMenu(linkShowOnMenu2);
		editPage.setLinkCode(linkCode2);
		editPage.save();

		browserPage = portletMenu.getSiteBrowserPage();
		browserPage.selectFolder(linkFolder2);
		Assert.assertTrue(browserPage.doesElementExist(linkTitle2), "ERROR - Authoring Server: Menu Link ('"+linkTitle1+"') should not exist at this moment in authoring server.");

		browserPage.pushElement(linkTitle2);
		//push link
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the link was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(linkTitle2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Menu link ("+linkTitle2+") push should not be in pending list.");
		logoutAuthoringServer();

		//Calling receiver server
		portletMenu = callReceiverServer();
		IMenuLinkPage menuLinkPage= portletMenu.getMenuLinkPage();

		Assert.assertTrue(menuLinkPage.doesLinkExist(linkTitle2), "ERROR - Receiver Server: Menu Link ('"+linkTitle2+"') should not exist at this moment in receiver server.");
		logoutReceiverServer();
	}

	/**
	 * Edit existing menu link and push/update remote server as limited user
	 * http://qa.dotcms.com/index.php?/cases/view/576
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc576_EditMenuLinkAndPushAsLimitedUser() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer(limitedUserEmail,limitedUserPaswword);
		portletMenu.sleep(2);
		ISiteBrowserPage browserPage = portletMenu.getSiteBrowserPage();
		browserPage.selectFolder(linkFolder2);
		IMenuLinkAddOrEdit_Page editPage = browserPage.editMenuLink(linkTitle2);
		editPage.setLinkShowOnMenu(linkShowOnMenu22);
		editPage.setLinkCode(linkCode22);
		editPage.save();
		portletMenu.sleep(2);
		browserPage = portletMenu.getSiteBrowserPage();
		browserPage.selectFolder(linkFolder2);
		Assert.assertTrue(browserPage.doesElementExist(linkTitle2), "ERROR - Authoring Server: Menu Link ('"+linkTitle1+"') should not exist at this moment in authoring server.");

		browserPage.pushElement(linkTitle2);
		//push link
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the link was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(linkTitle2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Menu link ("+linkTitle2+") push should not be in pending list.");
		logoutAuthoringServer();

		//call Authoring server
		portletMenu = callAuthoringServer();
		IMenuLinkPage menuLinkPage = portletMenu.getMenuLinkPage();

		//delete link
		menuLinkPage.deleteLink(linkTitle2);
		Assert.assertFalse(menuLinkPage.doesLinkExist(linkTitle2), "ERROR - Authoring Server: Menu Link ('"+linkTitle2+"') should not exist at this moment in authorin server.");
		logoutAuthoringServer();		

		//Calling receiver server
		portletMenu = callReceiverServer();
		menuLinkPage= portletMenu.getMenuLinkPage();

		Assert.assertTrue(menuLinkPage.doesLinkExist(linkTitle2), "ERROR - Receiver Server: Menu Link ('"+linkTitle2+"') should exist at this moment in receiver server.");

		editPage = menuLinkPage.editLink(linkTitle2);
		Assert.assertTrue(editPage.getLinkType().equals(IMenuLinkAddOrEdit_Page.CODE_LINK), "ERROR - Receiver Server: Menu Link ('"+linkTitle2+"') type doesn't match in authoring and receiver servers.");
		Assert.assertTrue(editPage.getLinkCode().equals(linkCode22), "ERROR - Receiver Server: Menu Link ('"+linkTitle2+"') external link doesn't match in authoring and receiver servers.");
		editPage.cancel();

		//delete link
		menuLinkPage= portletMenu.getMenuLinkPage();
		menuLinkPage.deleteLink(linkTitle2);
		Assert.assertFalse(menuLinkPage.doesLinkExist(linkTitle2), "ERROR - Receiver Server: Menu Link ('"+linkTitle2+"') should not exist at this moment in receiver server.");

		logoutReceiverServer();
	}

	/**
	 * Add a menu link to a bundle and push
	 * http://qa.dotcms.com/index.php?/cases/view/589
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc589_PushAMenuLinkInBundle() throws Exception {
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		portletMenu.sleep(2);
		IMenuLinkPage menuLinkPage = portletMenu.getMenuLinkPage();
		IMenuLinkAddOrEdit_Page editPage = menuLinkPage.addLink();
		editPage.setLinkTitle(linkTitle3); 
		editPage.setLinkFolder(linkFolder3);
		editPage.setLinkType(IMenuLinkAddOrEdit_Page.INTERNAL_LINK);
		editPage.setLinkTarget(IMenuLinkAddOrEdit_Page.SAME_TARGET);
		editPage.setLinkOrder(linkOrder3);
		editPage.setLinkShowOnMenu(linkShowOnMenu3);
		editPage.setLinkInternalCode(linkInternalHost3, linkInternalFolder3, linkInternalUrl3);
		editPage.saveAndPublish();
		portletMenu.sleep(2);
		menuLinkPage = portletMenu.getMenuLinkPage();
		String bundleName = "test589";
		menuLinkPage.addToBundle(linkTitle3,bundleName);

		editPage = menuLinkPage.addLink();
		editPage.setLinkTitle(linkTitle4); 
		editPage.setLinkFolder(linkFolder4);
		editPage.setLinkType(IMenuLinkAddOrEdit_Page.CODE_LINK);
		editPage.setLinkOrder(linkOrder4);
		editPage.setLinkShowOnMenu(linkShowOnMenu4);
		editPage.setLinkCode(linkCode4);
		editPage.saveAndPublish();

		menuLinkPage = portletMenu.getMenuLinkPage();
		menuLinkPage.addToBundle(linkTitle4, bundleName);

		//push bundle
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		publishingQueuePage.getBundlesTab();
		String authoringServerBundleId = publishingQueuePage.pushPublishBundle(bundleName);

		//wait until 5 minutes to check if the bundle was pushed
		boolean isPushed = publishingQueuePage.isBundlePushed(authoringServerBundleId,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Menu Link push should not be in pending list.");

		//delete link
		menuLinkPage= portletMenu.getMenuLinkPage();
		menuLinkPage.deleteLink(linkTitle3);
		Assert.assertFalse(menuLinkPage.doesLinkExist(linkTitle3), "ERROR - Receiver Server: Menu Link ('"+linkTitle3+"') should not exist at this moment in receiver server.");
		menuLinkPage.deleteLink(linkTitle4);
		Assert.assertFalse(menuLinkPage.doesLinkExist(linkTitle4), "ERROR - Receiver Server: Menu Link ('"+linkTitle4+"') should not exist at this moment in receiver server.");

		logoutAuthoringServer();

		//Calling receiver server
		portletMenu = callReceiverServer();
		menuLinkPage= portletMenu.getMenuLinkPage();

		Assert.assertTrue(menuLinkPage.doesLinkExist(linkTitle3), "ERROR - Receiver Server: Menu Link ('"+linkTitle3+"') should exist at this moment in receiver server.");
		Assert.assertTrue(menuLinkPage.doesLinkExist(linkTitle4), "ERROR - Receiver Server: Menu Link ('"+linkTitle4+"') should  exist at this moment in receiver server.");

		//delete Link
		menuLinkPage.deleteLink(linkTitle3);
		Assert.assertFalse(menuLinkPage.doesLinkExist(linkTitle3), "ERROR - Receiver Server: Menu Link ('"+linkTitle3+"') should not exist at this moment in receiver server.");
		menuLinkPage.deleteLink(linkTitle4);
		Assert.assertFalse(menuLinkPage.doesLinkExist(linkTitle4), "ERROR - Receiver Server: Menu Link ('"+linkTitle4+"') should not exist at this moment in receiver server.");

		logoutReceiverServer();
	}

	/*
	 * Workflow Tests
	 */

	/**
	 * Add New Workflow, assign to structure, and push structure 
	 * http://qa.dotcms.com/index.php?/cases/view/652
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc652_AddWorkflowAssignToStructureAndPush() throws Exception{
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		portletMenu.sleep(3);
		//create a workflow
		IWorkflowSchemesPage schemesPage = portletMenu.getWorkflowSchemesPage();
		IWorkflowSchemeAddOrEditPage addSchemePage = schemesPage.getAddSchemePage();
		addSchemePage.setName(workflowSchemeName1);
		addSchemePage.sleep(2);
		addSchemePage.setDescription(workflowSchemeName1);
		addSchemePage.save();

		//add actions
		schemesPage = portletMenu.getWorkflowSchemesPage();
		IWorkFlowStepsAddOrEdit_Page schemeStepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName1);
		schemeStepsPage.addWorkflowStep(workflowSchemeStep1);
		IWorkflowActionAddOrEdit_Page actionPage = schemeStepsPage.addActionToStep(workflowSchemeStep1);
		actionPage.setActionName(workflowActionName1);
		actionPage.setSaveContent(true);
		actionPage.setWhoCanUse("Admin User");
		actionPage.setWhoCanUse(limitedRole);
		actionPage.setAllowComment(true);
		actionPage.setUserCanAssign(true);
		actionPage.setAssignTo("Admin User");
		actionPage.sleep(2);
		actionPage.save();
		//adding subaction
		actionPage.addSubAction(worflowSubaction1);
		actionPage.save();

		//create structure 
		IStructuresPage structurePage = portletMenu.getStructuresPage();
		IStructureAddOrEdit_PropertiesPage addStructurePage = structurePage.getAddNewStructurePage();
		IStructureAddOrEdit_FieldsPage fieldsPage = addStructurePage.createNewStructure(contentStructureName9, "Content",contentStructureName9, demoServer,workflowSchemeName1);


		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName9Field1),"ERROR - The field ("+contentStructureName9Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(contentStructureName9Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName9Field1),"ERROR - The field ("+contentStructureName9Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName9Field2),"ERROR - The field ("+contentStructureName9Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(contentStructureName9Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName9Field2),"ERROR - The field ("+contentStructureName9Field2+") shoudl exist at this time");
		fieldsPage.sleep(3);

		//push Structure
		structurePage = portletMenu.getStructuresPage();
		structurePage.pushStructure(contentStructureName9);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(contentStructureName9,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Structure ("+contentStructureName9+") push should not be in pending list.");

		//delete structure and workflow
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(contentStructureName9, true);
		structurePage.sleep(2);
		Assert.assertFalse(structurePage.doesStructureExist(contentStructureName9), "ERROR - Structure ('"+contentStructureName9+"') should not exist in authoring server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName1);
		stepsPage.deleteStep(workflowSchemeStep1);
		WorkflowPageUtil.deleteWorkflow(workflowSchemeName1,serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertFalse(schemesPage.doesWorkflowSchemeExist(workflowSchemeName1), "ERROR - Workflow ('"+workflowSchemeName1+"') should not exist in authoring server");

		logoutAuthoringServer();

		//call Receiver
		portletMenu=callReceiverServer();
		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(contentStructureName9),  "ERROR - Structure ('"+contentStructureName9+"') should  exist in receiver server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertTrue(schemesPage.doesWorkflowSchemeExist(workflowSchemeName1), "ERROR - Workflow ('"+workflowSchemeName1+"') should not exist in receiver server");

		//delete structure and workflow
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(contentStructureName9, true);
		structurePage.sleep(2);
		Assert.assertFalse(structurePage.doesStructureExist(contentStructureName9), "ERROR - Structure ('"+contentStructureName9+"') should not exist in receiver server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		stepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName1);
		stepsPage.deleteStep(workflowSchemeStep1);
		WorkflowPageUtil.deleteWorkflow(workflowSchemeName1,serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
		stepsPage.sleep(2);
		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertFalse(schemesPage.doesWorkflowSchemeExist(workflowSchemeName1), "ERROR - Workflow ('"+workflowSchemeName1+"') should not exist in receiver server");

		logoutReceiverServer();
	}

	/**
	 * Add a new Workflow, assign to structure, and push a contentlet from that structure 
	 * http://qa.dotcms.com/index.php?/cases/view/653
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc653_AddWorkflowAssignToStructureAddContentAndPush() throws Exception{
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		portletMenu.sleep(3);
		//create a workflow
		IWorkflowSchemesPage schemesPage = portletMenu.getWorkflowSchemesPage();
		IWorkflowSchemeAddOrEditPage addSchemePage = schemesPage.getAddSchemePage();
		addSchemePage.setName(workflowSchemeName2);
		addSchemePage.sleep(2);
		addSchemePage.setDescription(workflowSchemeName2);
		addSchemePage.save();
		addSchemePage.sleep(2);
		//add actions
		schemesPage = portletMenu.getWorkflowSchemesPage();
		IWorkFlowStepsAddOrEdit_Page schemeStepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName2);
		schemeStepsPage.addWorkflowStep(workflowSchemeStep2);
		IWorkflowActionAddOrEdit_Page actionPage = schemeStepsPage.addActionToStep(workflowSchemeStep2);
		actionPage.setActionName(workflowActionName2);
		actionPage.setSaveContent(true);
		actionPage.setWhoCanUse("Admin User");
		actionPage.setWhoCanUse(limitedRole);
		actionPage.setAllowComment(false);
		actionPage.setUserCanAssign(true);
		actionPage.setAssignTo("Admin User");
		actionPage.sleep(2);
		actionPage.save();
		//adding subaction
		actionPage.addSubAction(worflowSubaction2);
		actionPage.save();

		schemesPage = portletMenu.getWorkflowSchemesPage();
		schemeStepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName2);
		schemeStepsPage.addWorkflowStep(workflowSchemeStep3);
		actionPage = schemeStepsPage.addActionToStep(workflowSchemeStep3);
		actionPage.setActionName(workflowActionName3);
		actionPage.setSaveContent(false);
		actionPage.setWhoCanUse("Admin User");
		actionPage.setWhoCanUse(limitedRole);
		actionPage.setAllowComment(false);
		actionPage.setUserCanAssign(true);
		actionPage.setAssignTo("Admin User");
		actionPage.sleep(2);
		actionPage.save();
		//adding subaction
		actionPage.addSubAction(worflowSubaction3);
		actionPage.save();

		//create structure 
		IStructuresPage structurePage = portletMenu.getStructuresPage();
		IStructureAddOrEdit_PropertiesPage addStructurePage = structurePage.getAddNewStructurePage();
		IStructureAddOrEdit_FieldsPage fieldsPage = addStructurePage.createNewStructure(contentStructureName10, "Content",contentStructureName10, demoServer,workflowSchemeName2);


		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName10Field1),"ERROR - The field ("+contentStructureName10Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(contentStructureName10Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName10Field1),"ERROR - The field ("+contentStructureName10Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName10Field2),"ERROR - The field ("+contentStructureName10Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(contentStructureName10Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName10Field2),"ERROR - The field ("+contentStructureName10Field2+") shoudl exist at this time");
		fieldsPage.sleep(3);

		//addContent
		IContentSearchPage searchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage contentPage = searchPage.addContent(contentStructureName10);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put("title", contentTitle10);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put("body", contentTextArea10);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();
		contentPage.sleep(2);
		searchPage = portletMenu.getContentSearchPage();

		//move content to workflow step
		contentPage = searchPage.editContent(contentTitle10, contentStructureName10);
		if(contentPage.isPresentContentLockButton()){
			contentPage.clickLockForEditingButton();
		}
		List<Map<String,String>> parameters = new ArrayList<Map<String,String>>();
		Map<String,String> paramsMap = new HashMap<String,String>();
		paramsMap.put("taskAssignmentAux", "Admin User");
		parameters.add(paramsMap);
		paramsMap = new HashMap<String,String>();
		paramsMap.put("clickButton", "Save");
		parameters.add(paramsMap);
		contentPage.selectWorkflowAction(workflowActionName2, parameters);

		//push content
		searchPage = portletMenu.getContentSearchPage();
		searchPage.pushContent(contentTitle10,contentStructureName10);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(contentTitle10,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+contentTitle10+") push should not be in pending list.");

		//delete structure and workflow
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(contentStructureName10, true);
		structurePage.sleep(2);
		Assert.assertFalse(structurePage.doesStructureExist(contentStructureName10), "ERROR - Structure ('"+contentStructureName10+"') should not exist in authoring server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName2);
		stepsPage.deleteStep(workflowSchemeStep3);
		stepsPage.deleteStep(workflowSchemeStep2);
		WorkflowPageUtil.deleteWorkflow(workflowSchemeName2,serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertFalse(schemesPage.doesWorkflowSchemeExist(workflowSchemeName2), "ERROR - Workflow ('"+workflowSchemeName2+"') should not exist in authoring server");

		logoutAuthoringServer();

		//call Receiver
		portletMenu=callReceiverServer();

		searchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(searchPage.doesContentExist(contentTitle10, contentStructureName10),  "ERROR - Content ('"+contentStructureName10+"') should  exist in receiver server");

		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(contentStructureName10),  "ERROR - Structure ('"+contentStructureName10+"') should  exist in receiver server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertTrue(schemesPage.doesWorkflowSchemeExist(workflowSchemeName2), "ERROR - Workflow ('"+workflowSchemeName2+"') should not exist in receiver server");

		IWorkflowTasksPage workflowTasksPage = portletMenu.getWorkflowTasksPage();
		Assert.assertTrue(workflowTasksPage.getWorflowTaskCurrentStep(contentTitle10, workflowSchemeName2).equals(workflowSchemeStep2),"ERROR - The workflow task ("+contentTitle10+") is not in the right step");

		//delete structure and workflow
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(contentStructureName10, true);
		structurePage.sleep(2);
		Assert.assertFalse(structurePage.doesStructureExist(contentStructureName10), "ERROR - Structure ('"+contentStructureName10+"') should not exist in receiver server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		stepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName2);
		stepsPage.deleteStep(workflowSchemeStep3);
		stepsPage.deleteStep(workflowSchemeStep2);
		WorkflowPageUtil.deleteWorkflow(workflowSchemeName2,serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
		stepsPage.sleep(2);
		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertFalse(schemesPage.doesWorkflowSchemeExist(workflowSchemeName2), "ERROR - Workflow ('"+workflowSchemeName2+"') should not exist in receiver server");

		logoutReceiverServer();
	}

	/**
	 * Content TESTS
	 */

	/**
	 * Unpublish some Content and then push it R 
	 * http://qa.dotcms.com/index.php?/cases/view/520
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc520_UnpublishContentAndPush() throws Exception{
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		portletMenu.sleep(2);
		IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage contentPage = contentSearchPage.addContent(contentStructureName1);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put("title", contentTitle1);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.WYSIWYG_FIELD);
		map.put("body", contentWYSIWYG1);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.save();
		contentPage.sleep(2);
		contentSearchPage = portletMenu.getContentSearchPage();
		//push content
		contentSearchPage.pushContent(contentTitle1,contentStructureName1);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(contentTitle1,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+contentTitle1+") push should not be in pending list.");

		logoutAuthoringServer();

		//calling receiver
		portletMenu = callReceiverServer();
		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(contentSearchPage.doesContentExist(contentTitle1, contentStructureName1), "ERROR - Receiver Server: Content ("+contentTitle1+") should exist at this moment in receiver server.");
		Assert.assertTrue(contentSearchPage.isUnpublish(contentTitle1, contentStructureName1), "ERROR - Receiver Server: Content ("+contentTitle1+") should be unpublished at this moment in receiver server.");
		Assert.assertFalse(contentSearchPage.isPublish(contentTitle1, contentStructureName1), "ERROR - Receiver Server: Content ("+contentTitle1+") should not be live at this moment in receiver server.");

		logoutReceiverServer();
	}

	/**
	 * Push a contentlet created from a New Structure then remote delete 
	 * http://qa.dotcms.com/index.php?/cases/view/496
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc496_PushContentAndPushToRemove() throws Exception{
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		portletMenu.sleep(3);

		//create structure
		IStructuresPage structurePage = portletMenu.getStructuresPage();
		IStructureAddOrEdit_PropertiesPage addStructurePage = structurePage.getAddNewStructurePage();
		IStructureAddOrEdit_FieldsPage fieldsPage = addStructurePage.createNewStructure(contentStructureName2, "Content",contentStructureName2, demoServer);

		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName2Field1),"ERROR - The field ("+contentStructureName2Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(contentStructureName2Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName2Field1),"ERROR - The field ("+contentStructureName2Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName2Field2),"ERROR - The field ("+contentStructureName2Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(contentStructureName2Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName2Field2),"ERROR - The field ("+contentStructureName2Field2+") shoudl exist at this time");
		fieldsPage.sleep(3);
		//create content
		IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage contentPage = contentSearchPage.addContent(contentStructureName2);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(contentStructureName2Field1.toLowerCase(), contentTitle2);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put(contentStructureName2Field2.toLowerCase(), contentTextArea2);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();

		contentPage.sleep(3);
		contentSearchPage = portletMenu.getContentSearchPage();
		//push content
		contentSearchPage.pushContent(contentTitle2,contentStructureName2);
		contentSearchPage.sleep(2);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(contentTitle2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+contentTitle2+") push should not be in pending list.");

		logoutAuthoringServer();

		//calling receiver
		portletMenu = callReceiverServer();
		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(contentStructureName2),"ERROR - Structure ('"+contentStructureName2+"') doesn't exist in receiver server");

		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(contentSearchPage.doesContentExist(contentTitle2, contentStructureName2), "ERROR - Receiver Server: Content ("+contentTitle2+") should exist at this moment in receiver server.");
		Assert.assertFalse(contentSearchPage.isUnpublish(contentTitle2, contentStructureName2), "ERROR - Receiver Server: Content ("+contentTitle2+") should not be unpublished at this moment in receiver server.");
		Assert.assertTrue(contentSearchPage.isPublish(contentTitle2, contentStructureName2), "ERROR - Receiver Server: Content ("+contentTitle2+") should be live at this moment in receiver server.");
		logoutReceiverServer();

		//calling authoring server
		portletMenu = callAuthoringServer();
		//push to remove content
		contentSearchPage = portletMenu.getContentSearchPage();
		contentSearchPage.pushContent(contentTitle2, contentStructureName2,WebKeys.PUSH_TO_REMOVE, null, null, null, null, false);

		publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		isPushed = publishingQueuePage.isObjectBundlePushed(contentTitle2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+contentTitle2+") push should not be in pending list.");

		//delete structure and content
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(contentStructureName2, true);
		logoutAuthoringServer();

		//calling receiver
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertFalse(contentSearchPage.doesContentExist(contentTitle2, contentStructureName2), "ERROR - Receiver Server: Content ("+contentTitle2+") should exist at this moment in receiver server.");		

		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(contentStructureName2),"ERROR - Structure ('"+contentStructureName2+"') doesn't exist in receiver server");

		//delete structure and content
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(contentStructureName2, true);

		logoutReceiverServer();
	}

	/**
	 * Push Multiple contentlets
	 * http://qa.dotcms.com/index.php?/cases/view/519
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc519_PushMultipleContentlets() throws Exception{
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		portletMenu.sleep(3);
		List<String> listOfContent = new ArrayList<String>();
		//create structure 1
		IStructuresPage structurePage = portletMenu.getStructuresPage();
		IStructureAddOrEdit_PropertiesPage addStructurePage = structurePage.getAddNewStructurePage();
		IStructureAddOrEdit_FieldsPage fieldsPage = addStructurePage.createNewStructure(contentStructureName3, "Content",contentStructureName3, demoServer);

		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName3Field1),"ERROR - The field ("+contentStructureName3Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(contentStructureName3Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName3Field1),"ERROR - The field ("+contentStructureName3Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName3Field2),"ERROR - The field ("+contentStructureName3Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(contentStructureName3Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName3Field2),"ERROR - The field ("+contentStructureName3Field2+") shoudl exist at this time");

		//create content 1
		IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage contentPage = contentSearchPage.addContent(contentStructureName3);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(contentStructureName3Field1.toLowerCase(), contentTitle3);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put(contentStructureName3Field2.toLowerCase(), contentTextArea3);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();
		contentPage.sleep(2);
		listOfContent.add(contentTitle3);


		//create structure 2
		structurePage = portletMenu.getStructuresPage();
		addStructurePage = structurePage.getAddNewStructurePage();
		fieldsPage = addStructurePage.createNewStructure(contentStructureName4, "Content",contentStructureName4, demoServer);

		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName4Field1),"ERROR - The field ("+contentStructureName4Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(contentStructureName4Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName4Field1),"ERROR - The field ("+contentStructureName4Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName4Field2),"ERROR - The field ("+contentStructureName4Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(contentStructureName4Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName4Field2),"ERROR - The field ("+contentStructureName4Field2+") shoudl exist at this time");

		//create content 2
		contentSearchPage = portletMenu.getContentSearchPage();
		contentPage = contentSearchPage.addContent(contentStructureName4);

		fields = new ArrayList<Map<String, Object>>();
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(contentStructureName4Field1.toLowerCase(), contentTitle4);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put(contentStructureName4Field2.toLowerCase(), contentTextArea4);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();
		contentPage.sleep(2);
		listOfContent.add(contentTitle4);
		//create structure 3
		structurePage = portletMenu.getStructuresPage();
		addStructurePage = structurePage.getAddNewStructurePage();
		fieldsPage = addStructurePage.createNewStructure(contentStructureName5, "Content",contentStructureName5, demoServer);

		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName5Field1),"ERROR - The field ("+contentStructureName5Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(contentStructureName5Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName5Field1),"ERROR - The field ("+contentStructureName5Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName5Field2),"ERROR - The field ("+contentStructureName5Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(contentStructureName5Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName5Field2),"ERROR - The field ("+contentStructureName5Field2+") shoudl exist at this time");

		//create content 3
		contentSearchPage = portletMenu.getContentSearchPage();
		contentPage = contentSearchPage.addContent(contentStructureName5);

		fields = new ArrayList<Map<String, Object>>();
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(contentStructureName5Field1.toLowerCase(), contentTitle5);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put(contentStructureName5Field2.toLowerCase(), contentTextArea5);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();
		contentPage.sleep(2);
		listOfContent.add(contentTitle5);

		//push contents
		contentSearchPage = portletMenu.getContentSearchPage();
		contentSearchPage.pushContentList(listOfContent,null,contentSearchFilterKey);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(contentTitle3,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+contentTitle3+","+contentTitle4+","+contentTitle5+") push should not be in pending list.");

		//delete content and structures
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(contentStructureName3, true);
		structurePage.deleteStructureAndContent(contentStructureName4, true);
		structurePage.deleteStructureAndContent(contentStructureName5, true);
		logoutAuthoringServer();

		//calling receiver
		portletMenu = callReceiverServer();
		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(contentStructureName3),"ERROR - Structure ('"+contentStructureName3+"') doesn't exist in receiver server");
		Assert.assertTrue(structurePage.doesStructureExist(contentStructureName4),"ERROR - Structure ('"+contentStructureName4+"') doesn't exist in receiver server");
		Assert.assertTrue(structurePage.doesStructureExist(contentStructureName5),"ERROR - Structure ('"+contentStructureName5+"') doesn't exist in receiver server");

		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(contentSearchPage.doesContentExist(contentTitle3, contentStructureName3), "ERROR - Receiver Server: Content ("+contentTitle3+") should exist at this moment in receiver server.");
		Assert.assertFalse(contentSearchPage.isUnpublish(contentTitle3, contentStructureName3), "ERROR - Receiver Server: Content ("+contentTitle3+") should not be unpublished at this moment in receiver server.");
		Assert.assertTrue(contentSearchPage.isPublish(contentTitle3, contentStructureName3), "ERROR - Receiver Server: Content ("+contentTitle3+") should be live at this moment in receiver server.");

		Assert.assertTrue(contentSearchPage.doesContentExist(contentTitle4, contentStructureName4), "ERROR - Receiver Server: Content ("+contentTitle4+") should exist at this moment in receiver server.");
		Assert.assertFalse(contentSearchPage.isUnpublish(contentTitle4, contentStructureName4), "ERROR - Receiver Server: Content ("+contentTitle4+") should not be unpublished at this moment in receiver server.");
		Assert.assertTrue(contentSearchPage.isPublish(contentTitle4, contentStructureName4), "ERROR - Receiver Server: Content ("+contentTitle4+") should be live at this moment in receiver server.");

		Assert.assertTrue(contentSearchPage.doesContentExist(contentTitle5, contentStructureName5), "ERROR - Receiver Server: Content ("+contentTitle5+") should exist at this moment in receiver server.");
		Assert.assertFalse(contentSearchPage.isUnpublish(contentTitle5, contentStructureName5), "ERROR - Receiver Server: Content ("+contentTitle5+") should not be unpublished at this moment in receiver server.");
		Assert.assertTrue(contentSearchPage.isPublish(contentTitle5, contentStructureName5), "ERROR - Receiver Server: Content ("+contentTitle5+") should be live at this moment in receiver server.");

		//delete content and structures
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(contentStructureName3, true);
		structurePage.deleteStructureAndContent(contentStructureName4, true);
		structurePage.deleteStructureAndContent(contentStructureName5, true);
		logoutReceiverServer();
	}

	/**
	 * Push special characters in Content 
	 * http://qa.dotcms.com/index.php?/cases/view/532
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc532_PushSpecialCharactersInContents() throws Exception{
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		portletMenu.sleep(3);

		//create structure 1
		IStructuresPage structurePage = portletMenu.getStructuresPage();
		IStructureAddOrEdit_PropertiesPage addStructurePage = structurePage.getAddNewStructurePage();
		IStructureAddOrEdit_FieldsPage fieldsPage = addStructurePage.createNewStructure(contentStructureName6, "Content",contentStructureName6, demoServer);

		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName6Field1),"ERROR - The field ("+contentStructureName6Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(contentStructureName6Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName6Field1),"ERROR - The field ("+contentStructureName6Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName6Field2),"ERROR - The field ("+contentStructureName6Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(contentStructureName6Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName6Field2),"ERROR - The field ("+contentStructureName6Field2+") shoudl exist at this time");
		fieldsPage.sleep(3);
		//create content 1
		IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage contentPage = contentSearchPage.addContent(contentStructureName6);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(contentStructureName6Field1.toLowerCase(), contentTitle6);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put(contentStructureName6Field2.toLowerCase(), contentTextArea6);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();
		contentPage.sleep(2);

		String structureKey="Test-532";
		//push contents
		contentSearchPage = portletMenu.getContentSearchPage();
		contentSearchPage.pushContent(contentTitle6,structureKey);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(contentTitle6,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+contentTitle6+") push should not be in pending list.");

		//delete content and structures
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(contentStructureName6, true);
		logoutAuthoringServer();

		//calling receiver
		portletMenu = callReceiverServer();
		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(contentStructureName6),"ERROR - Structure ('"+contentStructureName6+"') doesn't exist in receiver server");

		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(contentSearchPage.doesContentExist(contentTitle6, structureKey), "ERROR - Receiver Server: Content ("+contentTitle6+") should exist at this moment in receiver server.");

		//delete structure and contents
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(contentStructureName6, true);
	}

	/**
	 * Test Remote Publish, unpublish, and delete of content 
	 * http://qa.dotcms.com/index.php?/cases/view/528
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc528_PublishUnpublishAndDelete() throws Exception{
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		portletMenu.sleep(3);

		IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage contentPage = contentSearchPage.addContent(contentStructureName7);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(contentStructureName7Field1.toLowerCase(), contentTitle7);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.WYSIWYG_FIELD);
		map.put(contentStructureName7Field2.toLowerCase(), contentTextArea7);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();
		contentPage.sleep(3);
		//Push Content
		contentSearchPage = portletMenu.getContentSearchPage();
		contentSearchPage.pushContent(contentTitle7,contentStructureName7);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(contentTitle7,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+contentTitle7+") push should not be in pending list.");
		logoutAuthoringServer();

		//Calling receiver
		portletMenu=callReceiverServer();
		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(contentSearchPage.doesContentExist(contentTitle7, contentStructureName7),"ERROR - content ('"+contentTitle7+"') doesn't exist in receiver server");
		Assert.assertTrue(contentSearchPage.isPublish(contentTitle7, contentStructureName7),"ERROR - content ('"+contentTitle7+"') should be publish in receiver server");
		//unpublish content
		contentSearchPage.unpublish(contentTitle7, contentStructureName7);
		Assert.assertTrue(contentSearchPage.isUnpublish(contentTitle7, contentStructureName7),"ERROR - content ('"+contentTitle7+"') should be unpublish in receiver server");
		logoutReceiverServer();

		//calling authoring server
		portletMenu=callAuthoringServer();
		contentSearchPage = portletMenu.getContentSearchPage();
		//push to remove
		contentSearchPage.pushContent(contentTitle7, contentStructureName7, WebKeys.PUSH_TO_REMOVE, null, null, null, null, false);

		publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		isPushed = publishingQueuePage.isObjectBundlePushed(contentTitle7,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+contentTitle7+") push should not be in pending list.");

		//delete content
		contentSearchPage = portletMenu.getContentSearchPage();
		contentSearchPage.unpublish(contentTitle7, contentStructureName7);
		contentSearchPage.archive(contentTitle7, contentStructureName7);
		contentSearchPage.delete(contentTitle7, contentStructureName7);
		Assert.assertFalse(contentSearchPage.doesContentExist(contentTitle7, contentStructureName7),"ERROR - content ('"+contentTitle7+"') should not exist in authoring server");
		logoutAuthoringServer();

		//Calling receiver
		portletMenu=callReceiverServer();
		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertFalse(contentSearchPage.doesContentExist(contentTitle7, contentStructureName7),"ERROR - content ('"+contentTitle7+"') should not  exist in receiver server");
		logoutReceiverServer();

	}

	/**
	 * Push content created from a new structure as a limited user 
	 * http://qa.dotcms.com/index.php?/cases/view/572
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc572_PushContentAsLimitedUser() throws Exception{
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		portletMenu.sleep(3);

		//create structure
		IStructuresPage structurePage = portletMenu.getStructuresPage();
		IStructureAddOrEdit_PropertiesPage addStructurePage = structurePage.getAddNewStructurePage();
		IStructureAddOrEdit_FieldsPage fieldsPage = addStructurePage.createNewStructure(contentStructureName8, "Content",contentStructureName8, demoServer);

		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName8Field1),"ERROR - The field ("+contentStructureName8Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(contentStructureName8Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName8Field1),"ERROR - The field ("+contentStructureName8Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(contentStructureName8Field2),"ERROR - The field ("+contentStructureName8Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(contentStructureName8Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(contentStructureName8Field2),"ERROR - The field ("+contentStructureName8Field2+") shoudl exist at this time");
		fieldsPage.sleep(3);
		logoutAuthoringServer();

		//login as limited user
		portletMenu = callAuthoringServer(limitedUserEmail,limitedUserPaswword);
		portletMenu.sleep(3);
		IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage contentPage = contentSearchPage.addContent(contentStructureName8);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(contentStructureName8Field1.toLowerCase(), contentTitle8);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put(contentStructureName8Field2.toLowerCase(), contentTextArea8);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();
		contentPage.sleep(3);
		//Push Content
		contentSearchPage = portletMenu.getContentSearchPage();
		contentSearchPage.pushContent(contentTitle8,contentStructureName8);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(contentTitle8,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+contentTitle8+") push should not be in pending list.");
		logoutAuthoringServer();

		//Calling receiver
		portletMenu=callReceiverServer();
		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(contentSearchPage.doesContentExist(contentTitle8, contentStructureName8),"ERROR - content ('"+contentTitle8+"') doesn't exist in receiver server");
		Assert.assertTrue(contentSearchPage.isPublish(contentTitle8, contentStructureName8),"ERROR - content ('"+contentTitle8+"') should be publish in receiver server");

		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(contentStructureName8),"ERROR - Structure ('"+contentStructureName8+"') should exist in receiver server");

		//delete structure and content
		//structurePage.deleteStructureAndContent(contentStructureName8, true);
		//Assert.assertFalse(structurePage.doesStructureExist(contentStructureName8),"ERROR - Structure ('"+contentStructureName8+"') should not exist in receiver server");

		logoutReceiverServer();

		//calling authoring server
		//portletMenu = callAuthoringServer();
		//portletMenu.sleep(3);

		//delete structure
		//structurePage = portletMenu.getStructuresPage();
		//structurePage.deleteStructureAndContent(contentStructureName8, true);
		//Assert.assertFalse(structurePage.doesStructureExist(contentStructureName8),"ERROR - Structure ('"+contentStructureName8+"') should not exist in authoring server");

		//logoutAuthoringServer();
	}



	/**
	 * Edit existing content and push as limited user to update remote content 
	 * http://qa.dotcms.com/index.php?/cases/view/573
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc573_EditContentAndPushAsLimitedUser() throws Exception{
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer(limitedUserEmail,limitedUserPaswword);
		portletMenu.sleep(3);
		IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage contentPage = contentSearchPage.editContent(contentTitle8,contentStructureName8);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(contentStructureName8Field1.toLowerCase(), contentTitle8);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put(contentStructureName8Field2.toLowerCase(), contentTextArea82);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		if(contentPage.isPresentContentLockButton()){
			contentPage.clickLockForEditingButton();
		}
		contentPage.saveAndPublish();
		contentPage.sleep(3);
		//Push Content
		contentSearchPage = portletMenu.getContentSearchPage();
		contentSearchPage.pushContent(contentTitle8,contentStructureName8);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(contentTitle8,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+contentTitle8+") push should not be in pending list.");
		logoutAuthoringServer();

		//Calling receiver
		portletMenu=callReceiverServer();
		IStructuresPage structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(contentStructureName8),"ERROR - Structure ('"+contentStructureName8+"') should exist in receiver server");

		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(contentSearchPage.doesContentExist(contentTitle8, contentStructureName8),"ERROR - Content ('"+contentTitle8+"') should exist on receiver server");
		Assert.assertTrue(contentSearchPage.isPublish(contentTitle8, contentStructureName8),"ERROR - content ('"+contentTitle8+"') should be publish in receiver server");

		contentPage = contentSearchPage.editContent(contentTitle8,contentStructureName8);
		String text	= contentPage.getFieldValue(contentStructureName8Field2.toLowerCase());
		contentPage.cancel();
		Assert.assertTrue(text.equals(contentTextArea82),"ERROR - Content ('"+contentTitle8+"') are not the same in authoring and receiver servers");

		//delete structure and content
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(contentStructureName8, true);
		Assert.assertFalse(structurePage.doesStructureExist(contentStructureName8),"ERROR - Structure ('"+contentStructureName8+"') should not exist in receiver server");

		logoutReceiverServer();

		//calling authoring server
		portletMenu = callAuthoringServer();
		portletMenu.sleep(3);

		//delete structure
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(contentStructureName8, true);
		Assert.assertFalse(structurePage.doesStructureExist(contentStructureName8),"ERROR - Structure ('"+contentStructureName8+"') should not exist in authoring server");

		logoutAuthoringServer();
	}

	/**
	 * Push a folder containing Document type content that has a custom workflow 
	 * http://qa.dotcms.com/index.php?/cases/view/623
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc623_PushFolderContainingContenWithCustomWorkflow() throws Exception{
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();

		//create a workflow
		IWorkflowSchemesPage schemesPage = portletMenu.getWorkflowSchemesPage();
		IWorkflowSchemeAddOrEditPage addSchemePage = schemesPage.getAddSchemePage();
		addSchemePage.setName(workflowSchemeName3);
		addSchemePage.sleep(2);
		addSchemePage.setDescription(workflowSchemeName3);
		addSchemePage.save();
		addSchemePage.sleep(3);
		//add actions
		schemesPage = portletMenu.getWorkflowSchemesPage();
		IWorkFlowStepsAddOrEdit_Page schemeStepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName3);
		schemeStepsPage.addWorkflowStep(workflowSchemeStep4);
		IWorkflowActionAddOrEdit_Page actionPage = schemeStepsPage.addActionToStep(workflowSchemeStep4);
		actionPage.setActionName(workflowActionName4);
		actionPage.setSaveContent(true);
		actionPage.setWhoCanUse("Admin User");
		actionPage.setWhoCanUse(limitedRole);
		actionPage.setAllowComment(false);
		actionPage.setUserCanAssign(true);
		actionPage.setAssignTo("Admin User");
		actionPage.sleep(2);
		actionPage.save();
		//adding subaction
		actionPage.addSubAction(worflowSubaction4);
		actionPage.save();

		//create structure 
		IStructuresPage structurePage = portletMenu.getStructuresPage();
		IStructureAddOrEdit_PropertiesPage addStructurePage = structurePage.getAddNewStructurePage();
		IStructureAddOrEdit_FieldsPage fieldsPage = addStructurePage.createNewStructure(contentStructureName11, "File",contentStructureName11, demoServer,workflowSchemeName3);

		//create folder
		ISiteBrowserPage siteBrowserPage = portletMenu.getSiteBrowserPage();
		siteBrowserPage.createFolder(null, folderName2);
		siteBrowserPage.selectFolder(folderName2);
		IContentAddOrEdit_ContentPage contentPage = siteBrowserPage.addFileInFolder(folderName2, contentStructureName11);
		
		//Create content
		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(contentStructureName11Field1, contentTitle11);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.BINARY_FIELD);
		map.put(contentStructureName11Field2, contentTextArea11);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.saveAndPublish();
		contentPage.sleep(3);
		//push folder
		siteBrowserPage = portletMenu.getSiteBrowserPage();
		siteBrowserPage.pushFolder(folderName2);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(folderName2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Folder ("+folderName2+") push should not be in pending list.");

		//delete structure and workflow
		siteBrowserPage = portletMenu.getSiteBrowserPage();
		siteBrowserPage.deleteFolder(folderName2);
		Assert.assertFalse(siteBrowserPage.doesFolderExist(folderName2), "ERROR - Folder ('"+folderName2+"') should not exist in authoring server");

		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(contentStructureName11, true);
		structurePage.sleep(2);
		Assert.assertFalse(structurePage.doesStructureExist(contentStructureName11), "ERROR - Structure ('"+contentStructureName11+"') should not exist in authoring server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName3);
		stepsPage.deleteStep(workflowSchemeStep4);
		WorkflowPageUtil.deleteWorkflow(workflowSchemeName3,serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertFalse(schemesPage.doesWorkflowSchemeExist(workflowSchemeName3), "ERROR - Workflow ('"+workflowSchemeName3+"') should not exist in authoring server");
		
		logoutAuthoringServer();

		//Calling authoring Server
		portletMenu = callReceiverServer();
		
		siteBrowserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(siteBrowserPage.doesFolderExist(folderName2),  "ERROR - Folder ('"+folderName2+"') should  exist in receiver server");

		siteBrowserPage.selectFolder(folderName2);
		Assert.assertTrue(siteBrowserPage.doesElementExist(fileName11),  "ERROR - Content ('"+contentTitle11+"') should  exist in receiver server");

		contentPage = siteBrowserPage.editFile(fileName11);
		if(contentPage.isPresentContentLockButton()){
			contentPage.clickLockForEditingButton();
		}
		contentPage.save();
		
		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(contentStructureName11),  "ERROR - Structure ('"+contentStructureName11+"') should  exist in receiver server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertTrue(schemesPage.doesWorkflowSchemeExist(workflowSchemeName3), "ERROR - Workflow ('"+workflowSchemeName3+"') should not exist in receiver server");

		//delete structure and workflow
		siteBrowserPage = portletMenu.getSiteBrowserPage();
		siteBrowserPage.deleteFolder(folderName2);
		Assert.assertFalse(siteBrowserPage.doesFolderExist(folderName2), "ERROR - Folder ('"+folderName2+"') should not exist in receiver server");

		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(contentStructureName11, true);
		structurePage.sleep(2);
		Assert.assertFalse(structurePage.doesStructureExist(contentStructureName11), "ERROR - Structure ('"+contentStructureName11+"') should not exist in receiver server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		stepsPage = schemesPage.getEditSchemeStepsPage(workflowSchemeName3);
		stepsPage.deleteStep(workflowSchemeStep4);
		WorkflowPageUtil.deleteWorkflow(workflowSchemeName3,serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
		stepsPage.sleep(2);
		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertFalse(schemesPage.doesWorkflowSchemeExist(workflowSchemeName2), "ERROR - Workflow ('"+workflowSchemeName2+"') should not exist in receiver server");
		
		logoutReceiverServer();

	}
}