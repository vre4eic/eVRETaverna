package eu.vre4eic.eVRETaverna.ui.config;

import java.awt.Frame;
import java.awt.event.ActionEvent;

import net.sf.taverna.t2.workbench.ui.actions.activity.ActivityConfigurationAction;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationDialog;


import eu.vre4eic.eVRETaverna.VRE4EICActivity;
import eu.vre4eic.eVRETaverna.VRE4EICActivityConfigurationBean;

@SuppressWarnings("serial")
public class ExampleConfigureAction
		extends
		ActivityConfigurationAction<VRE4EICActivity, VRE4EICActivityConfigurationBean> {

	public ExampleConfigureAction(VRE4EICActivity activity, Frame owner) {
		
		super(activity);
		
	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		ActivityConfigurationDialog<VRE4EICActivity, VRE4EICActivityConfigurationBean> currentDialog = ActivityConfigurationAction
				.getDialog(getActivity());
		if (currentDialog != null) {
			currentDialog.toFront();
			return;
		}
		ExampleConfigurationPanel panel = new ExampleConfigurationPanel(
				getActivity());
		ActivityConfigurationDialog<VRE4EICActivity, VRE4EICActivityConfigurationBean> dialog = new ActivityConfigurationDialog<VRE4EICActivity, VRE4EICActivityConfigurationBean>(getActivity(), panel);

		ActivityConfigurationAction.setDialog(getActivity(), dialog);

	}

}
