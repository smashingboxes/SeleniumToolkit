package frameworkSandbox;

import com.google.gson.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class PractiTestConnection {
    private static final String URI_PROJECTS = "https://api.practitest.com/api/v2/projects.json";
    private static final String URI_USERS = "https://api.practitest.com/api/v2/users.json";
    private static final String URI_INSTANCES = "https://api.practitest.com/api/v2/projects/10669/instances.json";
    private static final String URI_RUNS = "https://api.practitest.com/api/v2/projects/10669/runs.json";
    private static final String DEVELOPER_EMAIL = "account@smashingboxes.com";
    private static final String API_TOKEN = "d5fd11db10e4e2d5ebec99ed0be67bcb09165a50";
    static byte[] encoding = Base64.encodeBase64((DEVELOPER_EMAIL + ":" + API_TOKEN).getBytes());
    static String instanceId = "";

    public final static void main(String[] args) throws Exception {

        HttpClient httpclient = new DefaultHttpClient();

        try {
            HttpGet request = getRequest(URI_USERS);
            // Create a response handler
            HttpResponse response = httpclient.execute(request);
//            HttpResponse response = httpclient.execute(postRequest);
            int statusCode = response.getStatusLine().getStatusCode();
//            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            String prettyResponse = toPrettyFormat(responseBody);
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

//    ======================================


//    public final static void main(String[] args) throws Exception {
//
//        HttpClient httpclient = new DefaultHttpClient();
//
//        try {
//            // Create a response handler
//            //Creating the instance
//            HttpPost request = createInstance();
//            HttpResponse response = httpclient.execute(request);
//            int statusCode = response.getStatusLine().getStatusCode();
//            HttpEntity entity = response.getEntity();
//            String responseBody = EntityUtils.toString(entity);
//
//
//            instanceId = getInstanceId(responseBody);
//
//
//            String prettyResponse = toPrettyFormat(responseBody);
//            if (statusCode == 200) {
//                System.out.println("SUCCESS: " + prettyResponse);
//            } else {
//                System.out.println("ERROR: " + statusCode + ": " + prettyResponse);
//            }
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//
//
//        try {
//            // Create a response handler
//            //Run the test
//            HttpPost request = runTest(instanceId);
//            HttpResponse response = httpclient.execute(request);
//            int statusCode = response.getStatusLine().getStatusCode();
//            HttpEntity entity = response.getEntity();
//            String responseBody = EntityUtils.toString(entity);
//
//            String prettyResponse = toPrettyFormat(responseBody);
//            if (statusCode == 200) {
//                System.out.println("SUCCESS: " + prettyResponse);
//            } else {
//                System.out.println("ERROR: " + statusCode + ": " + prettyResponse);
//            }
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("----------------------------------------");
//
//        // When HttpClient instance is no longer needed,
//        // shut down the connection manager to ensure
//        // immediate deallocation of all system resources
//        httpclient.getConnectionManager().shutdown();
//    }

    //    ======================================

    public static HttpGet getRequest(String uri){
        HttpGet request;

        switch(uri){
            case(URI_PROJECTS): request = new HttpGet(URI_PROJECTS); break;
            case(URI_USERS): request = new HttpGet(URI_USERS); break;
            default: request = new HttpGet(URI_PROJECTS); break;
        }

        request.setHeader("Authorization", "Basic " + new String(encoding));
        request.addHeader("Content-Type", "application/json");

        return request;
    }

    //Create instance
    public static HttpPost createInstance() throws Exception {
//        String json_str = "{\"data\": { \"type\": \"instances\", \"attributes\": {\"set-id\": 413400, \"test-id\": 1734823, \"instance-id\": 12831635, \"exit-code\": 0, \"automated-execution-output\": \"THIS IS MY OUTPUT\" }}} ";
//        String json_str_steps = "{\"data\": {\"type\": \"instances\", \"attributes\": {\"set-id\": 413400, \"test-id\": 1734823, \"instance-id\": 12831635}, \"steps\": {\"data\": [{\"name\": \"step one\", \"expected-results\": \"result\", \"status\": \"FAILED\"}, {\"name\": \"step two\", \"expected-results\": \"result2\", \"status\": \"PASSED\"}] }}}";
        String json_str_newInstance = "{\"data\": { \"type\": \"instances\", \"attributes\": {\"test-id\": 1734823, \"set-id\": 413400}  } }";

        return postRequest(URI_INSTANCES, json_str_newInstance);
    }

    public static HttpPost runTest(String instanceID) throws Exception {
        String json_str = "{\"data\": { \"type\": \"instances\", \"attributes\": {\"set-id\": 413400, \"test-id\": 1734823, \"instance-id\": " + instanceID + ", \"exit-code\": 0, \"automated-execution-output\": \"THIS IS MY OUTPUT\" }}} ";
//        String json_str_steps = "{\"data\": {\"type\": \"instances\", \"attributes\": {\"set-id\": 413400, \"test-id\": 1734823, \"instance-id\": 12831635}, \"steps\": {\"data\": [{\"name\": \"step one\", \"expected-results\": \"result\", \"status\": \"FAILED\"}, {\"name\": \"step two\", \"expected-results\": \"result2\", \"status\": \"PASSED\"}] }}}";
//        String json_str_newInstance = "{\"data\": { \"type\": \"instances\", \"attributes\": {\"test-id\": 1734823, \"set-id\": 413400}  } }";

        return postRequest(URI_RUNS, json_str);
    }

    public static HttpPost postRequest(String uri, String json_str) throws Exception{
        HttpPost request;

        switch(uri){
            case(URI_INSTANCES): request = new HttpPost(URI_INSTANCES); break;
            case(URI_RUNS): request = new HttpPost(URI_RUNS); break;
            default: request = new HttpPost(URI_INSTANCES); break;
        }

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

//            JsonElement f2 = jsonObject.get("f2");

            if(data.isJsonObject()){
                JsonObject dataObject = data.getAsJsonObject();

//                JsonElement newInstanceID = dataObject.get("id");
                newInstanceId = dataObject.get("id").toString();
            }

        }

        return newInstanceId;
    }

    public static String toPrettyFormat(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }
}
