package framework;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import gateways.GatewayUtils;

public class CommandHelpers {

	public static void printSteps(String action, String desc){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String outputStep = dateFormat.format(date).toString() + " -- [" + action + "] -- " + desc;
		System.out.println(outputStep);
		GatewayUtils.stepsOutput.add(outputStep.split(" -- ")[2]);
	}
}
