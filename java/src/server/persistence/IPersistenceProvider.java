package server.persistence;

public interface IPersistenceProvider 
{
	/**
	 * @throws PersistenceException
	 */
	void Clear() throws PersistenceException;
	
	/**
	 * @throws PersistenceException
	 */
	void StartTransaction() throws PersistenceException;
	
	/**
	 * Ends a transaction
	 * @param commit 
	 * @throws PersistenceException
	 */
	void EndTransaction(boolean commit) throws PersistenceException;
	
	/**
	 * Gets the user DAO
	 * @return
	 * @throws PersistenceException
	 */
	IUserDAO GetUserDAO() throws PersistenceException;
	
	/**
	 * Gets the game DAO
	 * @return
	 * @throws PersistenceException
	 */
	IGameDAO GetGameDAO() throws PersistenceException;
	
	/**
	 * gets the command DAO
	 * @return
	 * @throws PersistenceException
	 */
	ICommandDAO GetCommandDAO() throws PersistenceException;
	
	
}
