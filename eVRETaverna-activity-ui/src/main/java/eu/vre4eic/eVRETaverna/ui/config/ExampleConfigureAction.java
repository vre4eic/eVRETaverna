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
net.sf.taverna.raven.repository.impl.LocalRepository test;
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
