package eu.vre4eic.eVRETaverna;



import java.io.Serializable;
import java.net.URI;

/**
 * VRE4EIC configuration bean.
 * 
 */
public class VRE4EICActivityConfigurationBean implements Serializable {

	
	private String resourceDescription;
	private String resourceName;
	private URI resourceUri;

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceDescription() {
		return resourceDescription;
	}

	public void setResourceDescription(String description) {
		this.resourceDescription = description;
	}

	public URI getResourceUri() {
		return resourceUri;
	}

	public void setResourceUri(URI resourceUri) {
		this.resourceUri = resourceUri;
	}

}
