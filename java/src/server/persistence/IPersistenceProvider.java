package server.persistence;

public interface IPersistenceProvider 
{
	/**
	 * 
	 */
	void Clear();
	
	/**
	 * 
	 */
	void StartTransaction();
	
	/**
	 * Ends a transaction
	 * @param commit 
	 */
	void EndTransaction(boolean commit);
	
	/**
	 * Gets the user DAO
	 * @return
	 */
	IUserDAO GetUserDAO();
	
	/**
	 * Gets the game DAO
	 * @return
	 */
	IGameDAO GetGameDAO();
	
	/**
	 * gets the command DAO
	 * @return
	 */
	ICommandDAO GetCommandDAO();
	
	
}
