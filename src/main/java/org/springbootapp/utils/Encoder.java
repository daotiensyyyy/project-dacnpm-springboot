package org.springbootapp.utils;

import java.util.Base64;
import java.util.List;

public class Encoder {
	public static String encode(byte[] input) {
		String encoded = Base64.getEncoder().encodeToString(input);
		List<String> list = Utilities.split(encoded);
		StringBuilder positive = new StringBuilder();
		StringBuilder negative = new StringBuilder();
		String extra = list.size() % 2 == 0 ? new String() : list.remove(list.size() - 1);
		for (int i = 0; i < list.size(); i++) {
			if (i % 2 == 0)
				positive.append(list.get(i));
		}
		for (int i = 0; i < list.size(); i++) {
			if (i % 2 != 0)
				negative.append(list.get(i));
		}
		return positive.append(negative).append(extra).toString();
	}
}