package ManyToOne.java.ManyToOne.exceptions;

import ManyToOne.java.ManyToOne.exceptions.httpexceptions.*;
import org.springframework.data.mapping.PropertyReferenceException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;


import lombok.RequiredArgsConstructor;


@ControllerAdvice
@RequiredArgsConstructor
public class ErrorMessageController {

	private final ErrorMessageService errorMessageService;


	@ExceptionHandler(MissingServletRequestPartException.class)
	public ResponseEntity<ErrorMessage> handleMissingServletRequestPartException(MissingServletRequestPartException exception, WebRequest request) {
		return errorMessageService.handleException(exception.getStatusCode(), exception, request);
	}


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
		return errorMessageService.handleValidationException(exception.getStatusCode(), exception, request);
	}


	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<ErrorMessage> handlePropertyReferenceException(PropertyReferenceException exception, WebRequest request) {
		return errorMessageService.handleException(HttpStatus.BAD_REQUEST, exception, request);
	}


	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<ErrorMessage> handleMissingRequestHeaderException(MissingRequestHeaderException exception, WebRequest request) {
		return errorMessageService.handleException(exception.getStatusCode(), exception, request);
	}





	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorMessage> handleUnauthorizedException(UnauthorizedException exception, WebRequest request) {
		return errorMessageService.handleException(HttpStatus.UNAUTHORIZED, exception, request);
	}


	@ExceptionHandler(InternalServerErrorException.class)
	public ResponseEntity<ErrorMessage> handleInternalServerErrorException(InternalServerErrorException exception, WebRequest request) {
		return errorMessageService.handleException(HttpStatus.INTERNAL_SERVER_ERROR, exception, request);
	}


	@ExceptionHandler(UnavailableServiceException.class)
	public ResponseEntity<ErrorMessage> handleUnavailableServiceException(UnavailableServiceException exception, WebRequest request){
		return errorMessageService.handleException(HttpStatus.SERVICE_UNAVAILABLE, exception, request);
	}


	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException exception, WebRequest request) {
		return errorMessageService.handleException(HttpStatus.NOT_FOUND, exception, request);
	}

	@ExceptionHandler(NotAcceptableException.class)
	public ResponseEntity<ErrorMessage> handleNotAcceptableException(NotAcceptableException exception, WebRequest request) {
		return errorMessageService.handleException(HttpStatus.NOT_ACCEPTABLE, exception, request);
	}


	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorMessage> handleBadRequestException(BadRequestException exception, WebRequest request) {
		return errorMessageService.handleException(HttpStatus.BAD_REQUEST, exception, request);
	}
	

	@ExceptionHandler(BadGatewayException.class)
	public ResponseEntity<ErrorMessage> handleBadGatewayException(BadGatewayException exception, WebRequest request) {
		return errorMessageService.handleException(HttpStatus.BAD_GATEWAY, exception, request);
	}




}
