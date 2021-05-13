package org.springbootapp.serialize;

import java.io.IOException;

import org.springbootapp.entity.Product;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ProductSerialize extends StdSerializer<Product>{

	protected ProductSerialize(Class<Product> t) {
		super(t);
	}
	
	public ProductSerialize() {
		this(null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void serialize(Product value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField("id", value.getId());
		gen.writeObjectField("name", value.getName());
		gen.writeObjectField("code", value.getCode());
		gen.writeObjectField("price", value.getPrice());
		gen.writeObjectField("description", value.getDescription());
		gen.writeObjectField("evaluate", value.getEvaluate());
		gen.writeObjectField("exp", value.getExp());
		gen.writeObjectField("remain", value.getRemain());
		gen.writeObjectField("category", value.getCategory().getId());
		gen.writeObjectField("images", value.getImages());
		gen.writeObjectField("active", value.isActive());
		gen.writeEndObject(); 
	}

}
