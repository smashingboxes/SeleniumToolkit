package gateways.practiTest;

import gateways.GatewayConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;

public class PractiTestRequests {

    public static void executeTestRun(File f, String projectId, String testSetId, String testId) throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        byte[] encoding = PractiTestJSONUtils.getEncoding(f);
        String instanceId = "";

        try {
            // Create a response handler
            //Creating the instance
            HttpPost request = PractiTestJSONUtils.createInstance(projectId, testSetId, testId, encoding);
            HttpResponse response = httpclient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            instanceId = PractiTestJSONUtils.getInstanceId(responseBody);
            String prettyResponse = GatewayConfig.toPrettyFormat(responseBody);
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
            HttpPost request = PractiTestJSONUtils.runTest(projectId, instanceId, testSetId, testId, encoding);
            HttpResponse response = httpclient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);

            String prettyResponse = GatewayConfig.toPrettyFormat(responseBody);
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
