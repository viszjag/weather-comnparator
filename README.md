<b>weather-comparator</b><br>
<b>Prerequisites to execute Test automation script</b><br>

1) Make sure java is installed on your system.

Also, set environment variables 'path' for Java for windows or linux machine.<br>                                                                 
<b>Follow steps as mentioned in below link:</b><br>
[Setup enviroment path for variables](https://docs.oracle.com/javase/tutorial/essential/environment/paths.html)

2) Install Maven and set environment variable for same.<br>                                                                                     
<b>Follow steps as mentioned in below link:</b><br>
[Install Maven on windows, linux.](http://www.baeldung.com/install-maven-on-windows-linux-mac)

3) Clone repository [https://github.com/viszjag/weather-comparator.git] in your system.

4) Configuration file <b>config_env.properties</b> file is present at path: /weather-comparator/src/main/java/Config/. Here you can set config values like url, city, browser(can also be passed via testng.xml), weather we need to run on local machine or remotely on browserstack, temperature unit, variance, api key, baseURI, etc.
For exeecution on browserstack, please provide username and access key in config file.

5) Open command prompt and navigate to directory path were repository was cloned or contains .git file
<b>Execute following command on command prompt:</b><br>

```bash
mvn clean test
```
This will execute the testscript.

6) Application execution log file will generated within the project folder - Application.log <br>
<b>Alternatively, html report is generated at /weather-comparator/target/surefire-reports/emailable-report.html</b><br>

7) User can also import project into eclipse or intellij IDE as a maven project.<br>
<b> Make sure you have installed maven plugin inside IDE. </b><br>
 Just run pom.xml inside project folder as: i) maven clean and then ii) maven test <br>

8) Also, can install testng plugin for IDE, and then run testng.xml as a TestNG Suite<br>

9) Tests related to UI and API are listed in different packages at com.ui.tests and com.api.tests respectively. <br> 
They also have separate testng suites files placed at /weather-comparator/TestSuites.




 
