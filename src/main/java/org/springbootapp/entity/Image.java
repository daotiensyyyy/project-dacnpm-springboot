package org.springbootapp.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({ "product" })
public class Image extends Abstract {

	private String link;
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	public Image(Long id) {
		super(id);
	}

	@JsonCreator
	public Image(String link, String description) {
		super();
		this.link = link;
		this.description = description;
	}

	public Image(Long id, String link, String description) {
		super(id);
		this.link = link;
		this.description = description;
	}
}
