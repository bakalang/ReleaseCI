
package com.asuscloud.doTestcase.action;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.lang.StringUtils;

//import com.asuscloud.common.LoadConfig;
import com.asuscloud.doTestcase.action.base.ControlBase;
import com.asuscloud.doTestcase.action.base.TableButton;
import com.asuscloud.doTestcase.service.APICallerService;
import com.asuscloud.doTestcase.service.LoadCaseService;
import com.asuscloud.doTestcase.service.TestcaseLoadConfig;
import com.ecareme.http.api.APIException;
import com.ecareme.qed.bean.RegisterBean;
import com.ecareme.qed.constant.ProductType;
import com.google.gson.Gson;

public class ControlFrameTestcase extends ControlBase implements ActionListener
{
	private JFrame frame;
	private JPanel mainPanel;
	private JTabbedPane tabbedPane;
	private JPanel innerPanel;
	private JPanel logPanel;
	private JPanel subPanel;
	private JPanel clazzInnerPanel;
	private JScrollPane clazzSscrollPane;
	private JTextField componentTX;
	private JTextField clazzTX;
	private JTextField userIdTX;
	private JTextField passwordTX;
	private JTextField commercialIdTX;
	private JCheckBox isHomecloudCB;
	private JTextField spTX;
	private JTextField sgTX;
	private JTextField irTX;
	private JTextField wrTX;
	private JTextField msTX;
	private JTextField logPathTX;
	private JTextField quotaTX;
	private JTextArea logTA;
	private JScrollPane logSCL;
	private JButton runBTN;
	private JButton stopBTN;
	private JButton selectallBTN;
	private JButton unselectallBTN;
	private JButton userRegBTN;
	private JButton userRequestTokenBTN;
	private JButton getTeaminfoBTN;
	private JButton addMemberBTN;
	private JButton u2RegBTN;
	private JButton u3RegBTN;
	private JTable teaminfoTb;
	private JScrollPane teaminfoTbScl;
	private PrintStream standardOut;
	private LoadCaseService loadCaseService;

	/**
	 * Launch the application.
	 */
	public static void main(String args[])
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ControlFrameTestcase window = new ControlFrameTestcase();
					window.frame.setVisible(true);
				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ControlFrameTestcase()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 668);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

//		initialize();
		initTestcase();
		changeClassMethodDisplay();

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnTestcase = new JMenu("testcase");
		menuBar.add(mnTestcase);

		JMenuItem mntmBbb = new JMenuItem("load testcase");
		mntmBbb.setName("load_testcase");
		mntmBbb.addActionListener(this);
		mnTestcase.add(mntmBbb);

		JMenu mnTeam = new JMenu("team");
		menuBar.add(mnTeam);

		JMenuItem mntmTeamManager = new JMenuItem("team manager");
		mntmTeamManager.setName("team_manager");
		mntmTeamManager.addActionListener(this);
		mnTeam.add(mntmTeamManager);
	}

	/**
	 * Initialize the contents of the frame.
	 */
//	private void initialize()
//	{
//		mainPanel = new JPanel();
//		mainPanel.setBounds(12, 36, 285, 392);
//		mainPanel.setBorder(BorderFactory.createTitledBorder(MAIN_PANEL));
//		frame.getContentPane().add(mainPanel);
//		mainPanel.setLayout(null);
//
//		logPanel = new JPanel();
//		logPanel.setBounds(10, 428, 714, 170);
//		logPanel.setBorder(BorderFactory.createTitledBorder(LOG_PANEL));
//		frame.getContentPane().add(logPanel);
//		logPanel.setLayout(null);
//
//		subPanel = new JPanel();
//		subPanel.setBorder(BorderFactory.createTitledBorder(SUB_PANEL));
//		subPanel.setBounds(307, 37, 419, 392);
//		frame.getContentPane().add(subPanel, BorderLayout.CENTER);
//		subPanel.setLayout(null);
//
//		JLabel spLB = new JLabel("servicePortal");
//		spLB.setBounds(10, 25, 100, 15);
//		mainPanel.add(spLB);
//
//		JLabel sgLB = new JLabel("serviceGateway");
//		sgLB.setBounds(10, 50, 100, 15);
//		mainPanel.add(sgLB);
//
//		JLabel msLB = new JLabel("managerstudio");
//		msLB.setBounds(10, 75, 100, 15);
//		mainPanel.add(msLB);
//
//		spTX = new JTextField();
//		spTX.setText(LoadConfig.sp);
//		spTX.setBounds(122, 25, 140, 20);
//		mainPanel.add(spTX);
//		spTX.setColumns(10);
//
//		sgTX = new JTextField();
//		sgTX.setText(LoadConfig.sg);
//		sgTX.setColumns(10);
//		sgTX.setBounds(122, 50, 140, 20);
//		mainPanel.add(sgTX);
//
//		msTX = new JTextField();
//		msTX.setText("192.168.1.226:443");
//		msTX.setColumns(10);
//		msTX.setBounds(122, 75, 140, 20);
//		mainPanel.add(msTX);
//
//		JLabel userIdLB = new JLabel(KEY_USERID);
//		userIdLB.setBounds(10, 125, 45, 15);
//		mainPanel.add(userIdLB);
//
//		userIdTX = new JTextField();
//		userIdTX.setName(KEY_USERID);
//		userIdTX.setBounds(122, 125, 140, 20);
//		mainPanel.add(userIdTX);
//		userIdTX.setColumns(10);
//
//		JLabel passwordLB = new JLabel(KEY_PASSWORD);
//		passwordLB.setBounds(10, 150, 95, 20);
//		mainPanel.add(passwordLB);
//
//		passwordTX = new JTextField();
//		passwordTX.setName(KEY_PASSWORD);;
//		passwordTX.setBounds(122, 150, 140, 20);
//		mainPanel.add(passwordTX);
//		passwordTX.setColumns(10);
//
//		JLabel commercialIdLB = new JLabel("commercialId");
//		commercialIdLB.setBounds(10, 219, 140, 20);
//		mainPanel.add(commercialIdLB);
//
//		commercialIdTX = new JTextField();
//		commercialIdTX.setEnabled(false);
//		commercialIdTX.setName(KEY_COMMERCIALID);
//		commercialIdTX.setBounds(122, 219, 140, 20);
//		mainPanel.add(commercialIdTX);
//		commercialIdTX.setColumns(10);
//
//		userRegBTN = new JButton("register");
//		userRegBTN.setName("managerstudioregister_textfield" + NUMBER_SIGN + KEY_USERID + NUMBER_SIGN + KEY_PASSWORD);
//		userRegBTN.setBounds(58, 174, 83, 20);
//		userRegBTN.addActionListener(this);
//		mainPanel.add(userRegBTN);
//
//		userRegBTN = new JButton("enable");
//		userRegBTN.setName("enable_team" + NUMBER_SIGN + KEY_COMMERCIALID);
//		userRegBTN.setBounds(59, 246, 83, 20);
//		userRegBTN.addActionListener(this);
//		mainPanel.add(userRegBTN);
//
//		userRequestTokenBTN = new JButton("getCommercialId");
//		userRequestTokenBTN.setName("get_commercial_id" + NUMBER_SIGN + KEY_COMMERCIALID);
//		userRequestTokenBTN.setBounds(144, 174, 119, 20);
//		mainPanel.add(userRequestTokenBTN);
//
//		getTeaminfoBTN = new JButton("getTeaminfo");
//		getTeaminfoBTN.setName("get_teaminfo" + NUMBER_SIGN);
//		getTeaminfoBTN.setBounds(145, 246, 119, 20);
//		getTeaminfoBTN.addActionListener(this);
//		mainPanel.add(getTeaminfoBTN);
//		
//		quotaTX = new JTextField();
//		quotaTX.setName("quota");
//		quotaTX.setColumns(10);
//		quotaTX.setBounds(110, 279, 46, 20);
//		mainPanel.add(quotaTX);
//		
//		JLabel lblQuotaForMember = new JLabel("quota for member");
//		lblQuotaForMember.setBounds(10, 282, 96, 20);
//		mainPanel.add(lblQuotaForMember);
//		
//		addMemberBTN = new JButton("addMember");
//		addMemberBTN.setName("add_member");
//		addMemberBTN.setBounds(161, 279, 104, 20);
//		addMemberBTN.addActionListener(this);
//		mainPanel.add(addMemberBTN);
//
//		logTA = new JTextArea(5, 30);
//		logTA.setBounds(1, 1, 25, 10);
//
//		logSCL = new JScrollPane(logTA);
//		logSCL.setBounds(10, 23, 689, 139);
//		logSCL.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//		logSCL.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		logPanel.add(logSCL);
//
//		PrintStream printStream = new PrintStream(new LogOutputStream(logTA));
//
//		standardOut = System.out;
//		System.setOut(printStream);
//		System.setErr(printStream);
//
//		Vector columnNames = new Vector<String>();
//		columnNames.addElement("");
//		columnNames.addElement("userid");
//		columnNames.addElement("usedcapacity");
//		columnNames.addElement("Capacity");
//		columnNames.addElement("S");
//		columnNames.addElement("D");
//
//		teaminfoTb = new JTable(new Vector<Vector>(), columnNames);
//		teaminfoTb.setPreferredScrollableViewportSize(new Dimension(70, 70));
//		// contenTb.getColumnModel().removeColumn(contenTb.getColumnModel().getColumn(0)); // column 0 hidden
//		 teaminfoTb.getColumnModel().getColumn(0).setPreferredWidth(10);
//		 teaminfoTb.getColumnModel().getColumn(1).setPreferredWidth(300);
//		 teaminfoTb.getColumnModel().getColumn(2).setPreferredWidth(60);
//		 teaminfoTb.getColumnModel().getColumn(3).setPreferredWidth(60);
//		 teaminfoTb.getColumnModel().getColumn(4).setPreferredWidth(10);
//		 teaminfoTb.getColumnModel().getColumn(5).setPreferredWidth(10);
//
//		teaminfoTbScl = new JScrollPane(teaminfoTb);
//		teaminfoTbScl.setBounds(10, 22, 399, 144);
//		teaminfoTbScl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		subPanel.add(teaminfoTbScl);
//
//	}

	private void initTestcase()
	{
		mainPanel = new JPanel();
		mainPanel.setBounds(12, 36, 476, 392);
		mainPanel.setBorder(BorderFactory.createTitledBorder(MAIN_PANEL));
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);

		logPanel = new JPanel();
		logPanel.setBounds(10, 428, 709, 170);
		logPanel.setBorder(BorderFactory.createTitledBorder(LOG_PANEL));
		frame.getContentPane().add(logPanel);
		logPanel.setLayout(null);

		subPanel = new JPanel();
		subPanel.setBorder(BorderFactory.createTitledBorder(SUB_PANEL));
		subPanel.setBounds(502, 37, 224, 392);
		frame.getContentPane().add(subPanel, BorderLayout.CENTER);
		subPanel.setLayout(null);

		clazzInnerPanel = new JPanel();
		clazzSscrollPane = new JScrollPane(clazzInnerPanel);
		clazzInnerPanel.setLayout(new GridLayout(100, 1, 0, 0));
		clazzSscrollPane.setBounds(10, 49, 204, 332);
		clazzSscrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		subPanel.add(clazzSscrollPane);

		selectallBTN = new JButton("selectAll");
		selectallBTN.setName("selected_checkbox");
		selectallBTN.setBounds(10, 15, 90, 23);
		selectallBTN.addActionListener(this);
		subPanel.add(selectallBTN);

		unselectallBTN = new JButton("unSelectAll");
		unselectallBTN.setName("unselected_checkbox");
		unselectallBTN.setBounds(110, 15, 104, 23);
		unselectallBTN.addActionListener(this);
		subPanel.add(unselectallBTN);

		JLabel spLB = new JLabel("sp");
		spLB.setBounds(10, 26, 23, 14);
		mainPanel.add(spLB);

		JLabel sgLB = new JLabel("sg");
		sgLB.setBounds(10, 51, 23, 14);
		mainPanel.add(sgLB);

		spTX = new JTextField();
		spTX.setText("192.168.1.225:8443");
		spTX.setBounds(38, 23, 149, 20);
		mainPanel.add(spTX);
		spTX.setColumns(10);

		sgTX = new JTextField();
		sgTX.setText("192.168.1.226:8080");
		sgTX.setColumns(10);
		sgTX.setBounds(38, 48, 149, 20);
		mainPanel.add(sgTX);

		isHomecloudCB = new JCheckBox("isHomecloud");
		isHomecloudCB.setSelected(false);
		isHomecloudCB.setBounds(193, 47, 149, 23);
		mainPanel.add(isHomecloudCB);

		JLabel logPathLB = new JLabel("logPath");
		logPathLB.setBounds(10, 362, 46, 14);
		mainPanel.add(logPathLB);

		logPathTX = new JTextField();
		logPathTX.setText("D:/tmp");
		logPathTX.setBounds(66, 359, 181, 20);
		mainPanel.add(logPathTX);
		logPathTX.setColumns(10);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 179, 440, 172);

		for ( Iterator<SubnodeConfiguration> it = TestcaseLoadConfig.testPackageList.iterator(); it.hasNext(); )
		{
			HierarchicalConfiguration sub = (HierarchicalConfiguration)it.next();
			Map<String, String> params = new HashMap<String, String>();
			
			params.put(KEY_COMPONENT,sub.getString(KEY_COMPONENT));
			params.put(KEY_CLASS, sub.getString(KEY_CLASS));
//			params.put(KEY_COMPONENT, "inforelay");
//			params.put(KEY_CLASS, "com.ecareme.test.intergration.oministore.TestBasicIR");
//			params.put(KEY_USERID, "");
//			params.put(KEY_PASSWORD, "");
			tabbedPane.addTab(sub.getString(KEY_COMPONENT), createInnerPanel(params));
		}
		tabbedPane.setSelectedIndex(0);

		mainPanel.add(tabbedPane);

		runBTN = new JButton("start");
		runBTN.setName("start_testcase");
		runBTN.setBounds(315, 358, 62, 23);
		runBTN.addActionListener(this);
		mainPanel.add(runBTN);

		stopBTN = new JButton("stop");
		stopBTN.setName("stop_testcase");
		stopBTN.setBounds(387, 358, 62, 23);
		stopBTN.addActionListener(this);
		mainPanel.add(stopBTN);
		
		JLabel lblIr = new JLabel("ir");
		lblIr.setBounds(10, 79, 23, 14);
		mainPanel.add(lblIr);
		
		irTX = new JTextField();
		irTX.setText("192.168.1.201:9099");
		irTX.setColumns(10);
		irTX.setBounds(38, 76, 149, 20);
		mainPanel.add(irTX);
		
		JLabel lblWr = new JLabel("wr");
		lblWr.setBounds(10, 104, 23, 14);
		mainPanel.add(lblWr);
		
		wrTX = new JTextField();
		wrTX.setText("192.168.1.226:8888");
		wrTX.setColumns(10);
		wrTX.setBounds(38, 101, 149, 20);
		mainPanel.add(wrTX);

		logTA = new JTextArea(5, 30);
		logTA.setBounds(1, 1, 25, 10);

		logSCL = new JScrollPane(logTA);
		logSCL.setBounds(10, 23, 689, 139);
		logSCL.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		logSCL.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		logPanel.add(logSCL);

		PrintStream printStream = new PrintStream(new LogOutputStream(logTA));

		standardOut = System.out;
		System.setOut(printStream);
		System.setErr(printStream);

		tabbedPane.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				// clazzInnerPanel.removeAll();
				changeClassMethodDisplay();
			}
		});
	}

	private JPanel createInnerPanel(Map<String, String> params)
	{
		innerPanel = new JPanel();
		innerPanel.setLayout(null);

		componentTX = new JTextField();
		componentTX.setName(KEY_COMPONENT);
		componentTX.setText(params.get(KEY_COMPONENT));
		componentTX.setBounds(62, 5, 46, 20);
		innerPanel.add(componentTX);
		componentTX.setVisible(false);

		JLabel classLB = new JLabel(KEY_CLASS);
		classLB.setBounds(12, 25, 46, 14);
		innerPanel.add(classLB);

		clazzTX = new JTextField();
		clazzTX.setName(KEY_CLASS);
		clazzTX.setText(params.get(KEY_CLASS));
		clazzTX.setBounds(62, 25, 360, 20);
		innerPanel.add(clazzTX);
		clazzTX.setColumns(10);

		JLabel userIdLB = new JLabel(KEY_USERID);
		userIdLB.setBounds(12, 50, 45, 15);
		innerPanel.add(userIdLB);

		userIdTX = new JTextField();
		userIdTX.setName(KEY_USERID);
		userIdTX.setText(params.get(KEY_USERID));
		userIdTX.setBounds(62, 50, 200, 20);
		innerPanel.add(userIdTX);
		userIdTX.setColumns(10);

		userRegBTN = new JButton(BTN_REG);
		userRegBTN.setName("register_textfield" + NUMBER_SIGN + KEY_USERID + NUMBER_SIGN + KEY_PASSWORD);
		userRegBTN.setBounds(280, 50, 60, 20);
		userRegBTN.addActionListener(this);
		innerPanel.add(userRegBTN);

		JLabel passwordLB = new JLabel(KEY_PASSWORD);
		passwordLB.setBounds(12, 75, 45, 15);
		innerPanel.add(passwordLB);

		passwordTX = new JTextField();
		passwordTX.setName(KEY_PASSWORD);
		passwordTX.setText(params.get(KEY_PASSWORD));
		passwordTX.setBounds(63, 75, 200, 20);
		innerPanel.add(passwordTX);
		passwordTX.setColumns(10);
//
//		JLabel u2LB = new JLabel(KEY_U2);
//		u2LB.setBounds(12, 100, 45, 15);
//		innerPanel.add(u2LB);
//
//		u2TX = new JTextField();
//		u2TX.setName(KEY_U2);
//		u2TX.setText(params.get(KEY_U2));
//		u2TX.setBounds(62, 100, 200, 20);
//		innerPanel.add(u2TX);
//		u2TX.setColumns(10);
//
//		u2RegBTN = new JButton(BTN_REG);
//		u2RegBTN.setName("register_textfield" + NUMBER_SIGN + KEY_U2 + NUMBER_SIGN + KEY_P2);
//		u2RegBTN.setBounds(280, 100, 60, 20);
//		u2RegBTN.addActionListener(this);
//		innerPanel.add(u2RegBTN);
//
//		JLabel p2LB = new JLabel(KEY_P2);
//		p2LB.setBounds(12, 125, 45, 15);
//		innerPanel.add(p2LB);
//
//		p2TX = new JTextField();
//		p2TX.setName(KEY_P2);
//		p2TX.setText(params.get(KEY_P2));
//		p2TX.setBounds(62, 125, 200, 20);
//		innerPanel.add(p2TX);
//		p2TX.setColumns(10);
//
//		JLabel u3LB = new JLabel(KEY_U3);
//		u3LB.setBounds(12, 150, 45, 15);
//		innerPanel.add(u3LB);
//
//		u3TX = new JTextField();
//		u3TX.setName(KEY_U3);
//		u3TX.setText(params.get(KEY_U3));
//		u3TX.setBounds(62, 150, 200, 20);
//		innerPanel.add(u3TX);
//		u3TX.setColumns(10);
//
//		u3RegBTN = new JButton(BTN_REG);
//		u3RegBTN.setName("register_textfield" + NUMBER_SIGN + KEY_U3 + NUMBER_SIGN + KEY_P3);
//		u3RegBTN.setBounds(280, 150, 60, 20);
//		u3RegBTN.addActionListener(this);
//		innerPanel.add(u3RegBTN);
//
//		JLabel p3LB = new JLabel(KEY_P3);
//		p3LB.setBounds(12, 175, 45, 15);
//		innerPanel.add(p3LB);
//
//		p3TX = new JTextField();
//		p3TX.setName(KEY_P3);
//		p3TX.setText(params.get(KEY_P3));
//		p3TX.setBounds(62, 175, 200, 20);
//		innerPanel.add(p3TX);
//		p3TX.setColumns(10);

		return innerPanel;
	}

	public void actionPerformed(ActionEvent e)
	{

		Object source = e.getSource();
		if ( source instanceof JButton )
		{
			// System.out.println("! " + e.getActionCommand() + ", " + ( (JButton)source ).getName());
			try
			{
				APICallerService caller = new APICallerService(10014, "94C3C2CEDDD345AFA269643B0AA27A29");
				String[] key = ( (JButton)source ).getName().split(NUMBER_SIGN);
				RegisterBean rsbean = null;
				switch ( key[0] )
				{
					case "start_testcase" :
						Map<String, String> paneMap = getTextFieldsMap(findAllChildren((JComponent)tabbedPane.getSelectedComponent(), JTextField.class));
						Map<String, Object> testPackage = new HashMap<String, Object>();
						testPackage.put("testcase", paneMap.get(KEY_COMPONENT));
						testPackage.put("class", paneMap.get(KEY_CLASS));

						Map<String, String> params = new HashMap<String, String>();
						Map<String, String> userMap = new HashMap<String, String>();
						userMap.put(paneMap.get(KEY_USERID), paneMap.get(KEY_PASSWORD));
//						params.put(KEY_PASSWORD, paneMap.get(KEY_PASSWORD));
//						params.put(KEY_U2, paneMap.get(KEY_U2));
//						params.put(KEY_P2, paneMap.get(KEY_P2));
//						params.put(KEY_U3, paneMap.get(KEY_U3));
//						params.put(KEY_P3, paneMap.get(KEY_P3));
						params.put("sp", spTX.getText());
						params.put("sg", sgTX.getText());
						params.put("wr", wrTX.getText());
						params.put("ir", irTX.getText());
						params.put("users", new Gson().toJson(userMap));
						params.put("isHomeCloud", isHomecloudCB.isSelected()? "1": "0");
						
						testPackage.put("params", params);

						List<Map<String, Object>> testngParams = new ArrayList<Map<String, Object>>();
						testngParams.add(testPackage);
						List<String> cbMap = getCheckBoxUnSelectMap(findAllChildren((JComponent)clazzInnerPanel, JCheckBox.class));
						loadCaseService = new LoadCaseService(testngParams, logPathTX.getText(), cbMap);
						loadCaseService.start();
						break;
					case "stop_testcase" :
						loadCaseService.stopThread();
						loadCaseService.interrupt();
						break;
					case "register_textfield" :
						if ( isHomecloudCB.isSelected() )
							break;
						rsbean = caller.newregisteruser(spTX.getText());
						updateTextInPane(tabbedPane, key[1], rsbean.getUserid());
						updateTextInPane(tabbedPane, key[2], rsbean.getHashpwd());
						break;
					case "selected_checkbox" :
						checkBoxOperations(findAllChildren((JComponent)clazzInnerPanel, JCheckBox.class), true);
						break;
					case "unselected_checkbox" :
						checkBoxOperations(findAllChildren((JComponent)clazzInnerPanel, JCheckBox.class), false);
						break;
					case "managerstudioregister_textfield" :
						rsbean = caller.managerstudioregister(spTX.getText(), msTX.getText(), ProductType.Type_1000H);
						updateTextInPane(mainPanel, key[1], rsbean.getUserid());
						updateTextInPane(mainPanel, key[2], rsbean.getHashpwd());
						break;
					case "get_commercial_id" :
						commercialIdTX.setText(caller.getTeamCommercialId(msTX.getText(), userIdTX.getText(), passwordTX.getText()));
						break;
					case "enable_team" :
						commercialIdTX.setText(caller.getEnableTeamCommercialId(msTX.getText(), userIdTX.getText()));
						break;
					case "add_member" :						
						caller.addTeammember(msTX.getText(), commercialIdTX.getText(), quotaTX.getText());
						getTeamnfo(caller.getTeaminfo(msTX.getText(), sgTX.getText(), userIdTX.getText(), commercialIdTX.getText()));
						break;
					case "delete_member" :
						if ( StringUtils.isNotBlank(key[1]) )
						{
							System.out.println("can not delete admin");
							getTeamnfo(caller.getTeaminfo(msTX.getText(), sgTX.getText(), userIdTX.getText(), commercialIdTX.getText()));
							break;
						}
						teaminfoTb.removeAll();
						caller.deleteTeammember(sgTX.getText(), msTX.getText(), userIdTX.getText(), key[2], commercialIdTX.getText());
						DefaultTableModel defaultModel = (DefaultTableModel)teaminfoTb.getModel();
						getTeamnfo(caller.getTeaminfo(msTX.getText(), sgTX.getText(), userIdTX.getText(), commercialIdTX.getText()));
						
						break;
					case "get_teaminfo" :
						getTeamnfo(caller.getTeaminfo(msTX.getText(), sgTX.getText(), userIdTX.getText(), commercialIdTX.getText()));
						break;
					default :

						break;
				}
			}
			catch ( APIException | IOException e1 )
			{
				e1.printStackTrace();
			}
		}
		else if ( source instanceof JMenuItem )
		{
			String[] key = ( (JMenuItem)source ).getName().split(NUMBER_SIGN);
			switch ( key[0] )
			{
				case "load_testcase" :
					initTestcase();
					// changeClassMethodDisplay();
					frame.repaint();
					break;

				case "team_manager" :
					initTestcase();
					// changeClassMethodDisplay();
					frame.repaint();
					break;
				default :
					break;
			}
		}
	}

	private void getTeamnfo(List<Vector> addRows)
	{
		teaminfoTb.removeAll();
		DefaultTableModel defaultModel = (DefaultTableModel)teaminfoTb.getModel();
		defaultModel.setRowCount(0);
		for ( Vector v : addRows )
		{
			defaultModel.addRow(v);
			TableButton buttonEditor = new TableButton("D");
			teaminfoTb.getColumnModel().getColumn(teaminfoTb.getColumnModel().getColumnCount() - 1).setCellEditor(buttonEditor);
			teaminfoTb.getColumnModel().getColumn(teaminfoTb.getColumnModel().getColumnCount() - 1).setCellRenderer(buttonEditor);
			buttonEditor.addActionListener(this);
		}		
	}

	private void changeClassMethodDisplay()
	{
		try
		{
			clazzInnerPanel.removeAll();
			Map<String, String> paneMap = getTextFieldsMap(findAllChildren((JComponent)tabbedPane.getSelectedComponent(), JTextField.class));
			if ( StringUtils.isNotBlank(paneMap.get(KEY_CLASS)) )
			{

				Method m[] = Class.forName(paneMap.get(KEY_CLASS)).getDeclaredMethods();
				for ( int i = 0; i < m.length; i++ )
				{
					if(m[i].getName().toLowerCase().indexOf("beforeclass") >=0)	
						continue;
					JCheckBox checkBox = new JCheckBox(m[i].getName());
					checkBox.setName(m[i].getName());
					checkBox.setSelected(true);
					clazzInnerPanel.add(checkBox);
				}
			}
		}
		catch ( ClassNotFoundException e1 )
		{
			e1.printStackTrace();
		}

	}

	private void checkBoxOperations(List<?> tf, boolean selectAll)
	{
		Component[] cList = (Component[])clazzInnerPanel.getComponents();
		for ( int i = 0; i < cList.length; i++ )
		{
			( (JCheckBox)cList[i] ).setSelected(selectAll);
		}
		return;
	}

	private void updateTextInPane(Container co, String key, String value)
	{
		Component[] cList = null;
		if ( co instanceof JTabbedPane )
		{
			cList = (Component[])( (Container)( (JTabbedPane)co ).getSelectedComponent() ).getComponents();
		}
		else if ( co instanceof JPanel )
		{
			cList = (Component[])( (Container)( (JPanel)co ) ).getComponents();
		}
		for ( int i = 0; i < cList.length; i++ )
		{
			String tmp = StringUtils.isNotBlank((String)cList[i].getName())? (String)cList[i].getName(): "";
			if ( tmp.equals(key) )
			{
				( (JTextField)cList[i] ).setText(value);
			}
		}
	}
}
