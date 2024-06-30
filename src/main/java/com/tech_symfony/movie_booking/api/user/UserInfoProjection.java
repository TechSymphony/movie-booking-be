package com.tech_symfony.movie_booking.api.user;

import com.tech_symfony.movie_booking.api.user.attribute.Gender;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Date;
import java.time.LocalDateTime;

@Projection(name = "userInfo", types = {User.class})
interface UserInfoProjection {

	String getFullName();

	String getEmail();

	Date getDateOfBirth();

	String getAvatar();


	LocalDateTime getCreateDate();

	String getPhoneNumber();

	Gender getGender();


}
