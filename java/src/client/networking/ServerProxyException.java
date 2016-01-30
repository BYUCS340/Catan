package client.networking;

public class ServerProxyException extends Exception
{
	/**Default constructor
	 */
	public ServerProxyException() {
		return;
	}

	/**
	 * Creates an exception with a customizable message
	 * @param message The message to include in this exception
	 */
	public ServerProxyException(String message) {
		super(message);
	}

	/**
	 * Creates an exception with a throwable object
	 * @param cause The throwable that caused this exception
	 */
	public ServerProxyException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a message with both detail message and a throwable
	 * @param message The customized mesage
	 * @param cause The throwable cause of the exception
	 */
	public ServerProxyException(String message, Throwable cause) {
		super(message, cause);
	}
}
