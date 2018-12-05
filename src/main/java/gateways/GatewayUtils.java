package gateways;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GatewayUtils {

    public static List<String> stepsOutput = new ArrayList();

    @SuppressWarnings("resource")
    public static String[] readFile(File f, String gatewayApp){
        String[] kv = new String[2];

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String readLine = "";

            System.out.println("Reading file using Buffered Reader");

            while ((readLine = br.readLine()) != null) {
                String[] line = readLine.split("=");

                switch(gatewayApp){
                    case GatewayProps.sauceLabs:
                        if(line[0].equals("username")){
                            kv[0] = line[1];
                        } else if (line[0].equals("accessKey")){
                            kv[1] = line[1];
                        } break;
                    case GatewayProps.practiTest:
                        if(line[0].equals("practiTestDevEmail")){
                            kv[0] = line[1];
                        } else if (line[0].equals("practiTestAccessKey")){
                            kv[1] = line[1];
                        } break;
                    default: throw new NullPointerException();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!kv[0].equals(null) && !kv[1].equals(null)){
            return kv;
        } else {
            return null;
        }
    }

    public static String toPrettyFormat(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }

    public static String convertMillis(Long millis){
        Long seconds;
        Long minutes;
        Long hours;

        seconds = millis / 1000;
        minutes = seconds / 60;
        seconds = seconds % 60;
        hours = minutes / 60;
        minutes = minutes % 60;

        return adjustPartial(hours) + ":" + adjustPartial(minutes) + ":" + adjustPartial(seconds);
    }

    private static String adjustPartial(Long partialTime){
        if (partialTime < 10){
            return "0" + partialTime;
        } else {
            return partialTime.toString();
        }
    }

//    private fun adjustPartial(partialTime: Long) : String{
//        when (partialTime < 10){
//            true -> return "0$partialTime"
//            false -> return partialTime.toString()
//        }
//    }

//    fun convertMillis(millis: Long) : String {
//        var seconds: Long
//        var minutes: Long
//        val hours: Long
//                seconds = millis / 1000
//        minutes = seconds / 60
//        seconds = seconds % 60
//        hours = minutes / 60
//        minutes = minutes % 60
//
//        return "${adjustPartial(hours)}:${adjustPartial(minutes)}:${adjustPartial(seconds)}"
//    }
}
