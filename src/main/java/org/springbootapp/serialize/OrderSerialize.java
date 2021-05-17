package org.springbootapp.serialize;

import java.io.IOException;

import org.springbootapp.entity.Order;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class OrderSerialize extends StdSerializer<Order>{

	protected OrderSerialize(Class<Order> t) {
		super(t);
	}
	
	public OrderSerialize() {
		this(null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void serialize(Order value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField("id", value.getId());
		gen.writeObjectField("order_id", value.getOrder_id());
		gen.writeObjectField("payment_type", value.getPayment_type());
		gen.writeObjectField("delivery_address", value.getDelivery_address());
		gen.writeObjectField("user_id", value.getUser_id());
		gen.writeObjectField("product", value.getProduct());
		gen.writeObjectField("qty", value.getQty());
		gen.writeObjectField("price", value.getPrice());
		gen.writeObjectField("order_date", value.getOrder_date());
		gen.writeEndObject(); 
	}

}
