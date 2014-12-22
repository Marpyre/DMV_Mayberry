package smarple1MayberryBL;

import java.util.Collection;

import smarple1MayberryBO.Activity;
import smarple1MayberryBO.POI;

public class BLImpl {	
	
	public static Boolean isDangerous(POI poi){
		Collection<Activity> activities = poi.getActivities();

		for (Activity a : activities) {
			if (a.getType().equals(Activity.Code.Gang)) {
				return true;
			}
		}
		return false;
	}
	
}
