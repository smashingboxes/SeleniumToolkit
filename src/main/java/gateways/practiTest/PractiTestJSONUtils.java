package gateways.practiTest;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import gateways.GatewayUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.testng.ITestResult;

public class PractiTestJSONUtils {

    /**
     * Establishes the base64 encoding that captures the {@param devEmail} and {@param apiToken}
     *
     * @param  devEmail	       the developer email associated to the PractiTest account
     * @param  apiToken 	   the api token associated to the PractiTest account
     */
    public static byte[] getEncoding(String devEmail, String apiToken){
        return Base64.encodeBase64((devEmail + ":" + apiToken).getBytes());
    }

    /**
     * Establishes the JSON string and returns the HttpPost request for creating a PractiTest instance
     *
     * @param  encoding	       the base64 encoding that captures the devEmail and apiToken
     * @param  projectId	   the associated project id identified in PractiTest
     * @param  testSetId	   the associated test set id identified in PractiTest
     * @param  testId          the associated test id identified in PractiTest
     */
    public static HttpPost createInstance(byte[] encoding, String projectId, String testSetId, String testId)
            throws Exception {
        String json_str = "{\"data\": { \"type\": \"instances\", \"attributes\": {\"test-id\": " + testId +
                ", \"set-id\": " + testSetId + "}  } }";
        return postRequest(uriInstance(projectId), json_str, encoding);
    }

    /**
     * Establishes the JSON string and returns the HttpPost request for running a test case in PractiTest. This also
     * captures each step and adds it to the JSON string
     *
     * @param  encoding	       the base64 encoding that captures the devEmail and apiToken
     * @param  projectId	   the associated project id identified in PractiTest
     * @param  testSetId	   the associated test set id identified in PractiTest
     * @param  testId          the associated test id identified in PractiTest
     */
    public static HttpPost runTest(byte[] encoding, String projectId, String instanceID, String testSetId,
                                   String testId, ITestResult result) throws Exception{
        String json_str = "{\"data\": { \"type\": \"instances\", \"attributes\": {\"set-id\": " + testSetId + ", " +
                "\"test-id\": " + testId + ", \"run-duration\": \"" + getDuration(result) + "\", \"instance-id\": " +
                instanceID + ", \"exit-code\": " + getTestStatus(result.getStatus()) +
                ", \"automated-execution-output\": \"" + getMessage(result) + "\" }, \"steps\": {\"data\": [";

        Integer outputSize = GatewayUtils.stepsOutput.size();

        for (int i = 0; i < outputSize; i++){
            if (i != 0){ json_str += ", "; }

            json_str += "{\"name\": \"" + GatewayUtils.stepsOutput.get(i) + "\", \"status\": \"" +
                    stepStatus(result.getStatus(), i, outputSize) + "\"}";
        }

        json_str += "] }}} ";
        return postRequest(uriRun(projectId), json_str, encoding);
    }

    /**
     * Helper method that parses through the {@param responseBody} to return the instance ID
     *
     * @param  responseBody	   the response body from the HTTPPost request for creating an instance
     */
    public static String getInstanceId(String responseBody){
        JsonElement jsonTree = new JsonParser().parse(responseBody);
        String newInstanceId = "";

        if(jsonTree.isJsonObject()){
            JsonElement data = jsonTree.getAsJsonObject().get("data");

            if(data.isJsonObject()){
                newInstanceId = data.getAsJsonObject().get("id").toString();
            }
        }

        return newInstanceId;
    }

    /**
     * Helper method that returns the proper test status to be sent to PractiTest.
     *
     * Note: The status code for TestNG is different than the status code for PractiTest
     * TestNG success return code = 1
     * PractiTest success return code = 0
     * PractiTest failed return code = 1
     *
     * @param  status	   the status code returned from the TestNG test run
     */
    private static String getTestStatus(Integer status){
        if (status == 1){ return "0"; } else { return "1"; }
    }

    /**
     * Ensures that the message attached to the JSON string for running tests is formatted correctly.
     *
     * Note: A message is attached to the string ONLY when the test has failed.
     * Note: The status code for TestNG is different than the status code for PractiTest
     * TestNG success return code = 1
     * PractiTest success return code = 0
     * PractiTest failed return code = 1
     *
     * @param  result	   the TestNG result that contains the test status and error message
     */
    private static String getMessage(ITestResult result){
        if (result.getStatus() == 1){
            return "";
        } else {
            return formatMessage(result.getThrowable().getMessage());
        }
    }

    /**
     * Formats the TestNG error message in a way that is acceptable to the PractiTest API for running tests.
     *
     * Note: A message is attached to the string ONLY when the test has failed.
     *
     * @param  message	   the error message from the TestNG result according to the test run
     */
    private static String formatMessage(String message){
        message.replace("\n", " ").replace("\"", "\\\"");

        if (message.length() > 255){
            message = message.substring(0, 255);
        }

        return message;
    }

    /**
     * Returns the PASSED/FAILED string for each step according to the step's {@param status}
     *
     * Note: A message is attached to the string ONLY when the test has failed.
     *
     * @param  status	        the status code returned from the TestNG test run
     * @param  count	        the iterated step indicating how many steps have been completed out of the total number
     *                              of steps
     * @param  outputSize       the total number of captured steps for the test run
     */
    private static String stepStatus(Integer status, Integer count, Integer outputSize){
        if (count == outputSize - 1 && status != 1){ return "FAILED"; } else { return "PASSED"; }
    }

    /**
     * Sets up the HttpPost request with the appropriate headers
     *
     * @param  uri	        the address that the request is pointing to depending on the type of action being done
     * @param  json_str	    the JSON string that will be passed through the request
     * @param  encoding     the base64 encoding that captures the devEmail and apiToken
     */
    private static HttpPost postRequest(String uri, String json_str, byte[] encoding) throws Exception{
        HttpPost request = new HttpPost(uri);
        request.setEntity(new StringEntity(json_str));
        request.setHeader("Authorization", "Basic " + new String(encoding));
        request.addHeader("content-type", "application/json");

        System.out.println("executing request " + request.getURI());
        return request;
    }

    /**
     * Calculates the duration of the test run from the given {@param result}
     *
     * @param  result	   the TestNG result that contains the start and end time for the test run
     */
    private static String getDuration(ITestResult result){
        return GatewayUtils.convertMillis(result.getEndMillis() - result.getStartMillis());
    }

    /**
     * Returns the PractiTest URI for test runs
     *
     * @param  projectId    the associated project id identified in PractiTest
     */
    private static String uriRun(String projectId){
        return "https://api.practitest.com/api/v2/projects/" + projectId + "/runs.json";
    }

    /**
     * Returns the PractiTest URI for creating an instance
     *
     * @param  projectId    the associated project id identified in PractiTest
     */
    private static String uriInstance(String projectId){
        return "https://api.practitest.com/api/v2/projects/" + projectId + "/instances.json";
    }
}
