package org.springbootapp.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedEntityGraph(name = "Category.products", attributeNodes = @NamedAttributeNode(value = "products", subgraph = "images"), 
subgraphs = @NamedSubgraph(name = "images", attributeNodes = @NamedAttributeNode("images")))
public class Category extends Abstract {
	@Column(name = "name")
	private String name;
	private String description;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "category")
	private Set<Product> products = new HashSet<>();

	public Category(Long id) {
		this.id = id;
	}

	@JsonCreator
	public Category(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
}
