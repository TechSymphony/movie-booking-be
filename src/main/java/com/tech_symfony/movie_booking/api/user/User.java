package com.tech_symfony.movie_booking.api.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech_symfony.movie_booking.api.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	//	@Column(name = "full_name")
	private String fullName;

	@Column(unique = true)
	private String email;
	@JsonIgnore
	private String password;

	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	private String avatar;

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

	@Column(name = "phone_number")
	private String phoneNumber;

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

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(
		name = "role_id",
		nullable = false
	)
	private Role role;
//
//	@OneToMany(
//		mappedBy = "user",
//		fetch = FetchType.LAZY,
//		cascade = CascadeType.ALL
//	)
//	private Set<BillEntity> bills;
//
//	@OneToMany(
//		mappedBy = "user",
//		fetch = FetchType.LAZY,
//		cascade = CascadeType.ALL
//	)
//	private Set<CommentEntity> comments;

//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//		return authorities;
//	}


}
