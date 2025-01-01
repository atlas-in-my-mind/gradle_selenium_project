./gradlew --stop
./gradlew clean build
./gradlew build
./gradlew --refresh-dependencies


a) Commands to execute the TestSuite, TestSuiteFolder, TestClass, TestMethod :
1. To execute a specific suite XML file: ./gradlew runTest -PSuiteFile="src/main/java/com/company/test/suites/suite1.xml"
2. To execute all suite XML files in a folder: ./gradlew runTest -PSuiteFolder="src/main/java/com/company/test/suites"
3. To execute a specific test class: ./gradlew runTest -Pt="src/main/java/com/company/test/testcases/SeleniumTest.java"
                    or ./gradlew runTest -Pt="src/main/java/com/company/test/testcases/SeleniumTest"


b) Using Intellij Run/Debug Configuration during Test Execution (Used for debugging Tests) :
Best approach is to create Application in Run/Edit Configurations ->
Main class as TestRunner present in run folder: com.company.test.run.TestRunner

Pass the below commands for SuiteXML, SuiteFolder, TestClass paths :
For SuiteXML : -SuiteFolder src/main/java/com/company/test/suites
For TestClass : -t com/company/test/testcases/SeleniumTest.java




