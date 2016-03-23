import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Interface extends JFrame {

	private JPanel contentPane;
	private JTextField searchText;
	private JTextField nameText;
	private JTextField addressText;
	private JTextField districtText;
	private JTextField textStartHoliday;
	private JTextField textEndHoliday;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// OnHoliday Check @ load to see if people are still on holidays

		/*
		 * =====================================================================
		 * 
		 * call searchByOnHoliday() -- should pull up all customers on holiday
		 * 
		 * if(holiday end date < current date){ onHoliday = false; }
		 * 
		 * 
		 * =====================================================================
		 */

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame. *
	 */
	public Interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 464, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		/*
		 * ===============================================
		 * 
		 * REPORTS TAB
		 * 
		 * ===============================================
		 */

		JPanel panel = new JPanel();
		tabbedPane.addTab("Reports", null, panel, null);
		panel.setLayout(null);

		/*----------------------------------------------
		 * Last Printed Date
		 ----------------------------------------------*/

		JLabel lastPrinted = new JLabel("Last Printed: 1969-04-20");
		lastPrinted.setBounds(10, 11, 170, 14);
		panel.add(lastPrinted);
		
		/*----------------------------------------------
		 * Status Text Area for Reports + label
		 ----------------------------------------------*/
	
		JLabel lblLog = new JLabel("Log:");
		lblLog.setBounds(10, 51, 46, 14);
		panel.add(lblLog);

		JTextPane txtpnStatus = new JTextPane();
		txtpnStatus.setEditable(false);
		txtpnStatus.setText("log lol");
		txtpnStatus.setBounds(10, 70, 413, 198);
		panel.add(txtpnStatus);
		

		/*----------------------------------------------
		 * Drop Down Menu
		 ----------------------------------------------*/
		
		JComboBox mnuReport = new JComboBox();
		mnuReport.setModel(new DefaultComboBoxModel(new String[] {"Delivery Report", "Delivery Summary", "Customer Bills"}));
		mnuReport.setBounds(190, 8, 134, 20);
		panel.add(mnuReport);
		

		/*----------------------------------------------
		 * Print Button
		 ----------------------------------------------*/

		JButton btnPrint = new JButton("Display");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				

				String s = null;
				
				 if(mnuReport.getSelectedIndex() == 0){
				 	s = "Delivery Report";
				 	//Get all customers by district
				 	//(name, address, district, publications)
				 }
				 if(mnuReport.getSelectedIndex() == 1){
				 	s= "Delivery Summary";
				 	//Get all customers by district
				 	//(name, address, district, publications)
				 }
				  if(mnuReport.getSelectedIndex() == 2){
				 	s = "Customer Bills";
				 	//Print all customer billing information by district
				 	//(Name, monthly amount, billing date, address, district)
				 }
				
				// txtpnStatus.setText(s + Search results);
				  txtpnStatus.setText(s);
				
			}
		});
		btnPrint.setBounds(334, 7, 89, 23);
		panel.add(btnPrint);

		/*
		 * ===============================================
		 * 
		 * CUSTOMER VIEWER TAB
		 * 
		 * ===============================================
		 */

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Customer Viewer", null, panel_1, null);
		panel_1.setLayout(null);

		/*----------------------------------------------
		 * Search bar (text field)
		 ----------------------------------------------*/

		searchText = new JTextField();
		searchText.setText("search");
		searchText.setBounds(10, 11, 189, 20);
		panel_1.add(searchText);
		searchText.setColumns(10);

		/*----------------------------------------------
		 * Search by (Combo Box)
		 ----------------------------------------------*/

		JComboBox searchingBy = new JComboBox();
		searchingBy.setModel(new DefaultComboBoxModel(new String[] {"Customer Name", "Customer ID", "Address", "Publication", "District", "On Holiday", "Billing Date"}));
		searchingBy.setBounds(209, 11, 101, 20);
		panel_1.add(searchingBy);

		/*----------------------------------------------
		 * Search Button
		 ----------------------------------------------*/

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(320, 10, 89, 23);
		panel_1.add(btnSearch);
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				 if(searchingBy.getSelectedIndex() == 0){
				 	//search by Customer Name
				 }
				 if(searchingBy.getSelectedIndex() == 1){
				 	//search by Customer ID
				 }
				 if(searchingBy.getSelectedIndex() == 2){
				 	//search by Address
				 }
				 if(searchingBy.getSelectedIndex() == 3){
					//search by Publication
				 }
				 if(searchingBy.getSelectedIndex() == 4){
					//search by District
				 }
				 if(searchingBy.getSelectedIndex() == 5){
					//search by whether or not they're on holiday
				 }
				 if(searchingBy.getSelectedIndex() == 6){
					//search by Billing Date
				 }
			}
		});

		/*----------------------------------------------
		 * List for the results of the search
		 ----------------------------------------------*/

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 399, 171);
		panel_1.add(scrollPane);

		JList listResults = new JList();
		listResults.setModel(new AbstractListModel() {
			String[] values = new String[] { "customer 1", "customer 2" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(listResults);

		/*
		 * ===============================================
		 * 
		 * CUSTOMER CHANGES TAB
		 * 
		 * ===============================================
		 */

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Add / Edit Customer", null, panel_2, null);
		panel_2.setLayout(null);

		/*----------------------------------------------
		 * Customer ID labels
		 ----------------------------------------------*/

		JLabel customerID = new JLabel("Customer ID:");
		customerID.setBounds(10, 11, 65, 14);
		panel_2.add(customerID);

		JLabel idLabel = new JLabel("QQQQ");
		idLabel.setBounds(86, 11, 46, 14);
		panel_2.add(idLabel);

		/*----------------------------------------------
		 * Customer name - label + text box
		 ----------------------------------------------*/

		JLabel lblName = new JLabel("Name: ");
		lblName.setBounds(10, 34, 46, 14);
		panel_2.add(lblName);

		nameText = new JTextField();
		nameText.setText("karalynn krause");
		nameText.setBounds(86, 31, 128, 20);
		panel_2.add(nameText);
		nameText.setColumns(10);

		/*----------------------------------------------
		 * Customer address - label + text box
		 ----------------------------------------------*/

		JLabel lblAddress = new JLabel("Address: ");
		lblAddress.setBounds(10, 59, 46, 14);
		panel_2.add(lblAddress);

		addressText = new JTextField();
		addressText.setText("218 Diefenbaker ");
		addressText.setBounds(86, 56, 128, 20);
		panel_2.add(addressText);
		addressText.setColumns(10);

		/*----------------------------------------------
		 * Customer District - label + text box
		 ----------------------------------------------*/

		JLabel lblDistrictId = new JLabel("District ID:");
		lblDistrictId.setBounds(10, 84, 56, 14);
		panel_2.add(lblDistrictId);

		districtText = new JTextField();
		districtText.setText("XX");
		districtText.setBounds(86, 81, 128, 20);
		panel_2.add(districtText);
		districtText.setColumns(10);

		/*----------------------------------------------
		 * Customer is on holiday - Checkbox + label 
		 ----------------------------------------------*/

		JLabel lblOnHoliday = new JLabel("On Holiday: ");
		lblOnHoliday.setBounds(10, 159, 65, 14);
		panel_2.add(lblOnHoliday);
		
		

		JCheckBox chckbxOnHoliday = new JCheckBox("");
		chckbxOnHoliday.setVerticalAlignment(SwingConstants.BOTTOM);
		chckbxOnHoliday.setBounds(86, 155, 34, 23);
		panel_2.add(chckbxOnHoliday);
		
		chckbxOnHoliday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxOnHoliday.isSelected()){
					textStartHoliday.setEditable(true);
					textEndHoliday.setEditable(true);
				}
				else{
					textStartHoliday.setEditable(false);
					textEndHoliday.setEditable(false);
				}
			}
		});

		/*----------------------------------------------
		 *On Holiday duration - Start and end holiday fields
		 ----------------------------------------------*/

		// Start

		JLabel lblDuration = new JLabel("Duration:");
		lblDuration.setBounds(10, 185, 56, 14);
		panel_2.add(lblDuration);

		textStartHoliday = new JTextField();
		textStartHoliday.setEditable(false);
		textStartHoliday.setText("03-12");
		textStartHoliday.setBounds(86, 182, 56, 20);
		panel_2.add(textStartHoliday);
		textStartHoliday.setColumns(10);

		JLabel label = new JLabel("-");
		label.setBounds(152, 185, 4, 14);
		panel_2.add(label);

		// End

		textEndHoliday = new JTextField();
		textEndHoliday.setEditable(false);
		textEndHoliday.setText("03-17");
		textEndHoliday.setBounds(162, 182, 52, 20);
		panel_2.add(textEndHoliday);
		textEndHoliday.setColumns(10);

		/*----------------------------------------------
		 * Billing date labels 
		 ----------------------------------------------*/

		JLabel lblBillingName = new JLabel("Billing Date: ");
		lblBillingName.setBounds(10, 109, 65, 14);
		panel_2.add(lblBillingName);

		JLabel lblBillingDate = new JLabel("02-20");
		lblBillingDate.setBounds(86, 109, 46, 14);
		panel_2.add(lblBillingDate);

		/*----------------------------------------------
		 * Monthly Total labels
		 ----------------------------------------------*/

		JLabel lblMonthlyTotal = new JLabel("Monthly Total:");
		lblMonthlyTotal.setBounds(10, 134, 76, 14);
		panel_2.add(lblMonthlyTotal);

		JLabel lblTotalValue = new JLabel("$420.69");
		lblTotalValue.setBounds(86, 134, 46, 14);
		panel_2.add(lblTotalValue);

		/*----------------------------------------------
		 * Subscription labels and check box list
		 ----------------------------------------------*/

		JLabel lblSubs = new JLabel("Subscriptions");
		lblSubs.setBounds(298, 34, 76, 14);
		panel_2.add(lblSubs);

		JCheckBox chckbxTrumpWeekly = new JCheckBox("Trump Weekly");
		chckbxTrumpWeekly.setBounds(277, 105, 97, 23);
		panel_2.add(chckbxTrumpWeekly);

		JCheckBox chckbxRevolutionMonthly = new JCheckBox("Bernie");
		chckbxRevolutionMonthly.setBounds(277, 80, 65, 23);
		panel_2.add(chckbxRevolutionMonthly);

		JCheckBox chckbxQwerty = new JCheckBox("QWERTY");
		chckbxQwerty.setBounds(277, 55, 97, 23);
		panel_2.add(chckbxQwerty);

		JCheckBox chckbxMLP = new JCheckBox("MLP Gaming");
		chckbxMLP.setBounds(277, 130, 97, 23);
		panel_2.add(chckbxMLP);

		/*
		 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
		 * BITWISE STRING AND PUBLICATION CHANGES 
		 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 */

		int btws = 0;
		double totalCost = 0;

		if (chckbxTrumpWeekly.isSelected()) {
			btws += 1;
			// Get monthly amount for this publication
			// totalCost += result;

		}
		if (chckbxRevolutionMonthly.isSelected()) {
			btws += 3;
			// Get monthly amount for this publication
			// totalCost += result;
		}
		if (chckbxTrumpWeekly.isSelected()) {
			btws += 5;
			// Get monthly amount for this publication
			// totalCost += result;

		}
		if (chckbxQwerty.isSelected()) {
			btws += 7;
			// Get monthly amount for this publication
			// totalCost += result;
		}

		/*----------------------------------------------
		 * Save customer changes button
		 ----------------------------------------------*/

		JButton btnSaveMe = new JButton("Save");
		btnSaveMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				/*
				 * =============================================================
				 * 
				 * id = idLabel.getText(); nme = nameText.getText(); address =
				 * addressText.getText(); district = districtText.getText()
				 * billingDate = labelBillingDate.getText(); monthlyTotal =
				 * lblTotalValue.getText();
				 * 
				 * if(chckbxOnHoliday.isSelected(){
				 * 
				 * String date =
				 * 
				 * }
				 * 
				 * 
				 * 
				 * 
				 * deleteCustomer(id); addCustomer(id, name, address, district,
				 * onHoliday, billingDate, monthlyTotal, btws);
				 * 
				 * ============================================================
				 */
			}
		});
		btnSaveMe.setBounds(10, 210, 89, 23);
		panel_2.add(btnSaveMe);

		/*----------------------------------------------
		 * Add new customer button
		 ----------------------------------------------*/

		JButton btnAddMe = new JButton("Add");
		btnAddMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				/*
				 * =============================================================
				 * 
				 * id = idLabel.getText(); nme = nameText.getText(); address =
				 * addressText.getText(); district = districtText.getText()
				 * billingDate = labelBillingDate.getText(); monthlyTotal =
				 * lblTotalValue.getText();
				 * 
				 * if(chckbxOnHoliday.isSelected(){
				 * 
				 * String date =
				 * 
				 * }
				 * 
				 * addCustomer(id, nme, address, district, onHoliday,
				 * billingDate, monthlyTotal, btws);
				 * 
				 * =============================================================
				 */
			}
		});
		btnAddMe.setBounds(109, 210, 89, 23);
		panel_2.add(btnAddMe);

		/*----------------------------------------------
		 * Delete existing customer button
		 ----------------------------------------------*/

		JButton btnDeleteMe = new JButton("Delete");
		btnDeleteMe.setBounds(208, 210, 89, 23);
		panel_2.add(btnDeleteMe);

		btnDeleteMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// deleteCustomer(id);

			}
		});

	}
}
