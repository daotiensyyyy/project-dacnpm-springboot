package org.springbootapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springbootapp.serialize.CartSerialize;
import org.springbootapp.serialize.RevenueSerialize;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = RevenueSerialize.class)
public class Revenue extends Abstract{
	
	@Column(updatable = false, insertable = true)
	private int date;
	@Column(updatable = true, insertable = true)
	private double total;
}
