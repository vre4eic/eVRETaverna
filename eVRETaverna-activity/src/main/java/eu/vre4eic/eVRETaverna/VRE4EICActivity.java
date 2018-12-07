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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;

import net.sf.taverna.t2.invocation.InvocationContext;
import net.sf.taverna.t2.reference.ReferenceService;
import net.sf.taverna.t2.reference.T2Reference;
import net.sf.taverna.t2.workflowmodel.processor.activity.AbstractAsynchronousActivity;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivity;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivityCallback;

public class VRE4EICActivity extends
		AbstractAsynchronousActivity<VRE4EICActivityConfigurationBean>
		implements AsynchronousActivity<VRE4EICActivityConfigurationBean> {

	
	private static final String IN_FIRST_INPUT = "firsteVREInput";
	private static final String IN_EXTRA_DATA = "extraData";
	private static final String OUT_MORE_OUTPUTS = "moreOutputs";
	private static final String OUT_SIMPLE_OUTPUT = "simpleOutput";
	private static final String OUT_REPORT = "report";
	
	private VRE4EICActivityConfigurationBean configBean;

	@Override
	public void configure(VRE4EICActivityConfigurationBean configBean)
			throws ActivityConfigurationException {

		// Any pre-config sanity checks
		if (configBean.getResourceName().equals("invalidExample")) {
			throw new ActivityConfigurationException(
					"Example string can't be 'invalidExample'");
		}
		// Store for getConfiguration(), but you could also make
		// getConfiguration() return a new bean from other sources
		this.configBean = configBean;

		
		configurePorts();
	}

	protected void configurePorts() {
		// In case we are being reconfigured - remove existing ports first
		// to avoid duplicates
		removeInputs();
		removeOutputs();

		
		
		// Hard coded input port, expecting a single String
		addInput(IN_FIRST_INPUT, 0, true, null, String.class);

		// Optional ports depending on configuration
		if (configBean.getResourceName().equals("specialCase")) {
			// depth 1, ie. list of binary byte[] arrays
			addInput(IN_EXTRA_DATA, 1, true, null, byte[].class);
			addOutput(OUT_REPORT, 0);
		}
		
		// Single value output port (depth 0)
		addOutput(OUT_SIMPLE_OUTPUT, 0);
		// Output port with list of values (depth 1)
		addOutput(OUT_MORE_OUTPUTS, 1);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeAsynch(final Map<String, T2Reference> inputs,
			final AsynchronousActivityCallback callback) {
		// Don't execute service directly now, request to be run ask to be run
		// from thread pool and return asynchronously
		callback.requestRun(new Runnable() {
			
			public void run() {
				InvocationContext context = callback
						.getContext();
				ReferenceService referenceService = context
						.getReferenceService();
				// Resolve inputs 				
				String firstInput = (String) referenceService.renderIdentifier(inputs.get(IN_FIRST_INPUT), 
						String.class, context);
				
				// Support our configuration-dependendent input
				boolean optionalPorts = configBean.getResourceName().equals("specialCase"); 
				
				List<byte[]> special = null;
				// We'll also allow IN_EXTRA_DATA to be optionally not provided
				if (optionalPorts && inputs.containsKey(IN_EXTRA_DATA)) {
					// Resolve as a list of byte[]
					special = (List<byte[]>) referenceService.renderIdentifier(
							inputs.get(IN_EXTRA_DATA), byte[].class, context);
				}
				T2Reference reference = inputs.get(IN_EXTRA_DATA);
		//		System.out.println("Reference " + reference + " is depth " + reference.getDepth());

				

				MessageDigest digest;
				try {
				    digest = MessageDigest.getInstance("SHA");
				} catch (NoSuchAlgorithmException e) {
				    callback.fail("SHA algorithm not installed", e);
				    return;
				}
				if (special != null) {
				    for (byte[] data : special) {
				        digest.update(data);
				    }
				}
				byte[] checksumBinary = digest.digest();
				String checkSum = Hex.encodeHexString(checksumBinary);
				
				
				
				
				Map<String, T2Reference> outputs = new HashMap<String, T2Reference>();
				T2Reference simpleRef = referenceService.register(checkSum, 0, true, context);
				outputs.put(OUT_SIMPLE_OUTPUT, simpleRef);

				// For list outputs, only need to register the top level list
				List<String> moreValues = new ArrayList<String>();
				moreValues.add("Value 1");
				moreValues.add("Value 2");
				T2Reference moreRef = referenceService.register(moreValues, 1, true, context);
				outputs.put(OUT_MORE_OUTPUTS, moreRef);

				if (optionalPorts) {
					// Populate our optional output port					
					// NOTE: Need to return output values for all defined output ports
					String report = "Everything OK";
					outputs.put(OUT_REPORT, referenceService.register(report,
							0, true, context));
				}
				String report;
				if (special != null) {
				    report = "Checksum of " + special.size() + " items";
				} else {
				    report = "Checksum of empty list";
				}
				outputs.put(OUT_REPORT, referenceService.register(report,
				            0, true, context));
				
				callback.receiveResult(outputs, new int[0]);
			}
		});
	}

	@Override
	public VRE4EICActivityConfigurationBean getConfiguration() {
		return this.configBean;
	}

}
