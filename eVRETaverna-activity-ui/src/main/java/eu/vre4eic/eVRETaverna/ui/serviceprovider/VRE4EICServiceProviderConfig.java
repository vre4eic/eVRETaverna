package eu.vre4eic.eVRETaverna.ui.serviceprovider;

import java.net.URI;

import net.sf.taverna.t2.lang.beans.PropertyAnnotated;
import net.sf.taverna.t2.lang.beans.PropertyAnnotation;

public class VRE4EICServiceProviderConfig extends PropertyAnnotated {
	private URI uri = URI.create("http://v4e-lab.isti.cnr.it:8080/");
	private int numberOfServices = 5;
	private String userName="math";
	private String password="math";
	
	public VRE4EICServiceProviderConfig() {
	}
	
	@PropertyAnnotation(preferred=true, displayName="Login")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@PropertyAnnotation(displayName="Password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@PropertyAnnotation(displayName="URL of e-VRE system")
	//@PropertyAnnotation(displayName="e-VRE URL")
    public URI getUri() {
		return uri;
	}
	
	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	
}
