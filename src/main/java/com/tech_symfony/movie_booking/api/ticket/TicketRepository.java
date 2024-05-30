package com.tech_symfony.movie_booking.api.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

//@RepositoryRestResource(excerptProjection = TicketProjector.class)
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

}
