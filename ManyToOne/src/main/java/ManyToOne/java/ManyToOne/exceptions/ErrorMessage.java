package ManyToOne.java.ManyToOne.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatusCode;


public record ErrorMessage (HttpStatusCode statusCode, Date timestamp, 
		String message, String description)
{}
