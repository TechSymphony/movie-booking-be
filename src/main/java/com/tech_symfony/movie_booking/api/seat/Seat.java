package com.tech_symfony.movie_booking.api.seat;

import com.tech_symfony.movie_booking.api.room.Room;
import com.tech_symfony.movie_booking.api.seat_type.SeatType;
import com.tech_symfony.movie_booking.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "seats")
public class Seat extends BaseEntity {

	@NotNull
	@AssertTrue
	@Column(name = "status", columnDefinition = "boolean default true")
	private Boolean status;

	@NotEmpty
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(
		name = "room_id",
		nullable = false
	)
	private Room room;

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(
//            name = "seat_id",
//            referencedColumnName = "seat_id",
//            nullable = false
//    )
//    private SeatEntity seat;

	@NotEmpty
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(
		name = "seat_type_id",
		referencedColumnName = "id",
		nullable = false
	)
	private SeatType type;

	//	@OneToMany(
//		mappedBy = "seatRoom",
//		fetch = FetchType.LAZY,
//		cascade = CascadeType.ALL
//	)
//	private Set<TicketEntity> tickets;
	@Column
	@NotBlank(message = "seat room row must not be blank")
	@NotNull(message = "seat room row must not be null")
	private String row;

	@Column(name = "row_index")
	@Positive
	private Integer rowIndex;

	@Transient
	private Boolean isReserved;
}
