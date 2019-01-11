package gateways.practiTest;

import gateways.GatewayUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.ITestResult;

public class PractiTestRequests {

    /**
     * PractiTest API Requests
     *
     * This method is in two parts
     * 1) Creates an instance in PractiTest and retrieves the instance id
     * 2) Runs the test in PractiTest utilizing the instance id
     *
     * @param  devEmail	       the developer email associated to the PractiTest account
     * @param  apiToken	       the api token that is associated to the PractiTest account
     * @param  projectId	   the associated project id identified in PractiTest
     * @param  testSetId	   the associated test set id identified in PractiTest
     * @param  testId          the associated test id identified in PractiTest
     * @param  result          the TestNG result object
     */
    public static void executeTestRun(String devEmail, String apiToken, String projectId, String testSetId,
                                      String testId, ITestResult result) throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        byte[] encoding = PractiTestJSONUtils.getEncoding(devEmail, apiToken);
        String instanceId = "";

        try {
            // Create a response handler
            //Creating the instance
            HttpPost request = PractiTestJSONUtils.createInstance(encoding, projectId, testSetId, testId);
            HttpResponse response = httpclient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());
            instanceId = PractiTestJSONUtils.getInstanceId(responseBody);
            String prettyResponse = GatewayUtils.toPrettyFormat(responseBody);

            if (statusCode == 200) {
                System.out.println("SUCCESS: " + prettyResponse);
            } else {
                System.out.println("ERROR: " + statusCode + ": " + prettyResponse);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            // Create a response handler
            //Run the test
            HttpPost request = PractiTestJSONUtils.runTest(encoding, projectId, instanceId, testSetId, testId, result);
            HttpResponse response = httpclient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());
            String prettyResponse = GatewayUtils.toPrettyFormat(responseBody);

            if (statusCode == 200) {
                System.out.println("SUCCESS: " + prettyResponse);
            } else {
                System.out.println("ERROR: " + statusCode + ": " + prettyResponse);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        System.out.println("----------------------------------------");

        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();
    }
}
