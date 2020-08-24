package com.kgovt.utils;

public class AppUtilities {
	
	/**
	 * isNotNullAndNotEmpty is used to check given number is not null or not empty
	 * @param value
	 * @return true if string is not null and not empty
	 */
	public static boolean isNotNullAndNotEmpty(String value) {
		if(null == value) {
			return false;
		}
		value = value.trim();
		return value.length() >= 1 && !"null".equalsIgnoreCase(value);
	}

}
