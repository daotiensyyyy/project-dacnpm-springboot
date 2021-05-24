package org.springbootapp.entity;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "User.items", attributeNodes = @NamedAttributeNode(value = "items", subgraph = "items.graph"), subgraphs = {
		@NamedSubgraph(name = "items.graph", attributeNodes = {
				@NamedAttributeNode(value = "product", subgraph = "product.graph") }),
		@NamedSubgraph(name = "product.graph", attributeNodes = @NamedAttributeNode("images")) })
@NamedEntityGraph(name = "User.orders", attributeNodes = @NamedAttributeNode(value = "orders", subgraph = "Order.graph"), subgraphs = {
		@NamedSubgraph(name = "Order.graph", attributeNodes = { @NamedAttributeNode("payMethod"),
				@NamedAttributeNode(value = "items", subgraph = "OrderItem.graph") }),
		@NamedSubgraph(name = "OrderItem.graph", attributeNodes = @NamedAttributeNode("product")) })
public class User extends Abstract {

	private String username;
	private String email;
	private String password;
	private String address;
	private String phone;
	private String role;
	private String resetToken;
	private boolean active;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Order> orders;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	private Set<Cart> items; 
	
	public User(Long id) {
		super(id);
	}
	
	public User(String username, String email, String password, String address, String phone, String role,
			boolean active) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.address = address;
		this.phone = phone;
		this.role = role;
		this.active = active;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + ", password=" + password + ", address=" + address
				+ ", phone=" + phone + ", role=" + role + ", resetToken=" + resetToken + ", active=" + active + "]";
	}	
	
	
	public void addCartItem(Product product, Long quantity) {
		Cart cartItem = new Cart(this, product, quantity);
		if (!this.items.contains(cartItem)) {
			this.items.add(cartItem);
			return;
		}
		items.forEach(item -> {
			if (item.equals(cartItem))
				item.increaseQuantity(quantity);
		});
	}

	public void updateCart(Product product, String action) {
		for (Iterator<Cart> iterator = this.items.iterator(); iterator.hasNext();) {
			Cart item = iterator.next();
			if (item.getProduct().equals(product)) {
				switch (action) {
				case "increase":
					item.increaseQuantity(1L);
					break;
				case "decrease":
					if (item.getQty() > 1) {
						item.decreaseQuantity(1L);
						return;
					}
					iterator.remove();
					break;
				default:
					break;
				}
			}
		}
	}

	public void removeCartItem(Product product) {
		for (Iterator<Cart> iterator = this.items.iterator(); iterator.hasNext();) {
			Cart item = iterator.next();
			if (item.getProduct().equals(product)) {
				iterator.remove();
			}
		}
	}
	
	public void addOrder(Order order) {
		this.orders.add(order);
		order.setUser(this);
	}
	
	
}
