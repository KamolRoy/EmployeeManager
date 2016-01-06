package com.devcomol.em.gui.sub;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.springframework.stereotype.Component;

import com.devcomol.em.dao.ActivityModel;
import com.devcomol.em.dao.Employee;
import com.devcomol.em.gui.bones.EmployeesTableActionListener;
import com.devcomol.em.gui.bones.EnumGender;

@Component("tablePanel")
public class TablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable employeesTable;
	private TableModelEmployees tableModelEmployees;
	private JPopupMenu popMenu;
	private EmployeesTableActionListener employeesTableActionListener;

	public TablePanel() {
		tableModelEmployees = new TableModelEmployees();
		employeesTable = new JTable(tableModelEmployees){
			 
	         /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	         public java.awt.Component prepareRenderer(TableCellRenderer renderer, int row,
	                 int column) {
	            java.awt.Component component = super.prepareRenderer(renderer, row, column);
	            int rendererWidth = component.getPreferredSize().width;
	            TableColumn tableColumn = getColumnModel().getColumn(column);
	            tableColumn.setPreferredWidth(Math.max(rendererWidth +
	                    getIntercellSpacing().width,
	                    tableColumn.getPreferredWidth()));
	            return  component;
	         }
	      };
	      employeesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		popMenu = new JPopupMenu();

		setLayout(new BorderLayout());
		Dimension preferredSize = getPreferredSize();
		preferredSize.width = 750;
		setPreferredSize(preferredSize);
		setMinimumSize(preferredSize);
		
		
		employeesTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		 
		
		
		add(new JScrollPane(employeesTable), BorderLayout.CENTER);

		JMenuItem deleteRow = new JMenuItem("Delete Row");
		popMenu.add(deleteRow);
		
		employeesTable.setDefaultRenderer(EnumGender.class, new TableGenderRenderer());
		employeesTable.setDefaultEditor(EnumGender.class, new TableGenderEditor());
		employeesTable.setRowHeight(23);
		
		// ***********  Actions *****************

		employeesTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				int row = employeesTable.rowAtPoint(e.getPoint());
				employeesTable.getSelectionModel().setSelectionInterval(row, row);

				if (e.getButton() == MouseEvent.BUTTON3)
					popMenu.show(employeesTable, e.getX(), e.getY());
			}

		});

		deleteRow.addActionListener(e -> {
			int row = employeesTable.getSelectedRow();

			int eid = (int) employeesTable.getValueAt(row, 0);
			if (employeesTableActionListener != null) {

				employeesTableActionListener.deleteRow(eid,row);
				tableModelEmployees.fireTableRowsDeleted(row, row);
			}
		});
	}

	public void setEmployees(List<Employee> employees) {
		tableModelEmployees.setEmployees(employees);
	}

	public void refresh() {
		tableModelEmployees.fireTableDataChanged();
	}

	public void setEmployeesTableActionListener(EmployeesTableActionListener employeesTableActionListener) {
		this.employeesTableActionListener = employeesTableActionListener;
	}

	public void setActivityLog(List<ActivityModel> activityLog) {
		tableModelEmployees.setActivityLog(activityLog);
	}

}
