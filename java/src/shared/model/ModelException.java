package shared.model;

/**
 * A generic exception thrown by the model when an action is impossible.
 * Some types of this might be NotYourTurn, NotEnoughResources, BadLocation
 * @author matthewcarlson
 *
 */
public class ModelException extends Exception
{
	
	/**
	 * Not sure what this is for
	 */
	private static final long serialVersionUID = -6690703880332646229L;
	
	public ModelException(){
		this("Unknown exception");
	}
	
	public ModelException(String message){
		super(message);
	}

}
