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

import com.dotcms.qa.selenium.pages.backend.IConfigurationPage;
import com.dotcms.qa.selenium.pages.backend.IContainerAddOrEditPage;
import com.dotcms.qa.selenium.pages.backend.IContainersPage;
import com.dotcms.qa.selenium.pages.backend.ILoginPage;
import com.dotcms.qa.selenium.pages.backend.IPortletMenu;
import com.dotcms.qa.selenium.pages.backend.IPublishingEnvironments;
import com.dotcms.qa.selenium.pages.backend.IPublishingQueuePage;
import com.dotcms.qa.selenium.pages.backend.IRolesPage;
import com.dotcms.qa.selenium.pages.backend.ISiteBrowserPage;
import com.dotcms.qa.selenium.pages.backend.IStructureAddOrEdit_FieldsPage;
import com.dotcms.qa.selenium.pages.backend.IStructureAddOrEdit_PropertiesPage;
import com.dotcms.qa.selenium.pages.backend.IStructuresPage;
import com.dotcms.qa.selenium.pages.backend.ITemplateAddOrEditAdvanceTemplatePage;
import com.dotcms.qa.selenium.pages.backend.ITemplatesPage;
import com.dotcms.qa.selenium.pages.backend.IUsersPage;
import com.dotcms.qa.selenium.util.SeleniumConfig;
import com.dotcms.qa.selenium.util.SeleniumPageManager;
import com.dotcms.qa.util.UsersPageUtil;

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
	
	private String templateTitle1="Test 555";
	private String templateContainer1="Default 1";
	private String pageTitle1="Test 555";


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
			deletePreviousTest();

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

			/*Delete containers*/
			IPortletMenu portletMenu = callAuthoringServer();
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
			/* Delete structure*/
			IStructuresPage structurePage = portletMenu.getStructuresPage();
			if(structurePage.doesStructureExist(structureName)){
				structurePage.deleteStructureAndContent(structureName, true);
			}
			
			/* Delete template*/
			ITemplatesPage templatesPage = portletMenu.getTemplatesPage();
			if(templatesPage.doesTemplateExist(templateTitle1)){
				templatesPage.deleteTemplate(templateTitle1);
			}
			
			/*Delete limited user*/
			IUsersPage usersPage = portletMenu.getUsersPage();
			Map<String, String> fakeUser = usersPage.getUserProperties(limitedUserEmail);
			String fakeUserId = fakeUser.get("userId");
			if(fakeUserId != null && !fakeUserId.equals("")){
				UsersPageUtil.deleteUser(fakeUserId);
			}
			/* Delete limited role*/
			IRolesPage rolePage = portletMenu.getRolesPage();
			if(rolePage.doesRoleExist(limitedRole)){
				rolePage.deleteRole(limitedRole);
			}
			logoutAuthoringServer();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}

		//Receiver Server
		try{
			/*Containers Test*/

			/*Delete containers*/
			IPortletMenu portletMenu = callReceiverServer();
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
			/* Delete structure*/
			IStructuresPage structurePage = portletMenu.getStructuresPage();
			if(structurePage.doesStructureExist(structureName)){
				structurePage.deleteStructureAndContent(structureName, true);
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

			rolePage.addPermissionOnHost(limitedRole, demoServer, subpermissions,true, false, false, false, false, false);
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
		
		templatesPage = portletMenu.getTemplatesPage();
		Assert.assertTrue(templatesPage.doesTemplateExist(templateTitle1), "ERROR - Authoring Server: Template ('"+templateTitle1+"') should exist at this moment in authoring server.");
		
		//create test page
		ISiteBrowserPage siteBrowser= portletMenu.getSiteBrowserPage();
		siteBrowser.createHTMLPage(pageTitle1, templateTitle1);
		
		
		
		//Delete template
		templatesPage = portletMenu.getTemplatesPage();
		templatesPage.deleteTemplate(templateTitle1);
		Assert.assertFalse(templatesPage.doesTemplateExist(templateTitle1), "ERROR - Authoring Server: Template ('"+templateTitle1+"') should not exist at this moment in authoring server.");
		
	}
}