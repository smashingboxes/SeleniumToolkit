package gateways;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

public class GatewayUtils {

    public static List<String> stepsOutput;

    public static String toPrettyFormat(String jsonString) {
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();

        return new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(json);
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
}
