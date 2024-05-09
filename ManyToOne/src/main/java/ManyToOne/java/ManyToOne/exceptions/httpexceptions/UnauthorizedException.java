package ManyToOne.java.ManyToOne.exceptions.httpexceptions;

public class UnauthorizedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UnauthorizedException(String message) {
		super(message);
	}

}
