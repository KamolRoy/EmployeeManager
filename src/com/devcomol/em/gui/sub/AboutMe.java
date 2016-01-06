package com.devcomol.em.gui.sub;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class AboutMe extends JFrame {

	/**
	 * About Me tha extend a JDialog
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jPanel;

	public AboutMe(JFrame mainFrame) throws IOException {

		jPanel = new JPanel();

		jPanel.setLayout(new BorderLayout());

		File sourceimage = new File("about_me.jpg");
		Image image = ImageIO.read(sourceimage);
		Image newImg = image.getScaledInstance(600, 360, java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newImg);

		JTextArea infoLabel = new JTextArea();
		infoLabel.setWrapStyleWord(true);
		infoLabel.setLineWrap(true);
		infoLabel.setColumns(400);
		infoLabel.setEditable(false);

		String infoText = "I have a passion for solving problems and find the field of computer programming extremely rewarding in this sense. As long as I can remember I have always strived to better my understanding of the things around me and tried to grasp knowledge that would enable me to create bigger and better things. \n\n"
				+ "Since I’ve been introduced to the world of programming I have been engulfed by its pristinely methodical procedures. I have extensive experience of Java (Core), Java web development with Spring, Java Desktop Application, Oracle SQL, HTML, CSS, JavaScript, XML and Eclipse as IDE. I consider myself an expert at resolving application defects, and at testing, debugging, and refining software to produce the required product. \n\n"
				+ "If you’re searching for a versatile, full-stack programmer striving for innovation, you’ve come to the right person! Check out my Profile to see what technical skills I can contribute to you or your organization.";

		infoLabel.setText(infoText);

		JScrollPane infoScrollPane = new JScrollPane(infoLabel);
		// infoScrollPane.setPreferredSize(new Dimension(500, 100));
		infoScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		jPanel.add(new JLabel(newIcon), BorderLayout.NORTH);
		jPanel.add(infoScrollPane, BorderLayout.CENTER);

		add(jPanel);

		setSize(800, 500);
		setLocationRelativeTo(mainFrame);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
