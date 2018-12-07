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
package eu.vre4eic.eVRETaverna.ui.serviceprovider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.jvnet.ws.wadl.Application;
import org.jvnet.ws.wadl.Param;
import org.jvnet.ws.wadl.ast.ApplicationNode;
import org.jvnet.ws.wadl.ast.InvalidWADLException;
import org.jvnet.ws.wadl.ast.MethodNode;
import org.jvnet.ws.wadl.ast.PathSegment;
import org.jvnet.ws.wadl.ast.RepresentationNode;
import org.jvnet.ws.wadl.ast.ResourceNode;
import org.jvnet.ws.wadl.ast.WadlAstBuilder;
import org.jvnet.ws.wadl.util.MessageListener;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.sf.taverna.t2.activities.rest.RESTActivityConfigurationBean;
import net.sf.taverna.t2.activities.rest.RESTActivity.HTTP_METHOD;
import net.sf.taverna.t2.servicedescriptions.AbstractConfigurableServiceProvider;
import net.sf.taverna.t2.servicedescriptions.ConfigurableServiceProvider;
import net.sf.taverna.t2.servicedescriptions.ServiceDescription;
import eu.vre4eic.eVRETaverna.ui.serviceprovider.WadlServiceDesc;
import eu.vre4eic.eVRETaverna.util.Common;

public class EvreServiceProvider extends AbstractConfigurableServiceProvider<VRE4EICServiceProviderConfig>
		implements ConfigurableServiceProvider<VRE4EICServiceProviderConfig> {

	private Logger logger = Logger.getLogger(EvreServiceProvider.class);
	private String token="";
	private List<WadlServiceDesc> catalogResults = new ArrayList<WadlServiceDesc>();

	public EvreServiceProvider() {
		
		super( new VRE4EICServiceProviderConfig());
		//catalogResults.clear();
		

	}

	private static final URI providerId = URI.create("http://v4e-lab.isti.cnr.it:8080/service-provider/eVRETaverna");

	

	@SuppressWarnings("unchecked")
	public void findServiceDescriptionsAsync(final FindServiceDescriptionsCallBack callBack) {
		// Use callback.status() for long-running searches
		// callBack.status("Resolving example services");

		// final SchemaCompiler s2j = XJC.createSchemaCompiler();

		//final Set<URI> jsonSchemas = new LinkedHashSet<URI>();

		WadlAstBuilder astBuilder = new WadlAstBuilder(new WadlAstBuilder.SchemaCallback() {

			public void processSchema(InputSource input) {

				/*
				 * // Assume that the stream is a buffered stream at this point // and mark a
				 * position InputStream is = input.getByteStream(); is.mark(8192);
				 * 
				 * // Read the first bytes and look for the xml header // String peakContent =
				 * null;
				 * 
				 * try { Reader r = new InputStreamReader(is, "UTF-8");
				 * 
				 * CharBuffer cb = CharBuffer.allocate(20); r.read(cb); cb.flip(); peakContent =
				 * cb.toString(); } catch (IOException e) { throw new
				 * RuntimeException("Internal problem pushing back buffer", e); } finally { try
				 * { is.reset(); } catch (IOException ex) { throw new
				 * RuntimeException("Internal problem pushing back buffer", ex); }
				 * 
				 * }
				 * 
				 * // By default assume a xml schema, better guess // because some XML files
				 * don't start with <?xml // as per bug WADL-66 if
				 * (peakContent.matches("^\\s*\\{")) { // We are guessing this is a json type
				 * jsonSchemas.add(URI.create(input.getSystemId())); } else { //if
				 * (peakContent==null || peakContent.contains("<?xml") ||
				 * peakContent.startsWith("<")) { s2j.parseSchema(input); }
				 */
			}

			public void processSchema(String uri, Element node) {
				/* s2j.parseSchema(uri, node); */
			}
		}, new MessageListener() {

			@Override
			public void warning(String message, Throwable throwable) {
				logger.warn(message, throwable);
			}

			@Override
			public void info(String message) {
				logger.info(message);
			}

			@Override
			public void error(String message, Throwable throwable) {
				logger.error(message, throwable);
			}
		}) {
			@Override
			protected Application processDescription(URI desc) throws JAXBException, IOException {
				URLConnection conn = desc.toURL().openConnection();
				conn.setRequestProperty("Accept", "application/vnd.sun.wadl+xml");
				conn.setRequestProperty("Accept", "application/xml");
				
				System.out.println("Message 1"+desc);
				InputStream is = conn.getInputStream();
				System.out.println("Message 2"+desc+" "+is.toString());
				return processDescription(desc, is);
			}
		};

		
		callBack.status("Resolving e-VRE services");
		String uNm=getConfiguration().getUserName();
		String uPw=getConfiguration().getPassword();
		
		String evreUrl=getConfiguration().getUri().toASCIIString();
		
		
		URL loginUrl;
		URL wsDescUrl;
		
		try {
			//token=Common.getToken();
			Common.setEvreUrl(new URL("http://v4e-lab.isti.cnr.it:8080/"));
			
			if (!evreUrl.equals(Common.getEvreUrl().toString()))
				return;

			Common.setUserId(uNm);
			Common.setPassword(uPw);
			//Common.setEvreUrl(new URL("http://v4e-lab.isti.cnr.it:8080"));
	
			
			loginUrl = Common.getLoginUrl();
			
			callBack.partialResults(catalogResults);
			HttpURLConnection loginCon= (HttpURLConnection) loginUrl.openConnection();
			loginCon.setRequestMethod("GET");
			loginCon.setDoInput(true);
			loginCon.setDoOutput(true);
			InputStream iSt= loginCon.getInputStream();
			BufferedReader bRead=new BufferedReader(new InputStreamReader(iSt, "UTF-8"));
			String line="";
		    JsonObject ob=new JsonObject();
			while ((line=bRead.readLine())!=null) {
				ob= (JsonObject) new JsonParser().parse(line); 
				token=ob.get("token").toString();
				System.out.println(token);
			}
			Common.setToken(token.replaceAll("\"", "").trim());
			
			
			//get service descriptions references
			//wsDescUrl=new URL("http://v4e-lab.isti.cnr.it:8080/WorkflowService/wfservice/getserviced?evresid="+uNm+"&token="+token.replaceAll("\"", "").trim());
			
			wsDescUrl=new URL(evreUrl+"/WorkflowService/wfservice/getserviced?evresid="+uNm+"&token="+token.replaceAll("\"", "").trim());
			
			
			HttpURLConnection descCon= (HttpURLConnection) wsDescUrl.openConnection();
			descCon.setRequestMethod("GET");
			descCon.setDoInput(true);
			descCon.setDoOutput(true);
			InputStream iStDesc= descCon.getInputStream();
			BufferedReader bReadDesc=new BufferedReader(new InputStreamReader(iStDesc, "UTF-8"));
			//System.out.println("fava");
			line="";
		    ob=new JsonObject();
		    JsonArray jA= new JsonArray();
		    while ((line=bReadDesc.readLine())!=null) {
				ob= (JsonObject) new JsonParser().parse(line); 
				String status=ob.get("status").toString();
				if (status.equalsIgnoreCase("\"SUCCEED\"")) {
					jA=ob.getAsJsonObject("message").getAsJsonArray("services");
				
					System.out.println(jA.toString());
				}
				
			}
		    
		   
		   if (jA.size()>0){
			   Iterator<JsonElement> itjA=jA.iterator();
			    List<ResourceNode> rs;
		    while (itjA.hasNext())   {
		    	ob=(JsonObject)itjA.next();
		    	System.out.println(ob.get("ref").toString());
		    	this.getConfiguration().setUri(new URI(ob.get("ref").toString().replaceAll("\"", "").trim()));
		    	try {
		    		
				ApplicationNode an = astBuilder.buildAst(new URI(this.getConfiguration().getUri().toASCIIString()));
		    				rs = an.getResources();
		    						
					for (ResourceNode r : rs) {
						
					processEndpointClass(catalogResults, r, ob.get("name").toString().replaceAll("\"", "").trim(), uNm);
					}
					
					callBack.partialResults(catalogResults);
		    	}
		    	catch(Exception exc) {
		    		continue;
		    	}
		    }
		}
		    
		} catch (Exception e1) {
			//callBack.partialResults(catalogResults);
			e1.printStackTrace();
			return;
		}
		
/*		for (int i = 1; i <= getConfiguration().getNumberOfServices(); i++) {
			System.out.println("-------Example " + i);
			ExampleServiceDesc service = new ExampleServiceDesc();
			// Populate the service description bean

			service.setExampleString("VRE4EIC WS " + i);

			service.setExampleUri(getConfiguration().getUri());
			// Optional: set description
			service.setDescription("VRE4EIC Web Service " + i);
			evreResults.add(service);

		}

		
		callBack.partialResults(evreResults);*/

		/*List<WadlServiceDesc> results = new ArrayList<WadlServiceDesc>();

		try {
			callBack.status("Resolving EPOS services");
			for (int i = 0; i < wadlURLString.length; i++) {
				String tu = this.getConfiguration().getUri().toASCIIString();
				this.getConfiguration().setUri(new URI(wadlURLString[i]));
				ApplicationNode an = astBuilder.buildAst(new URI(this.getConfiguration().getUri().toASCIIString()));
				List<ResourceNode> rs = an.getResources();
				for (ResourceNode r : rs) {
					processEndpointClass(results, r);
				}
			}

		} catch (InvalidWADLException | IOException | URISyntaxException e) {
			logger.error(e);
		}*/

		// partialResults() can also be called several times from inside
		// for-loop if the full search takes a long time
		
		callBack.partialResults(catalogResults);

		// No more results will be coming
		//callBack.finished();
	}

	private void processEndpointClass(List<WadlServiceDesc> results, ResourceNode root, String name, String userNm) {
		for (ResourceNode r : root.getChildResources()) {
		
			processSubClass(results, r, name, userNm);
		}
	}

	private void processSubClass(List<WadlServiceDesc> results, ResourceNode resource, String name, String userNm) {
		// generate Java methods for each resource method
		for (MethodNode m : resource.getMethods()) {
			
			processMethodDecls(results, m, name, userNm);
		}

		// generate sub classes for each child resource
		for (ResourceNode r : resource.getChildResources()) {
			
			processSubClass(results, r, name, userNm);
		}
	}

	private void processMethodDecls(List<WadlServiceDesc> results, MethodNode method, String name, String userNm) {
		RESTActivityConfigurationBean serviceConfigBean = RESTActivityConfigurationBean.getDefaultInstance();

		HTTP_METHOD httpMethod = HTTP_METHOD.GET;
		String methodName = method.getName();
		switch (methodName) {
		case "GET":
			httpMethod = HTTP_METHOD.GET;
			break;
		case "POST":
			httpMethod = HTTP_METHOD.POST;
			break;
		case "PUT":
			httpMethod = HTTP_METHOD.PUT;
			break;
		case "DELETE":
			httpMethod = HTTP_METHOD.DELETE;
			break;
		default:
			// This means that valid HTTP methods such as OPTIONS are ignored
			return;
		}

		serviceConfigBean.setHttpMethod(httpMethod);

		StringBuilder sb = new StringBuilder();

		List<PathSegment> segments = method.getOwningResource().getPathSegments();
		List<String> pathSegments = new ArrayList<String>();
		for (int segement = 0; segement < segments.size(); segement++) {
			String pathSegment = segments.get(segement).getTemplate();
			if (pathSegment.contains("{") && pathSegment.contains(":")) {
				String firstPart = pathSegment.substring(0, pathSegment.indexOf(":"));
				String secondPart = pathSegment.substring(pathSegment.indexOf("}"));
				pathSegment = firstPart + secondPart;
			}
			boolean bufferEndsInSlash = sb.length() > 0 ? sb.charAt(sb.length() - 1) == '/' : false;
			boolean pathSegmentStartsWithSlash = pathSegment.startsWith("/");

			if (pathSegmentStartsWithSlash && bufferEndsInSlash) {
				// We only need the one slash, so remove one from the
				// pathSegement
				sb.append(pathSegment, 1, pathSegment.length());
			} else if (pathSegmentStartsWithSlash || bufferEndsInSlash || (segement == 0)) {
				// Only one has a slash so it is fine to append
				sb.append(pathSegment);
			} else {
				// Neither has one so add a slash in
				sb.append('/');
				sb.append(pathSegment);
			}
			if ("/".equals(pathSegment)) {
				pathSegments.add(pathSegment);
			} else {
				if (pathSegment.startsWith("/")) {
					pathSegments.add(pathSegment.substring(1));
				} else {
					pathSegments.add(pathSegment);
				}
			}
		}

		if (!method.getQueryParameters().isEmpty()) {
			boolean first = true;
			for (Param p : method.getQueryParameters()) {
				if (first) {
					first = false;
					sb.append("?");
				} else {
					sb.append("&");
				}
				String paramName = p.getName();
				sb.append(paramName + "={" + paramName + "}");
			}
		}

		serviceConfigBean.setUrlSignature(sb.toString());

		List<RepresentationNode> supportedInputs = method.getSupportedInputs();
		List<RepresentationNode> supportedOutputs = new ArrayList<RepresentationNode>();
		for (List<RepresentationNode> nodeList : method.getSupportedOutputs().values()) {
			for (RepresentationNode node : nodeList) {
				supportedOutputs.add(node);
			}
		}

		if (!supportedInputs.isEmpty()) {
			serviceConfigBean.setContentTypeForUpdates(supportedInputs.get(0).getMediaType());
		}

		if (!supportedOutputs.isEmpty()) {
			serviceConfigBean.setAcceptsHeaderValue(supportedOutputs.get(0).getMediaType());
		}

		// TODO Something about the headers

		WadlServiceDesc newServiceDesc = new WadlServiceDesc(this.getConfiguration().getUri().toASCIIString(),
				pathSegments, serviceConfigBean, name, userNm.trim());
		results.add(newServiceDesc);

	}

	/**
	 * Icon for service provider
	 */
	public Icon getIcon() {
		return ExampleServiceIcon.getIcon();
	}

	/**
	 * Name of service provider, appears in right click for 'Remove service
	 * provider'
	 */
	public String getName() {
		return "VRE4EIC WS Resources";
	}

	@Override
	public String toString() {
		return getName();
	}

	public String getId() {
		return providerId.toASCIIString();
	}

	@Override
	protected List<? extends Object> getIdentifyingData() {
		return Arrays.asList(getConfiguration().getUri());
	}

}
