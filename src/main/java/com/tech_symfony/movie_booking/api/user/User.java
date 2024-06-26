package com.tech_symfony.movie_booking.api.user;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech_symfony.movie_booking.api.bill.Bill;
import com.tech_symfony.movie_booking.api.role.Role;
import com.tech_symfony.movie_booking.model.BaseUUIDEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "users")
@RequiredArgsConstructor
public class User extends BaseUUIDEntity {


	@NotBlank(message = "Full name must not be blank")
	@Column(name = "full_name")
	private String fullName;

	@NotBlank(message = "Email must not be blank")
	@Email(message = "Email should be valid")
	@Column(unique = true)
	private String email;

	@NotBlank(message = "Password must not be blank")
	@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password = "123";


	//	@NotNull(message = "Date of birth must not be null")
	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth")
	@Future()
	private Date dateOfBirth;

	private String avatar = "";

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Provider must not be null")
	private Provider provider = Provider.LOCAL;

	@Column(name = "provider_id")
	private String providerId;


	@JsonIgnore
	private Boolean verify;
	@JsonIgnore
	@Column(name = "verify_account")
	private String verifyAccount;

	@JsonIgnore
	@Column(name = "verify_pass")
	private String verifyPass;

	@CreationTimestamp
	private LocalDateTime createDate;

	//	@NotBlank(message = "Phone number must not be blank")
	@Column(name = "phone_number")
	@Pattern(regexp = "^[0-9]{10}$", message = "Invalid number phone")
	private String phoneNumber;

	@NotNull(message = "Gender must not be null")
	@Enumerated(EnumType.STRING)
	private Gender gender = Gender.UNKNOWN;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "role_id",
		nullable = true
	)
	private Role role;

	@OneToMany(
		mappedBy = "user",
		fetch = FetchType.LAZY
	)
	private Set<Bill> bills;

}
