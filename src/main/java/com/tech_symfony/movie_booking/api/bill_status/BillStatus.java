package com.tech_symfony.movie_booking.api.bill_status;

import com.tech_symfony.movie_booking.api.bill.Bill;
import com.tech_symfony.movie_booking.model.NamedEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Entity
@Table(name = "bill_status")
public class BillStatus extends NamedEntity {


	@OneToMany(
		mappedBy = "status",
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private Set<Bill> bills;
}
