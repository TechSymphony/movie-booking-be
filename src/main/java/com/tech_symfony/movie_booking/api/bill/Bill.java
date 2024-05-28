package com.tech_symfony.movie_booking.api.bill;

import com.tech_symfony.movie_booking.api.bill_status.BillStatus;
import com.tech_symfony.movie_booking.api.ticket.Ticket;
import com.tech_symfony.movie_booking.api.user.User;
import com.tech_symfony.movie_booking.model.BaseUUIDEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "bills")
public class Bill extends BaseUUIDEntity {

	@Column(name = "create_time")
	@Temporal(TemporalType.DATE)
	private Date createTime;

	@Column(name = "payment_at")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime paymentAt;

	@Digits(integer = 2, fraction = 0, message = "Changed point MUST be a number")
	@Min(value = 0, message = "Changed point MUST MUST be at least 0")
	@Max(value = 50, message = "Changed point MUST be less than or equal to 18")
	@Column(name = "change_point")
	private Integer changedPoint;

	@Positive(message = "Total must be positive")
	private Double total;

	@Column(name = "transaction_id")
	@NotEmpty(message = "Transaction id must not be empty")
	private String transactionId;

	@Column(name = "cancel_reason")
	@NotNull(message = "Cancel reason must not be null")
	private String cancelReason;

	@Column(name = "cancel_date")
	private LocalDateTime cancelDate;


	@OneToMany(
		mappedBy = "bill",
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private Set<Ticket> tickets;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(
		name = "user_id",
		nullable = false
	)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(
		name = "status_id",
		nullable = false
	)
	private BillStatus status;


}
