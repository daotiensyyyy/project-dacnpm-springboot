package org.springbootapp.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Abstract {

	private String username;
	private String email;
	private String password;
	private String address;
	private String phone;
	private String role;
	private String resetToken;
	private boolean active;
	
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
	
	
}
