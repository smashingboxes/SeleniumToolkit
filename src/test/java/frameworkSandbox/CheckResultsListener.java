package frameworkSandbox;

import gateways.practiTest.PractiTestRequests;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;

public class CheckResultsListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result){
        //Status = 2
        System.out.println("It Failed");
        try {
            runTest(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result){
        //Status = 1
        System.out.println("It Succeeded");
        try {
            runTest(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runTest(ITestResult result) throws Exception {
        File file = new File("/Users/darrinwhitley/Documents/workspace/slCreds");
        String [] kv = ReadFile.readFile(file, "sauceLabs");

        PractiTestRequests.executeTestRun(kv[0], kv[1], PractiTestIntegration.projectId,
                PractiTestIntegration.testSetId, PractiTestIntegration.testId, result);
    }
}
