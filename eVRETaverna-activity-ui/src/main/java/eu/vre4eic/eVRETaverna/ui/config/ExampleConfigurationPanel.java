package eu.vre4eic.eVRETaverna.ui.config;

import java.awt.GridLayout;
import java.net.URI;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationPanel;


import eu.vre4eic.eVRETaverna.VRE4EICActivity;
import eu.vre4eic.eVRETaverna.VRE4EICActivityConfigurationBean;


@SuppressWarnings("serial")
public class ExampleConfigurationPanel
		extends
		ActivityConfigurationPanel<VRE4EICActivity, VRE4EICActivityConfigurationBean> {

	private VRE4EICActivity activity;
	private VRE4EICActivityConfigurationBean configBean;
	
	private JTextField fieldString;
	private JTextField fieldURI;

	public ExampleConfigurationPanel(VRE4EICActivity activity) {
		
		this.activity = activity;
		initGui();
	}

	protected void initGui() {
		removeAll();
		setLayout(new GridLayout(0, 2));

	
		JLabel labelString = new JLabel("Example string:");
		add(labelString);
		fieldString = new JTextField(20);
		add(fieldString);
		labelString.setLabelFor(fieldString);

		JLabel labelURI = new JLabel("Example URI:");
		add(labelURI);
		fieldURI = new JTextField(25);
		add(fieldURI);
		labelURI.setLabelFor(fieldURI);

		// Populate fields from activity configuration bean
		refreshConfiguration();
	}

	/**
	 * Check that user values in UI are valid
	 */
	@Override
	public boolean checkValues() {
		try {
			URI.create(fieldURI.getText());
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, ex.getCause().getMessage(),
					"Invalid URI", JOptionPane.ERROR_MESSAGE);
			// Not valid, return false
			return false;
		}
		// All valid, return true
		return true;
	}

	/**
	 * Return configuration bean generated from user interface last time
	 * noteConfiguration() was called.
	 */
	@Override
	public VRE4EICActivityConfigurationBean getConfiguration() {
		
		return configBean;
	}

	/**
	 * Check if the user has changed the configuration from the original
	 */
	@Override
	public boolean isConfigurationChanged() {
		String originalString = configBean.getResourceName();
		String originalUri = configBean.getResourceUri().toASCIIString();
		
		return ! (originalString.equals(fieldString.getText())
				&& originalUri.equals(fieldURI.getText()));
	}

	/**
	 * Prepare a new configuration bean from the UI, to be returned with
	 * getConfiguration()
	 */
	@Override
	public void noteConfiguration() {
		configBean = new VRE4EICActivityConfigurationBean();
		
		// FIXME: Update bean fields from your UI elements
		configBean.setResourceName(fieldString.getText());
		configBean.setResourceUri(URI.create(fieldURI.getText()));
	}

	/**
	 * Update GUI from a changed configuration bean (perhaps by undo/redo).
	 * 
	 */
	@Override
	public void refreshConfiguration() {
		configBean = activity.getConfiguration();
		
		
		fieldString.setText(configBean.getResourceName());
		fieldURI.setText(configBean.getResourceUri().toASCIIString());
	}
}
