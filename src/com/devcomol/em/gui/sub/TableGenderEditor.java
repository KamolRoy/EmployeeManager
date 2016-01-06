package com.devcomol.em.gui.sub;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.devcomol.em.gui.bones.EnumGender;

public class TableGenderEditor extends AbstractCellEditor implements TableCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5119369331819459346L;
	private JComboBox<EnumGender> genderCombo;
	
	

	public TableGenderEditor() {
		genderCombo = new JComboBox<>(EnumGender.values());
	}

	@Override
	public Object getCellEditorValue() {
		return genderCombo.getSelectedItem();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		genderCombo.setSelectedItem(value);
		
		genderCombo.addActionListener(e->{
			fireEditingStopped();
		});
		
		return genderCombo;
	}

	@Override
	public boolean isCellEditable(EventObject e) {
		
		return true;
	}

	

}
