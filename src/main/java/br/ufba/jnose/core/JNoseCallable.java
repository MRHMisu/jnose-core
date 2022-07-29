package br.ufba.jnose.core;

import br.ufba.jnose.dto.TestClass;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JNoseCallable implements Callable<List<TestClass>> {

    private final static Logger LOGGER = Logger.getLogger(JNoseCallable.class.getName());

    private Path filePath;
    private String projectName;
    private Path startDir;
    private JNoseCore jNoseCore;


    public JNoseCallable(Path filePath, String projectName, Path startDir, JNoseCore jNoseCore) {
        this.filePath = filePath;
        this.projectName = projectName;
        this.startDir = startDir;
        this.jNoseCore = jNoseCore;
    }

    @Override
    public List<TestClass> call() throws Exception {
        List<TestClass> files = new ArrayList<>();

        if (filePath.getFileName().toString().lastIndexOf(".") != -1) {
            String fileNameWithoutExtension = filePath.getFileName().toString().substring(0, filePath.getFileName().toString().lastIndexOf(".")).toLowerCase();

            if (filePath.toString().toLowerCase().endsWith(".java") && (
                    fileNameWithoutExtension.matches("^.*test\\d*$") ||
                            fileNameWithoutExtension.matches("^.*tests\\d*$") ||
                            fileNameWithoutExtension.matches("^test.*") ||
                            fileNameWithoutExtension.matches("^tests.*") ||
                            fileNameWithoutExtension.matches("^testcase.*") ||
                            fileNameWithoutExtension.matches("^testcases.*") ||
                            fileNameWithoutExtension.matches("^.*testcase\\d*$") ||
                            fileNameWithoutExtension.matches("^.*testcases\\d*$")
            )) {

                Boolean isStartsWithTest = fileNameWithoutExtension.matches("^test.*");
                Boolean isStartsWithTests = fileNameWithoutExtension.matches("^tests.*");
                Boolean isStartWithTestCase = fileNameWithoutExtension.matches("^testcase.*");
                Boolean isStartWithTestCases = fileNameWithoutExtension.matches("^testcases.*");

                Boolean isEndsWithTest = fileNameWithoutExtension.matches("^.*test\\d*$");
                Boolean isEndsWithTests = fileNameWithoutExtension.matches("^.*tests\\d*$");
                Boolean isEndsWithTestCase = fileNameWithoutExtension.matches("^.*testcase\\d*$");
                Boolean isEndsWithTestCases = fileNameWithoutExtension.matches("^.*testcases\\d*$");


                TestClass testClass = new TestClass();
                testClass.setProjectName(projectName);
                testClass.setPathFile(filePath.toString());

                if (jNoseCore.isTestFile(testClass)) {
                    LOGGER.log(Level.INFO, "getFilesTest: " + testClass.getPathFile());
                    String productionFileName = "";
                    int index = 0;

                    if (isEndsWithTest) index = testClass.getName().toLowerCase().lastIndexOf("test");
                    if (isEndsWithTestCase) index = testClass.getName().toLowerCase().lastIndexOf("testcase");
                    if (isEndsWithTestCases) index = testClass.getName().toLowerCase().lastIndexOf("testcases");
                    if (isEndsWithTests) index = testClass.getName().toLowerCase().lastIndexOf("tests");

                    if (index > 0) {
                        if (isEndsWithTest) {// test...
                            productionFileName = testClass.getName().substring(0, testClass.getName().toLowerCase().lastIndexOf("test")) + ".java";
                        }
                        if (isEndsWithTestCase) {// testcase...
                            productionFileName = testClass.getName().substring(0, testClass.getName().toLowerCase().lastIndexOf("testcase")) + ".java";
                        }
                        if (isEndsWithTestCases) {// testcases...
                            productionFileName = testClass.getName().substring(0, testClass.getName().toLowerCase().lastIndexOf("testcases")) + ".java";
                        }
                        if (isEndsWithTests) {//tests...
                            productionFileName = testClass.getName().substring(0, testClass.getName().toLowerCase().lastIndexOf("tests")) + ".java";
                        }
                    } else {
                        if (isStartsWithTest) {// test...
                            System.out.println(testClass.getName());
                            productionFileName = testClass.getName().substring(4) + ".java";
                        }
                        if (isStartWithTestCase) {// testcase...
                            productionFileName = testClass.getName().substring(8) + ".java";
                        }
                        if (isStartWithTestCases) {// testcases...
                            productionFileName = testClass.getName().substring(9) + ".java";
                        }
                        if (isStartsWithTests) {// tests...
                            productionFileName = testClass.getName().substring(5) + ".java";
                        }
                    }

                    testClass.setProductionFile(jNoseCore.getFileProduction(startDir.toString(), productionFileName));

                    if (!testClass.getProductionFile().isEmpty()) {
                        jNoseCore.getTestSmells(testClass);
                        files.add(testClass);
                    }
                }
            }
        }
        return files;
    }
}
