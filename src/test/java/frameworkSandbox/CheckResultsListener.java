package frameworkSandbox;

import gateways.practiTest.PractiTestRequests;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;

public class CheckResultsListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result){
        System.out.println("It Failed");
        try {
            runTest(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result){
        System.out.println("It Succeeded");
    }

    public static void runTest(ITestResult result) throws Exception {
        PractiTestRequests.executeTestRun(
                new File("/Users/darrinwhitley/Documents/workspace/slCreds"),
                PractiTestIntegration.projectId, PractiTestIntegration.testSetId,
                PractiTestIntegration.testId, result
        );
    }
}
