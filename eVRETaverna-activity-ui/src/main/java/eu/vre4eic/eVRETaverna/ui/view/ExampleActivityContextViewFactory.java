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
package eu.vre4eic.eVRETaverna.ui.view;

import java.util.Arrays;
import java.util.List;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.ContextualView;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ContextualViewFactory;


import eu.vre4eic.eVRETaverna.VRE4EICActivity;

public class ExampleActivityContextViewFactory implements
		ContextualViewFactory<VRE4EICActivity> {

	public boolean canHandle(Object selection) {
		;
		return selection instanceof VRE4EICActivity;
	}

	public List<ContextualView> getViews(VRE4EICActivity selection) {
		
		return Arrays.<ContextualView>asList(new ExampleContextualView(selection));
	}
	
}
