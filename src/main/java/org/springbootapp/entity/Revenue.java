package org.springbootapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Revenue extends Abstract{
	
	private String month;
//	@Column(updatable = true, insertable = false)
	private double total;
}
