package eu.vre4eic.eVRETaverna.ui.save;


	import net.sf.taverna.t2.workbench.file.FileType;
	import net.sf.taverna.t2.workflowmodel.serialization.xml.XMLSerializationConstants;

	public class VRE4EICFlowType extends FileType {

		@Override
		public String getDescription() {
			return "VRE4EIC 2 workflow";
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
