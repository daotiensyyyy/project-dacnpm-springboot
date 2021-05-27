package org.springbootapp.utils;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
	private static final String secretKey = "AHDVSG";

	public static List<String> split(String encoded) {
		int limit = encoded.length();
		int secretKeyLength = secretKey.length();
		List<String> list = new ArrayList<>();

		for (int start = 0; start < limit; start = start + secretKeyLength) {
			int end = start + secretKeyLength;
			String substring = end <= limit ? encoded.substring(start, end) : encoded.substring(start);
			list.add(substring);
		}
		return list;
	}
}
