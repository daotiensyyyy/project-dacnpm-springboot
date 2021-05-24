package org.springbootapp.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springbootapp.serialize.OrderSerialize;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
@NoArgsConstructor
@JsonSerialize(using = OrderSerialize.class)
@NamedEntityGraph(name = "Order.items", attributeNodes = @NamedAttributeNode(value = "items", subgraph = "items.product"), subgraphs = @NamedSubgraph(name = "items.product", attributeNodes = @NamedAttributeNode("product")))
public class Order extends Abstract {
	private static final long serialVersionUID = 560226111253281070L;
	@Column(unique = true)
	private String orderCode;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	private BigDecimal total;
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	private String consigneeName;
	private String consigneePhone;
	private String address;
//	**
	private String payMethod;
	@OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<OrderItem> items;

	@JsonCreator
	public Order(@JsonProperty("customerID") Long userID, @JsonProperty("total") BigDecimal total,
			@JsonProperty("consigneeName") String consigneeName, @JsonProperty("consigneePhone") String consigneePhone,
			@JsonProperty("address") String address, @JsonProperty("payMethod") String payMethod,
			@JsonProperty("items") Set<OrderItem> items) {
		super();
		this.orderCode = getCode();
		this.user = userID != null ? new User(userID) : null;
		this.createdDate = new Date(System.currentTimeMillis());
		this.total = total;
		this.consigneeName = consigneeName;
		this.consigneePhone = consigneePhone;
		this.address = address;
		this.payMethod = payMethod;
		this.items = items;
	}

	public void addItem(Product product, Long quantity) {
		OrderItem item = new OrderItem(this, product, quantity);
		this.items.add(item);
	}
	
	public String getCode() {
		Random r = new Random(System.currentTimeMillis());
		return String.valueOf(10000 + r.nextInt(20000));
	}

}