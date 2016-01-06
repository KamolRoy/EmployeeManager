package com.devcomol.em.gui.bones;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class EmployeeFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {

		if (file.isDirectory()) {
			return true;
		}

		String fileName = file.getName();

		String extension = GEUtils.getFileExtention(fileName);
		if (extension == null) {
			return false;
		}

		if (extension.equals("emp")) {
			return true;
		}

		return false;
	}

	@Override
	public String getDescription() {
		return "Employee DB file (*.emp)";
	}

}
