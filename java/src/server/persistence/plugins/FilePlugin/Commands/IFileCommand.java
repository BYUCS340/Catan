/**
 * 
 */
package server.persistence.plugins.FilePlugin.Commands;

import server.persistence.PersistenceException;

/**
 * @author Parker Ridd
 *
 */
public interface IFileCommand {
	public void execute() throws PersistenceException;
}
