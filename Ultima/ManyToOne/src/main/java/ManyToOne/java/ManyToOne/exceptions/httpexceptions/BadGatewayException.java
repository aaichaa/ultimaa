package ManyToOne.java.ManyToOne.exceptions.httpexceptions;


public class BadGatewayException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public BadGatewayException(String message)  {
		super(message);
	}

}
