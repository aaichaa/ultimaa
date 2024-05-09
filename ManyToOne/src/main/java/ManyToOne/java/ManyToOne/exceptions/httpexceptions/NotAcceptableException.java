package ManyToOne.java.ManyToOne.exceptions.httpexceptions;


public class NotAcceptableException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public NotAcceptableException() {
		super("Not accepted instances present.");
	}
	
	public NotAcceptableException(String message) {
		super(message);
	}

}
