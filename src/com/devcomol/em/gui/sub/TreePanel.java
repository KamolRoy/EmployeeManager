package com.devcomol.em.gui.sub;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import org.springframework.stereotype.Component;

import com.devcomol.em.controller.Controller;
import com.devcomol.em.dao.DivisionAndDepartment;
import com.devcomol.em.dao.DivisionModel;
import com.devcomol.em.dao.Employee;
import com.devcomol.em.gui.bones.EnumGender;
import com.devcomol.em.gui.bones.GEUtils;

@Component("treePanel")
public class TreePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4550045356809855492L;
	private JTree employeeTree;
	private DefaultTreeCellRenderer treeCellRenderer;
	private Map<Integer, Employee> employees;
	private List<DivisionModel> divisions;
	private Map<Integer, ListModel<String>> departments;

	private JTextArea employeeTextArea;
	private JList<EmployeeListItem> employeeList;
	private JSplitPane upperPane;
	private JSplitPane lowerPane;

	private Controller controller;

	public TreePanel() {
		employees = new HashMap<>();
		divisions = DivisionAndDepartment.getDivision();
		departments = DivisionAndDepartment.getDepartment();

		employeeTextArea = new JTextArea();
		employeeList = new JList<>();
		employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		employeeList.setCellRenderer(new DefaultListCellRenderer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index,
					boolean isSelected, boolean cellHasFocus) {
				JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
						cellHasFocus);
				EmployeeListItem employeeListItem = (EmployeeListItem)value;
				EnumGender gender= employeeListItem.getGender();
				
				switch (gender) {
				case MALE:
					label.setIcon(GEUtils.createIcon24("/com/devcomol/em/resources/male.png"));
					break;
				case FEMALE:
					label.setIcon(GEUtils.createIcon24("/com/devcomol/em/resources/female.jpe"));
					break;
				}
				
				return label;
			}
		});

		treeCellRenderer = new DefaultTreeCellRenderer();
		treeCellRenderer.setLeafIcon(GEUtils.createIcon24("/com/devcomol/em/resources/department.png"));
		treeCellRenderer.setOpenIcon(GEUtils.createIcon24("/com/devcomol/em/resources/open.png"));
		treeCellRenderer.setClosedIcon(GEUtils.createIcon24("/com/devcomol/em/resources/close.png"));

		employeeTree = new JTree(createTree());
		employeeTree.setCellRenderer(treeCellRenderer);
		employeeTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		employeeTree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {

				employeeTextArea.setText("");

				/*
				 * if(employeeList.isSelectionEmpty()){
				 * employeeList.clearSelection(); }
				 */
				employees.clear();

				DefaultMutableTreeNode lastNode = (DefaultMutableTreeNode) employeeTree.getLastSelectedPathComponent();
				Object lastUserObject = lastNode.getUserObject();
				if (lastUserObject instanceof EmployeeNode) {
					String department = lastUserObject.toString();
					try {
						List<Employee> deptEmployee = controller.loadFromDatabase(department);

						if (deptEmployee.size() > 0) {
							DefaultListModel<EmployeeListItem> listModel = new DefaultListModel<>();

							for (Employee employee : deptEmployee) {
								listModel.addElement(new EmployeeListItem(employee.getGender(), employee.geteID() + ": " + employee.getFirstName() + " "
										+ employee.getLastName()));
								employees.put(employee.geteID(), employee);
							}

							employeeList.setModel(listModel);
						} else {
							System.out.println("Still Alive");
							DefaultListModel<EmployeeListItem> listModel = new DefaultListModel<>();
							listModel.addElement(new EmployeeListItem(EnumGender.MALE, "0000: No employee found under this department"));

							employeeList.setModel(listModel);
						}

					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Something wrong! couldn't load employee data",
								"Load Error:", JOptionPane.ERROR_MESSAGE);
						System.out.println("Something wrong");
						e1.printStackTrace();
					}
				}

			}
		});

		employeeList.addListSelectionListener(e -> {

			int index = employeeList.getSelectedIndex();

			if (index != -1) {
				String selectedValue = employeeList.getSelectedValue().toString();
				int employeeID = Integer.parseInt(selectedValue.substring(0, selectedValue.indexOf(":")));

				if (employeeID == 0) {
					employeeTextArea.setText("No employee information found");
					return;
				}

				Employee employee = employees.get(employeeID);
				StringBuilder sb = new StringBuilder();

				sb.append("EmployeeID: " + employee.geteID() + "\n");
				sb.append("First Name: " + employee.getFirstName() + "\n");
				sb.append("Last Name: " + employee.getLastName() + "\n");
				sb.append("Date of Birth: " + employee.getDateOfBirth().toString() + "\n");
				sb.append("Division: " + employee.getDivision() + "\n");
				sb.append("Department: " + employee.getDepartment() + "\n");
				sb.append("Citizen: " + employee.isAuCitizen() + "\n");
				sb.append("TFN: " + employee.getTfn() + "\n");
				sb.append("Responsibility: " + employee.getResponsibility() + "\n");

				employeeTextArea.setText(sb.toString());
			}
		});

		// ***************** Visual Design *************************
		JScrollPane textAreaJP = new JScrollPane(employeeTextArea);
		Dimension textDim = textAreaJP.getPreferredSize();
		textDim.height = 150;
		textAreaJP.setMinimumSize(textDim);

		JScrollPane listJP = new JScrollPane(employeeList);
		Dimension listDim = listJP.getPreferredSize();
		listDim.height = 80;
		listJP.setMinimumSize(listDim);

		lowerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, listJP, textAreaJP);
		upperPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(employeeTree), lowerPane);

		lowerPane.setResizeWeight(0);
		upperPane.setResizeWeight(0.6);

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEtchedBorder());
		add(upperPane, BorderLayout.CENTER);
	}

	private DefaultMutableTreeNode createTree() {
		DefaultMutableTreeNode office = new DefaultMutableTreeNode("Office");

		for (DivisionModel divisionModel : divisions) {
			DefaultMutableTreeNode division = new DefaultMutableTreeNode(divisionModel);

			ListModel<String> listModelDepartment = departments.get(divisionModel.getDid());

			for (int i = 0; i < listModelDepartment.getSize(); i++) {
				DefaultMutableTreeNode department = new DefaultMutableTreeNode(
						new EmployeeNode(i, listModelDepartment.getElementAt(i).toString()));
				division.add(department);
			}

			office.add(division);
		}

		return office;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

}

class EmployeeNode {
	int id;
	String deptName;

	public EmployeeNode(int id, String deptName) {
		this.id = id;
		this.deptName = deptName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return deptName;
	}
}

class EmployeeListItem {
	EnumGender gender;
	String employeeInfo;

	public EmployeeListItem(EnumGender gender, String employeeInfo) {
		this.gender = gender;
		this.employeeInfo = employeeInfo;
	}

	public EnumGender getGender() {
		return gender;
	}

	public void setGender(EnumGender gender) {
		this.gender = gender;
	}

	public String getEmployeeInfo() {
		return employeeInfo;
	}

	public void setEmployeeInfo(String employeeInfo) {
		this.employeeInfo = employeeInfo;
	}

	@Override
	public String toString() {
		return employeeInfo;
	}
}
