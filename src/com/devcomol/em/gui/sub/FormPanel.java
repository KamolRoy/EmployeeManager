package com.devcomol.em.gui.sub;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import org.springframework.stereotype.Component;

import com.devcomol.em.dao.DivisionAndDepartment;
import com.devcomol.em.dao.DivisionModel;
import com.devcomol.em.gui.bones.FormActionEvent;
import com.devcomol.em.gui.bones.FormActionListener;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

@Component()
public class FormPanel extends JPanel {

	private JTextField firstField;
	private JTextField lastField;
	private JComboBox<DivisionModel> divisionComboBox;
	private JList<String> departmentList;
	private JCheckBox auCitizen;
	private JCheckBox tfnCheck;
	private JLabel tfnLabel;
	private JTextField tfnField;
	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup;
	private JTextArea responsibilityArea;
	public JButton submitButton;
	private JDatePickerImpl datePicker;

	DefaultListModel<String> departmentListModel;
	
	List<DivisionModel> divisions;
	Map<Integer, ListModel<String>> departments;

	FormActionListener formActionListener;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FormPanel() {

		firstField = new JTextField(20);
		lastField = new JTextField(20);
		divisionComboBox = new JComboBox<>();
		departmentList = new JList<String>();
		auCitizen = new JCheckBox();
		tfnCheck = new JCheckBox();
		tfnLabel = new JLabel("TFN No");
		tfnField = new JTextField(20);
		maleRadio = new JRadioButton("Male");
		femaleRadio = new JRadioButton("Female");
		genderGroup = new ButtonGroup();
		datePicker = getDatePicker();
		responsibilityArea = new JTextArea();
		submitButton = new JButton("Submit");

		divisions=DivisionAndDepartment.getDivision();
		departments=DivisionAndDepartment.getDepartment();
		responsibilityArea.setText("Nothing");
		// ************* Division Combo Box *************************
		DefaultComboBoxModel<DivisionModel> divisionComboBoxModel = new DefaultComboBoxModel<>();
		if(divisions!=null){
			for(DivisionModel divisionModel: divisions){
				divisionComboBoxModel.addElement(divisionModel);
			}
		}

		divisionComboBox.setModel(divisionComboBoxModel);
//		divisionComboBox.setSelectedIndex(0);

		// ************** Department List Box **************************

		departmentListModel = new DefaultListModel<String>();

		departmentListModel.addElement("Government Relation");
		departmentListModel.addElement("Administration");

		departmentList.setModel(departmentListModel);
		departmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		departmentList.setSelectedIndex(1);

		// ************** TFN Check Box and Field ********************
		tfnLabel.setEnabled(false);
		tfnField.setEnabled(false);
		tfnField.setText("1234");

		// ************** Gender Radio Button ***********************

		maleRadio.setSelected(true);
		maleRadio.setActionCommand("male");
		femaleRadio.setActionCommand("female");
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);

		// ************* Responsibility TextArea ***********************

		responsibilityArea.setColumns(15);
		responsibilityArea.setWrapStyleWord(true);
		responsibilityArea.setLineWrap(true);

		// ************** Action definition area ***********************

		divisionComboBox.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				DivisionModel dModel = (DivisionModel) e.getItem();

				setDepartmentModel(dModel);
				departmentList.setSelectedIndex(0);
			}
		});

		tfnCheck.addActionListener(e -> {
			boolean isChecked = tfnCheck.isSelected();

			tfnLabel.setEnabled(isChecked);
			tfnField.setEnabled(isChecked);
		});

		submitButton.addActionListener(e -> {
			String firstName = firstField.getText();
			String lastName = lastField.getText();
			DivisionModel divisionModel = (DivisionModel) divisionComboBoxModel.getSelectedItem();
			int divisionID = divisionModel.getDid();
			String department = departmentList.getSelectedValue().toString();
			boolean citizen = auCitizen.isSelected();
			boolean hasTfn = tfnCheck.isSelected();
			String tfn = tfnField.getText();
			String gender = genderGroup.getSelection().getActionCommand();
			Date dateOfBirth = (Date) datePicker.getModel().getValue();
			String responsibility = responsibilityArea.getText();

			FormActionEvent formActionEvent = new FormActionEvent(this, firstName, lastName, divisionID, department,
					citizen, hasTfn, tfn, gender, dateOfBirth, responsibility);

			formActionListener.FormActionPerformed(formActionEvent);

		});

		// **************** Graphical Interface **********************

		Dimension preferredSize = getPreferredSize();
		preferredSize.width = 350;
		setPreferredSize(preferredSize);
		setMinimumSize(preferredSize);
		setMaximumSize(new Dimension(preferredSize.height,450));

		Border innerBorder = BorderFactory.createTitledBorder("Employee Entry Form");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		layOutComponent();

	}

	private void layOutComponent() {
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NONE;

		// ********** Setting Up First Name Text Field *******************
		gbc.gridy = 0;

		gbc.weightx = 1;
		gbc.weighty = 0.1;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("First Name"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(firstField, gbc);

		// *********** Setting Up Last Name Text Field ******************
		gbc.gridy++;

		gbc.weightx = 1;
		gbc.weighty = 0.1;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Last Name"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(lastField, gbc);

		// *********** Setting Up Combo Box List ****************
		gbc.gridy++;

		gbc.weightx = 1;
		gbc.weighty = 0.1;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Division"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(divisionComboBox, gbc);

		// ************ Setting Up Department List *****************
		gbc.gridy++;
		gbc.weightx = 1;
		gbc.weighty = 0.1;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Department"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		JScrollPane departmentJP = new JScrollPane(departmentList);
		departmentJP.setPreferredSize(new Dimension(220, 90));
		departmentJP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(departmentJP, gbc);

		// ************** Setting up Citizenship Check Box *****************
		gbc.gridy++;
		gbc.weightx = 1;
		gbc.weighty = 0.1;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Citizen"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(auCitizen, gbc);

		// ************** Setting up TFN Check Box *****************
		gbc.gridy++;
		gbc.weightx = 1;
		gbc.weighty = 0.1;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("TFN"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(tfnCheck, gbc);

		// ************** Setting up TFN Label *****************
		gbc.gridy++;
		gbc.weightx = 1;
		gbc.weighty = 0.1;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(tfnLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(tfnField, gbc);

		// ************** Setting up Gender Radio Button *****************
		gbc.gridy++;
		gbc.weightx = 1;
		gbc.weighty = 0.1;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Gener"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		JPanel genderPanel = new JPanel(new FlowLayout());
		genderPanel.add(maleRadio);
		genderPanel.add(femaleRadio);
		add(genderPanel, gbc);

		// ************** Setting up Date of Birth *****************
		gbc.gridy++;
		gbc.weightx = 1;
		gbc.weighty = 0.1;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Date of Birth"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;

		add(datePicker, gbc);

		// ************** Setting up Responsibility Text Area *****************
		gbc.gridy++;
		gbc.weightx = 1;
		gbc.weighty = 0.1;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Responsibility"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		JScrollPane responsibilityJP = new JScrollPane(responsibilityArea);
		responsibilityJP.setPreferredSize(new Dimension(220, 80));
		add(responsibilityJP, gbc);

		// *********** Setting Up Last Row ******************
		gbc.gridy++;

		gbc.weightx = 1;
		gbc.weighty = 2;

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		gbc.insets = new Insets(10, 0, 0, 18);
		add(submitButton, gbc);
	}

	private void setDepartmentModel(DivisionModel dModel) {

		int dID = dModel.getDid();

		switch (dID) {
		case 0:
			departmentList.setModel(departments.get(0));
			break;
		case 1:
			departmentList.setModel(departments.get(1));
			break;
		case 2:
			departmentList.setModel(departments.get(2));
			break;
		case 3:
			departmentList.setModel(departments.get(3));
			break;
		case 4:
			departmentList.setModel(departments.get(4));
			break;
		default:
			departmentList.setModel(departments.get(0));
			break;
		}

	}

	

	private JDatePickerImpl getDatePicker() {
		UtilDateModel model = new UtilDateModel();
		model.setDate(2000, 00, 01);
		model.setSelected(true);
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePickerImpl returnDatePicker = new JDatePickerImpl(datePanel);

		return returnDatePicker;
	}


	public void setFormActionListener(FormActionListener formActionListener) {
		this.formActionListener = formActionListener;
	}

}
