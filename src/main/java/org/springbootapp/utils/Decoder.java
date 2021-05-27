package org.springbootapp.utils;

import java.util.Base64;
import java.util.List;

public class Decoder {
	public static byte[] decode(String input) {
		List<String> list = Utilities.split(input);
		String extra = list.size() % 2 == 0 ? new String() : list.remove(list.size() - 1);
		int divideIndex = (list.size() / 2);
		StringBuilder builder = new StringBuilder();
		for (int i = divideIndex; i < list.size(); i++) {
			builder.append(list.get(i - divideIndex)).append(list.get(i));
		}
		String result = builder.append(extra).toString();

		return Base64.getDecoder().decode(result);
	}
}
