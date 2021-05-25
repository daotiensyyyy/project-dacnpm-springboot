package org.springbootapp.serialize;

import java.io.IOException;

import org.springbootapp.entity.Revenue;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class RevenueSerialize extends StdSerializer<Revenue> {

	private static final long serialVersionUID = 1880223790881130861L;

	protected RevenueSerialize(Class<Revenue> t) {
		super(t);
	}

	public RevenueSerialize() {
		this(null);
	}

	@Override
	public void serialize(Revenue value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField("date", value.getDate());
		gen.writeObjectField("total", value.getTotal());
		gen.writeEndObject();
	}

}