@echo off

REM - assumes cygwin is installed and executables are on the path
echo 'Working dir:'
cd

set

REM - ensure clean workspace
REM rm -rf *

For /F "Tokens=*" %%I in ('\cygwin64\bin\date.exe +%%Y%%m%%d_%%H%%M%%S') Do Set QA_TestStartTime=%%I

set QA_StarterURL=s3://qa.dotcms.com/starters/3.2_qastarter_v.0.4b.zip
set QA_TomcatFolder=%WORKSPACE%\dotcms\dotserver\tomcat-8.0.18
set QA_TomcatLogFile=%QA_TomcatFolder%\logs\catalina.out
set QA_AccessLogFile=%QA_TomcatFolder%\logs\dotcms_access..$(date +%Y-%m-%d).log
For /F "Tokens=*" %%I in ('\cygwin64\bin\date.exe +%%Y-%%m-%%d') Do Set QA_AccessLogFile=%QA_TomcatFolder%\logs\dotcms_access..%%I
set QA_StarterFullFilePath=%QA_TomcatFolder%\webapps\ROOT\starter.zip

set QA_Milestone=%DOTCMS_VERSION%
set QA_RunLabel=%QA_Milestone%_dotCMSServer_%BUILD_NUMBER%_%QA_DB%_%QA_TestStartTime%
set QA_TestArtifactFilename=%QA_RunLabel%_Artifacts.tar.gz
set QA_DBInstance=%DOTCMS_VERSION%-%USERNAME%-%BUILD_NUMBER%

echo "***************************"
set
echo "***************************"

echo "Sending IP Address to %QA_SERVER_IP_URL%"
ipconfig | \cygwin64\bin\grep.exe -E -i "IPv4" | \cygwin64\bin\grep.exe -E -o "[0-9][0-9.]+" > ip.txt
aws s3 cp .\ip.txt %QA_SERVER_IP_URL%

echo "Initializing" > status.txt
aws s3 cp .\status.txt %QA_SERVER_STATUS_URL%

echo 'Cloning qa repo'
cd %WORKSPACE%
git clone git@github.com:dotCMS/qa.git
echo "Checking out master-%DOTCMS_VERSION% branch"
cd qa
git checkout master-%DOTCMS_VERSION%
cd %WORKSPACE%

echo 'Pulling down and extracting dotCMS build'
mkdir %WORKSPACE%\downloads
wget -q -O %WORKSPACE%\downloads\dotcms.zip %DOTCMS_ZIP_URL%
mkdir %WORKSPACE%\dotcms
pushd %WORKSPACE%\dotcms
unzip %WORKSPACE%\downloads\dotcms.zip > NUL

echo 'Pulling down and replacing starter'
echo 'QA_StarterURL=%QA_StarterURL%'
echo 'QA_StarterFullFilePath=%QA_StarterFullFilePath%'
aws s3 cp %QA_StarterURL% %QA_StarterFullFilePath%

echo 'Setting index pages to legacy setting'
sed -i 's/CMS_INDEX_PAGE = index/CMS_INDEX_PAGE = index.html/g' %QA_TomcatFolder%\webapps\ROOT\WEB-INF\classes\dotmarketing-config.properties

echo 'Creating and configuring DB'
pushd %WORKSPACE%\qa
set startRDS=false
if "%QA_DB%" == "Oracle" set startRDS=true
if "%QA_DB%" == "MSSQL" set startRDS=true
if "%startRDS%" == "true" (
	ant -DDBInstanceID=%QA_DBInstance% start-aws-db-server
	sleep 60
	dbstatus=``

	For /F "Tokens=*" %%I in ("aws rds describe-db-instances --db-instance-identifier %QA_DBInstance% | python -c 'import sys, json; print json.load(sys.stdin)["DBInstances"][0]["DBInstanceStatus"]'") Do Set dbstatus=%%I
	
	echo "dbstatus=%dbstatus%"
	while [ %dbstatus% != "available" ]
	do
		echo "waiting for DB Server to become available..."
		sleep 30
		For /F "Tokens=*" %%I in ("aws rds describe-db-instances --db-instance-identifier %QA_DBInstance% | python -c 'import sys, json; print json.load(sys.stdin)["DBInstances"][0]["DBInstanceStatus"]'") Do Set dbstatus=%%I
	done
	echo "dbstatus=%dbstatus%"
	dbserver=""
	For /F "Tokens=*" %%I in ("aws rds describe-db-instances --db-instance-identifier %QA_DBInstance% | python -c 'import sys, json; print json.load(sys.stdin)[\"DBInstances\"][0][\"Endpoint\"][\"Address\"]'") Do Set dbserver=%%I
	echo "dbserver=%dbserver%"
)
call ant create-db
call ant create-context-xml
popd

sed -i 's/-Xmx1536M/-Xmx1536M/g' bin\startup.bat
rem sed -i 's/start/run/g' bin\startup.bat

echo 'Starting dotCMS'
echo "Starting dotCMS" > status.txt
aws s3 cp .\status.txt %QA_SERVER_STATUS_URL%

call bin\startup.bat
sleep 30
logcount=`grep -c "org.apache.catalina.startup.Catalina.start Server startup in" %QA_TomcatLogFile%`
echo "logcount=%logcount%"
:loop1
if %logcount% LSS 1 (
	echo "sleeping..."
	sleep 10
	logcount=`grep -c "org.apache.catalina.startup.Catalina.start Server startup in" %QA_TomcatLogFile%`
	if %logcount% LSS 1 goto loop1
)
echo "logcount=%logcount%"


echo 'Building and deploying qa_automation plugin'
cd %WORKSPACE%\qa\plugins\com.dotcms.rest.qa_automation
.\gradlew jar
cp .\build\libs\com.dotcms.rest.qa_automation-0.1.jar %QA_TomcatFolder%\webapps\ROOT\WEB-INF\felix\load\.

echo 'Getting trial license'
cp %WORKSPACE%\qa\artifacts\license\trial.jsp %QA_TomcatFolder%\webapps\ROOT\trial.jsp
curl http://localhost:8080/trial.jsp

echo "Running" > status.txt
aws s3 cp .\status.txt %QA_SERVER_STATUS_URL%

date /T
REM polling looking for request to shutdown dotCMS server - until then keep the server running
logcount2=`grep -c "shutdown.jsp" %QA_AccessLogFile%`
echo "logcount2=%logcount2%"
:loop2
if %logcount2% LSS 1 (
	echo "running..."
	sleep 60
	logcount2=`grep -c "shutdown.jsp" %QA_AccessLogFile%`
	if %logcount2% LSS 1 goto loop2
)
echo "logcount2=%logcount2%"
date

echo "Shutting down dotCMS" > status.txt
aws s3 cp .\status.txt %QA_SERVER_STATUS_URL%

echo 'Shutting down dotCMS'
%WORKSPACE%\dotcms\bin\shutdown.bat
popd


pushd %WORKSPACE%\qa
ant drop-db
if "%startRDS%" == "true" (
	echo 'Shutting down RDS instance'
	ant -DDBInstanceID=%QA_DBInstance% shutdown-aws-db-server
)
popd 


echo 'Grabbing and packaging logs'
sleep 10
mkdir %WORKSPACE%\temp_log
pushd %WORKSPACE%\temp_log
mkdir %WORKSPACE%\temp_log\dotcms
cp -a %QA_TomcatFolder%\logs\ .\dotcms
tar -cvzf ..\%QA_TestArtifactFilename% .
popd
rm -rf %WORKSPACE%\temp_log\

echo 'Storing logs into s3'
aws s3 cp %WORKSPACE%\%QA_TestArtifactFilename% s3://qa.dotcms.com\testartifacts\%QA_TestArtifactFilename%
 
echo 'Cleaning up - preparing for another possible run'
rm -rf %WORKSPACE%\downloads
aws s3 rm %QA_SERVER_IP_URL%
aws s3 rm %QA_SERVER_STATUS_URL%