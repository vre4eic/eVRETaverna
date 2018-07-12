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
