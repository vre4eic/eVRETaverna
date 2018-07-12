package eu.vre4eic.eVRETaverna.ui;

import net.sf.taverna.t2.workbench.dev.DeveloperWorkbench;

/**
 * Run with parameters:
 * 
 * -Xmx300m -XX:MaxPermSize=140m 
 * -Dsun.swing.enableImprovedDragGesture
 * -Dtaverna.startup=.
 * 
 * 
 * 
 */
public class TavernaWorkbenchWithExamplePlugin {
	public static void main(String[] args) throws Exception {
		net.sf.taverna.raven.spi.SpiRegistry sr;
		DeveloperWorkbench.main(args);
	}
}
