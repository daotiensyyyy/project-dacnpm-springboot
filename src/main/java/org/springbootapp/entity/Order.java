package org.springbootapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springbootapp.serialize.OrderSerialize;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = OrderSerialize.class)
public class Order extends Abstract {

	String order_id, payment_type, delivery_address;
	Long user_id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	Product product;
	int qty;
	double price;
//	@Column(updatable = false, insertable = false)
	String order_date;
	
	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", payment_type=" + payment_type + ", delivery_address="
				+ delivery_address + ", user_id=" + user_id + ", qty=" + qty + ", price="
				+ price + ", order_date=" + order_date + "]";
	}

	
}