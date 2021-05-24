package org.springbootapp.serialize;

import java.io.IOException;

import org.springbootapp.entity.OrderItem;
import org.springbootapp.entity.Product;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class OrderItemSerialize extends StdSerializer<OrderItem> {

	private static final long serialVersionUID = -3019191627126061759L;

	protected OrderItemSerialize(Class<OrderItem> t) {
		super(t);
	}

	public OrderItemSerialize() {
		this(null);
	}

	@Override
	public void serialize(OrderItem value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		Product product = value.getProduct();
		gen.writeObjectField("productCode", product.getCode());
		gen.writeObjectField("productName", product.getName());
		gen.writeObjectField("productPrice", product.getPrice());
		gen.writeObjectField("quantity", value.getQuantity());
		gen.writeEndObject();
	}
}