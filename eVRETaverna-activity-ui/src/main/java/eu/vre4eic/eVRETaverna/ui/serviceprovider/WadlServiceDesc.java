/*******************************************************************************
 * Copyright 2018 VRE4EIC Consortium
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
 
package eu.vre4eic.eVRETaverna.ui.serviceprovider;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.sf.taverna.t2.activities.rest.RESTActivity;
import net.sf.taverna.t2.activities.rest.RESTActivityConfigurationBean;

import net.sf.taverna.t2.servicedescriptions.ServiceDescription;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;

import org.apache.log4j.Logger;

public class WadlServiceDesc extends ServiceDescription<RESTActivityConfigurationBean> {
	
	private static Logger logger = Logger.getLogger(WadlServiceDesc.class);
	private static  String providerId = "http://v4e-lab.isti.cnr.it";
	
	
	private RESTActivityConfigurationBean configBean;

	private String uri;
	private String servName;

	private String userName;
	
	private List<String> pathSegments = new java.util.Vector <String>();

	public WadlServiceDesc(String uri, List<String> pathSegments, RESTActivityConfigurationBean configBean, String name, String userNm) {
		this.uri = uri;
		this.servName=name;
		this.pathSegments.add(name);
		//this.pathSegments.addAll(pathSegments);
		this.pathSegments.add(pathSegments.get(1));
		//this.pathSegments = pathSegments;
		this.configBean = configBean;
		this.userName=userNm;
	}
	
	/**
	 * The subclass of Activity which should be instantiated when adding a service
	 * for this description 
	 */
	@Override
	public Class<? extends Activity<RESTActivityConfigurationBean>> getActivityClass() {
		return RESTActivity.class;
	}

	/**
	 * The configuration bean which is to be used for configuring the instantiated activity.
	 * Making this bean will typically require some of the fields set on this service
	 * description, like an endpoint URL or method name. 
	 * 
	 */
	@Override
	public RESTActivityConfigurationBean getActivityConfiguration() {
		RESTActivityConfigurationBean result = new RESTActivityConfigurationBean();
		result.setHttpMethod(configBean.getHttpMethod());
		result.setAcceptsHeaderValue(configBean.getAcceptsHeaderValue());
		result.setContentTypeForUpdates(configBean.getContentTypeForUpdates());
		result.setUrlSignature(configBean.getUrlSignature());
		/*
		try {
			URL fva=new URL(configBean.getUrlSignature());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		result.setOutgoingDataFormat(configBean.getOutgoingDataFormat());
		result.setSendHTTPExpectRequestHeader(configBean.getSendHTTPExpectRequestHeader());
		result.setShowRedirectionOutputPort(configBean.getShowRedirectionOutputPort());
		result.setShowActualUrlPort(configBean.getShowActualUrlPort());
		result.setShowResponseHeadersPort(configBean.getShowResponseHeadersPort());
		result.setEscapeParameters(configBean.getEscapeParameters());
		result.setOtherHTTPHeaders(new ArrayList<ArrayList<String>>(configBean.getOtherHTTPHeaders()));
		return result;
	}

	/**
	 * An icon to represent this service description in the service palette.
	 */
	@Override
	public Icon getIcon() {
		//return new ImageIcon(ExampleServiceIcon.class.getResource("/vre4eic_logo_ico.png"));
		return new ImageIcon(ExampleServiceIcon.class.getResource("/vre4eic_logo_ico.png"));
		//RESTActivityIcon.getRESTActivityIcon();
	}

	/**
	 * The display name that will be shown in service palette and will
	 * be used as a template for processor name .
	 */
	@Override
	public String getName() {
		//return configBean.getHttpMethod().toString() + " " + configBean.getUrlSignature();
		return configBean.getUrlSignature();
		//return this.servName;
	}

	/**
	 * The path to this service description in the service palette. Folders
	 * will be created for each element of the returned path.
	 */
	@Override
	public List<String> getPath() {
		
		ArrayList<String> result = new ArrayList<String>();
		
		result.add("VRE4EIC Catalogue ("+userName+")");
		//result.add("EPOS Catalogue " + this.providerId);
		result.addAll(pathSegments);
		return result;
	}

	/**
	 * Return a list of data values uniquely identifying this service
	 * description (to avoid duplicates). Include only primary key like fields,
	 * ie. ignore descriptions, icons, etc.
	 */
	@Override
	protected List<? extends Object> getIdentifyingData() {
		return Arrays.<Object>asList(this.getUri(), configBean.getHttpMethod(), configBean.getUrlSignature());
		
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
