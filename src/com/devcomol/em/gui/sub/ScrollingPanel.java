package com.devcomol.em.gui.sub;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.devcomol.em.gui.bones.GEUtils;

public class ScrollingPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2840306072915296905L;

	private int x;
	private int y;
	private String text;
	
	
	
	public ScrollingPanel() {
		text = "[Sample Swing Project by Kamol Roy, Web: www.comolroy.com.au, Email: comolroy@gmail.com, Mob: +61470601092]";
		x = this.getWidth()+text.length();
		y = getPreferredSize().height;
	}



	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(GEUtils.createFont("/com/devcomol/em/resources/Exo-Regular.otf").deriveFont(Font.BOLD, 12));
		
		g2.drawString(text, x, y);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		
		x-=5;
		if(x<0-text.length()){
			x=this.getWidth();
		}
		
		repaint();

	}

	

}
