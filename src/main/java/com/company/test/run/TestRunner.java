package com.company.test.run;

import org.testng.TestNG;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static void main(String[] args) {
        String suiteFile = null;
        String suiteFolder = null;
        String testClass = null;

        TestNG testng = new TestNG();

        // Iterate over arguments to parse SuiteFile, SuiteFolder, and Test Class
        for (int i = 0; i < args.length; i++) {
            if ("-SuiteFile".equals(args[i])) {
                // If the argument is -SuiteFile, set the suite file path
                suiteFile = args[++i];
                testng.setTestSuites(java.util.Collections.singletonList(suiteFile));
            }
            else if ("-SuiteFolder".equals(args[i])) {
                // If the argument is -SuiteFolder, set the suite folder path and load all .xml files in it
                // Ensure there's a next argument before advancing the index
                if (i + 1 < args.length) {
                    suiteFolder = args[++i];
                } else {
                    System.err.println("Error: No folder path provided after -SuiteFolder.");
                    return;
                }

                File folder = new File(suiteFolder);

                if (folder.isDirectory()) {
                    File[] files = folder.listFiles((dir, name) -> name.endsWith(".xml"));
                    if (files != null && files.length > 0) {
                        List<String> suiteFiles = new ArrayList<>();
                        for (File file : files) {
                            suiteFiles.add(file.getAbsolutePath());
                        }
                        testng.setTestSuites(suiteFiles);
                    } else {
                        System.err.println("No XML files found in the folder: " + suiteFolder);
                        return;
                    }
                } else {
                    System.err.println("Provided path is not a directory: " + suiteFolder);
                    return;
                }
            }
            else if ("-t".equals(args[i])) {
                // Test class path (either fully qualified class name or file path)
                testClass = args[++i];

                // If the testClass is provided, strip everything before "com"
                if (testClass.contains("com")) {
                    // Strip everything before "com"
                    testClass = testClass.substring(testClass.indexOf("com"));
                }

                // If the testClass is a file path, convert it to a class name
                if (testClass.endsWith(".java")) {
                    // Strip .java extension and convert to class name format
                    testClass = testClass.substring(0, testClass.length() - 5);
                }
                testClass = testClass.replace('/', '.').replace('\\', '.');

                // Add the test class to TestNG
                try {
                    testng.setTestClasses(new Class[] { findClass(testClass) });
                } catch (Exception e) {
                    System.err.println("Failed to load test class: " + testClass);
                    e.printStackTrace();
                    return;
                }
            }
        }

        // Run TestNG
        if (suiteFile != null || suiteFolder != null || testClass != null) {
            testng.setVerbose(2);
            testng.run();
        } else {
            System.err.println("No valid suite file, suite folder, or test class provided.");
        }
    }

    // A helper method to load the class dynamically by its name
    private static Class<?> findClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }
}
