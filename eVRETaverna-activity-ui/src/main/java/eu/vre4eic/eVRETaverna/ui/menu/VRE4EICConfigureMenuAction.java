/*******************************************************************************
 * Copyright 2018 cesare
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
package eu.vre4eic.eVRETaverna.ui.menu;

import javax.swing.Action;

import net.sf.taverna.t2.workbench.activitytools.AbstractConfigureActivityMenuAction;

import eu.vre4eic.eVRETaverna.VRE4EICActivity;
import eu.vre4eic.eVRETaverna.ui.config.ExampleConfigureAction;

public class VRE4EICConfigureMenuAction extends
		AbstractConfigureActivityMenuAction<VRE4EICActivity> {

	public VRE4EICConfigureMenuAction() {
		super(VRE4EICActivity.class);
		
	}

	@Override
	protected Action createAction() {
		
		VRE4EICActivity a = findActivity();
		Action result = null;
		
		result = new ExampleConfigureAction(findActivity(),
				getParentFrame());
		result.putValue(Action.NAME, "Configure VRE4EIC service");
		addMenuDots(result);
		return result;
	}

}
