package ManyToOne.java.ManyToOne.exceptions.httpexceptions;

public class InternalServerErrorException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InternalServerErrorException() {
		super("Internal server error.");
	}
	
	public InternalServerErrorException(String message) {
		super(message);
	}

}
