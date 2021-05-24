package org.springbootapp.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class PkOrderItem implements Serializable {
	private static final long serialVersionUID = 2546814589424177325L;
	private Long orderId;
	private Long productId;
}