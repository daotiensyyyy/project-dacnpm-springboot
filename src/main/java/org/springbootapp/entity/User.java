package org.springbootapp.entity;

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
public class User extends Abstract {

	private String username;
	private String email;
	private String password;
	private String address;
	private String phone;
	private String role;
	private String resetToken;
	private boolean active;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	private Set<Cart> items; 
	
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

	public User(Long id) {
		super(id);
	}

	
	
	
}
