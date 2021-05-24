package org.springbootapp.serialize;

import java.io.IOException;

import org.springbootapp.entity.Cart;
import org.springbootapp.entity.Product;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CartSerialize extends StdSerializer<Cart> {

	private static final long serialVersionUID = 1880223790881130861L;

	protected CartSerialize(Class<Cart> t) {
		super(t);
	}

	public CartSerialize() {
		this(null);
	}

	@Override
	public void serialize(Cart value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		Product product = value.getProduct();
		gen.writeObjectField("productID", product.getId());
		gen.writeObjectField("productCode", product.getCode());
		gen.writeObjectField("productName", product.getName());
		gen.writeObjectField("productPrice", product.getPrice());
		gen.writeObjectField("productDescription", product.getDescription());
		gen.writeObjectField("productImage",
				product.getImages() != null ? product.getImages().iterator().next().getLink() : null);
		gen.writeObjectField("exp", product.getExp());
		gen.writeObjectField("evaluate", product.getEvaluate());
		gen.writeObjectField("quantity", value.getQty());
		gen.writeEndObject();
	}

}