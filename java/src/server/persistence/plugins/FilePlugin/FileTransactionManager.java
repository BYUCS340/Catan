package server.persistence.plugins.FilePlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import server.persistence.PersistenceException;
import server.persistence.plugins.FilePlugin.Commands.IFileCommand;

public class FileTransactionManager {
	
	private static boolean transactionBegun = false;
	private static List<IFileCommand> transaction;
	private static Semaphore transactionSemaphore = new Semaphore(1);
	
	
	public static boolean addCommand(IFileCommand cmd)
	{	
		if(transactionBegun)
		{
			if(transaction == null)
				transaction = new ArrayList<IFileCommand>();
			
			transaction.add(cmd);
			return true;
		}
		return false;
	}
	
	public static boolean endTransaction(boolean commit)
	{
		if(!transactionBegun)
			return false;
		boolean successful = true;
		if(commit)
		{
			for(IFileCommand ifc : transaction)
			{
				try{
					ifc.execute();
				}
				catch(PersistenceException e)
				{
					//TODO make it roll back transactions
					successful = false;
				}
			}			
			//TODO check if successful and rollback if not
		}
		else
		{
			transaction.clear();
		}
		transactionBegun = false;
		transactionSemaphore.release();
		return successful;
	}
	
	public static boolean startTransaction()
	{
		try
		{
			transactionSemaphore.acquire();
		}
		catch(InterruptedException e)
		{
			System.out.println("Unable to acquire transaction semaphore!");
			e.printStackTrace();
			return false;
		}
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
	
	public static int transactionSize()
	{
		if(transaction != null)
			return transaction.size();
		else
			return 0;
	}
}
