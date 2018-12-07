/*******************************************************************************
 * Copyright 2018 VRE4EIC
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
 ******************************************************************************/
 ******************************************************************************/
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
