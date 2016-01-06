package com.devcomol.em.gui.sub;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToolBar;

import org.springframework.stereotype.Component;

import com.devcomol.em.gui.bones.GEUtils;
import com.devcomol.em.gui.bones.ToolBarListener;

@Component("topToolBar")
public class TopToolBar extends JToolBar implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton saveButton;
	private JButton clearButton;
	private JButton refreshButton;

	ToolBarListener toolBarListener;

	public TopToolBar() {
		saveButton = new JButton();
		saveButton.setIcon(GEUtils.createIcon24("/com/devcomol/em/resources/save.png"));
		saveButton.setToolTipText("Save Table data to Database");
		saveButton.setBorderPainted(false);
		
		clearButton = new JButton();
		clearButton.setIcon(GEUtils.createIcon24("/com/devcomol/em/resources/clear.png"));
		clearButton.setToolTipText("Clear the Table");
		clearButton.setBorderPainted(false);
		
		refreshButton = new JButton();
		refreshButton.setIcon(GEUtils.createIcon24("/com/devcomol/em/resources/refresh.png"));
		refreshButton.setToolTipText("Load Table data from Database");
		refreshButton.setBorderPainted(false);

		saveButton.addActionListener(this);
		clearButton.addActionListener(this);
		refreshButton.addActionListener(this);

		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createEtchedBorder());

		add(saveButton);
		add(clearButton);
		add(refreshButton);

	}

	public void setToolBarListener(ToolBarListener toolBarListener) {
		this.toolBarListener = toolBarListener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton toolClicked = (JButton) e.getSource();

		if (toolClicked == saveButton) {
			toolBarListener.saveData();
		} else if (toolClicked == clearButton) {
			toolBarListener.clearData();
		} else if (toolClicked == refreshButton) {
			toolBarListener.refreshData();
		}

	}

}
