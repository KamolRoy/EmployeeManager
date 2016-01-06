package com.devcomol.em.gui.bones;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class GEUtils {
	public static String getFileExtention(String fileName) {

		int pointIndex = fileName.lastIndexOf(".");

		if (pointIndex == -1) {
			return null;
		}

		if (pointIndex == fileName.length() - 1) {
			return null;
		}

		return fileName.substring(pointIndex + 1, fileName.length());
	}

	public static Font createFont(String path) {
		URL url = System.class.getResource(path);

		if (url == null) {
			System.err.println("Unable to load font: " + path);
		}

		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
		} catch (FontFormatException e) {
			System.err.println("Bad format in font file: " + path);
		} catch (IOException e) {
			System.err.println("Unable to read file: " + path);
		}
		return font;
	}

	public static ImageIcon createIcon16(String path) {
		URL url = System.class.getResource(path);

		if (url == null) {
			System.err.println("Unable to load image: " + path);
		}

		ImageIcon icon = new ImageIcon(url);
		Image img = icon.getImage();
		Image newImg = img.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newImg);
		return newIcon;
	}

	public static Icon createIcon24(String path) {
		URL url = System.class.getResource(path);

		if (url == null) {
			System.err.println("Unable to load image: " + path);
		}

		ImageIcon icon = new ImageIcon(url);
		Image img = icon.getImage();
		Image newImg = img.getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newImg);
		return newIcon;
	}
}
