package frameworkSandbox;

import gateways.practiTest.PractiTestRequests;
import org.testng.annotations.Test;

import java.io.File;

public class PractiTestIntegration {

    public static String projectId = "10669";
    public static String testId = "1734823";
    public static String testSetId = "413400";
    public static String runDuration = "00:01:34";

    @Test
    public static void beginTest() throws Exception {
        File file = new File("/Users/darrinwhitley/Documents/workspace/slCreds");
        PractiTestRequests.executeTestRun(file, projectId, testSetId, testId, runDuration);
    }
}
