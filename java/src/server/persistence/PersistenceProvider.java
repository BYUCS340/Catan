package server.persistence;

public class PersistenceProvider implements IPersistenceProvider
{

    /**
     *
     */
    @Override
    public void Clear() {

    }

    /**
     *
     */
    @Override
    public void StartTransaction() {

    }

    /**
     * Ends a transaction
     *
     * @param commit
     */
    @Override
    public void EndTransaction(boolean commit) {

    }

    /**
     * Gets the user DAO
     *
     * @return
     */
    @Override
    public IUserDAO GetUserDAO() {
        return null;
    }

    /**
     * Gets the game DAO
     *
     * @return
     */
    @Override
    public IGameDAO GetGameDAO() {
        return null;
    }

    /**
     * gets the command DAO
     *
     * @return
     */
    @Override
    public ICommandDAO GetCommandDAO() {
        return null;
    }
}
