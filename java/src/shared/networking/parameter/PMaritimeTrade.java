/**
 * 
 */
package shared.networking.parameter;

import java.io.Serializable;

import shared.definitions.ResourceType;

/**
 * @author Parker
 *
 */
public class PMaritimeTrade implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6137073816630130851L;
	private int ratio;
	private ResourceType inputResource;
	private ResourceType outputReseource;
	/**
	 * @return the ratio
	 */
	public int getRatio() {
		return ratio;
	}
	/**
	 * @param ratio the ratio to set
	 */
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	/**
	 * @return the inputResource
	 */
	public ResourceType getInputResource() {
		return inputResource;
	}
	/**
	 * @param inputResource the inputResource to set
	 */
	public void setInputResource(ResourceType inputResource) {
		this.inputResource = inputResource;
	}
	/**
	 * @return the outputReseource
	 */
	public ResourceType getOutputReseource() {
		return outputReseource;
	}
	/**
	 * @param outputReseource the outputReseource to set
	 */
	public void setOutputReseource(ResourceType outputReseource) {
		this.outputReseource = outputReseource;
	}
}
