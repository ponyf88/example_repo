package com.example.shared;

/**
 * The JSONException is thrown by the JSON.org classes when things are amiss.
 * @author  JSON.org
 * @version  2010-12-24
 */
public class JSONException extends Exception {
	private static final long serialVersionUID = 0;
	/**
	 * @uml.property  name="cause"
	 */
	private Throwable cause;

    /**
     * Constructs a JSONException with an explanatory message.
     * @param message Detail about the reason for the exception.
     */
    public JSONException(String message) {
        super(message);
    }

    public JSONException(Throwable cause) {
        super(cause.getMessage());
        this.cause = cause;
    }

    /**
	 * @return
	 * @uml.property  name="cause"
	 */
    public Throwable getCause() {
        return this.cause;
    }
}
