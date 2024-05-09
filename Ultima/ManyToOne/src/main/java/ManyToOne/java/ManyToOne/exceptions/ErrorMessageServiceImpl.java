package ManyToOne.java.ManyToOne.exceptions;


import java.util.Date;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.log4j.Log4j2;



@Log4j2
@Service
public class ErrorMessageServiceImpl implements ErrorMessageService {

	@Override
	public ResponseEntity<ErrorMessage> handleException(HttpStatusCode httpStatus, Exception exception,
			WebRequest request) {
    	log.trace(exception.getStackTrace());
		return getErrorMessage(httpStatus, request, exception.getMessage());
	}

	@Override
	public ResponseEntity<ErrorMessage> handleValidationException(HttpStatusCode httpStatus, Exception exception,
			WebRequest request) {
		log.trace(exception.getMessage());
		log.trace(exception.getStackTrace());
		String errorMessageText = "Validation failed. Please check the request body.";
		return getErrorMessage(httpStatus, request, errorMessageText);
	}

	private ResponseEntity<ErrorMessage> getErrorMessage(HttpStatusCode httpStatus, WebRequest request,
			String errorMessageText) {
		ErrorMessage errorMessage = new ErrorMessage(
				httpStatus,
				new Date(),
				errorMessageText,
				request.getDescription(false));
		
		return new ResponseEntity<>(errorMessage, errorMessage.statusCode());
	}

}
