/**
 * 
 */
package server.persistence.plugins.FilePlugin.Commands;

/**
 * @author Parker Ridd
 *
 */
public interface IFileCommand {
	public boolean execute();
	public String getResult();
}
