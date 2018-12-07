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
package eu.vre4eic.eVRETaverna.ui.save;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import net.sf.taverna.t2.workbench.icons.WorkbenchIcons;

/**
 * Dialog that is popped up while we are exporting workflow run provenance. 
 * This is to let the user know that Taverna is doing something.
 * 
 * @author Alex Nenadic
 *
 */
public class SaveWFInProgressDialog extends JDialog implements PropertyChangeListener{

	private static final long serialVersionUID = 3022516542431968398L;

	public SaveWFInProgressDialog(final SaveWFSwingWorker worker) {
		
		super((JFrame) null, "Saving workflow in VRE4EIC catalogue", true);
		worker.addPropertyChangeListener(this);

		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		JPanel textPanel = new JPanel();
		JLabel text = new JLabel(WorkbenchIcons.workingIcon);
		text.setText("Saving workflow...");
		text.setBorder(new EmptyBorder(10,0,10,0));
		textPanel.add(text);
		panel.add(textPanel, BorderLayout.CENTER);
		
		JButton cancelButton = new JButton(new AbstractAction("Cancel") {
			@Override
			public void actionPerformed(ActionEvent e) {
				worker.cancel(true);
			}
		});
		JPanel buttons = new JPanel();
		buttons.add(cancelButton, BorderLayout.CENTER);		
		panel.add(buttons, BorderLayout.SOUTH);
		
		setContentPane(panel);
		setPreferredSize(new Dimension(300, 100));

		pack();		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("state".equals(evt.getPropertyName())
                && SwingWorker.StateValue.DONE == evt.getNewValue()) {
            this.setVisible(false);
            this.dispose();
        }		
	}

}
