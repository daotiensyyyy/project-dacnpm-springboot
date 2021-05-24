package org.springbootapp.entity;

import java.util.Random;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.springbootapp.serialize.CartSerialize;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = CartSerialize.class)
public class Cart {
	@EmbeddedId
	private PkCartItem pk;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("product_id")
	@Include
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("user_id")
	@Include
	private User user;

	private Long qty;

//	@Column(updatable = true, insertable = true)
//	private int total;
//	@Column(updatable = false, insertable = false)
//	String added_date;

	public Cart(User user, Product product, Long qty) {
		super();
		this.pk = new PkCartItem(user.getId(), product.getId());
		this.user = user;
		this.product = product;
		this.qty = qty;
	}

	public Long increaseQuantity(Long qty) {
		this.qty += qty;
		return this.qty;
	}

	public Long decreaseQuantity(Long qty) {
		this.qty -= qty;
		return this.qty;
	}

	public int getOrderId() {
		Random r = new Random(System.currentTimeMillis());
		return 10000 + r.nextInt(20000);
	}

	@JsonCreator
	public Cart(@JsonProperty("product") Long productID, @JsonProperty("qty") Long quantity) {
		super();
		this.product = new Product(productID);
		this.qty = quantity;
	}

//	@Transient
//	String productName;
}
