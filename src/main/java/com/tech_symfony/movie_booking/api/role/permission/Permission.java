package com.tech_symfony.movie_booking.api.role.permission;

import com.tech_symfony.movie_booking.api.role.Role;
import com.tech_symfony.movie_booking.model.BaseEntity;
import com.tech_symfony.movie_booking.model.NamedEntity;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "permissions")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permission extends NamedEntity {


	private String description;

}
