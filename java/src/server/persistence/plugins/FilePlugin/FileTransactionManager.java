package server.persistence.plugins.FilePlugin;

import java.util.ArrayList;
import java.util.List;

import server.persistence.plugins.FilePlugin.Commands.IFileCommand;

public class FileTransactionManager {
	
	private static boolean transactionBegun = false;
	private static List<IFileCommand> transaction;
	
	
	public static boolean addCommand(IFileCommand cmd)
	{
		if(transactionBegun)
		{
			transaction.add(cmd);
			return true;
		}
		
		return false;
	}
	
	public static boolean endTransaction(boolean commit)
	{
		if(!transactionBegun)
			return false;
		
		if(commit)
		{
			boolean successful = true;
			for(IFileCommand ifc : transaction)
			{
				successful = ifc.execute();
			}
			transactionBegun = false;
			
			//TODO check if successful and rollback if not
			return successful;
		}
		else
		{
			transactionBegun = false;
			transaction.clear();
			return false;
		}
	}
	
	public static boolean startTransaction()
	{
		if(transactionBegun)
			return false;
		transaction = new ArrayList<IFileCommand>();
		transactionBegun = true;
		return true;
	}
	
	public static boolean isInMiddleOfTransaction()
	{
		return transactionBegun;
	}
}
