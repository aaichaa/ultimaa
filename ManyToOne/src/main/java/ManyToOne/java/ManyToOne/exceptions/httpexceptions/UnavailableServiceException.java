package ManyToOne.java.ManyToOne.exceptions.httpexceptions;


public class UnavailableServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnavailableServiceException() {
		super("Service Unavailable");
	}
	
	public UnavailableServiceException(String message) {
		super(message);
	}

}
