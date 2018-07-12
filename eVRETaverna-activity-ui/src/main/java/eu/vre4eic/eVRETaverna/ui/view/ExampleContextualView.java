package eu.vre4eic.eVRETaverna.ui.view;

import java.awt.Frame;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.ContextualView;


import eu.vre4eic.eVRETaverna.VRE4EICActivity;
import eu.vre4eic.eVRETaverna.VRE4EICActivityConfigurationBean;
import eu.vre4eic.eVRETaverna.ui.config.ExampleConfigureAction;

@SuppressWarnings("serial")
public class ExampleContextualView extends ContextualView {
	private final VRE4EICActivity activity;
	private JLabel description = new JLabel("ads");

	public ExampleContextualView(VRE4EICActivity activity) {

		this.activity = activity;
		initView();
	}

	@Override
	public JComponent getMainFrame() {
		JPanel jPanel = new JPanel();
		jPanel.add(description);
		refreshView();
		return jPanel;
	}

	@Override
	public String getViewTitle() {
		VRE4EICActivityConfigurationBean configuration = activity.getConfiguration();
		return "Example service " + configuration.getResourceName();
	}

	/**
	 * Typically called when the activity configuration has changed.
	 */
	@Override
	public void refreshView() {
		VRE4EICActivityConfigurationBean configuration = activity
				.getConfiguration();
		description.setText("Example service " + configuration.getResourceUri()
				+ " - " + configuration.getResourceName());
		
	}

	/**
	 * View position hint
	 */
	@Override
	public int getPreferredPosition() {
		
		return 100;
	}
	
	@Override
	public Action getConfigureAction(final Frame owner) {
		return new ExampleConfigureAction(activity, owner);
	}

}
