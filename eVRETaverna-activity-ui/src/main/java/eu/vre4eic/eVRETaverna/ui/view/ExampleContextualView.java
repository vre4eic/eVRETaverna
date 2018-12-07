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
