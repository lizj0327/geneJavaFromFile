package com.aa.bb.md;

public class StringUtil {

	public static boolean empty(String line) {
		if(line == null || line.trim().equals("")) {
			return true;
		}
		return false;
	}
	
	public static String firstUpperCase(String str) {
		char[] chars = str.toCharArray();
		if(chars[0] >= 97 && chars[0]<=122) {
			chars[0] = (char)(chars[0] - 32);
		}
		return new String(chars);
	}
}
