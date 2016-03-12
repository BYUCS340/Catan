package shared.networking.parameter;

import java.io.Serializable;

public class PSendChat implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4882417109550917720L;
	private String content;

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
