package org.springbootapp.utils;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenUtils {
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Long expired_date = 1000 * 60 * 5L;

	public static String generateToken(Map<String, Object> information) throws JsonProcessingException {
		information.put("expiredDate", new Date(System.currentTimeMillis() + expired_date));
		String encode = Encoder.encode(mapper.writeValueAsBytes(information));
		return encode;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getInfomationFromToken(String token)
			throws JsonParseException, JsonMappingException, IOException {
		byte[] decode = Decoder.decode(token);
		return mapper.readValue(decode, Map.class);
	}

}
