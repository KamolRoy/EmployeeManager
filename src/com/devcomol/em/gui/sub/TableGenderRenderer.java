package com.devcomol.em.gui.sub;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.devcomol.em.gui.bones.EnumGender;

public class TableGenderRenderer implements TableCellRenderer{
	
	private JComboBox<EnumGender> genderCombo;
	
	

	public TableGenderRenderer() {
		genderCombo = new JComboBox<>(EnumGender.values());
		genderCombo.setMinimumSize(new Dimension((int) genderCombo.getPreferredSize()
				.getHeight(), 30));
	}



	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		genderCombo.setSelectedItem(value);
		return genderCombo;
	}

}
