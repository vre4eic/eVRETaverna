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
package eu.vre4eic.eVRETaverna.ui.save;


	import net.sf.taverna.t2.workbench.file.FileType;
	import net.sf.taverna.t2.workflowmodel.serialization.xml.XMLSerializationConstants;

	public class VRE4EICFlowType extends FileType {

		@Override
		public String getDescription() {
			return "VRE4EIC workflow";
		}

		@Override
		public String getExtension() {
			return "evreflow";
		}

		@Override
		public String getMimeType() {
			return XMLSerializationConstants.WORKFLOW_DOCUMENT_MIMETYPE;
		}

	}
