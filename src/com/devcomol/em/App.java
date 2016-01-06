package com.devcomol.em;

import javax.swing.SwingUtilities;

import com.devcomol.em.gui.MainFrame;

public class App {
	public static void main(String args[]) {

		SwingUtilities.invokeLater(() -> {
			new MainFrame();
		});
	}
}
 