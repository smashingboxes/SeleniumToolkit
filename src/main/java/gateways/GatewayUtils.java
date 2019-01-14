package gateways;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

public class GatewayUtils {

    public static List<String> stepsOutput;

    /**
     * Formats the returned {@param jsonString} in a readable format
     *
     * @param  jsonString   the JSON string that is returned from the PractiTest API call and will be printed in the
     *                          console
     */
    public static String toPrettyFormat(String jsonString) {
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();

        return new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(json);
    }

    /**
     * Calculates and returns the test run duration based on the given {@param millis} in a format that is acceptable
     * to the PractiTest API
     *
     * @param  milliSeconds   the absolute time to be convered into two digit hours, minutes, and seconds
     */
    public static String convertMillis(Long milliSeconds){
        Long seconds;
        Long minutes;
        Long hours;

        seconds = milliSeconds / 1000;
        minutes = seconds / 60;
        seconds = seconds % 60;
        hours = minutes / 60;
        minutes = minutes % 60;

        return adjustPartial(hours) + ":" + adjustPartial(minutes) + ":" + adjustPartial(seconds);
    }

    /**
     * Helper method that makes the hours, minutes, and seconds two digits if they are not two digits already. This
     * helps with formatting the time that is acceptable to the PractiTest API
     *
     * @param  partialTime   the pre-calculated time that needs to be returned as two digits
     */
    private static String adjustPartial(Long partialTime){
        if (partialTime < 10){
            return "0" + partialTime;
        } else {
            return partialTime.toString();
        }
    }
}
