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
	private String limitedUserNameA="MyLimitedA";
	private String limitedUserLastNameA="UserA";
	private String limitedUserEmailA="limited.usera@dotcms.com";
	private String limitedUserPaswwordA="limited123A";

	private String limitedUserNameB="MyLimitedB";
	private String limitedUserLastNameB="UserB";
	private String limitedUserEmailB="limited.userb@dotcms.com";
	private String limitedUserPaswwordB="limited123B";

	//test 555
	private String test555templateTitle1="Test-555";
	private String test555templateContainer1="Default 1";
	private String test555pageTitle1="Test-555";
	private String test555pageUrl1="test-555.html";
	//test 556 and 557
	private String test556templateTitle2="Test-556";
	private String test556templateTheme2="quest";
	private String test556templateContainer2="Default 1";
	private String test556templateContainer22="Default 2";

	private String test556templateTitle3="Test-556-2";
	private String test556templateTheme3="quest";
	private String test556templateContainer3="Default 1";
	private String test556pageTitle2="Test 556";
	private String test556pageUrl2="test-556.html";
	//test 558
	private String test558folderName1="test558";
	private String test558templateTitle4="Test-558";
	private String test558templateTheme4="quest";
	private String test558templateContainer4="Default 1";
	private String test558pageTitle3="Test-558";
	private String test558pageUrl3="test-558.html";
	//test 523
	private String test523templateTitle5="Test-523A";
	private String test523templateContainer5="Default 1";
	private String test523templateTitle6="Test-523B";
	private String test523templateTheme6="quest";
	private String test523templateContainer6="Default 1";
	//test 568 and 570
	private String test568templateTitle7="Test-568";
	private String test568templateContainer7="Default 1";
	private String test568templateContainer72="Default 2";
	private String test568pageTitle4="Test-568";
	private String test568pageUrl4="test-568.html";
	//test 507, 577, 578 and 582
	private String test507containerTitle4="Test 507 Container";
	private String test507containerCode4 ="<h2>Test 507</h2><br/><p>This is a test for push publishing</p>";
	private String test507templateTitle8="Test-507";
	private String test507pageTitle5="Test-507";
	private String test507pageUrl5="test-507.html";
	//test 577
	private String test577pageTitle52="Test 507 and 577";
	//test 578
	private String test578pageTitle53 = "Test 507, 577 and 578";
	//test 582
	private String test582pageTitle6="Test-582";
	private String test582pageUrl6="test-582.html";
	//test 625
	private String test625pageTitle7="Test-625";
	private String test625pageUrl7="test-625.tml";
	private String test625template625="Quest - 1 Column (With Content Padding)";
	//test 524 and 574
	private String test524linkTitle1="Test-524";
	private String test524linkFolder1="home";
	private String test524linkInternalHost1="qademo.dotcms.com";
	private String test524linkInternalFolder1="about-us"; 
	private String test524linkInternalUrl1="what-we-do.html";
	private int test524linkOrder1=1;
	private boolean test524linkShowOnMenu1=true;
	//test 574
	private String test574linkExternalUrl1="www.dotcms.com";
	//test 575 and 576
	private String test575linkTitle2="Test-575";
	private String test575linkFolder2="services";
	private String test575linkCode2="<a href='http://www.google.com'>Google</a>";
	private int test575linkOrder2=1;
	private boolean test575linkShowOnMenu2=true;
	//test 576
	private String test576linkCode22="<a href='http://www.google.com'>Google</a><a href='http://www.dotcms.com'>DotCMS</a>";
	private boolean test576linkShowOnMenu22=false;
	//test 589
	private String test589linkTitle3="Test-589A";
	private String test589linkFolder3="services";
	private String test589linkInternalHost3="qademo.dotcms.com";
	private String test589linkInternalFolder3="about-us"; 
	private String test589linkInternalUrl3="what-we-do.html";
	private int test589linkOrder3=1;
	private boolean test589linkShowOnMenu3=true;

	private String test589linkTitle4="Test-589B";
	private String test589linkFolder4="services";
	private String test589linkCode4="<a href='http://www.google.com'>Google</a>";
	private int test589linkOrder4=1;
	private boolean test589linkShowOnMenu4=true;
	//test 520
	private String test520contentStructureName1="Content ";
	private String test520contentTitle1="Test-520";
	private String test520contentWYSIWYG1="Test 520";
	//test 496
	private String test496contentStructureName2="Test-496";
	private String test496contentStructureName2Field1="Headline";
	private String test496contentStructureName2Field2="Description";
	private String test496contentTitle2="Test-496";
	private String test496contentTextArea2="Test 496";
	//test 519
	private String test519contentStructureName3="Test-519-A";
	private String test519contentStructureName3Field1="Headline";
	private String test519contentStructureName3Field2="Description";
	private String test519contentTitle3="Test-519-A";
	private String test519contentTextArea3="Test 519A";
	private String test519contentStructureName4="Test-519-B";
	private String test519contentStructureName4Field1="Headline";
	private String test519contentStructureName4Field2="Description";
	private String test519contentTitle4="Test-519-B";
	private String test519contentTextArea4="Test 519B";
	private String test519contentStructureName5="Test-519-C";
	private String test519contentStructureName5Field1="Headline";
	private String test519contentStructureName5Field2="Description";
	private String test519contentTitle5="Test-519-C";
	private String test519contentTextArea5="Test 519C";
	private String test519contentSearchFilterKey="Test-519";
	//test 532
	private String test532contentStructureName6="Test-532-#!%&*";
	private String test532contentStructureName6Field1="Title";
	private String test532contentStructureName6Field2="Description";
	private String test532contentTitle6="Test-532";
	private String test532contentTextArea6="Test 532";
	//test 528
	private String test528contentStructureName7="Content";
	private String test528contentStructureName7Field1="Title";
	private String test528contentTitle7="Test-528";
	private String test528contentStructureName7Field2="Body";
	private String test528contentTextArea7="Test 528";
	//test 572 and 573
	private String test572contentStructureName8="Test-572";
	private String test572contentStructureName8Field1="Title";
	private String test572contentTitle8="Test-572";
	private String test572contentStructureName8Field2="Body";
	private String test572contentTextArea8="Test 572";
	//test 573
	private String test573contentTextArea82="Test 572 and 573";
	//test 652
	private String test652contentStructureName9="Test-652";
	private String test652contentStructureName9Field1="Title";
	private String test652contentStructureName9Field2="Body";
	private String test652workflowSchemeName1="Test-652";
	private String test652workflowSchemeStep1="Test652Assign";
	private String test652workflowActionName1="Assign";
	private String test652worflowSubaction1="Unlock content"; 

	//test 653
	private String test653contentStructureName10="Test-653";
	private String test653contentStructureName10Field1="Title";
	private String test653contentTitle10="Test-653";
	private String test653contentStructureName10Field2="Body";
	private String test653contentTextArea10="Test 653";
	private String test653workflowSchemeName2="Test-653";
	private String test653workflowSchemeStep2="Test653Assign";
	private String test653workflowActionName2="Assign";
	private String test653worflowSubaction2="Lock content"; 
	private String test653workflowSchemeStep3="Test653Review";
	private String test653workflowActionName3="Review";
	private String test653worflowSubaction3="Unlock content"; 
	//test-623
	private String test623folderName2="test623";
	private String test623contentStructureName11="Test-623";
	private String test623contentStructureName11Field1="title";
	private String test623contentTitle11="Test-623";
	private String test623fileName11="test623.jpg";
	private String test623contentStructureName11Field2="binary1FileUpload";
	private String test623contentTextArea11="/src/main/resources/test623.jpg";
	private String test623workflowSchemeName3="Test-623";
	private String test623workflowSchemeStep4="Test623Assign";
	private String test623workflowActionName4="Assign";
	private String test623worflowSubaction4="Lock content"; 
	//test 628
	private String test628contentStructureName12="Content";
	private String test628contentStructureName12Field1="title";
	private String test628contentTitle12="Test-628";
	private String test628contentStructureName12Field2="body";
	private String test628contentTextArea12="Test 628";
	private String test628contentTextArea122="Test 628 modified";

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
			createLimitedUser(limitedUserNameB, limitedUserLastNameB, limitedUserEmailB, limitedUserPaswwordB, receiverPortletMenu);

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

			//create limited user for testing in authoring server
			createLimitedUser(limitedUserNameA, limitedUserLastNameA, limitedUserEmailA, limitedUserPaswwordA, authoringPortletMenu);

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
			if(browserPage.doesElementExist(test555pageUrl1)){
				browserPage.unPublishElement(test555pageUrl1);
				browserPage.archiveElement(test555pageUrl1);
				browserPage.deletePage(test555pageUrl1);
			}

			if(browserPage.doesElementExist(test556pageUrl2)){
				browserPage.unPublishElement(test556pageUrl2);
				browserPage.archiveElement(test556pageUrl2);
				browserPage.deletePage(test556pageUrl2);
			}

			if(browserPage.doesElementExist(test558pageUrl3)){
				browserPage.unPublishElement(test558pageUrl3);
				browserPage.archiveElement(test558pageUrl3);
				browserPage.deletePage(test558pageUrl3);
			}

			if(browserPage.doesElementExist(test568pageUrl4)){
				browserPage.unPublishElement(test568pageUrl4);
				browserPage.archiveElement(test568pageUrl4);
				browserPage.deletePage(test568pageUrl4);
			}

			if(browserPage.doesElementExist(test507pageUrl5)){
				browserPage.unPublishElement(test507pageUrl5);
				browserPage.archiveElement(test507pageUrl5);
				browserPage.deletePage(test507pageUrl5);
			}

			if(browserPage.doesElementExist(test582pageUrl6)){
				browserPage.unPublishElement(test582pageUrl6);
				browserPage.archiveElement(test582pageUrl6);
				browserPage.deletePage(test582pageUrl6);
			}

			if(browserPage.doesElementExist(test625pageUrl7)){
				browserPage.unPublishElement(test625pageUrl7);
				browserPage.archiveElement(test625pageUrl7);
				browserPage.deletePage(test625pageUrl7);
			}

			if(browserPage.doesFolderExist(test558folderName1)){
				browserPage.deleteFolder(test558folderName1);
			}

			/* Delete template*/
			ITemplatesPage templatesPage = portletMenu.getTemplatesPage();
			if(templatesPage.doesTemplateExist(test555templateTitle1)){
				templatesPage.deleteTemplate(test555templateTitle1);
			}

			if(templatesPage.doesTemplateExist(test556templateTitle2)){
				templatesPage.deleteTemplate(test556templateTitle2);
			}

			if(templatesPage.doesTemplateExist(test556templateTitle3)){
				templatesPage.deleteTemplate(test556templateTitle3);
			}

			if(templatesPage.doesTemplateExist(test558templateTitle4)){
				templatesPage.deleteTemplate(test558templateTitle4);
			}

			if(templatesPage.doesTemplateExist(test523templateTitle5)){
				templatesPage.deleteTemplate(test523templateTitle5);
			}

			if(templatesPage.doesTemplateExist(test523templateTitle6)){
				templatesPage.deleteTemplate(test523templateTitle6);
			}

			if(templatesPage.doesTemplateExist(test568templateTitle7)){
				templatesPage.deleteTemplate(test568templateTitle7);
			}

			if(templatesPage.doesTemplateExist(test507templateTitle8)){
				templatesPage.deleteTemplate(test507templateTitle8);
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

			if(containersPage.existContainer(test507containerTitle4)){
				containersPage.deleteContainer(test507containerTitle4);
			}

			/* Delete Menu link*/
			IMenuLinkPage menuLinkPage= portletMenu.getMenuLinkPage();
			if(menuLinkPage.doesLinkExist(test524linkTitle1)){
				menuLinkPage.deleteLink(test524linkTitle1);
			}

			if(menuLinkPage.doesLinkExist(test575linkTitle2)){
				menuLinkPage.deleteLink(test575linkTitle2);
			}

			if(menuLinkPage.doesLinkExist(test589linkTitle3)){
				menuLinkPage.deleteLink(test589linkTitle3);
			}

			if(menuLinkPage.doesLinkExist(test589linkTitle4)){
				menuLinkPage.deleteLink(test589linkTitle4);
			}

			/* Delete structure*/
			IStructuresPage structurePage = portletMenu.getStructuresPage();
			if(structurePage.doesStructureExist(structureName)){
				structurePage.deleteStructureAndContent(structureName, true);
			}

			if(structurePage.doesStructureExist(test496contentStructureName2)){
				structurePage.deleteStructureAndContent(test496contentStructureName2, true);
			}

			if(structurePage.doesStructureExist(test519contentStructureName3)){
				structurePage.deleteStructureAndContent(test519contentStructureName3, true);
			}

			if(structurePage.doesStructureExist(test519contentStructureName4)){
				structurePage.deleteStructureAndContent(test519contentStructureName4, true);
			}

			if(structurePage.doesStructureExist(test519contentStructureName5)){
				structurePage.deleteStructureAndContent(test519contentStructureName5, true);
			}

			if(structurePage.doesStructureExist(test532contentStructureName6)){
				structurePage.deleteStructureAndContent(test532contentStructureName6, true);
			}

			if(structurePage.doesStructureExist(test572contentStructureName8)){
				structurePage.deleteStructureAndContent(test572contentStructureName8, true);
			}

			if(structurePage.doesStructureExist(test652contentStructureName9)){
				structurePage.deleteStructureAndContent(test652contentStructureName9, true);
			}

			if(structurePage.doesStructureExist(test653contentStructureName10)){
				structurePage.deleteStructureAndContent(test653contentStructureName10, true);
			}

			if(structurePage.doesStructureExist(test623contentStructureName11)){
				structurePage.deleteStructureAndContent(test623contentStructureName11, true);
			}

			/* Delete content*/
			IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
			if(contentSearchPage.doesContentExist(test520contentTitle1, test520contentStructureName1)){
				contentSearchPage.unpublish(test520contentTitle1, test520contentStructureName1);
				contentSearchPage.archive(test520contentTitle1, test520contentStructureName1);
				contentSearchPage.delete(test520contentTitle1, test520contentStructureName1);
			}

			if(contentSearchPage.doesContentExist(test528contentTitle7, test528contentStructureName7)){
				contentSearchPage.unpublish(test528contentTitle7, test528contentStructureName7);
				contentSearchPage.archive(test528contentTitle7, test528contentStructureName7);
				contentSearchPage.delete(test528contentTitle7, test528contentStructureName7);
			}
			
			if(contentSearchPage.doesContentExist(test628contentTitle12, test628contentStructureName12)){
				contentSearchPage.unpublish(test628contentTitle12, test628contentStructureName12);
				contentSearchPage.archive(test628contentTitle12, test628contentStructureName12);
				contentSearchPage.delete(test628contentTitle12, test628contentStructureName12);
			}

			/* Delete workflows*/
			IWorkflowSchemesPage schemesPage = portletMenu.getWorkflowSchemesPage();
			if(schemesPage.doesWorkflowSchemeExist(test652workflowSchemeName1)){
				schemesPage = portletMenu.getWorkflowSchemesPage();
				IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(test652workflowSchemeName1);
				stepsPage.deleteStep(test652workflowSchemeStep1);
				WorkflowPageUtil.deleteWorkflow(test652workflowSchemeName1,serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
			}
			schemesPage = portletMenu.getWorkflowSchemesPage();
			if(schemesPage.doesWorkflowSchemeExist(test653workflowSchemeName2)){
				schemesPage = portletMenu.getWorkflowSchemesPage();
				IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(test653workflowSchemeName2);
				stepsPage.deleteStep(test653workflowSchemeStep3);
				stepsPage.deleteStep(test653workflowSchemeStep2);
				WorkflowPageUtil.deleteWorkflow(test653workflowSchemeName2,serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
			}

			schemesPage = portletMenu.getWorkflowSchemesPage();
			if(schemesPage.doesWorkflowSchemeExist(test623workflowSchemeName3)){
				schemesPage = portletMenu.getWorkflowSchemesPage();
				IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(test623workflowSchemeName3);
				stepsPage.deleteStep(test623workflowSchemeStep4);
				WorkflowPageUtil.deleteWorkflow(test623workflowSchemeName3,serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
			}

			/*Delete limited user*/
			/*IUsersPage usersPage = portletMenu.getUsersPage();
			Map<String, String> fakeUser = usersPage.getUserProperties(limitedUserEmailA);
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
			if(browserPage.doesElementExist(test555pageUrl1)){
				browserPage.unPublishElement(test555pageUrl1);
				browserPage.archiveElement(test555pageUrl1);
				browserPage.deletePage(test555pageUrl1);
			}

			if(browserPage.doesElementExist(test556pageUrl2)){
				browserPage.unPublishElement(test556pageUrl2);
				browserPage.archiveElement(test556pageUrl2);
				browserPage.deletePage(test556pageUrl2);
			}

			if(browserPage.doesElementExist(test558pageUrl3)){
				browserPage.unPublishElement(test558pageUrl3);
				browserPage.archiveElement(test558pageUrl3);
				browserPage.deletePage(test558pageUrl3);
			}

			if(browserPage.doesElementExist(test568pageUrl4)){
				browserPage.unPublishElement(test568pageUrl4);
				browserPage.archiveElement(test568pageUrl4);
				browserPage.deletePage(test568pageUrl4);
			}

			if(browserPage.doesElementExist(test507pageUrl5)){
				browserPage.unPublishElement(test507pageUrl5);
				browserPage.archiveElement(test507pageUrl5);
				browserPage.deletePage(test507pageUrl5);
			}

			if(browserPage.doesElementExist(test582pageUrl6)){
				browserPage.unPublishElement(test582pageUrl6);
				browserPage.archiveElement(test582pageUrl6);
				browserPage.deletePage(test582pageUrl6);
			}

			if(browserPage.doesElementExist(test625pageUrl7)){
				browserPage.unPublishElement(test625pageUrl7);
				browserPage.archiveElement(test625pageUrl7);
				browserPage.deletePage(test625pageUrl7);
			}

			if(browserPage.doesFolderExist(test558folderName1)){
				browserPage.deleteFolder(test558folderName1);
			}

			if(browserPage.doesFolderExist(test623folderName2)){
				browserPage.deleteFolder(test623folderName2);
			}

			/* Delete template*/
			ITemplatesPage templatesPage = portletMenu.getTemplatesPage();
			if(templatesPage.doesTemplateExist(test555templateTitle1)){
				templatesPage.deleteTemplate(test555templateTitle1);
			}

			templatesPage = portletMenu.getTemplatesPage();
			if(templatesPage.doesTemplateExist(test556templateTitle2)){
				templatesPage.deleteTemplate(test556templateTitle2);
			}

			if(templatesPage.doesTemplateExist(test556templateTitle3)){
				templatesPage.deleteTemplate(test556templateTitle3);
			}

			if(templatesPage.doesTemplateExist(test558templateTitle4)){
				templatesPage.deleteTemplate(test558templateTitle4);
			}

			if(templatesPage.doesTemplateExist(test523templateTitle5)){
				templatesPage.deleteTemplate(test523templateTitle5);
			}

			if(templatesPage.doesTemplateExist(test523templateTitle6)){
				templatesPage.deleteTemplate(test523templateTitle6);
			}

			if(templatesPage.doesTemplateExist(test568templateTitle7)){
				templatesPage.deleteTemplate(test568templateTitle7);
			}

			if(templatesPage.doesTemplateExist(test507templateTitle8)){
				templatesPage.deleteTemplate(test507templateTitle8);
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

			if(containersPage.existContainer(test507containerTitle4)){
				containersPage.deleteContainer(test507containerTitle4);
			}

			/* Delete Menu link*/
			IMenuLinkPage menuLinkPage= portletMenu.getMenuLinkPage();
			if(menuLinkPage.doesLinkExist(test524linkTitle1)){
				menuLinkPage.deleteLink(test524linkTitle1);
			}

			if(menuLinkPage.doesLinkExist(test575linkTitle2)){
				menuLinkPage.deleteLink(test575linkTitle2);
			}

			if(menuLinkPage.doesLinkExist(test589linkTitle3)){
				menuLinkPage.deleteLink(test589linkTitle3);
			}

			if(menuLinkPage.doesLinkExist(test589linkTitle4)){
				menuLinkPage.deleteLink(test589linkTitle4);
			}

			/* Delete structure*/
			IStructuresPage structurePage = portletMenu.getStructuresPage();
			if(structurePage.doesStructureExist(structureName)){
				structurePage.deleteStructureAndContent(structureName, true);
			}

			if(structurePage.doesStructureExist(test496contentStructureName2)){
				structurePage.deleteStructureAndContent(test496contentStructureName2, true);
			}

			if(structurePage.doesStructureExist(test519contentStructureName3)){
				structurePage.deleteStructureAndContent(test519contentStructureName3, true);
			}

			if(structurePage.doesStructureExist(test519contentStructureName4)){
				structurePage.deleteStructureAndContent(test519contentStructureName4, true);
			}

			if(structurePage.doesStructureExist(test519contentStructureName5)){
				structurePage.deleteStructureAndContent(test519contentStructureName5, true);
			}

			if(structurePage.doesStructureExist(test532contentStructureName6)){
				structurePage.deleteStructureAndContent(test532contentStructureName6, true);
			}

			if(structurePage.doesStructureExist(test572contentStructureName8)){
				structurePage.deleteStructureAndContent(test572contentStructureName8, true);
			}

			if(structurePage.doesStructureExist(test652contentStructureName9)){
				structurePage.deleteStructureAndContent(test652contentStructureName9, true);
			}

			if(structurePage.doesStructureExist(test653contentStructureName10)){
				structurePage.deleteStructureAndContent(test653contentStructureName10, true);
			}

			if(structurePage.doesStructureExist(test623contentStructureName11)){
				structurePage.deleteStructureAndContent(test623contentStructureName11, true);
			}

			/* Delete content*/
			IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
			if(contentSearchPage.doesContentExist(test520contentTitle1, test520contentStructureName1)){
				contentSearchPage.unpublish(test520contentTitle1, test520contentStructureName1);
				contentSearchPage.archive(test520contentTitle1, test520contentStructureName1);
				contentSearchPage.delete(test520contentTitle1, test520contentStructureName1);
			}

			if(contentSearchPage.doesContentExist(test528contentTitle7, test528contentStructureName7)){
				contentSearchPage.unpublish(test528contentTitle7, test528contentStructureName7);
				contentSearchPage.archive(test528contentTitle7, test528contentStructureName7);
				contentSearchPage.delete(test528contentTitle7, test528contentStructureName7);
			}
			
			if(contentSearchPage.doesContentExist(test628contentTitle12, test628contentStructureName12)){
				contentSearchPage.unpublish(test628contentTitle12, test628contentStructureName12);
				contentSearchPage.archive(test628contentTitle12, test628contentStructureName12);
				contentSearchPage.delete(test628contentTitle12, test628contentStructureName12);
			}

			/* Delete workflows*/
			IWorkflowSchemesPage schemesPage = portletMenu.getWorkflowSchemesPage();
			if(schemesPage.doesWorkflowSchemeExist(test652workflowSchemeName1)){
				schemesPage = portletMenu.getWorkflowSchemesPage();
				IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(test652workflowSchemeName1);
				stepsPage.deleteStep(test652workflowSchemeStep1);
				WorkflowPageUtil.deleteWorkflow(test652workflowSchemeName1,serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
			}
			schemesPage = portletMenu.getWorkflowSchemesPage();
			if(schemesPage.doesWorkflowSchemeExist(test653workflowSchemeName2)){
				schemesPage = portletMenu.getWorkflowSchemesPage();
				IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(test653workflowSchemeName2);
				stepsPage.deleteStep(test653workflowSchemeStep3);
				stepsPage.deleteStep(test653workflowSchemeStep2);
				WorkflowPageUtil.deleteWorkflow(test653workflowSchemeName2,serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
			}

			schemesPage = portletMenu.getWorkflowSchemesPage();
			if(schemesPage.doesWorkflowSchemeExist(test623workflowSchemeName3)){
				schemesPage = portletMenu.getWorkflowSchemesPage();
				IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(test623workflowSchemeName3);
				stepsPage.deleteStep(test623workflowSchemeStep4);
				WorkflowPageUtil.deleteWorkflow(test623workflowSchemeName3,serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
			}

			/*Delete limited user*/
			/*IUsersPage usersPage = portletMenu.getUsersPage();
			Map<String, String> fakeUser = usersPage.getUserProperties(limitedUserEmailB);
			String fakeUserId = fakeUser.get("userId");
			if(fakeUserId != null && !fakeUserId.equals("")){
				UsersPageUtil.deleteUser(fakeUserId,serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
			}*/

			/* Delete limited role*/
			/*IRolesPage rolePage = portletMenu.getRolesPage();
			if(rolePage.doesRoleExist(limitedRole)){
				rolePage.deleteRole(limitedRole);
			}*/

			logoutReceiverServer();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Create a limited user for push publishing test
	 * @param limitedUserName	   User Name
	 * @param limitedUserLastName  User Last name
	 * @param limitedUserEmail     User email
	 * @param limitedUserPaswword  User Password
	 * @param portletMenu          Portlet Connection
	 * @throws Exception
	 */
	private void createLimitedUser(String limitedUserName, String limitedUserLastName, String limitedUserEmail, String limitedUserPaswword, IPortletMenu portletMenu) throws Exception{
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
		portletMenu = callAuthoringServer(limitedUserEmailA,limitedUserPaswwordA);
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
		IPortletMenu portletMenu = callAuthoringServer(limitedUserEmailA,limitedUserPaswwordA);

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
		template.put("titleField",test555templateTitle1);
		template.put("friendlyNameField", test555templateTitle1);
		template.put("AddContainers", test555templateContainer1);

		advanceTemplate.setTemplateFields(template);
		templatesPage= advanceTemplate.saveAndPublish();
		Assert.assertTrue(templatesPage.doesTemplateExist(test555templateTitle1), "ERROR - Authoring Server: Template ('"+test555templateTitle1+"') should exist at this moment in authoring server.");

		//create test page
		templatesPage.sleep(3);
		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		browserPage.createHTMLPage(test555pageTitle1, test555templateTitle1, test555pageUrl1);

		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();

		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test555pageUrl1), "ERROR - Authoring Server: Page ('"+test555pageUrl1+"') should exist at this moment in authoring server.");

		browserPage.pushElement(test555pageUrl1);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test555pageTitle1,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ('"+test555pageUrl1+"') push should not be in pending list.");

		//delete template and page
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(test555pageUrl1);
		browserPage.archiveElement(test555pageUrl1);
		browserPage.deletePage(test555pageUrl1);
		Assert.assertFalse(browserPage.doesElementExist(test555pageUrl1), "ERROR - Authoring Server: Page ('"+test555pageUrl1+"') should not exist at this moment in authoring server.");
		browserPage.sleep(2);
		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(test555templateTitle1);
		Assert.assertFalse(templatesPage.doesTemplateExist(test555templateTitle1), "ERROR - Authoring Server: Template ('"+test555templateTitle1+"') should not exist at this moment in authoring server.");	
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test555pageUrl1), "ERROR - Receiver Server: Page ('"+test555pageUrl1+"') should exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(test555templateTitle1), "ERROR - Receiver Server: Template ('"+test555templateTitle1+"') should exist at this moment in receiver server.");

		//Delete template and page in receiver
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(test555pageUrl1);
		browserPage.archiveElement(test555pageUrl1);
		browserPage.deletePage(test555pageUrl1);
		Assert.assertFalse(browserPage.doesElementExist(test555pageUrl1), "ERROR - Receiver Server: Page ('"+test555pageUrl1+"') should not exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(test555templateTitle1);
		Assert.assertFalse(templatesPage.doesTemplateExist(test555templateTitle1), "ERROR - Receiver Server: Template ('"+test555templateTitle1+"') should not exist at this moment in receiver server.");
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
		designTemplate.setTemplateTitle(test556templateTitle2);
		designTemplate.setTheme(test556templateTheme2);
		designTemplate.addContainer(test556templateContainer2);

		templatesPage = designTemplate.saveAndPublish();
		Assert.assertTrue(templatesPage.doesTemplateExist(test556templateTitle2), "ERROR - Authoring Server: Template ('"+test556templateTitle2+"') should exist at this moment in authoring server.");

		templatesPage.pushTemplate(test556templateTitle2);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the template was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test556templateTitle2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Template ('"+test556templateTitle2+"') push should not be in pending list.");

		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		templatesPage= portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(test556templateTitle2), "ERROR - Receiver Server: Template ('"+test556templateTitle2+"') should exist at this moment in receiver server.");

		logoutReceiverServer();

		//Second part of the test push a template as a page dependency
		//Calling authoring Server
		portletMenu = callAuthoringServer();
		templatesPage = portletMenu.getTemplatesPage();

		designTemplate = templatesPage.addDesignTemplate();
		designTemplate.setTemplateTitle(test556templateTitle3);
		designTemplate.setTheme(test556templateTheme3);
		designTemplate.addContainer(test556templateContainer3);

		templatesPage = designTemplate.saveAndPublish();
		Assert.assertTrue(templatesPage.doesTemplateExist(test556templateTitle3), "ERROR - Authoring Server: Template ('"+test556templateTitle3+"') should exist at this moment in authoring server.");

		//create test page
		templatesPage.sleep(3);
		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		browserPage.createHTMLPage(test556pageTitle2, test556templateTitle3, test556pageUrl2);

		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();

		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test556pageUrl2), "ERROR - Authoring Server: Page ('"+test556pageUrl2+"') should exist at this moment in authoring server.");

		browserPage.pushElement(test556pageUrl2);
		publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		isPushed = publishingQueuePage.isObjectBundlePushed(test556pageTitle2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ('"+test556pageUrl2+"') push should not be in pending list.");

		//delete template and page
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(test556pageUrl2);
		browserPage.archiveElement(test556pageUrl2);
		browserPage.deletePage(test556pageUrl2);
		Assert.assertFalse(browserPage.doesElementExist(test556pageUrl2), "ERROR - Authoring Server: Page ('"+test556pageUrl2+"') should not exist at this moment in authoring server.");

		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(test556templateTitle3);
		Assert.assertFalse(templatesPage.doesTemplateExist(test556templateTitle3), "ERROR - Authoring Server: Template ('"+test556templateTitle3+"') should not exist at this moment in authoring server.");	
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		browserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test556pageUrl2), "ERROR - Receiver Server: Page ('"+test556pageUrl2+"') should exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(test556templateTitle3), "ERROR - Receiver Server: Template ('"+test556templateTitle3+"') should exist at this moment in receiver server.");

		//Delete template and page in receiver
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(test556pageUrl2);
		browserPage.archiveElement(test556pageUrl2);
		browserPage.deletePage(test556pageUrl2);
		Assert.assertFalse(browserPage.doesElementExist(test556pageUrl2), "ERROR - Receiver Server: Page ('"+test556pageUrl2+"') should not exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(test556templateTitle3);
		Assert.assertFalse(templatesPage.doesTemplateExist(test556templateTitle3), "ERROR - Receiver Server: Template ('"+test556templateTitle3+"') should not exist at this moment in receiver server.");
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
		templatesPage.editTemplate(test556templateTitle2);
		ITemplateAddOrEditDesignTemplatePage designTemplate = SeleniumPageManager.getBackEndPageManager().getPageObject(ITemplateAddOrEditDesignTemplatePage.class);
		designTemplate.addContainer(test556templateContainer22);
		List<Map<String,String>> authoringContainers = designTemplate.getTemplateContainers();
		templatesPage = designTemplate.saveAndPublish();
		Assert.assertTrue(templatesPage.doesTemplateExist(test556templateTitle2), "ERROR - Authoring Server: Template ('"+test556templateTitle2+"') should exist at this moment in authoring server.");

		templatesPage.pushTemplate(test556templateTitle2);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the template was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test556templateTitle2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Template ('"+test556templateTitle2+"') push should not be in pending list.");
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(3);
		templatesPage= portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(test556templateTitle2), "ERROR - Receiver Server: Template ('"+test556templateTitle2+"') should exist at this moment in receiver server.");

		templatesPage.editTemplate(test556templateTitle2);
		designTemplate = SeleniumPageManager.getBackEndPageManager().getPageObject(ITemplateAddOrEditDesignTemplatePage.class);
		List<Map<String,String>> containers = designTemplate.getTemplateContainers();
		designTemplate.cancel();
		Assert.assertTrue(containers.size()==authoringContainers.size(), "ERROR - Receiver Server: Template ('"+test556templateTitle2+"') doesn't have the same number of containers as the authoring server in receiver server.");
		for(Map<String,String> map : authoringContainers){
			boolean containerFound = false;
			for(Map<String,String> map2 : containers){
				if(map.get("name").equals(map2.get("name"))){
					containerFound=true;
					break;
				}
			}
			Assert.assertTrue(containerFound, "ERROR - Receiver Server: Template ('"+test556templateTitle2+"') doesn't have this container ("+map.get("name")+") in receiver server.");
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
		designTemplate.setTemplateTitle(test558templateTitle4);
		designTemplate.setTheme(test558templateTheme4);
		designTemplate.addContainer(test558templateContainer4);

		templatesPage = designTemplate.saveAndPublish();
		Assert.assertTrue(templatesPage.doesTemplateExist(test558templateTitle4), "ERROR - Authoring Server: Template ('"+test558templateTitle4+"') should exist at this moment in authoring server.");

		//create test page
		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		browserPage.createFolder(null, test558folderName1);
		browserPage.selectFolder(test558folderName1);
		browserPage.createHTMLPage(test558pageTitle3, test558templateTitle4, test558pageUrl3);

		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();

		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test558pageUrl3), "ERROR - Authoring Server: Page ('"+test558pageUrl3+"') should exist at this moment in authoring server.");

		browserPage.pushFolder(test558folderName1);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the folder was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test558folderName1,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Folder ('"+test558folderName1+"') push should not be in pending list.");

		//delete template and page
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(test558pageUrl3);
		browserPage.archiveElement(test558pageUrl3);
		browserPage.deletePage(test558pageUrl3);
		Assert.assertFalse(browserPage.doesElementExist(test558pageUrl3), "ERROR - Authoring Server: Page ('"+test558pageUrl3+"') should not exist at this moment in authoring server.");
		browserPage.deleteFolder(test558folderName1);
		Assert.assertFalse(browserPage.doesFolderExist(test558folderName1), "ERROR - Authoring Server: Folder ('"+test558folderName1+"') should not exist at this moment in authoring server.");

		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(test558templateTitle4);
		Assert.assertFalse(templatesPage.doesTemplateExist(test558templateTitle4), "ERROR - Authoring Server: Template ('"+test558templateTitle4+"') should not exist at this moment in authoring server.");	
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		browserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesFolderExist(test558folderName1), "ERROR - Receiver Server: Folder ('"+test558folderName1+"') should exist at this moment in receiver server.");
		browserPage.selectFolder(test558folderName1);
		Assert.assertTrue(browserPage.doesElementExist(test558pageUrl3), "ERROR - Receiver Server: Page ('"+test558pageUrl3+"') should exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(test558templateTitle4), "ERROR - Receiver Server: Template ('"+test558templateTitle4+"') should exist at this moment in receiver server.");

		//Delete template and page in receiver
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.selectFolder(test558folderName1);
		browserPage.unPublishElement(test558pageUrl3);
		browserPage.archiveElement(test558pageUrl3);
		browserPage.deletePage(test558pageUrl3);
		Assert.assertFalse(browserPage.doesElementExist(test558pageUrl3), "ERROR - Receiver Server: Page ('"+test558pageUrl3+"') should not exist at this moment in receiver server.");

		browserPage.deleteFolder(test558folderName1);
		Assert.assertFalse(browserPage.doesFolderExist(test558folderName1), "ERROR - Receiver Server: Folder ('"+test558folderName1+"') should not exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(test558templateTitle4);
		Assert.assertFalse(templatesPage.doesTemplateExist(test558templateTitle4), "ERROR - Receiver Server: Template ('"+test558templateTitle4+"') should not exist at this moment in receiver server.");
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
		template.put("titleField",test523templateTitle5);
		template.put("friendlyNameField", test523templateTitle5);
		template.put("AddContainers", test523templateContainer5);

		advanceTemplate.setTemplateFields(template);
		templatesPage= advanceTemplate.save();
		Assert.assertTrue(templatesPage.doesTemplateExist(test523templateTitle5), "ERROR - Authoring Server: Template ('"+test523templateTitle5+"') should exist at this moment in authoring server.");
		templatesPage.sleep(3);

		templatesPage.pushTemplate(test523templateTitle5);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the template was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test523templateTitle5,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Template ('"+test523templateTitle5+"') push should not be in pending list.");
		publishingQueuePage.sleep(3);

		//create design template
		templatesPage = portletMenu.getTemplatesPage();
		ITemplateAddOrEditDesignTemplatePage designTemplate = templatesPage.addDesignTemplate();
		designTemplate.setTemplateTitle(test523templateTitle6);
		designTemplate.setTheme(test523templateTheme6);
		designTemplate.addContainer(test523templateContainer6);
		templatesPage = designTemplate.save();
		Assert.assertTrue(templatesPage.doesTemplateExist(test523templateTitle6), "ERROR - Authoring Server: Template ('"+test523templateTitle6+"') should exist at this moment in authoring server.");

		templatesPage.pushTemplate(test523templateTitle6);
		publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the template was pushed
		isPushed = publishingQueuePage.isObjectBundlePushed(test523templateTitle6,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Template ('"+test523templateTitle6+"') push should not be in pending list.");

		//delete template and page
		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(test523templateTitle5);
		Assert.assertFalse(templatesPage.doesTemplateExist(test523templateTitle5), "ERROR - Authoring Server: Template ('"+test523templateTitle5+"') should not exist at this moment in authoring server.");	

		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(test523templateTitle6);
		Assert.assertFalse(templatesPage.doesTemplateExist(test523templateTitle6), "ERROR - Authoring Server: Template ('"+test523templateTitle6+"') should not exist at this moment in authoring server.");	
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);

		templatesPage = portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(test523templateTitle5), "ERROR - Receiver Server: Template ('"+test523templateTitle5+"') should exist at this moment in receiver server.");
		Assert.assertTrue(templatesPage.doesTemplateExist(test523templateTitle6), "ERROR - Receiver Server: Template ('"+test523templateTitle6+"') should exist at this moment in receiver server.");

		//Delete template and page in receiver
		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.sleep(3);
		templatesPage.deleteTemplate(test523templateTitle5);
		Assert.assertFalse(templatesPage.doesTemplateExist(test523templateTitle5), "ERROR - Receiver Server: Template ('"+test523templateTitle5+"') should not exist at this moment in receiver server.");

		templatesPage.deleteTemplate(test523templateTitle6);
		Assert.assertFalse(templatesPage.doesTemplateExist(test523templateTitle6), "ERROR - Receiver Server: Template ('"+test523templateTitle6+"') should not exist at this moment in receiver server.");
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
		template.put("titleField",test568templateTitle7);
		template.put("friendlyNameField", test568templateTitle7);
		template.put("AddContainers", test568templateContainer7);

		advanceTemplate.setTemplateFields(template);
		templatesPage= advanceTemplate.saveAndPublish();
		Assert.assertTrue(templatesPage.doesTemplateExist(test568templateTitle7), "ERROR - Authoring Server: Template ('"+test568templateTitle7+"') should exist at this moment in authoring server.");

		//create test page
		templatesPage.sleep(3);
		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		browserPage.createHTMLPage(test568pageTitle4, test568templateTitle7, test568pageUrl4);
		browserPage.sleep(2);
		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();
		logoutAuthoringServer();

		//login as limited user
		portletMenu =callAuthoringServer(limitedUserEmailA, limitedUserPaswwordA);
		portletMenu.sleep(4);
		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test568pageUrl4), "ERROR - Authoring Server: Page ('"+test568pageUrl4+"') should exist at this moment in authoring server.");

		browserPage.pushElement(test568pageUrl4);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test568pageTitle4,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ('"+test568pageUrl4+"') push should not be in pending list.");
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test568pageUrl4), "ERROR - Receiver Server: Page ('"+test568pageUrl4+"') should exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(test568templateTitle7), "ERROR - Receiver Server: Template ('"+test568templateTitle7+"') should exist at this moment in receiver server.");
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
		IPortletMenu portletMenu = callAuthoringServer(limitedUserEmailA, limitedUserPaswwordA);
		ITemplatesPage templatesPage = portletMenu.getTemplatesPage();

		templatesPage.editTemplate(test568templateTitle7);
		ITemplateAddOrEditAdvanceTemplatePage advanceTemplate=  SeleniumPageManager.getBackEndPageManager().getPageObject(ITemplateAddOrEditAdvanceTemplatePage.class);
		Map<String,String> template = new HashMap<String, String>();
		template.put("titleField",test568templateTitle7);
		template.put("friendlyNameField", test568templateTitle7);
		template.put("AddContainers", test568templateContainer72);
		advanceTemplate.setTemplateFields(template);

		String originalTemplateBody = advanceTemplate.getFieldValue("ace");
		templatesPage=advanceTemplate.saveAndPublish();
		templatesPage.sleep(2);
		Assert.assertTrue(templatesPage.doesTemplateExist(test568templateTitle7), "ERROR - Authoring Server: Template ('"+test568templateTitle7+"') should exist at this moment in authoring server.");

		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test568pageUrl4), "ERROR - Authoring Server: Page ('"+test568pageUrl4+"') should exist at this moment in authoring server.");

		browserPage.pushElement(test568pageUrl4);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test568pageTitle4,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ('"+test568pageUrl4+"') push should not be in pending list.");

		//delete test 
		browserPage = portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(test568pageUrl4);
		browserPage.archiveElement(test568pageUrl4);
		browserPage.deletePage(test568pageUrl4);
		Assert.assertFalse(browserPage.doesElementExist(test568pageUrl4), "ERROR - Authoring Server: Page ('"+test568pageUrl4+"') should not exist at this moment in authoring server.");


		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.sleep(3);
		templatesPage.deleteTemplate(test568templateTitle7);
		Assert.assertFalse(templatesPage.doesTemplateExist(test568templateTitle7), "ERROR - Authoring Server: Template ('"+test568templateTitle7+"') should not exist at this moment in authoring server.");
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test568pageUrl4), "ERROR - Receiver Server: Page ('"+test568pageUrl4+"') should exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(test568templateTitle7), "ERROR - Receiver Server: Template ('"+test568templateTitle7+"') should exist at this moment in receiver server.");
		templatesPage.editTemplate(test568templateTitle7);
		advanceTemplate=  SeleniumPageManager.getBackEndPageManager().getPageObject(ITemplateAddOrEditAdvanceTemplatePage.class);
		String receiverTemplateBody = advanceTemplate.getFieldValue("ace");
		advanceTemplate.cancel();
		Assert.assertTrue(originalTemplateBody.equals(receiverTemplateBody), "ERROR - Receiver Server: Template ('"+test568templateTitle7+"') body is not the same in both authoring and receiver server.");

		//delete test 
		browserPage = portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(test568pageUrl4);
		browserPage.archiveElement(test568pageUrl4);
		browserPage.deletePage(test568pageUrl4);
		Assert.assertFalse(browserPage.doesElementExist(test568pageUrl4), "ERROR - Receiver Server: Page ('"+test568pageUrl4+"') should not exist at this moment in receiver server.");


		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.sleep(3);
		templatesPage.deleteTemplate(test568templateTitle7);
		Assert.assertFalse(templatesPage.doesTemplateExist(test568templateTitle7), "ERROR - Receiver Server: Template ('"+test568templateTitle7+"') should not exist at this moment in receiver server.");


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
		container.put("titleField", test507containerTitle4);
		container.put("friendlyNameField", test507containerTitle4);
		container.put("ace", test507containerCode4);
		//create test container to push
		addContainerPage.setFields(container);
		containersPage = addContainerPage.saveAndPublish();
		Assert.assertTrue(containersPage.existContainer(test507containerTitle4), "ERROR - Container ('"+test507containerTitle4+"')  should exist at this moment in authoring server.");

		ITemplatesPage templatesPage = portletMenu.getTemplatesPage();
		ITemplateAddOrEditAdvanceTemplatePage advanceTemplate = templatesPage.addAdvanceTemplate();
		Map<String,String> template = new HashMap<String, String>();
		template.put("titleField",test507templateTitle8);
		template.put("friendlyNameField", test507templateTitle8);
		template.put("AddContainers", test507containerTitle4);

		advanceTemplate.setTemplateFields(template);
		templatesPage= advanceTemplate.saveAndPublish();
		Assert.assertTrue(templatesPage.doesTemplateExist(test507templateTitle8), "ERROR - Authoring Server: Template ('"+test507templateTitle8+"') should exist at this moment in authoring server.");

		//create test page
		templatesPage.sleep(3);
		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		browserPage.createHTMLPage(test507pageTitle5, test507templateTitle8, test507pageUrl5);

		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();

		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test507pageUrl5), "ERROR - Authoring Server: Page ('"+test507pageUrl5+"') should exist at this moment in authoring server.");

		browserPage.pushElement(test507pageUrl5);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test507pageTitle5,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ('"+test507pageUrl5+"') push should not be in pending list.");

		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test507pageUrl5), "ERROR - Receiver Server: Page ('"+test507pageUrl5+"') should exist at this moment in receiver server.");

		templatesPage = portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(test507templateTitle8), "ERROR - Receiver Server: Template ('"+test507templateTitle8+"') should exist at this moment in receiver server.");

		containersPage = portletMenu.getContainersPage();
		Assert.assertTrue(containersPage.existContainer(test507containerTitle4), "ERROR - Authoring Server: Container ('"+test507containerTitle4+"') should not exist at this moment in authoring server.");

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
		browserPage.editHTMLPageProperties(test507pageUrl5);
		IHTMLPageAddOrEdit_ContentPage htmlAddPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IHTMLPageAddOrEdit_ContentPage.class);
		if(htmlAddPage.isLocked()){
			htmlAddPage.unlock();
		}
		htmlAddPage.setTitle(test577pageTitle52);
		htmlAddPage.saveAndPublish();

		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();

		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.pushElement(test507pageUrl5);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test577pageTitle52,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ('"+test507pageUrl5+"') push should not be in pending list.");

		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test507pageUrl5), "ERROR - Receiver Server: Page ('"+test507pageUrl5+"') should exist at this moment in receiver server.");

		browserPage.editHTMLPageProperties(test507pageUrl5);
		htmlAddPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IHTMLPageAddOrEdit_ContentPage.class);
		String title = htmlAddPage.getPageTitle();
		htmlAddPage.cancel();

		Assert.assertTrue(title.equals(test577pageTitle52), "ERROR - Receiver Server: Page ('"+test507pageUrl5+"') title doesn't match between authoring and receiver servers.");		
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
		IPortletMenu portletMenu = callAuthoringServer(limitedUserEmailA,limitedUserPaswwordA);
		portletMenu.sleep(2);
		ISiteBrowserPage browserPage= portletMenu.getSiteBrowserPage();
		browserPage.editHTMLPageProperties(test507pageUrl5);
		IHTMLPageAddOrEdit_ContentPage htmlAddPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IHTMLPageAddOrEdit_ContentPage.class);
		if(htmlAddPage.isLocked()){
			htmlAddPage.unlock();
		}
		htmlAddPage.setTitle(test578pageTitle53);
		htmlAddPage.saveAndPublish();

		// escape preview page
		IBackendSideMenuPage sideMenu = SeleniumPageManager.getBackEndPageManager().getPageObject(IBackendSideMenuPage.class);
		portletMenu = sideMenu.gotoAdminScreen();

		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test507pageUrl5), "ERROR - Authoring Server: Page ('"+test507pageUrl5+"') should exist at this moment in authoring server.");

		browserPage.pushElement(test507pageUrl5);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test578pageTitle53,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ('"+test507pageUrl5+"') push should not be in pending list.");
		publishingQueuePage.sleep(2);
		//Delete template and page in receiver
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(test507pageUrl5);
		browserPage.archiveElement(test507pageUrl5);
		browserPage.deletePage(test507pageUrl5);
		Assert.assertFalse(browserPage.doesElementExist(test507pageUrl5), "ERROR - Authoring Server: Page ('"+test507pageUrl5+"') should not exist at this moment in authoring server.");

		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test507pageUrl5), "ERROR - Receiver Server: Page ('"+test507pageUrl5+"') should exist at this moment in receiver server.");

		browserPage.editHTMLPageProperties(test507pageUrl5);
		htmlAddPage = SeleniumPageManager.getBackEndPageManager().getPageObject(IHTMLPageAddOrEdit_ContentPage.class);
		String title = htmlAddPage.getPageTitle();
		htmlAddPage.cancel();

		Assert.assertTrue(title.equals(test578pageTitle53), "ERROR - Receiver Server: Page ('"+test507pageUrl5+"') title doesn't match between authoring and receiver servers.");		

		//Delete template and page in receiver
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(test507pageUrl5);
		browserPage.archiveElement(test507pageUrl5);
		browserPage.deletePage(test507pageUrl5);
		Assert.assertFalse(browserPage.doesElementExist(test507pageUrl5), "ERROR - Receiver Server: Page ('"+test507pageUrl5+"') should not exist at this moment in receiver server.");
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
		browserPage.createHTMLPage(test582pageTitle6, test507templateTitle8, test582pageUrl6);

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
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ("+test582pageUrl6+") push should not be in pending list.");
		logoutAuthoringServer();


		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test582pageUrl6), "ERROR - Receiver Server: Page ('"+test582pageUrl6+"') should exist at this moment in receiver server.");

		//Delete template and page in receiver
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(test582pageUrl6);
		browserPage.archiveElement(test582pageUrl6);
		browserPage.deletePage(test582pageUrl6);
		Assert.assertFalse(browserPage.doesElementExist(test582pageUrl6), "ERROR - Receiver Server: Page ('"+test582pageUrl6+"') should not exist at this moment in receiver server.");
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
		browserPage.createUnpublishHTMLPage(test625pageTitle7, test625template625, test625pageUrl7);

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
		browserPage.pushElement(test625pageUrl7);
		//push page
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the page was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test625pageTitle7,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ("+test625pageUrl7+") push should not be in pending list.");
		logoutAuthoringServer();


		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test625pageUrl7), "ERROR - Receiver Server: Page ('"+test625pageUrl7+"') should exist at this moment in receiver server.");

		Assert.assertFalse(browserPage.isElementPublish(test625pageUrl7), "ERROR - Receiver Server: Page ('"+test625pageUrl7+"') should exist at this moment in receiver server.");

		Assert.assertTrue(browserPage.isElementUnpublish(test625pageUrl7), "ERROR - Receiver Server: Page ('"+test625pageUrl7+"') should exist at this moment in receiver server.");
		logoutReceiverServer();

		//connecting to authoring server
		portletMenu = callAuthoringServer();
		browserPage =portletMenu.getSiteBrowserPage();
		browserPage.publishElement(test625pageUrl7);

		browserPage.pushElement(test625pageUrl7);
		//push page
		publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the container was pushed
		isPushed = publishingQueuePage.isObjectBundlePushed(test625pageTitle7,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Page ("+test625pageUrl7+") push should not be in pending list.");

		//Delete template and page in authoring
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(test625pageUrl7);
		browserPage.archiveElement(test625pageUrl7);
		browserPage.deletePage(test625pageUrl7);
		Assert.assertFalse(browserPage.doesElementExist(test625pageUrl7), "ERROR - Authoring Server: Page ('"+test625pageUrl7+"') should not exist at this moment in authoring server.");
		logoutAuthoringServer();

		//Connect to receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		browserPage= portletMenu.getSiteBrowserPage();
		Assert.assertTrue(browserPage.doesElementExist(test625pageUrl7), "ERROR - Receiver Server: Page ('"+test625pageUrl7+"') should exist at this moment in receiver server.");

		Assert.assertFalse(browserPage.isElementUnpublish(test625pageUrl7), "ERROR - Receiver Server: Page ('"+test625pageUrl7+"') should exist at this moment in receiver server.");

		Assert.assertTrue(browserPage.isElementPublish(test625pageUrl7), "ERROR - Receiver Server: Page ('"+test625pageUrl7+"') should exist at this moment in receiver server.");

		//Delete template and page in receiver
		browserPage= portletMenu.getSiteBrowserPage();
		browserPage.unPublishElement(test625pageUrl7);
		browserPage.archiveElement(test625pageUrl7);
		browserPage.deletePage(test625pageUrl7);
		Assert.assertFalse(browserPage.doesElementExist(test625pageUrl7), "ERROR - Receiver Server: Page ('"+test625pageUrl7+"') should not exist at this moment in receiver server.");
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
		editPage.setLinkTitle(test524linkTitle1); 
		editPage.setLinkFolder(test524linkFolder1);
		editPage.setLinkType(IMenuLinkAddOrEdit_Page.INTERNAL_LINK);
		editPage.setLinkTarget(IMenuLinkAddOrEdit_Page.SAME_TARGET);
		editPage.setLinkOrder(test524linkOrder1);
		editPage.setLinkShowOnMenu(test524linkShowOnMenu1);
		editPage.setLinkInternalCode(test524linkInternalHost1, test524linkInternalFolder1, test524linkInternalUrl1);
		editPage.saveAndPublish();
		menuLinkPage= portletMenu.getMenuLinkPage();
		Assert.assertTrue(menuLinkPage.doesLinkExist(test524linkTitle1), "ERROR - Authoring Server: Menu Link ('"+test524linkTitle1+"') should not exist at this moment in authoring server.");

		menuLinkPage.pushLink(test524linkTitle1);
		menuLinkPage.sleep(1);
		//push link
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the link was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test524linkTitle1,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Menu link ("+test524linkTitle1+") push should not be in pending list.");
		logoutAuthoringServer();

		//Calling receiver server
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		menuLinkPage= portletMenu.getMenuLinkPage();

		Assert.assertTrue(menuLinkPage.doesLinkExist(test524linkTitle1), "ERROR - Receiver Server: Menu Link ('"+test524linkTitle1+"') should not exist at this moment in receiver server.");
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
		IMenuLinkAddOrEdit_Page editPage = menuLinkPage.editLink(test524linkTitle1);
		editPage.setLinkTitle(test524linkTitle1); 
		editPage.setLinkType(IMenuLinkAddOrEdit_Page.EXTERNAL_LINK);
		editPage.setLinkTarget(IMenuLinkAddOrEdit_Page.NEW_TARGET);
		editPage.setLinkShowOnMenu(test524linkShowOnMenu1);
		editPage.setLinkExternalCode(IMenuLinkAddOrEdit_Page.HTTP_PROTOCOL, test574linkExternalUrl1);
		editPage.saveAndPublish();
		menuLinkPage= portletMenu.getMenuLinkPage();
		Assert.assertTrue(menuLinkPage.doesLinkExist(test524linkTitle1), "ERROR - Authoring Server: Menu Link ('"+test524linkTitle1+"') should not exist at this moment in authoring server.");

		menuLinkPage.pushLink(test524linkTitle1);
		//push link
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the link was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test524linkTitle1,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Menu link ("+test524linkTitle1+") push should not be in pending list.");

		//delete Link
		portletMenu.sleep(2);
		menuLinkPage= portletMenu.getMenuLinkPage();
		menuLinkPage.deleteLink(test524linkTitle1);
		Assert.assertFalse(menuLinkPage.doesLinkExist(test524linkTitle1), "ERROR - Authoring Server: Menu Link ('"+test524linkTitle1+"') should not exist at this moment in authoring server.");

		logoutAuthoringServer();

		//Calling receiver server
		portletMenu = callReceiverServer();
		menuLinkPage= portletMenu.getMenuLinkPage();
		Assert.assertTrue(menuLinkPage.doesLinkExist(test524linkTitle1), "ERROR - Receiver Server: Menu Link ('"+test524linkTitle1+"') should not exist at this moment in receiver server.");

		editPage = menuLinkPage.editLink(test524linkTitle1);
		Assert.assertTrue(editPage.getLinkType().equals(IMenuLinkAddOrEdit_Page.EXTERNAL_LINK), "ERROR - Receiver Server: Menu Link ('"+test524linkTitle1+"') type doesn't match in authoring and receiver servers.");
		Assert.assertTrue(editPage.getLinkTarget().equals(IMenuLinkAddOrEdit_Page.NEW_TARGET), "ERROR - Receiver Server: Menu Link ('"+test524linkTitle1+"') target doesn't match in authoring and receiver servers.");
		Assert.assertTrue(editPage.getLinkExternalCode().equals(IMenuLinkAddOrEdit_Page.HTTP_PROTOCOL+test574linkExternalUrl1), "ERROR - Receiver Server: Menu Link ('"+test524linkTitle1+"') external link doesn't match in authoring and receiver servers.");
		editPage.cancel();

		//delete Link
		menuLinkPage= portletMenu.getMenuLinkPage();
		menuLinkPage.deleteLink(test524linkTitle1);
		Assert.assertFalse(menuLinkPage.doesLinkExist(test524linkTitle1), "ERROR - Receiver Server: Menu Link ('"+test524linkTitle1+"') should not exist at this moment in receiver server.");

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
		IPortletMenu portletMenu = callAuthoringServer(limitedUserEmailA,limitedUserPaswwordA);
		portletMenu.sleep(2);
		ISiteBrowserPage browserPage = portletMenu.getSiteBrowserPage();
		IMenuLinkAddOrEdit_Page editPage = browserPage.addMenuLinkInFolder(test575linkFolder2);
		editPage.setLinkTitle(test575linkTitle2); 
		editPage.setLinkType(IMenuLinkAddOrEdit_Page.CODE_LINK);
		editPage.setLinkOrder(test575linkOrder2);
		editPage.setLinkShowOnMenu(test575linkShowOnMenu2);
		editPage.setLinkCode(test575linkCode2);
		editPage.save();

		browserPage = portletMenu.getSiteBrowserPage();
		browserPage.selectFolder(test575linkFolder2);
		Assert.assertTrue(browserPage.doesElementExist(test575linkTitle2), "ERROR - Authoring Server: Menu Link ('"+test575linkTitle2+"') should not exist at this moment in authoring server.");

		browserPage.pushElement(test575linkTitle2);
		//push link
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the link was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test575linkTitle2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Menu link ("+test575linkTitle2+") push should not be in pending list.");
		logoutAuthoringServer();

		//Calling receiver server
		portletMenu = callReceiverServer();
		IMenuLinkPage menuLinkPage= portletMenu.getMenuLinkPage();

		Assert.assertTrue(menuLinkPage.doesLinkExist(test575linkTitle2), "ERROR - Receiver Server: Menu Link ('"+test575linkTitle2+"') should not exist at this moment in receiver server.");
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
		IPortletMenu portletMenu = callAuthoringServer(limitedUserEmailA,limitedUserPaswwordA);
		portletMenu.sleep(2);
		ISiteBrowserPage browserPage = portletMenu.getSiteBrowserPage();
		browserPage.selectFolder(test575linkFolder2);
		IMenuLinkAddOrEdit_Page editPage = browserPage.editMenuLink(test575linkTitle2);
		editPage.setLinkShowOnMenu(test576linkShowOnMenu22);
		editPage.setLinkCode(test576linkCode22);
		editPage.save();
		portletMenu.sleep(2);
		browserPage = portletMenu.getSiteBrowserPage();
		browserPage.selectFolder(test575linkFolder2);
		Assert.assertTrue(browserPage.doesElementExist(test575linkTitle2), "ERROR - Authoring Server: Menu Link ('"+test575linkTitle2+"') should not exist at this moment in authoring server.");

		browserPage.pushElement(test575linkTitle2);
		//push link
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the link was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test575linkTitle2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Menu link ("+test575linkTitle2+") push should not be in pending list.");
		logoutAuthoringServer();

		//call Authoring server
		portletMenu = callAuthoringServer();
		IMenuLinkPage menuLinkPage = portletMenu.getMenuLinkPage();

		//delete link
		menuLinkPage.deleteLink(test575linkTitle2);
		Assert.assertFalse(menuLinkPage.doesLinkExist(test575linkTitle2), "ERROR - Authoring Server: Menu Link ('"+test575linkTitle2+"') should not exist at this moment in authorin server.");
		logoutAuthoringServer();		

		//Calling receiver server
		portletMenu = callReceiverServer();
		menuLinkPage= portletMenu.getMenuLinkPage();

		Assert.assertTrue(menuLinkPage.doesLinkExist(test575linkTitle2), "ERROR - Receiver Server: Menu Link ('"+test575linkTitle2+"') should exist at this moment in receiver server.");

		editPage = menuLinkPage.editLink(test575linkTitle2);
		Assert.assertTrue(editPage.getLinkType().equals(IMenuLinkAddOrEdit_Page.CODE_LINK), "ERROR - Receiver Server: Menu Link ('"+test575linkTitle2+"') type doesn't match in authoring and receiver servers.");
		Assert.assertTrue(editPage.getLinkCode().equals(test576linkCode22), "ERROR - Receiver Server: Menu Link ('"+test575linkTitle2+"') external link doesn't match in authoring and receiver servers.");
		editPage.cancel();

		//delete link
		menuLinkPage= portletMenu.getMenuLinkPage();
		menuLinkPage.deleteLink(test575linkTitle2);
		Assert.assertFalse(menuLinkPage.doesLinkExist(test575linkTitle2), "ERROR - Receiver Server: Menu Link ('"+test575linkTitle2+"') should not exist at this moment in receiver server.");

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
		editPage.setLinkTitle(test589linkTitle3); 
		editPage.setLinkFolder(test589linkFolder3);
		editPage.setLinkType(IMenuLinkAddOrEdit_Page.INTERNAL_LINK);
		editPage.setLinkTarget(IMenuLinkAddOrEdit_Page.SAME_TARGET);
		editPage.setLinkOrder(test589linkOrder3);
		editPage.setLinkShowOnMenu(test589linkShowOnMenu3);
		editPage.setLinkInternalCode(test589linkInternalHost3, test589linkInternalFolder3, test589linkInternalUrl3);
		editPage.saveAndPublish();
		portletMenu.sleep(2);
		menuLinkPage = portletMenu.getMenuLinkPage();
		String bundleName = "test589";
		menuLinkPage.addToBundle(test589linkTitle3,bundleName);

		editPage = menuLinkPage.addLink();
		editPage.setLinkTitle(test589linkTitle4); 
		editPage.setLinkFolder(test589linkFolder4);
		editPage.setLinkType(IMenuLinkAddOrEdit_Page.CODE_LINK);
		editPage.setLinkOrder(test589linkOrder4);
		editPage.setLinkShowOnMenu(test589linkShowOnMenu4);
		editPage.setLinkCode(test589linkCode4);
		editPage.saveAndPublish();

		menuLinkPage = portletMenu.getMenuLinkPage();
		menuLinkPage.addToBundle(test589linkTitle4, bundleName);

		//push bundle
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		publishingQueuePage.getBundlesTab();
		String authoringServerBundleId = publishingQueuePage.pushPublishBundle(bundleName);

		//wait until 5 minutes to check if the bundle was pushed
		boolean isPushed = publishingQueuePage.isBundlePushed(authoringServerBundleId,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Menu Link push should not be in pending list.");

		//delete link
		menuLinkPage= portletMenu.getMenuLinkPage();
		menuLinkPage.deleteLink(test589linkTitle3);
		Assert.assertFalse(menuLinkPage.doesLinkExist(test589linkTitle3), "ERROR - Receiver Server: Menu Link ('"+test589linkTitle3+"') should not exist at this moment in receiver server.");
		menuLinkPage.deleteLink(test589linkTitle4);
		Assert.assertFalse(menuLinkPage.doesLinkExist(test589linkTitle4), "ERROR - Receiver Server: Menu Link ('"+test589linkTitle4+"') should not exist at this moment in receiver server.");

		logoutAuthoringServer();

		//Calling receiver server
		portletMenu = callReceiverServer();
		menuLinkPage= portletMenu.getMenuLinkPage();

		Assert.assertTrue(menuLinkPage.doesLinkExist(test589linkTitle3), "ERROR - Receiver Server: Menu Link ('"+test589linkTitle3+"') should exist at this moment in receiver server.");
		Assert.assertTrue(menuLinkPage.doesLinkExist(test589linkTitle4), "ERROR - Receiver Server: Menu Link ('"+test589linkTitle4+"') should  exist at this moment in receiver server.");

		//delete Link
		menuLinkPage.deleteLink(test589linkTitle3);
		Assert.assertFalse(menuLinkPage.doesLinkExist(test589linkTitle3), "ERROR - Receiver Server: Menu Link ('"+test589linkTitle3+"') should not exist at this moment in receiver server.");
		menuLinkPage.deleteLink(test589linkTitle4);
		Assert.assertFalse(menuLinkPage.doesLinkExist(test589linkTitle4), "ERROR - Receiver Server: Menu Link ('"+test589linkTitle4+"') should not exist at this moment in receiver server.");

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
		addSchemePage.setName(test652workflowSchemeName1);
		addSchemePage.sleep(2);
		addSchemePage.setDescription(test652workflowSchemeName1);
		addSchemePage.save();

		//add actions
		schemesPage = portletMenu.getWorkflowSchemesPage();
		IWorkFlowStepsAddOrEdit_Page schemeStepsPage = schemesPage.getEditSchemeStepsPage(test652workflowSchemeName1);
		schemeStepsPage.addWorkflowStep(test652workflowSchemeStep1);
		IWorkflowActionAddOrEdit_Page actionPage = schemeStepsPage.addActionToStep(test652workflowSchemeStep1);
		actionPage.setActionName(test652workflowActionName1);
		actionPage.setSaveContent(true);
		actionPage.setWhoCanUse("Admin User");
		actionPage.setWhoCanUse(limitedRole);
		actionPage.setAllowComment(true);
		actionPage.setUserCanAssign(true);
		actionPage.setAssignTo("Admin User");
		actionPage.sleep(2);
		actionPage.save();
		//adding subaction
		actionPage.addSubAction(test652worflowSubaction1);
		actionPage.save();

		//create structure 
		IStructuresPage structurePage = portletMenu.getStructuresPage();
		IStructureAddOrEdit_PropertiesPage addStructurePage = structurePage.getAddNewStructurePage();
		IStructureAddOrEdit_FieldsPage fieldsPage = addStructurePage.createNewStructure(test652contentStructureName9, "Content",test652contentStructureName9, demoServer,test652workflowSchemeName1);


		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(test652contentStructureName9Field1),"ERROR - The field ("+test652contentStructureName9Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(test652contentStructureName9Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test652contentStructureName9Field1),"ERROR - The field ("+test652contentStructureName9Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(test652contentStructureName9Field2),"ERROR - The field ("+test652contentStructureName9Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(test652contentStructureName9Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test652contentStructureName9Field2),"ERROR - The field ("+test652contentStructureName9Field2+") shoudl exist at this time");
		fieldsPage.sleep(3);

		//push Structure
		structurePage = portletMenu.getStructuresPage();
		structurePage.pushStructure(test652contentStructureName9);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test652contentStructureName9,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Structure ("+test652contentStructureName9+") push should not be in pending list.");

		//delete structure and workflow
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(test652contentStructureName9, true);
		structurePage.sleep(2);
		Assert.assertFalse(structurePage.doesStructureExist(test652contentStructureName9), "ERROR - Structure ('"+test652contentStructureName9+"') should not exist in authoring server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(test652workflowSchemeName1);
		stepsPage.deleteStep(test652workflowSchemeStep1);
		WorkflowPageUtil.deleteWorkflow(test652workflowSchemeName1,serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertFalse(schemesPage.doesWorkflowSchemeExist(test652workflowSchemeName1), "ERROR - Workflow ('"+test652workflowSchemeName1+"') should not exist in authoring server");

		logoutAuthoringServer();

		//call Receiver
		portletMenu=callReceiverServer();
		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(test652contentStructureName9),  "ERROR - Structure ('"+test652contentStructureName9+"') should  exist in receiver server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertTrue(schemesPage.doesWorkflowSchemeExist(test652workflowSchemeName1), "ERROR - Workflow ('"+test652workflowSchemeName1+"') should not exist in receiver server");

		//delete structure and workflow
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(test652contentStructureName9, true);
		structurePage.sleep(2);
		Assert.assertFalse(structurePage.doesStructureExist(test652contentStructureName9), "ERROR - Structure ('"+test652contentStructureName9+"') should not exist in receiver server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		stepsPage = schemesPage.getEditSchemeStepsPage(test652workflowSchemeName1);
		stepsPage.deleteStep(test652workflowSchemeStep1);
		WorkflowPageUtil.deleteWorkflow(test652workflowSchemeName1,serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
		stepsPage.sleep(2);
		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertFalse(schemesPage.doesWorkflowSchemeExist(test652workflowSchemeName1), "ERROR - Workflow ('"+test652workflowSchemeName1+"') should not exist in receiver server");

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
		addSchemePage.setName(test653workflowSchemeName2);
		addSchemePage.sleep(2);
		addSchemePage.setDescription(test653workflowSchemeName2);
		addSchemePage.save();
		addSchemePage.sleep(2);
		//add actions
		schemesPage = portletMenu.getWorkflowSchemesPage();
		IWorkFlowStepsAddOrEdit_Page schemeStepsPage = schemesPage.getEditSchemeStepsPage(test653workflowSchemeName2);
		schemeStepsPage.addWorkflowStep(test653workflowSchemeStep2);
		IWorkflowActionAddOrEdit_Page actionPage = schemeStepsPage.addActionToStep(test653workflowSchemeStep2);
		actionPage.setActionName(test653workflowActionName2);
		actionPage.setSaveContent(true);
		actionPage.setWhoCanUse("Admin User");
		actionPage.setWhoCanUse(limitedRole);
		actionPage.setAllowComment(false);
		actionPage.setUserCanAssign(true);
		actionPage.setAssignTo("Admin User");
		actionPage.sleep(2);
		actionPage.save();
		//adding subaction
		actionPage.addSubAction(test653worflowSubaction2);
		actionPage.save();

		schemesPage = portletMenu.getWorkflowSchemesPage();
		schemeStepsPage = schemesPage.getEditSchemeStepsPage(test653workflowSchemeName2);
		schemeStepsPage.addWorkflowStep(test653workflowSchemeStep3);
		actionPage = schemeStepsPage.addActionToStep(test653workflowSchemeStep3);
		actionPage.setActionName(test653workflowActionName3);
		actionPage.setSaveContent(false);
		actionPage.setWhoCanUse("Admin User");
		actionPage.setWhoCanUse(limitedRole);
		actionPage.setAllowComment(false);
		actionPage.setUserCanAssign(true);
		actionPage.setAssignTo("Admin User");
		actionPage.sleep(2);
		actionPage.save();
		//adding subaction
		actionPage.addSubAction(test653worflowSubaction3);
		actionPage.save();

		//create structure 
		IStructuresPage structurePage = portletMenu.getStructuresPage();
		IStructureAddOrEdit_PropertiesPage addStructurePage = structurePage.getAddNewStructurePage();
		IStructureAddOrEdit_FieldsPage fieldsPage = addStructurePage.createNewStructure(test653contentStructureName10, "Content",test653contentStructureName10, demoServer,test653workflowSchemeName2);


		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(test653contentStructureName10Field1),"ERROR - The field ("+test653contentStructureName10Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(test653contentStructureName10Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test653contentStructureName10Field1),"ERROR - The field ("+test653contentStructureName10Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(test653contentStructureName10Field2),"ERROR - The field ("+test653contentStructureName10Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(test653contentStructureName10Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test653contentStructureName10Field2),"ERROR - The field ("+test653contentStructureName10Field2+") shoudl exist at this time");
		fieldsPage.sleep(3);

		//addContent
		IContentSearchPage searchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage contentPage = searchPage.addContent(test653contentStructureName10);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put("title", test653contentTitle10);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put("body", test653contentTextArea10);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();
		contentPage.sleep(2);
		searchPage = portletMenu.getContentSearchPage();

		//move content to workflow step
		contentPage = searchPage.editContent(test653contentTitle10, test653contentStructureName10);
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
		contentPage.selectWorkflowAction(test653workflowActionName2, parameters);

		//push content
		searchPage = portletMenu.getContentSearchPage();
		searchPage.pushContent(test653contentTitle10,test653contentStructureName10);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test653contentTitle10,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+test653contentTitle10+") push should not be in pending list.");

		//delete structure and workflow
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(test653contentStructureName10, true);
		structurePage.sleep(2);
		Assert.assertFalse(structurePage.doesStructureExist(test653contentStructureName10), "ERROR - Structure ('"+test653contentStructureName10+"') should not exist in authoring server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(test653workflowSchemeName2);
		stepsPage.deleteStep(test653workflowSchemeStep3);
		stepsPage.deleteStep(test653workflowSchemeStep2);
		WorkflowPageUtil.deleteWorkflow(test653workflowSchemeName2,serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertFalse(schemesPage.doesWorkflowSchemeExist(test653workflowSchemeName2), "ERROR - Workflow ('"+test653workflowSchemeName2+"') should not exist in authoring server");

		logoutAuthoringServer();

		//call Receiver
		portletMenu=callReceiverServer();

		searchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(searchPage.doesContentExist(test653contentTitle10, test653contentStructureName10),  "ERROR - Content ('"+test653contentStructureName10+"') should  exist in receiver server");

		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(test653contentStructureName10),  "ERROR - Structure ('"+test653contentStructureName10+"') should  exist in receiver server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertTrue(schemesPage.doesWorkflowSchemeExist(test653workflowSchemeName2), "ERROR - Workflow ('"+test653workflowSchemeName2+"') should not exist in receiver server");

		IWorkflowTasksPage workflowTasksPage = portletMenu.getWorkflowTasksPage();
		Assert.assertTrue(workflowTasksPage.getWorflowTaskCurrentStep(test653contentTitle10, test653workflowSchemeName2).equals(test653workflowSchemeStep2),"ERROR - The workflow task ("+test653contentTitle10+") is not in the right step");

		//delete structure and workflow
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(test653contentStructureName10, true);
		structurePage.sleep(2);
		Assert.assertFalse(structurePage.doesStructureExist(test653contentStructureName10), "ERROR - Structure ('"+test653contentStructureName10+"') should not exist in receiver server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		stepsPage = schemesPage.getEditSchemeStepsPage(test653workflowSchemeName2);
		stepsPage.deleteStep(test653workflowSchemeStep3);
		stepsPage.deleteStep(test653workflowSchemeStep2);
		WorkflowPageUtil.deleteWorkflow(test653workflowSchemeName2,serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
		stepsPage.sleep(2);
		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertFalse(schemesPage.doesWorkflowSchemeExist(test653workflowSchemeName2), "ERROR - Workflow ('"+test653workflowSchemeName2+"') should not exist in receiver server");

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
		IContentAddOrEdit_ContentPage contentPage = contentSearchPage.addContent(test520contentStructureName1);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put("title", test520contentTitle1);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.WYSIWYG_FIELD);
		map.put("body", test520contentWYSIWYG1);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.save();
		contentPage.sleep(2);
		contentSearchPage = portletMenu.getContentSearchPage();
		//push content
		contentSearchPage.pushContent(test520contentTitle1,test520contentStructureName1);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test520contentTitle1,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+test520contentTitle1+") push should not be in pending list.");

		logoutAuthoringServer();

		//calling receiver
		portletMenu = callReceiverServer();
		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(contentSearchPage.doesContentExist(test520contentTitle1, test520contentStructureName1), "ERROR - Receiver Server: Content ("+test520contentTitle1+") should exist at this moment in receiver server.");
		Assert.assertTrue(contentSearchPage.isUnpublish(test520contentTitle1, test520contentStructureName1), "ERROR - Receiver Server: Content ("+test520contentTitle1+") should be unpublished at this moment in receiver server.");
		Assert.assertFalse(contentSearchPage.isPublish(test520contentTitle1, test520contentStructureName1), "ERROR - Receiver Server: Content ("+test520contentTitle1+") should not be live at this moment in receiver server.");

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
		IStructureAddOrEdit_FieldsPage fieldsPage = addStructurePage.createNewStructure(test496contentStructureName2, "Content",test496contentStructureName2, demoServer);

		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(test496contentStructureName2Field1),"ERROR - The field ("+test496contentStructureName2Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(test496contentStructureName2Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test496contentStructureName2Field1),"ERROR - The field ("+test496contentStructureName2Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(test496contentStructureName2Field2),"ERROR - The field ("+test496contentStructureName2Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(test496contentStructureName2Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test496contentStructureName2Field2),"ERROR - The field ("+test496contentStructureName2Field2+") shoudl exist at this time");
		fieldsPage.sleep(3);
		//create content
		IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage contentPage = contentSearchPage.addContent(test496contentStructureName2);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(test496contentStructureName2Field1.toLowerCase(), test496contentTitle2);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put(test496contentStructureName2Field2.toLowerCase(), test496contentTextArea2);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();

		contentPage.sleep(3);
		contentSearchPage = portletMenu.getContentSearchPage();
		//push content
		contentSearchPage.pushContent(test496contentTitle2,test496contentStructureName2);
		contentSearchPage.sleep(2);
		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test496contentTitle2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+test496contentTitle2+") push should not be in pending list.");

		logoutAuthoringServer();

		//calling receiver
		portletMenu = callReceiverServer();
		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(test496contentStructureName2),"ERROR - Structure ('"+test496contentStructureName2+"') doesn't exist in receiver server");

		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(contentSearchPage.doesContentExist(test496contentTitle2, test496contentStructureName2), "ERROR - Receiver Server: Content ("+test496contentTitle2+") should exist at this moment in receiver server.");
		Assert.assertFalse(contentSearchPage.isUnpublish(test496contentTitle2, test496contentStructureName2), "ERROR - Receiver Server: Content ("+test496contentTitle2+") should not be unpublished at this moment in receiver server.");
		Assert.assertTrue(contentSearchPage.isPublish(test496contentTitle2, test496contentStructureName2), "ERROR - Receiver Server: Content ("+test496contentTitle2+") should be live at this moment in receiver server.");
		logoutReceiverServer();

		//calling authoring server
		portletMenu = callAuthoringServer();
		//push to remove content
		contentSearchPage = portletMenu.getContentSearchPage();
		contentSearchPage.pushContent(test496contentTitle2, test496contentStructureName2,WebKeys.PUSH_TO_REMOVE, null, null, null, null, false);

		publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		isPushed = publishingQueuePage.isObjectBundlePushed(test496contentTitle2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+test496contentTitle2+") push should not be in pending list.");

		//delete structure and content
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(test496contentStructureName2, true);
		logoutAuthoringServer();

		//calling receiver
		portletMenu = callReceiverServer();
		portletMenu.sleep(2);
		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertFalse(contentSearchPage.doesContentExist(test496contentTitle2, test496contentStructureName2), "ERROR - Receiver Server: Content ("+test496contentTitle2+") should exist at this moment in receiver server.");		

		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(test496contentStructureName2),"ERROR - Structure ('"+test496contentStructureName2+"') doesn't exist in receiver server");

		//delete structure and content
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(test496contentStructureName2, true);

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
		IStructureAddOrEdit_FieldsPage fieldsPage = addStructurePage.createNewStructure(test519contentStructureName3, "Content",test519contentStructureName3, demoServer);

		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(test519contentStructureName3Field1),"ERROR - The field ("+test519contentStructureName3Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(test519contentStructureName3Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test519contentStructureName3Field1),"ERROR - The field ("+test519contentStructureName3Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(test519contentStructureName3Field2),"ERROR - The field ("+test519contentStructureName3Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(test519contentStructureName3Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test519contentStructureName3Field2),"ERROR - The field ("+test519contentStructureName3Field2+") shoudl exist at this time");

		//create content 1
		IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage contentPage = contentSearchPage.addContent(test519contentStructureName3);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(test519contentStructureName3Field1.toLowerCase(), test519contentTitle3);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put(test519contentStructureName3Field2.toLowerCase(), test519contentTextArea3);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();
		contentPage.sleep(2);
		listOfContent.add(test519contentTitle3);


		//create structure 2
		structurePage = portletMenu.getStructuresPage();
		addStructurePage = structurePage.getAddNewStructurePage();
		fieldsPage = addStructurePage.createNewStructure(test519contentStructureName4, "Content",test519contentStructureName4, demoServer);

		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(test519contentStructureName4Field1),"ERROR - The field ("+test519contentStructureName4Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(test519contentStructureName4Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test519contentStructureName4Field1),"ERROR - The field ("+test519contentStructureName4Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(test519contentStructureName4Field2),"ERROR - The field ("+test519contentStructureName4Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(test519contentStructureName4Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test519contentStructureName4Field2),"ERROR - The field ("+test519contentStructureName4Field2+") shoudl exist at this time");

		//create content 2
		contentSearchPage = portletMenu.getContentSearchPage();
		contentPage = contentSearchPage.addContent(test519contentStructureName4);

		fields = new ArrayList<Map<String, Object>>();
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(test519contentStructureName4Field1.toLowerCase(), test519contentTitle4);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put(test519contentStructureName4Field2.toLowerCase(), test519contentTextArea4);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();
		contentPage.sleep(2);
		listOfContent.add(test519contentTitle4);
		//create structure 3
		structurePage = portletMenu.getStructuresPage();
		addStructurePage = structurePage.getAddNewStructurePage();
		fieldsPage = addStructurePage.createNewStructure(test519contentStructureName5, "Content",test519contentStructureName5, demoServer);

		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(test519contentStructureName5Field1),"ERROR - The field ("+test519contentStructureName5Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(test519contentStructureName5Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test519contentStructureName5Field1),"ERROR - The field ("+test519contentStructureName5Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(test519contentStructureName5Field2),"ERROR - The field ("+test519contentStructureName5Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(test519contentStructureName5Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test519contentStructureName5Field2),"ERROR - The field ("+test519contentStructureName5Field2+") shoudl exist at this time");

		//create content 3
		contentSearchPage = portletMenu.getContentSearchPage();
		contentPage = contentSearchPage.addContent(test519contentStructureName5);

		fields = new ArrayList<Map<String, Object>>();
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(test519contentStructureName5Field1.toLowerCase(), test519contentTitle5);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put(test519contentStructureName5Field2.toLowerCase(), test519contentTextArea5);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();
		contentPage.sleep(2);
		listOfContent.add(test519contentTitle5);

		//push contents
		contentSearchPage = portletMenu.getContentSearchPage();
		contentSearchPage.pushContentList(listOfContent,null,test519contentSearchFilterKey);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test519contentTitle3,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+test519contentTitle3+","+test519contentTitle4+","+test519contentTitle5+") push should not be in pending list.");

		//delete content and structures
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(test519contentStructureName3, true);
		structurePage.deleteStructureAndContent(test519contentStructureName4, true);
		structurePage.deleteStructureAndContent(test519contentStructureName5, true);
		logoutAuthoringServer();

		//calling receiver
		portletMenu = callReceiverServer();
		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(test519contentStructureName3),"ERROR - Structure ('"+test519contentStructureName3+"') doesn't exist in receiver server");
		Assert.assertTrue(structurePage.doesStructureExist(test519contentStructureName4),"ERROR - Structure ('"+test519contentStructureName4+"') doesn't exist in receiver server");
		Assert.assertTrue(structurePage.doesStructureExist(test519contentStructureName5),"ERROR - Structure ('"+test519contentStructureName5+"') doesn't exist in receiver server");

		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(contentSearchPage.doesContentExist(test519contentTitle3, test519contentStructureName3), "ERROR - Receiver Server: Content ("+test519contentTitle3+") should exist at this moment in receiver server.");
		Assert.assertFalse(contentSearchPage.isUnpublish(test519contentTitle3, test519contentStructureName3), "ERROR - Receiver Server: Content ("+test519contentTitle3+") should not be unpublished at this moment in receiver server.");
		Assert.assertTrue(contentSearchPage.isPublish(test519contentTitle3, test519contentStructureName3), "ERROR - Receiver Server: Content ("+test519contentTitle3+") should be live at this moment in receiver server.");

		Assert.assertTrue(contentSearchPage.doesContentExist(test519contentTitle4, test519contentStructureName4), "ERROR - Receiver Server: Content ("+test519contentTitle4+") should exist at this moment in receiver server.");
		Assert.assertFalse(contentSearchPage.isUnpublish(test519contentTitle4, test519contentStructureName4), "ERROR - Receiver Server: Content ("+test519contentTitle4+") should not be unpublished at this moment in receiver server.");
		Assert.assertTrue(contentSearchPage.isPublish(test519contentTitle4, test519contentStructureName4), "ERROR - Receiver Server: Content ("+test519contentTitle4+") should be live at this moment in receiver server.");

		Assert.assertTrue(contentSearchPage.doesContentExist(test519contentTitle5, test519contentStructureName5), "ERROR - Receiver Server: Content ("+test519contentTitle5+") should exist at this moment in receiver server.");
		Assert.assertFalse(contentSearchPage.isUnpublish(test519contentTitle5, test519contentStructureName5), "ERROR - Receiver Server: Content ("+test519contentTitle5+") should not be unpublished at this moment in receiver server.");
		Assert.assertTrue(contentSearchPage.isPublish(test519contentTitle5, test519contentStructureName5), "ERROR - Receiver Server: Content ("+test519contentTitle5+") should be live at this moment in receiver server.");

		//delete content and structures
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(test519contentStructureName3, true);
		structurePage.deleteStructureAndContent(test519contentStructureName4, true);
		structurePage.deleteStructureAndContent(test519contentStructureName5, true);
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
		IStructureAddOrEdit_FieldsPage fieldsPage = addStructurePage.createNewStructure(test532contentStructureName6, "Content",test532contentStructureName6, demoServer);

		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(test532contentStructureName6Field1),"ERROR - The field ("+test532contentStructureName6Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(test532contentStructureName6Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test532contentStructureName6Field1),"ERROR - The field ("+test532contentStructureName6Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(test532contentStructureName6Field2),"ERROR - The field ("+test532contentStructureName6Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(test532contentStructureName6Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test532contentStructureName6Field2),"ERROR - The field ("+test532contentStructureName6Field2+") shoudl exist at this time");
		fieldsPage.sleep(3);
		//create content 1
		IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage contentPage = contentSearchPage.addContent(test532contentStructureName6);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(test532contentStructureName6Field1.toLowerCase(), test532contentTitle6);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put(test532contentStructureName6Field2.toLowerCase(), test532contentTextArea6);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();
		contentPage.sleep(2);

		String structureKey="Test-532";
		//push contents
		contentSearchPage = portletMenu.getContentSearchPage();
		contentSearchPage.pushContent(test532contentTitle6,structureKey);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test532contentTitle6,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+test532contentTitle6+") push should not be in pending list.");

		//delete content and structures
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(test532contentStructureName6, true);
		logoutAuthoringServer();

		//calling receiver
		portletMenu = callReceiverServer();
		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(test532contentStructureName6),"ERROR - Structure ('"+test532contentStructureName6+"') doesn't exist in receiver server");

		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(contentSearchPage.doesContentExist(test532contentTitle6, structureKey), "ERROR - Receiver Server: Content ("+test532contentTitle6+") should exist at this moment in receiver server.");

		//delete structure and contents
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(test532contentStructureName6, true);
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
		IContentAddOrEdit_ContentPage contentPage = contentSearchPage.addContent(test528contentStructureName7);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(test528contentStructureName7Field1.toLowerCase(), test528contentTitle7);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.WYSIWYG_FIELD);
		map.put(test528contentStructureName7Field2.toLowerCase(), test528contentTextArea7);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();
		contentPage.sleep(3);
		//Push Content
		contentSearchPage = portletMenu.getContentSearchPage();
		contentSearchPage.pushContent(test528contentTitle7,test528contentStructureName7);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test528contentTitle7,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+test528contentTitle7+") push should not be in pending list.");
		logoutAuthoringServer();

		//Calling receiver
		portletMenu=callReceiverServer();
		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(contentSearchPage.doesContentExist(test528contentTitle7, test528contentStructureName7),"ERROR - content ('"+test528contentTitle7+"') doesn't exist in receiver server");
		Assert.assertTrue(contentSearchPage.isPublish(test528contentTitle7, test528contentStructureName7),"ERROR - content ('"+test528contentTitle7+"') should be publish in receiver server");
		//unpublish content
		contentSearchPage.unpublish(test528contentTitle7, test528contentStructureName7);
		Assert.assertTrue(contentSearchPage.isUnpublish(test528contentTitle7, test528contentStructureName7),"ERROR - content ('"+test528contentTitle7+"') should be unpublish in receiver server");
		logoutReceiverServer();

		//calling authoring server
		portletMenu=callAuthoringServer();
		contentSearchPage = portletMenu.getContentSearchPage();
		//push to remove
		contentSearchPage.pushContent(test528contentTitle7, test528contentStructureName7, WebKeys.PUSH_TO_REMOVE, null, null, null, null, false);

		publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		isPushed = publishingQueuePage.isObjectBundlePushed(test528contentTitle7,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+test528contentTitle7+") push should not be in pending list.");

		//delete content
		contentSearchPage = portletMenu.getContentSearchPage();
		contentSearchPage.unpublish(test528contentTitle7, test528contentStructureName7);
		contentSearchPage.archive(test528contentTitle7, test528contentStructureName7);
		contentSearchPage.delete(test528contentTitle7, test528contentStructureName7);
		Assert.assertFalse(contentSearchPage.doesContentExist(test528contentTitle7, test528contentStructureName7),"ERROR - content ('"+test528contentTitle7+"') should not exist in authoring server");
		logoutAuthoringServer();

		//Calling receiver
		portletMenu=callReceiverServer();
		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertFalse(contentSearchPage.doesContentExist(test528contentTitle7, test528contentStructureName7),"ERROR - content ('"+test528contentTitle7+"') should not  exist in receiver server");
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
		IStructureAddOrEdit_FieldsPage fieldsPage = addStructurePage.createNewStructure(test572contentStructureName8, "Content",test572contentStructureName8, demoServer);

		//Test that the field doesn't exist
		Assert.assertFalse(fieldsPage.doesFieldExist(test572contentStructureName8Field1),"ERROR - The field ("+test572contentStructureName8Field1+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextField(test572contentStructureName8Field1, true, true, true, true, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test572contentStructureName8Field1),"ERROR - The field ("+test572contentStructureName8Field1+") shoudl exist at this time");

		Assert.assertFalse(fieldsPage.doesFieldExist(test572contentStructureName8Field2),"ERROR - The field ("+test572contentStructureName8Field2+") shoudl not exist at this time");
		fieldsPage = fieldsPage.addTextareaField(test572contentStructureName8Field2, "", "", "","", false, false, false);
		fieldsPage.sleep(2);
		Assert.assertTrue(fieldsPage.doesFieldExist(test572contentStructureName8Field2),"ERROR - The field ("+test572contentStructureName8Field2+") shoudl exist at this time");
		fieldsPage.sleep(3);
		logoutAuthoringServer();

		//login as limited user
		portletMenu = callAuthoringServer(limitedUserEmailA,limitedUserPaswwordA);
		portletMenu.sleep(3);
		IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage contentPage = contentSearchPage.addContent(test572contentStructureName8);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(test572contentStructureName8Field1.toLowerCase(), test572contentTitle8);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put(test572contentStructureName8Field2.toLowerCase(), test572contentTextArea8);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.sleep(2);
		contentPage.saveAndPublish();
		contentPage.sleep(3);
		//Push Content
		contentSearchPage = portletMenu.getContentSearchPage();
		contentSearchPage.pushContent(test572contentTitle8,test572contentStructureName8);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test572contentTitle8,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+test572contentTitle8+") push should not be in pending list.");
		logoutAuthoringServer();

		//Calling receiver
		portletMenu=callReceiverServer();
		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(contentSearchPage.doesContentExist(test572contentTitle8, test572contentStructureName8),"ERROR - content ('"+test572contentTitle8+"') doesn't exist in receiver server");
		Assert.assertTrue(contentSearchPage.isPublish(test572contentTitle8, test572contentStructureName8),"ERROR - content ('"+test572contentTitle8+"') should be publish in receiver server");

		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(test572contentStructureName8),"ERROR - Structure ('"+test572contentStructureName8+"') should exist in receiver server");

		logoutReceiverServer();

	}



	/**
	 * Edit existing content and push as limited user to update remote content 
	 * http://qa.dotcms.com/index.php?/cases/view/573
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc573_EditContentAndPushAsLimitedUser() throws Exception{
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer(limitedUserEmailA,limitedUserPaswwordA);
		portletMenu.sleep(3);
		IContentSearchPage contentSearchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage contentPage = contentSearchPage.editContent(test572contentTitle8,test572contentStructureName8);

		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(test572contentStructureName8Field1.toLowerCase(), test572contentTitle8);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXTAREA_FIELD);
		map.put(test572contentStructureName8Field2.toLowerCase(), test573contentTextArea82);
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
		contentSearchPage.pushContent(test572contentTitle8,test572contentStructureName8);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test572contentTitle8,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+test572contentTitle8+") push should not be in pending list.");
		logoutAuthoringServer();

		//Calling receiver
		portletMenu=callReceiverServer();
		IStructuresPage structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(test572contentStructureName8),"ERROR - Structure ('"+test572contentStructureName8+"') should exist in receiver server");

		contentSearchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(contentSearchPage.doesContentExist(test572contentTitle8, test572contentStructureName8),"ERROR - Content ('"+test572contentTitle8+"') should exist on receiver server");
		Assert.assertTrue(contentSearchPage.isPublish(test572contentTitle8, test572contentStructureName8),"ERROR - content ('"+test572contentTitle8+"') should be publish in receiver server");

		contentPage = contentSearchPage.editContent(test572contentTitle8,test572contentStructureName8);
		String text	= contentPage.getFieldValue(test572contentStructureName8Field2.toLowerCase());
		contentPage.cancel();
		Assert.assertTrue(text.equals(test573contentTextArea82),"ERROR - Content ('"+test572contentTitle8+"') are not the same in authoring and receiver servers");

		//delete structure and content
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(test572contentStructureName8, true);
		Assert.assertFalse(structurePage.doesStructureExist(test572contentStructureName8),"ERROR - Structure ('"+test572contentStructureName8+"') should not exist in receiver server");

		logoutReceiverServer();

		//calling authoring server
		portletMenu = callAuthoringServer();
		portletMenu.sleep(3);

		//delete structure
		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(test572contentStructureName8, true);
		Assert.assertFalse(structurePage.doesStructureExist(test572contentStructureName8),"ERROR - Structure ('"+test572contentStructureName8+"') should not exist in authoring server");

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
		addSchemePage.setName(test623workflowSchemeName3);
		addSchemePage.sleep(2);
		addSchemePage.setDescription(test623workflowSchemeName3);
		addSchemePage.save();
		addSchemePage.sleep(3);
		//add actions
		schemesPage = portletMenu.getWorkflowSchemesPage();
		IWorkFlowStepsAddOrEdit_Page schemeStepsPage = schemesPage.getEditSchemeStepsPage(test623workflowSchemeName3);
		schemeStepsPage.addWorkflowStep(test623workflowSchemeStep4);
		IWorkflowActionAddOrEdit_Page actionPage = schemeStepsPage.addActionToStep(test623workflowSchemeStep4);
		actionPage.setActionName(test623workflowActionName4);
		actionPage.setSaveContent(true);
		actionPage.setWhoCanUse("Admin User");
		actionPage.setWhoCanUse(limitedRole);
		actionPage.setAllowComment(false);
		actionPage.setUserCanAssign(true);
		actionPage.setAssignTo("Admin User");
		actionPage.sleep(2);
		actionPage.save();
		//adding subaction
		actionPage.addSubAction(test623worflowSubaction4);
		actionPage.save();

		//create structure 
		IStructuresPage structurePage = portletMenu.getStructuresPage();
		IStructureAddOrEdit_PropertiesPage addStructurePage = structurePage.getAddNewStructurePage();
		addStructurePage.createNewStructure(test623contentStructureName11, "File",test623contentStructureName11, demoServer,test623workflowSchemeName3);

		//create folder
		ISiteBrowserPage siteBrowserPage = portletMenu.getSiteBrowserPage();
		siteBrowserPage.createFolder(null, test623folderName2);
		siteBrowserPage.selectFolder(test623folderName2);
		IContentAddOrEdit_ContentPage contentPage = siteBrowserPage.addFileInFolder(test623folderName2, test623contentStructureName11);

		//Create content
		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(test623contentStructureName11Field1, test623contentTitle11);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.BINARY_FIELD);
		map.put(test623contentStructureName11Field2, test623contentTextArea11);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.saveAndPublish();
		contentPage.sleep(3);
		//push folder
		siteBrowserPage = portletMenu.getSiteBrowserPage();
		siteBrowserPage.pushFolder(test623folderName2);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test623folderName2,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Folder ("+test623folderName2+") push should not be in pending list.");

		//delete structure and workflow
		siteBrowserPage = portletMenu.getSiteBrowserPage();
		siteBrowserPage.deleteFolder(test623folderName2);
		Assert.assertFalse(siteBrowserPage.doesFolderExist(test623folderName2), "ERROR - Folder ('"+test623folderName2+"') should not exist in authoring server");

		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(test623contentStructureName11, true);
		structurePage.sleep(2);
		Assert.assertFalse(structurePage.doesStructureExist(test623contentStructureName11), "ERROR - Structure ('"+test623contentStructureName11+"') should not exist in authoring server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		IWorkFlowStepsAddOrEdit_Page stepsPage = schemesPage.getEditSchemeStepsPage(test623workflowSchemeName3);
		stepsPage.deleteStep(test623workflowSchemeStep4);
		WorkflowPageUtil.deleteWorkflow(test623workflowSchemeName3,serversProtocol+"://"+authoringServer+":"+authoringServerPort+"/");
		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertFalse(schemesPage.doesWorkflowSchemeExist(test623workflowSchemeName3), "ERROR - Workflow ('"+test623workflowSchemeName3+"') should not exist in authoring server");

		logoutAuthoringServer();

		//Calling authoring Server
		portletMenu = callReceiverServer();

		siteBrowserPage = portletMenu.getSiteBrowserPage();
		Assert.assertTrue(siteBrowserPage.doesFolderExist(test623folderName2),  "ERROR - Folder ('"+test623folderName2+"') should  exist in receiver server");

		siteBrowserPage.selectFolder(test623folderName2);
		Assert.assertTrue(siteBrowserPage.doesElementExist(test623fileName11),  "ERROR - Content ('"+test623contentTitle11+"') should  exist in receiver server");

		contentPage = siteBrowserPage.editFile(test623fileName11);
		if(contentPage.isPresentContentLockButton()){
			contentPage.clickLockForEditingButton();
		}
		contentPage.save();

		structurePage = portletMenu.getStructuresPage();
		Assert.assertTrue(structurePage.doesStructureExist(test623contentStructureName11),  "ERROR - Structure ('"+test623contentStructureName11+"') should  exist in receiver server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertTrue(schemesPage.doesWorkflowSchemeExist(test623workflowSchemeName3), "ERROR - Workflow ('"+test623workflowSchemeName3+"') should not exist in receiver server");

		//delete structure and workflow
		siteBrowserPage = portletMenu.getSiteBrowserPage();
		siteBrowserPage.deleteFolder(test623folderName2);
		Assert.assertFalse(siteBrowserPage.doesFolderExist(test623folderName2), "ERROR - Folder ('"+test623folderName2+"') should not exist in receiver server");

		structurePage = portletMenu.getStructuresPage();
		structurePage.deleteStructureAndContent(test623contentStructureName11, true);
		structurePage.sleep(2);
		Assert.assertFalse(structurePage.doesStructureExist(test623contentStructureName11), "ERROR - Structure ('"+test623contentStructureName11+"') should not exist in receiver server");

		schemesPage = portletMenu.getWorkflowSchemesPage();
		stepsPage = schemesPage.getEditSchemeStepsPage(test623workflowSchemeName3);
		stepsPage.deleteStep(test623workflowSchemeStep4);
		WorkflowPageUtil.deleteWorkflow(test623workflowSchemeName3,serversProtocol+"://"+receiverServer+":"+receiverServerPort+"/");
		stepsPage.sleep(2);
		schemesPage = portletMenu.getWorkflowSchemesPage();
		Assert.assertFalse(schemesPage.doesWorkflowSchemeExist(test623workflowSchemeName3), "ERROR - Workflow ('"+test623workflowSchemeName3+"') should not exist in receiver server");

		logoutReceiverServer();

	}

	/**
	 * From Content Manager Tab, select all on more than 50 contentlets and add to Bundle 
	 * http://qa.dotcms.com/index.php?/cases/view/624
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc624_PushMoreThan50ContentletInBundle() throws Exception{
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		IContentSearchPage searchPage = portletMenu.getContentSearchPage();
		//searchPage.selectAllContent(test624ContentStructure12);
		//searchPage.pushContentList(listOfContent, structure, contentSearchFilterKey);

		logoutAuthoringServer();

		//Calling receiver Server
		portletMenu = callReceiverServer();

		logoutReceiverServer();
	}

	/**
	 * Push Content with Content locked by different users on both sides
	 * http://qa.dotcms.com/index.php?/cases/view/628
	 * @throws Exception
	 */
	@Test (groups = {"PushPublishing"})
	public void tc628_PushContentLockedByDifferentUsers() throws Exception{
		//Calling authoring Server
		IPortletMenu portletMenu = callAuthoringServer();
		IContentSearchPage searchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage contentPage = searchPage.addContent(test628contentStructureName12);

		//Create content
		List<Map<String,Object>> fields = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(test628contentStructureName12Field1, test628contentTitle12);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.WYSIWYG_FIELD);
		map.put(test628contentStructureName12Field2, test628contentTextArea12);
		fields.add(map) ;
		contentPage.setFields(fields);
		contentPage.saveAndPublish();
		contentPage.sleep(3);
		//push content
		searchPage = portletMenu.getContentSearchPage();
		searchPage.pushContent(test628contentTitle12, test628contentStructureName12);

		IPublishingQueuePage publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		boolean isPushed = publishingQueuePage.isObjectBundlePushed(test628contentTitle12,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+test628contentTitle12+") push should not be in pending list.");

		logoutAuthoringServer();

		//Calling receiver Server
		portletMenu = callReceiverServer(limitedUserEmailB,limitedUserPaswwordB);
		searchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(searchPage.doesContentExist(test628contentTitle12,null),  "ERROR - Content ('"+test628contentTitle12+"') should  exist in receiver server");

		//lock content
		contentPage = searchPage.editContent(test628contentTitle12, test628contentStructureName12);
		contentPage.clickLockForEditingButton();
		contentPage.cancel();
		//validate that the content is locked
		searchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(searchPage.islock(test628contentTitle12, null), "ERROR - Receiver Server: Content ("+test628contentTitle12+") is not locked.");

		logoutReceiverServer();

		//Calling authoring Server
		portletMenu = callAuthoringServer(limitedUserEmailA,limitedUserPaswwordA);
		searchPage = portletMenu.getContentSearchPage();
		IContentAddOrEdit_ContentPage editPage = searchPage.editContent(test628contentTitle12,test628contentStructureName12);
		fields = new ArrayList<Map<String, Object>>();
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.TEXT_FIELD);
		map.put(test628contentStructureName12Field1, test628contentTitle12);
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("type", WebKeys.WYSIWYG_FIELD);
		map.put(test628contentStructureName12Field2, test628contentTextArea122);
		fields.add(map) ;
		editPage.setFields(fields);
		if(editPage.isPresentContentLockButton()){
			editPage.clickLockForEditingButton();
		}
		editPage.saveAndPublish();
		editPage.sleep(3);
		
		//lock content
		editPage = searchPage.editContent(test628contentTitle12, test628contentStructureName12);
		editPage.clickLockForEditingButton();
		editPage.cancel();
		
		//validate that the content is locked
		searchPage = portletMenu.getContentSearchPage();
		Assert.assertTrue(searchPage.islock(test628contentTitle12, null),"ERROR - Authoring Server: Content ("+test628contentTitle12+") is not locked.");
		//push object
		searchPage.pushContent(test628contentTitle12, test628contentStructureName12);

		publishingQueuePage = portletMenu.getPublishingQueuePage();
		//wait until 5 minutes to check if the content was pushed
		isPushed = publishingQueuePage.isObjectBundlePushed(test628contentTitle12,5000,60);
		Assert.assertTrue(isPushed, "ERROR - Authoring Server: Content ("+test628contentTitle12+") push should not be in pending list.");

		//delete content
		searchPage = portletMenu.getContentSearchPage();
		searchPage.unpublish(test628contentTitle12, null);
		searchPage.archive(test628contentTitle12, null);
		searchPage.delete(test628contentTitle12, null);
		Assert.assertFalse(searchPage.doesContentExist(test628contentTitle12,null),  "ERROR - Content ('"+test628contentTitle12+"') should  exist in authoring server");

		logoutReceiverServer();

		//Calling receiver Server
		portletMenu = callReceiverServer(limitedUserEmailB,limitedUserPaswwordB);
		
		searchPage = portletMenu.getContentSearchPage();
		//edit content
		Assert.assertTrue(searchPage.islock(test628contentTitle12, null),"ERROR - Authoring Server: Content ("+test628contentTitle12+") is not locked.");
		
		contentPage = searchPage.editContent(test628contentTitle12, test628contentStructureName12);
		String body = contentPage.getFieldValue(test628contentStructureName12Field2);
		Assert.assertTrue(body.equals(test628contentTextArea122),"ERROR - Receiver Server: Content ("+test628contentTitle12+") fields does not have the same value.");
		
		//delete content
		searchPage = portletMenu.getContentSearchPage();
		searchPage.unpublish(test628contentTitle12, null);
		searchPage.archive(test628contentTitle12, null);
		searchPage.delete(test628contentTitle12, null);
		Assert.assertFalse(searchPage.doesContentExist(test628contentTitle12,null),  "ERROR - Content ('"+test628contentTitle12+"') should  exist in authoring server");

		logoutReceiverServer();

	}
}