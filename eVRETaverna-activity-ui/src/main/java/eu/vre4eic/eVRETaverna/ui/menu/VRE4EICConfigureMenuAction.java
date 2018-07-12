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
