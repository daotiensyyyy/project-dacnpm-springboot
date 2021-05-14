package org.springbootapp.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springbootapp.serialize.ProductSerialize;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(name = "Product.graph", attributeNodes = { @NamedAttributeNode("category"),
		@NamedAttributeNode("images") })
@NamedEntityGraph(name = "Product.images", attributeNodes = { @NamedAttributeNode("images") })
@JsonSerialize(using = ProductSerialize.class)
public class Product extends Abstract {
	
	private String code;
	private String name;
	private int price;
	private String description;
	private double evaluate;
	private String exp;
	private int remain;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
	private Set<Image> images;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="category_id", nullable=false)
	private Category category;
	private boolean active;
	
	@JsonCreator
	public Product(Long id, String name, int price, String description, double evaluate, 
			Long category, Set<Image> images, boolean active ) {
		super(id);
		this.name = name;
		this.price = price;
		this.description = description;
		this.evaluate = evaluate;
		this.category = new Category(category);
		this.images = new HashSet<>();
		if (images != null) {
			images.forEach(image -> this.addImage(image));
		}
		this.active = active;
	}
	
	public Product(Long id) {
		super(id);
	}

	public void addImage(Image image) {
		this.images.add(image);
		image.setProduct(this);
	}

	public void setCategory(Category category) {
		this.category = category;
		category.getProducts().add(this);
	}
////	Cart
//	@ManyToMany(mappedBy = "products")
//	private List<CartEntity> carts = new ArrayList<>();

////	Order
//	@ManyToMany(mappedBy = "products")
//	private List<OrderEntity> orders = new ArrayList<>();

}
