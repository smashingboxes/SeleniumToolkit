package framework;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import gateways.GatewayUtils;

public class CommandHelpers {

	/**
	 * Prints out the given steps ({@param action} and {@param desc}) in the console.
	 *
	 * Format: 2019/01/10 12:47:32 -- [{@param action}] -- {@param desc}
	 *
	 * Also adds each {@param desc} of the step to a list to be used for third-party capturing and reporting
	 *
	 * @param  action	the method executed for the step
	 * @param  desc 	short description of the action being done
	 */
	public static void printSteps(String action, String desc){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String outputStep = dateFormat.format(date).toString() + " -- [" + action + "] -- " + desc;
		System.out.println(outputStep);
		GatewayUtils.stepsOutput.add(outputStep.split(" -- ")[2]);
	}
}
