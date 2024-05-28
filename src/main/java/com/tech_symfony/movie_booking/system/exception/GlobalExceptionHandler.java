package com.tech_symfony.movie_booking.system.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.samples.petclinic.rest.controller.BindingErrorsResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {


	/**
	 * Handles exception thrown by Bean Validation on controller methods parameters
	 *
	 * @param e       The thrown exception
	 * @param request the current web request
	 * @return an empty response entity
	 */
	@ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
	@ResponseStatus(code = BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<?> handlerException(ConstraintViolationException e, WebRequest request) {
		final List<Object> errors = new ArrayList<>();
		e.getConstraintViolations().stream().forEach(fieldError -> {
			Map<String, Object> error = new HashMap<>();
			error.put("path", String.valueOf(fieldError.getPropertyPath()));
			error.put("message", fieldError.getMessage());
			errors.add(error);
		});
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		Map<String, Object> body = new HashMap<>();
		body.put("error", errors);
		return new ResponseEntity<>(body, httpStatus);
	}

//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<String> exception(Exception e) {
//		ObjectMapper mapper = new ObjectMapper();
//		ErrorInfo errorInfo = new ErrorInfo(e);
//		String respJSONstring = "{}";
//		try {
//			respJSONstring = mapper.writeValueAsString(errorInfo);
//		} catch (JsonProcessingException e1) {
//			e1.printStackTrace();
//		}
//		return ResponseEntity.badRequest().body(respJSONstring);
//	}

//	/**
//	 * Handles exception thrown by Bean Validation on controller methods parameters
//	 *
//	 * @param ex      The thrown exception
//	 * @param request the current web request
//	 * @return an empty response entity
//	 */
//	@ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
//	@ResponseStatus(code = BAD_REQUEST)
//	@ResponseBody
//	public ResponseEntity<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
//		BindingErrorsResponse errors = new BindingErrorsResponse();
//		BindingResult bindingResult = ex.getBindingResult();
//		HttpHeaders headers = new HttpHeaders();
//		if (bindingResult.hasErrors()) {
//			errors.addAllErrors(bindingResult);
//			headers.add("errors", errors.toJSON());
//		}
//		return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
//	}

	private class ErrorInfo {
		public final String className;
		public final String exMessage;

		public ErrorInfo(Exception ex) {
			this.className = ex.getClass().getName();
			this.exMessage = ex.getLocalizedMessage();
		}
	}
}
