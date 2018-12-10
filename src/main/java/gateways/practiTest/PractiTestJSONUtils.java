package gateways.practiTest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gateways.GatewayUtils;
import gateways.GatewayProps;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.testng.ITestResult;

import java.io.File;

public class PractiTestJSONUtils {

    public static byte[] getEncoding(File f){
        String[] kv = GatewayUtils.readFile(f, GatewayProps.practiTest);
        return Base64.encodeBase64((kv[0] + ":" + kv[1]).getBytes());
    }

    public static HttpPost createInstance(byte[] encoding, String projectId, String testSetId, String testId) throws Exception {
        String json_str = "{\"data\": { \"type\": \"instances\", \"attributes\": {\"test-id\": " +
                testId + ", \"set-id\": " + testSetId + "}  } }";
        return postRequest(uriInstance(projectId), json_str, encoding);
    }

    public static HttpPost runTest(byte[] encoding, String projectId, String instanceID, String testSetId, String testId, ITestResult result) throws Exception{
        String json_str = "{\"data\": { \"type\": \"instances\", \"attributes\": {\"set-id\": " + testSetId + ", " +
                "\"test-id\": " + testId + ", \"run-duration\": \"" +
                getDuration(result) + "\", \"instance-id\": " + instanceID + ", \"exit-code\": " + getTestStatus(result.getStatus()) + ", " +
                "\"automated-execution-output\": \"" +
                getMessage(result)
                + "\" }, \"steps\": {\"data\": [";

        Integer outputSize = GatewayUtils.stepsOutput.size();

        for (int i = 0; i < outputSize; i++){
            if (i != 0){ json_str += ", "; }

            json_str += "{\"name\": \"" + GatewayUtils.stepsOutput.get(i) + "\", \"status\": \"" +
                    stepStatus(result.getStatus(), i, outputSize) + "\"}";
        }

        json_str += "] }}} ";
        return postRequest(uriRun(projectId), json_str, encoding);
    }

    private static String getTestStatus(Integer status){
        if (status == 1){ return "0"; } else { return "1"; }
    }

    private static String getMessage(ITestResult result){
        if (result.getStatus() == 1){
            return "";
        } else {
            return result.getThrowable().getMessage()
                    .replace("\n", " ")
                    .replace("\"", "\\\"")
                    .substring(0, 255);
        }
    }

    private static String stepStatus(Integer status, Integer count, Integer outputSize){
        if (count == outputSize - 1 && status != 1){
            return "FAILED";
        } else {
            return "PASSED";
        }
    }

    public static HttpPost postRequest(String uri, String json_str, byte[] encoding) throws Exception{
        HttpPost request = new HttpPost(uri);
        request.setEntity(new StringEntity(json_str));
        request.setHeader("Authorization", "Basic " + new String(encoding));
        request.addHeader("content-type", "application/json");

        System.out.println("executing request " + request.getURI());
        return request;
    }

    public static String getInstanceId(String responseBody){
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(responseBody);
        String newInstanceId = "";

        if(jsonTree.isJsonObject()){
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonElement data = jsonObject.get("data");

            if(data.isJsonObject()){
                JsonObject dataObject = data.getAsJsonObject();
                newInstanceId = dataObject.get("id").toString();
            }
        }

        return newInstanceId;
    }

    public static String getDuration(ITestResult result){
        return GatewayUtils.convertMillis(result.getEndMillis() - result.getStartMillis());
    }

    public static String uriRun(String projectId){
        return "https://api.practitest.com/api/v2/projects/" + projectId + "/runs.json";
    }

    public static String uriInstance(String projectId){
        return "https://api.practitest.com/api/v2/projects/" + projectId + "/instances.json";
    }
}
