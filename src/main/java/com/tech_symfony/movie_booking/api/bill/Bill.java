package com.tech_symfony.movie_booking.api.bill;

import com.tech_symfony.movie_booking.api.ticket.Ticket;
import com.tech_symfony.movie_booking.api.user.User;
import com.tech_symfony.movie_booking.model.BaseUUIDEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.rest.core.annotation.RestResource;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "bills")
public class Bill extends BaseUUIDEntity {

	@Column(name = "create_time")
	@CreationTimestamp
	private Date createTime;

	@Column(name = "payment_at")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime paymentAt;

	@Digits(integer = 2, fraction = 0, message = "Changed point MUST be a number")
	@Min(value = 0, message = "Changed point MUST MUST be at least 0")
	@Max(value = 50, message = "Changed point MUST be less than or equal to 18")
	@Column(name = "change_point")
	private Integer changedPoint = 0;

	@Positive(message = "Total must be positive")
	private Double total;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "cancel_reason")
	@NotNull(message = "Cancel reason must not be null")
	private String cancelReason = "";

	@Column(name = "cancel_date")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime cancelDate;


	@OneToMany(
		mappedBy = "bill",
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private Set<Ticket> tickets;

	@ManyToOne
	@JoinColumn(
		name = "user_id",
		nullable = false
	)
	@NotNull(message = "User must not be null")
	private User user;


	@NotNull(message = "Status must not be null")
	@Enumerated(EnumType.ORDINAL)
	private BillStatus status = BillStatus.IN_PROGRESS;

	public void addTicket(
		Ticket ticket
	) {
		if (this.tickets == null) this.tickets = new HashSet<>();
		this.tickets.add(ticket);
		ticket.setBill(this);
	}

	@Transient
	String vnpayUrl;
}
