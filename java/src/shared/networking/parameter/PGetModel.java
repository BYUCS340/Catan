package shared.networking.parameter;

import java.io.Serializable;

public class PGetModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2543868922800545686L;
	int version;

	
	/**
	 * @param version
	 */
	public PGetModel(int version)
	{
		super();
		this.version = version;
	}

	/**
	 * @return the version
	 */
	public int getVersion()
	{
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version)
	{
		this.version = version;
	}
	
}
