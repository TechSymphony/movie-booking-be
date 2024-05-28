package com.tech_symfony.movie_booking.api.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech_symfony.movie_booking.api.bill.Bill;
import com.tech_symfony.movie_booking.api.role.Role;
import com.tech_symfony.movie_booking.model.BaseUUIDEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "users")
public class User extends BaseUUIDEntity implements UserDetails {


	@NotBlank(message = "Full name must not be blank")
	@Column(name = "full_name")
	private String fullName;

	@NotBlank(message = "Email must not be blank")
	@Email(message = "Email should be valid")
	@Column(unique = true)
	private String email;

	@NotBlank(message = "Password must not be blank")
	@JsonIgnore
	private String password;


	@NotNull(message = "Date of birth must not be null")
	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth")
	private Date dateOfBirth;


	private String avatar;

	@Positive(message = "Point must be positive")
	private Integer point;
	@JsonIgnore
	private Boolean verify;
	@JsonIgnore
	@Column(name = "verify_account")
	private String verifyAccount;

	@JsonIgnore
	@Column(name = "verify_pass")
	private String verifyPass;


	private LocalDateTime createDate;

	@NotBlank(message = "Phone number must not be blank")
	@Column(name = "phone_number")
	private String phoneNumber;

	@NotNull(message = "Gender must not be null")
	@Enumerated(EnumType.STRING)
	private Gender gender;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays
			.stream(
				new String[]{"Geeks", "for", "Geeks"}
//				userInfoEntity
//				.getRoles()
//				.split(",")

			)
			.map(SimpleGrantedAuthority::new)
			.toList();
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		return this.email;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(
		name = "role_id",
		nullable = false
	)
	private Role role;

	@OneToMany(
		mappedBy = "user",
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private Set<Bill> bills;


}
