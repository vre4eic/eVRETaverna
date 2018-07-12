package eu.vre4eic.eVRETaverna.ui.serviceprovider;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.sf.taverna.t2.workbench.activityicons.ActivityIconSPI;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;
import eu.vre4eic.eVRETaverna.VRE4EICActivity;

public class ExampleServiceIcon implements ActivityIconSPI {

	private static Icon icon;

	public int canProvideIconScore(Activity<?> activity) {
		
		if (activity instanceof VRE4EICActivity) {
			return DEFAULT_ICON;
		}
		
		return NO_ICON;
	}

	public Icon getIcon(Activity<?> activity) {
		return getIcon();
	}
	
	public static Icon getIcon() {
		
		if (icon == null) {
			icon = new ImageIcon(ExampleServiceIcon.class.getResource("/vre4eic_logo_ico.png"));
		}
		return icon;
	}

}
