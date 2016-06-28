package by.epam.exception.custom;

public class DaoException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private Exception detail;
	
	public DaoException(String message) {
		super(message);
		initCause(null);  // Disallow subsequent initCause
	}
	
	public DaoException(String message, Exception cause) {
		super(message);
		initCause(null);  // Disallow subsequent initCause
		detail = cause;
	}
	
	@Override
	public String getMessage() {
		if (detail == null) {
            return super.getMessage();
        } else {
            return super.getMessage() + "; nested exception is: \n\t" +
                detail.toString();
        }
	}
}
