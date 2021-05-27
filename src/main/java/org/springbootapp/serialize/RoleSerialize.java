package org.springbootapp.serialize;

import java.io.IOException;

import org.springbootapp.entity.Role;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class RoleSerialize extends StdSerializer<Role> {

	private static final long serialVersionUID = 7000458020726245082L;

	protected RoleSerialize(Class<Role> t) {
		super(t);
	}

	public RoleSerialize() {
		this(null);
	}

	@Override
	public void serialize(Role value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField("name", value.getName());
		gen.writeEndObject();
	}
}