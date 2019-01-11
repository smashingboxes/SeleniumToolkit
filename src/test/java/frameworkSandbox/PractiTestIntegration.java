package frameworkSandbox;

import framework.Commands;
import gateways.practiTest.PractiTestRequests;
import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Set;

public class PractiTestIntegration {

    public static String projectId = "10669";
    public static String testId = "1734823";
    public static String testSetId = "413400";
    public static String runDuration = "00:01:28";

    @Test
    public static void beginTest() throws Exception {
        File file = new File("/Users/darrinwhitley/Documents/workspace/slCreds");
        Commands.printStep("actionOne", "Step One");
        Commands.printStep("actionTwo", "Step Two");
        Commands.printStep("actionThree", "Step Three");

//        PractiTestRequests.executeTestRun(file, projectId, testSetId, testId, r);
    }
}
