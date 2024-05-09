package ManyToOne.java.ManyToOne.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;


public interface ErrorMessageService {

	public abstract ResponseEntity<ErrorMessage> handleException(HttpStatusCode httpStatus, Exception exception, WebRequest request);
	public abstract ResponseEntity<ErrorMessage> handleValidationException(HttpStatusCode httpStatus, Exception exception, WebRequest request);
}
