package com.tech_symfony.movie_booking.api.user;

import com.tech_symfony.movie_booking.api.user.register.RegisterRequest;
import com.tech_symfony.movie_booking.api.user.register.TokenService;
import com.tech_symfony.movie_booking.model.BaseUnitTest;
import com.tech_symfony.movie_booking.system.mail.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest extends BaseUnitTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private TokenService tokenService;

	@Mock
	private EmailService emailService;

	@InjectMocks
	private UserService userService;


	@Test
	public void testRegisterSuccess() {
		RegisterRequest request = RegisterRequest.builder()
			.email("test@example.com")
			.password("password")
			.fullName("Test User")
			.build();

		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

		User savedUser = new User();
		savedUser.setId(UUID.randomUUID());
		savedUser.setEmail(request.getEmail());
		savedUser.setFullName(request.getFullName());
		savedUser.setPassword(request.getPassword());
		savedUser.setVerify(false);
		when(userRepository.save(any(User.class))).thenReturn(savedUser);

		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

		when(tokenService.generateToken(anyString())).thenReturn("testToken");

		doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

		String result = userService.register(request);

		assertEquals("Success", result);

		verify(userRepository, times(1)).findByEmail(anyString());
		verify(userRepository, times(1)).save(any(User.class));
		verify(tokenService, times(1)).generateToken(anyString());
		verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
	}

	@Test
	public void testRegisterEmailTaken() {
		RegisterRequest request = RegisterRequest.builder()
			.email("test@example.com")
			.password("password")
			.fullName("Test User")
			.build();

		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

		assertThrows(UserAlreadyExistsException.class, () -> userService.register(request));
	}

	@Test
	@Transactional
	public void testVerifyUserSuccess() {
		String token = "testToken";
		String userId = UUID.randomUUID().toString();
		User user = new User();
		user.setVerify(false);

		when(tokenService.extractSubject(anyString())).thenReturn(userId);
		when(userRepository.findByIdAndVerifyFalse(any(UUID.class))).thenReturn(Optional.of(user));
		when(tokenService.isTokenExpired(anyString())).thenReturn(false);

		userService.verifyUser(token);

		verify(userRepository, times(1)).findByIdAndVerifyFalse(any(UUID.class));
		verify(tokenService, times(1)).extractSubject(anyString());
		verify(tokenService, times(1)).isTokenExpired(anyString());
		assertEquals(true, user.getVerify());
	}

	@Test
	public void testVerifyUserNotFound() {
		String token = "testToken";
		String userId = UUID.randomUUID().toString();

		when(tokenService.extractSubject(anyString())).thenReturn(userId);
		when(userRepository.findByIdAndVerifyFalse(any(UUID.class))).thenReturn(Optional.empty());

		assertThrows(UsernameNotFoundException.class, () -> userService.verifyUser(token));
	}

	@Test
	public void testVerifyUserTokenExpired() {
		String token = "testToken";
		String userId = UUID.randomUUID().toString();
		User user = new User();
		user.setVerify(false);

		when(tokenService.extractSubject(anyString())).thenReturn(userId);
		when(userRepository.findByIdAndVerifyFalse(any(UUID.class))).thenReturn(Optional.of(user));
		when(tokenService.isTokenExpired(anyString())).thenReturn(true);

		assertThrows(JwtException.class, () -> userService.verifyUser(token));
	}
}
