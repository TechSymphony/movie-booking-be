package com.tech_symfony.movie_booking.api.user;

import com.tech_symfony.movie_booking.api.bill.Bill;
import com.tech_symfony.movie_booking.api.role.Role;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

@Projection(name = "userInfo", types = {User.class})
interface UserInfoProjector {

	String getFullName();

	String getEmail();

	Date getDateOfBirth();

	String getAvatar();


	LocalDateTime getCreateDate();

	String getPhoneNumber();

	Gender getGender();


}
