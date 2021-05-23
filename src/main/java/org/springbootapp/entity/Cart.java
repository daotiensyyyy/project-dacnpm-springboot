package org.springbootapp.entity;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonSerialize(using = CartSerialize.class)
public class Cart extends Abstract {
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	private int qty;

	@Column(updatable = true, insertable = true)
	private int total;
	@Column(updatable = false, insertable = false)
	String added_date;

	public int getOrderId() {
		Random r = new Random(System.currentTimeMillis());
		return 10000 + r.nextInt(20000);
	}

//	@Transient
//	String productName;
}
