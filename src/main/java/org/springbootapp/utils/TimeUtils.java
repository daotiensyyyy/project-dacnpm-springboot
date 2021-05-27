package org.springbootapp.utils;

import java.util.Date;

public class TimeUtils {
	public static boolean isExpired(Date date) {
		if (date.before(new Date(System.currentTimeMillis()))) {
			return true;
		}
		return false;
	}
}