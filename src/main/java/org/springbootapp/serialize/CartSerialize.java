package org.springbootapp.serialize;

import java.io.IOException;

import org.springbootapp.entity.Cart;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CartSerialize extends StdSerializer<Cart>{

	protected CartSerialize(Class<Cart> t) {
		super(t);
	}
	
	public CartSerialize() {
		this(null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void serialize(Cart value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField("id", value.getId());
		gen.writeObjectField("product", value.getProduct());
		gen.writeObjectField("qty", value.getQty());
		gen.writeObjectField("price", value.getPrice());
		gen.writeObjectField("user_id", value.getUser_id());
		gen.writeObjectField("added_date", value.getAdded_date());
		gen.writeEndObject(); 
	}

}
