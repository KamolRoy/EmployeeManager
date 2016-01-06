package com.devcomol.em.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.devcomol.em.controller.Controller;
import com.devcomol.em.dao.ActivityModel;
import com.devcomol.em.gui.bones.EmployeeFileFilter;
import com.devcomol.em.gui.bones.FormActionEvent;
import com.devcomol.em.gui.bones.FormActionListener;
import com.devcomol.em.gui.bones.GEUtils;
import com.devcomol.em.gui.bones.ToolBarListener;
import com.devcomol.em.gui.sub.FormPanel;
import com.devcomol.em.gui.sub.ScrollingPanel;
import com.devcomol.em.gui.sub.TablePanel;
import com.devcomol.em.gui.sub.TopToolBar;
import com.devcomol.em.gui.sub.TreePanel;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFileChooser fileChooser;

	private TopToolBar topToolBar;
	private FormPanel formPanel;
	private TablePanel tablePanel;
	private TreePanel treePanel;
	private Controller controller;
	private JTabbedPane tabbedPane;
	private JSplitPane splitPane;
	private List<ActivityModel> activityLog;


	public MainFrame() {
		super("Employee Manager");

		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("com/devcomol/em/configxml/appbeans.xml");
		
		activityLog = new ArrayList<>();
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new EmployeeFileFilter());
		

		topToolBar = (TopToolBar) context.getBean("topToolBar");
		formPanel = (FormPanel) context.getBean("formPanel");
		tablePanel = (TablePanel) context.getBean("tablePanel");
		treePanel = (TreePanel) context.getBean("treePanel");
		controller = (Controller) context.getBean("controller");
		
		setJMenuBar(createMenuBar());
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Eamployee Table", tablePanel);
		tabbedPane.addTab("Employee Tree", treePanel);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel,tabbedPane);
		splitPane.setOneTouchExpandable(true);

		// ************* Communication *************************
		controller.setActivityLog(activityLog);
		controller.setParentFrame(this);
		
		tablePanel.setEmployees(controller.getEmployees());
		tablePanel.setActivityLog(activityLog);
		
		treePanel.setController(controller);

		tablePanel.setEmployeesTableActionListener((eid, rowIndex) -> {
			controller.deleteEmployee(eid, rowIndex);
		});

		try {
			controller.loadFromDatabase();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from database", "Load Error",
					JOptionPane.ERROR_MESSAGE);
		}

		topToolBar.setToolBarListener(new ToolBarListener() {

			@Override
			public void saveData() {
				controller.saveChange();
				tablePanel.refresh();
			}

			@Override
			public void clearData() {
				controller.clear();
				tablePanel.refresh();
			}

			@Override
			public void refreshData() {
				try {
					controller.loadFromDatabase();
					tablePanel.refresh();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		
		formPanel.setFormActionListener(new FormActionListener() {

			@Override
			public void FormActionPerformed(FormActionEvent formActionEvent) {
				controller.addEmployee(formActionEvent);
				tablePanel.refresh();
			}

		});

		tabbedPane.addChangeListener(e->{
			int tabIndex = tabbedPane.getSelectedIndex();
			if(tabIndex==0){
				formPanel.submitButton.setEnabled(true);
			}else {
				formPanel.submitButton.setEnabled(false);
			}
		});
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				int message = JOptionPane.showConfirmDialog(MainFrame.this, "Do you want to eixt this application",
						"Exit Confirmation", JOptionPane.OK_CANCEL_OPTION);
				if (message == JOptionPane.OK_OPTION) {
					dispose();
					System.gc();
				}

			}

		});

		// ************* Visual Design ************************

		setLayout(new BorderLayout());
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(getClass().getResource("/com/devcomol/em/resources/EmployeeManager.png")));

		add(topLabel(), BorderLayout.NORTH);
		add(splitPane, BorderLayout.CENTER);

		// setSize(screenSize);
		setMinimumSize(new Dimension(900, 650));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	private Component topLabel() {

		JPanel topPanel = new JPanel(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Top Lable One
		gbc.gridy = 0;

		gbc.weightx = 1;
		gbc.weighty = 1;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 0, 0);

		JLabel topLabel1 = new JLabel("Employee Manager", SwingConstants.RIGHT);
		topLabel1.setForeground(Color.WHITE);
		topLabel1.setOpaque(true);
		topLabel1.setBackground(new Color(85, 146, 220));
		topLabel1.setFont(GEUtils.createFont("/com/devcomol/em/resources/Exo-Regular.otf").deriveFont(Font.BOLD, 30));
		topLabel1.setIcon(GEUtils.createIcon24("/com/devcomol/em/resources/EmployeeManager.png"));
		topLabel1.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
		topPanel.add(topLabel1, gbc);

		// Top Label Two
		gbc.gridy++;

		gbc.weightx = 1;
		gbc.weighty = 1;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(0, 0, 0, 0);

		ScrollingPanel topLabel2 = new ScrollingPanel();
		topLabel2.setForeground(Color.WHITE);
		topLabel2.setOpaque(true);
		topLabel2.setBackground(new Color(85, 146, 220));
		topLabel2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		topPanel.add(topLabel2, gbc);

		// Top toolBar
		gbc.gridy++;

		gbc.weightx = 1;
		gbc.weighty = 1;

		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(0, 0, 0, 0);
		topPanel.add(topToolBar, gbc);

		return topPanel;
	}


	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		// --------------------- File Menu -----------------------------
		JMenu fileMenu = new JMenu("File");

		JMenuItem saveToItem = new JMenuItem("Save to Local Disk...");
		JMenuItem loadFromItem = new JMenuItem("Load from Local Disk...");
		JMenuItem exitItem = new JMenuItem("Exit");

		fileMenu.add(saveToItem);
		fileMenu.add(loadFromItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		fileMenu.setMnemonic(KeyEvent.VK_F);
		saveToItem.setMnemonic(KeyEvent.VK_S);
		saveToItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		loadFromItem.setMnemonic(KeyEvent.VK_I);
		loadFromItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		// ---------------------- Window Menu ----------------------------
		JMenu windowMenu = new JMenu("Window");
		JMenuItem showMenu = new JMenu("Show Panel");
/*		JMenuItem projectDetailsMenu = new JMenuItem ("Project Details");
		JMenuItem aboutMeMenu = new JMenuItem("About Me");*/
		JMenuItem showEForm = new JCheckBoxMenuItem("Employee Entry Form");
		showMenu.add(showEForm);

		showEForm.setSelected(true);

		windowMenu.add(showMenu);
		/*windowMenu.add(projectDetailsMenu);
		windowMenu.add(aboutMeMenu);*/

		menuBar.add(fileMenu);
		menuBar.add(windowMenu);

		// ------------------------ Actions -------------------------------
		showEForm.addActionListener(e -> formPanel.setVisible(showEForm.isSelected()));

		saveToItem.addActionListener(e -> {
			if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
				try {
					controller.saveToFile(fileChooser.getSelectedFile());
					JOptionPane.showMessageDialog(MainFrame.this, "Data saved successfully", "Save Successfull",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(MainFrame.this, "Error: Couldn't save data to file", "Save Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		loadFromItem.addActionListener(e -> {
			if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
				try {
					controller.loadFromFile(fileChooser.getSelectedFile());
					tablePanel.refresh();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(MainFrame.this, "Error: Couldn't load data from file \nIOException",
							"Load Error", JOptionPane.ERROR_MESSAGE);
				} catch (ClassNotFoundException e2) {
					JOptionPane.showMessageDialog(MainFrame.this,
							"Error: Couldn't load data from file \nClassNotFoundException", "Load Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		exitItem.addActionListener(e -> {

			WindowListener[] listeners = getWindowListeners();
			for (WindowListener listener : listeners) {
				listener.windowClosing(new WindowEvent(MainFrame.this, 0));
			}

		});
		

		return menuBar;

	}
}
