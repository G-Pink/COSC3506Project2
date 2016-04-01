import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.print.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Interface extends JFrame {

	private JPanel contentPane;
	private JTextField searchText;
	private JTextField nameText;
	private JTextField addressText;
	private JTextField districtText;
	private JTextField textEndHoliday;
	
	

	/**
	 * Launch the application.
	 */
	// Table table = new Table("Subscriptions");

	ArrayList<String> row1 = new ArrayList<String>();
	ArrayList<String> row2 = new ArrayList<String>();
	ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();

	public static void main(String[] args) {

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

		/*----------------------------------------------
		 * Database Creation
		 ----------------------------------------------*/

		Database db = new Database();
		db.getConnection();

		/*----------------------------------------------
		 * Base Panes
		 ----------------------------------------------*/

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 513, 389);
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
		 * Status Text Area for Reports + label
		 ----------------------------------------------*/

		JLabel lblLog = new JLabel("Log:");
		lblLog.setBounds(10, 51, 46, 14);
		panel.add(lblLog);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 70, 462, 232);
		panel.add(scrollPane_1);

		JTextPane txtpnStatus = new JTextPane();
		scrollPane_1.setViewportView(txtpnStatus);
		txtpnStatus.setFont(new Font("Courier New", Font.PLAIN, 11));
		txtpnStatus.setEditable(false);
		txtpnStatus.setText("...waiting for input");

		/*----------------------------------------------
		 * Drop Down Menu
		 ----------------------------------------------*/

		JComboBox mnuReport = new JComboBox();
		mnuReport.setModel(
				new DefaultComboBoxModel(new String[] {"Delivery Report", "Customer Bills"}));
		mnuReport.setBounds(10, 8, 145, 20);
		panel.add(mnuReport);

		/*----------------------------------------------
		 * Display Button
		 ----------------------------------------------*/

		JButton btnDisplay = new JButton("Display");
		btnDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// String s = table.get(0).toString();

				if (mnuReport.getSelectedIndex() == 0) {

					txtpnStatus.setText("Dist  Address             Name                Publications");

					ArrayList<ArrayList<String>> dist0 = db.seachByDistrict("0");
					ArrayList<ArrayList<String>> dist1 = db.seachByDistrict("1");
					ArrayList<ArrayList<String>> dist2 = db.seachByDistrict("2");
					ArrayList<ArrayList<String>> dist3 = db.seachByDistrict("3");

					String output = " ";
					output = (distFormatter(dist0) + "\n" + distFormatter(dist1) + "\n" + distFormatter(dist2) + "\n"
							+ distFormatter(dist3) + "\n");

					txtpnStatus.setText(txtpnStatus.getText() + "\n" + output);

					// s = "Delivery Report";
					// Get all customers by district
					// (name, address, district, publications)
					// s = (table.get(0) + "\n" + table.get(1));
				}
				if (mnuReport.getSelectedIndex() == 1) {
					// s = "Customer Bills";
					// Print all customer billing information by district
					// (Name, monthly amount, billing date, address, district)
					
					txtpnStatus.setText("Dist    ID   Name             Billing Date    Monthly Total");
					
					ArrayList<ArrayList<String>> dist0 = db.seachByDistrict("0");
					ArrayList<ArrayList<String>> dist1 = db.seachByDistrict("1");
					ArrayList<ArrayList<String>> dist2 = db.seachByDistrict("2");
					ArrayList<ArrayList<String>> dist3 = db.seachByDistrict("3");
					
					String output = " ";
					output = (cashFormatter(dist0) + "\n" + cashFormatter(dist1) + "\n" + cashFormatter(dist2) + "\n"
							+ cashFormatter(dist3) + "\n");
					
					txtpnStatus.setText(txtpnStatus.getText() + "\n" + output);
				}
			}
		});
		btnDisplay.setBounds(218, 7, 89, 23);
		panel.add(btnDisplay);
		
		
		/*----------------------------------------------
		 * Print Button
		 ----------------------------------------------*/

		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					boolean complete =  txtpnStatus.print();
					
					if(complete) {
						JOptionPane.showMessageDialog(null, "Done Printing", "Information" , JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Printing Cancelled", "Printer" , JOptionPane.ERROR_MESSAGE);
					}
					
				}
				catch(PrinterException re){
					JOptionPane.showMessageDialog(null, re);
				}
				
			}
		});
		btnPrint.setBounds(352, 7, 89, 23);
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
		searchText.setBounds(10, 11, 221, 20);
		panel_1.add(searchText);
		searchText.setColumns(10);

		/*----------------------------------------------
		 * Search by (Combo Box)
		 ----------------------------------------------*/

		JComboBox searchingBy = new JComboBox();
		searchingBy.setModel(new DefaultComboBoxModel(new String[] { "Customer Name", "Customer ID", "Address",
				"Publication", "District", "On Holiday", "Billing Date" }));
		searchingBy.setBounds(241, 11, 132, 20);
		panel_1.add(searchingBy);

		/*----------------------------------------------
		 * List for the results of the search
		 ----------------------------------------------*/

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 462, 260);
		panel_1.add(scrollPane);

		JList listResults = new JList();
		listResults.setFont(new Font("Courier New", Font.PLAIN, 11));
		scrollPane.setViewportView(listResults);
		listResults.setModel(new AbstractListModel() {
			String[] values = new String[] { "customer 1", "customer 2" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});

		/*----------------------------------------------
		 * Search Button
		 ----------------------------------------------*/

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(383, 10, 89, 23);
		panel_1.add(btnSearch);

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
		customerID.setBounds(10, 11, 103, 14);
		panel_2.add(customerID);

		JLabel idLabel = new JLabel(db.getNewCustomerID());
		idLabel.setBounds(123, 11, 46, 14);
		panel_2.add(idLabel);

		/*----------------------------------------------
		 * Customer name - label + text box
		 ----------------------------------------------*/

		JLabel lblName = new JLabel("Name: ");
		lblName.setBounds(10, 34, 103, 14);
		panel_2.add(lblName);

		nameText = new JTextField();
		nameText.setText("karalynn krause");
		nameText.setBounds(123, 31, 128, 20);
		panel_2.add(nameText);
		nameText.setColumns(10);

		/*----------------------------------------------
		 * Customer address - label + text box
		 ----------------------------------------------*/

		JLabel lblAddress = new JLabel("Address: ");
		lblAddress.setBounds(10, 59, 103, 14);
		panel_2.add(lblAddress);

		addressText = new JTextField();
		addressText.setText("218 Diefenbaker ");
		addressText.setBounds(123, 56, 128, 20);
		panel_2.add(addressText);
		addressText.setColumns(10);

		/*----------------------------------------------
		 * Customer District - label + text box
		 ----------------------------------------------*/

		JLabel lblDistrictId = new JLabel("District ID:");
		lblDistrictId.setBounds(10, 84, 103, 14);
		panel_2.add(lblDistrictId);

		districtText = new JTextField();
		districtText.setText("XX");
		districtText.setBounds(123, 81, 128, 20);
		panel_2.add(districtText);
		districtText.setColumns(10);

		/*----------------------------------------------
		 * Customer is on holiday - Checkbox + label 
		 ----------------------------------------------*/

		JLabel lblOnHoliday = new JLabel("On Holiday: ");
		lblOnHoliday.setBounds(10, 159, 107, 14);
		panel_2.add(lblOnHoliday);

		JCheckBox chckbxOnHoliday = new JCheckBox("");
		chckbxOnHoliday.setVerticalAlignment(SwingConstants.BOTTOM);
		chckbxOnHoliday.setBounds(123, 159, 34, 23);
		panel_2.add(chckbxOnHoliday);

		chckbxOnHoliday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxOnHoliday.isSelected()) {
					textEndHoliday.setEditable(true);
				} else {
					textEndHoliday.setEditable(false);
				}
			}
		});

		/*----------------------------------------------
		 *On Holiday duration - Start and end holiday fields
		 ----------------------------------------------*/

		// Start

		JLabel lblDuration = new JLabel("End Date:");
		lblDuration.setBounds(10, 185, 103, 14);
		panel_2.add(lblDuration);

		// End

		textEndHoliday = new JTextField();
		textEndHoliday.setEditable(false);
		textEndHoliday.setText("03-17-2016");
		textEndHoliday.setBounds(123, 189, 128, 20);
		panel_2.add(textEndHoliday);
		textEndHoliday.setColumns(10);

		/*----------------------------------------------
		 * Billing date labels 
		 ----------------------------------------------*/

		JLabel lblBillingName = new JLabel("Billing Date: ");
		lblBillingName.setBounds(10, 109, 107, 14);
		panel_2.add(lblBillingName);

		java.util.Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = 0;
		if(cal.get(Calendar.DAY_OF_MONTH) < 15){
			month = cal.get(Calendar.MONTH) + 1;
		}else{
			month = cal.get(Calendar.MONTH) + 2;
		}
		
		int year = cal.get(Calendar.YEAR);
		
		JLabel lblBillingDate = new JLabel(month + "-15-" + year);
		lblBillingDate.setBounds(123, 109, 128, 14);
		panel_2.add(lblBillingDate);

		/*----------------------------------------------
		 * Monthly Total labels
		 ----------------------------------------------*/

		JLabel lblMonthlyTotal = new JLabel("Monthly Total:");
		lblMonthlyTotal.setBounds(10, 134, 107, 14);
		panel_2.add(lblMonthlyTotal);

		JLabel lblTotalValue = new JLabel("$420.69");
		lblTotalValue.setBounds(123, 134, 128, 14);
		panel_2.add(lblTotalValue);

		/*----------------------------------------------
		 * Subscription labels and check box list
		 ----------------------------------------------*/

		JLabel lblSubs = new JLabel("Subscriptions");
		lblSubs.setBounds(348, 54, 101, 14);
		panel_2.add(lblSubs);

		JCheckBox chckbxP3 = new JCheckBox("Trump Weekly");
		chckbxP3.setBounds(327, 125, 122, 23);
		panel_2.add(chckbxP3);

		JCheckBox chckbxP2 = new JCheckBox("Bernie");
		chckbxP2.setBounds(327, 100, 122, 23);
		panel_2.add(chckbxP2);

		JCheckBox chckbxP1 = new JCheckBox("QWERTY");
		chckbxP1.setBounds(327, 75, 122, 23);
		panel_2.add(chckbxP1);

		JCheckBox chckbxP4 = new JCheckBox("MLP Gaming");
		chckbxP4.setBounds(327, 150, 122, 23);
		panel_2.add(chckbxP4);

		/*
		 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
		 * BITWISE STRING AND
		 * PUBLICATION CHANGES
		 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		 */

	
		char c1 = '0';
		char c2 = '0';
		char c3 = '0';
		char c4 = '0';

		if (chckbxP1.isSelected()) {
			c1 = '1';
		}
		if (chckbxP2.isSelected()) {
			c2 = '1';
		}
		if (chckbxP3.isSelected()) {
			c1 = '1';
		}
		if (chckbxP4.isSelected()) {
			c1 = '1';
		}
		
		final String btws = (""+c1+c2+c3+c4);

		/*----------------------------------------------
		 * Save customer changes button
		 ----------------------------------------------*/

		JButton btnSaveMe = new JButton("Save");
		btnSaveMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//name
				db.editCustomerName(idLabel.getText(), nameText.getText());
				//address
				db.editCustomerAddress(idLabel.getText(), addressText.getText());
				//district
				db.editCustomerDistrictID(idLabel.getText(), districtText.getText());
				
				//holiday
				if(chckbxOnHoliday.isSelected()){
					db.addOnHoliday(idLabel.getText(), textEndHoliday.getText());
				}
				else
				{
					db.deleteOnHoliday(idLabel.getText());
				}
				
				db.updateMonthlyTotal(idLabel.getText());
				
				//publication
				if (chckbxP1.isSelected() && btws.charAt(0) == '1') {
					db.editSubscription(idLabel.getText(), "0");

				}
				if (chckbxP2.isSelected()&& btws.charAt(1) == '1') {
					db.editSubscription(idLabel.getText(), "1");
				}
				if (chckbxP3.isSelected()&& btws.charAt(2) == '1'){
					db.editSubscription(idLabel.getText(), "2");

				}
				if (chckbxP4.isSelected()&& btws.charAt(3) == '1') {
					db.editSubscription(idLabel.getText(), "3");
				}
				
			}
		});
		btnSaveMe.setBounds(10, 267, 89, 23);
		panel_2.add(btnSaveMe);

		/*----------------------------------------------
		 * Add new customer button
		 ----------------------------------------------*/

		JButton btnAddMe = new JButton("Add");
		btnAddMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

						
				//id
				String newID = idLabel.getText();
				//new customer
				db.createCustomer(newID, lblBillingDate.getText());
				//name
				db.editCustomerName(newID, nameText.getText());
				//address
				db.editCustomerAddress(newID, addressText.getText());
				//district
				db.editCustomerDistrictID(newID, districtText.getText());
				
				//holiday
				if(chckbxOnHoliday.isSelected()){
					db.addOnHoliday(newID, textEndHoliday.getText());
				}
				
				db.updateMonthlyTotal(newID);
				
				//publication
				if (chckbxP1.isSelected() && btws.charAt(0) == '1') {
					db.editSubscription(newID, "0");

				}
				if (chckbxP2.isSelected()&& btws.charAt(1) == '1') {
					db.editSubscription(newID, "1");
				}
				if (chckbxP3.isSelected()&& btws.charAt(2) == '1'){
					db.editSubscription(newID, "2");

				}
				if (chckbxP4.isSelected()&& btws.charAt(3) == '1') {
					db.editSubscription(newID, "3");
				}
			}
		});
		btnAddMe.setBounds(188, 267, 89, 23);
		panel_2.add(btnAddMe);

		/*----------------------------------------------
		 * Delete existing customer button
		 ----------------------------------------------*/

		JButton btnDeleteMe = new JButton("Delete");
		btnDeleteMe.setBounds(360, 267, 89, 23);
		panel_2.add(btnDeleteMe);
		btnDeleteMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


				db.deleteCustomer(idLabel.getText());

			}
		});

		/*----------------------------------------------
		 * Clear Button
		 ----------------------------------------------*/

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnClear.setBounds(339, 11, 89, 23);
		panel_2.add(btnClear);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				//id
				idLabel.setText(db.getNewCustomerID());
				nameText.setText("");
				addressText.setText("");
				districtText.setText("");
				chckbxOnHoliday.setSelected(false);
				chckbxP1.setSelected(false);
				chckbxP2.setSelected(false);
				chckbxP3.setSelected(false);
				chckbxP4.setSelected(false);
				
			
			}
		});
		
		

		/*----------------------------------------------
		 * ACTION Button for Searching
		 ----------------------------------------------*/
		
						
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Search by Name
				if (searchingBy.getSelectedIndex() == 0) {

					table = db.searchByName(searchText.getText());

				}

				// Search by ID
				if (searchingBy.getSelectedIndex() == 1) {

					table.clear();
					ArrayList<String> qqq = db.searchByCustomerID(searchText.getText());
					table.add(qqq);

				}

				// Search by Address
				if (searchingBy.getSelectedIndex() == 2) {

					table = db.searchByAddress(searchText.getText());

				}

				// search by Publication
				if (searchingBy.getSelectedIndex() == 3) {

					String publicate = " ";

					if (searchText.getText().equalsIgnoreCase(chckbxP1.getText())) {
						publicate = "0";
					} else if (searchText.getText().equalsIgnoreCase(chckbxP2.getText())) {
						publicate = "1";
					} else if (searchText.getText().equalsIgnoreCase(chckbxP3.getText())) {
						publicate = "2";
					} else if (searchText.getText().equalsIgnoreCase(chckbxP3.getText())) {
						publicate = "3";
					}

					table = db.searchByPublication(publicate);

				}
				// search by District
				if (searchingBy.getSelectedIndex() == 4) {
					table = db.seachByDistrict(searchText.getText());
				}
				// search by whether or not they're on holiday
				if (searchingBy.getSelectedIndex() == 5) {
					table = db.searchByOnHoliday(searchText.getText().toLowerCase());
				}
				// search by Billing Date
				if (searchingBy.getSelectedIndex() == 6) {
					table = db.searchByBillingDate(searchText.getText().toLowerCase());
				}
				if (table == null) {
					String[] err = new String[] { "No results found" };
					listResults.setListData(err);
				} else {

					//String txt = ("ID  Name      Address   District");
					//ArrayList<String> nw = new ArrayList<String>();
					//nw.add(txt);
					//table.add(0, nw);

					listResults.setListData(table.toArray());
				}

			}
		});
		
		/*----------------------------------------------
		 * Double Click Mouse Listener
		 ----------------------------------------------*/

		MouseAdapter mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int indx = listResults.locationToIndex(e.getPoint());
					System.out.print(indx);
					System.out.print(table.get(indx));
					ArrayList<String> idex = table.get(indx);
					String index = idex.get(0);		
					
					ArrayList<String> res = db.searchByCustomerID(index);
					System.out.println(res);
					System.out.println("Double clicked on Item " + index);
					tabbedPane.setSelectedIndex(2);
					idLabel.setText(res.get(0));
					nameText.setText(res.get(1));
					addressText.setText(res.get(2));
					districtText.setText(res.get(3));
					chckbxOnHoliday.setSelected(Boolean.parseBoolean(res.get(4)));
					lblBillingDate.setText(res.get(5));
					lblTotalValue.setText(res.get(6));

					if (res.get(7).charAt(0) == '1') {
						chckbxP1.setSelected(true);
					} else {
						chckbxP1.setSelected(false);
					}
					if (res.get(7).charAt(1) == '1') {
						chckbxP2.setSelected(true);
					} else {
						chckbxP2.setSelected(false);
					}
					if (res.get(7).charAt(2) == '1') {
						chckbxP3.setSelected(true);
					} else {
						chckbxP3.setSelected(false);
					}
					if (res.get(7).charAt(3) == '1') {
						chckbxP4.setSelected(true);
					} else {
						chckbxP4.setSelected(false);
					}

					textEndHoliday.setText(res.get(8));

				}
			}
		};

		listResults.addMouseListener(mouseListener);

	}
	
	public static String cashFormatter(ArrayList<ArrayList<String>> table){
		String output = "";

		if (table == null) {
			return "";
		} else {
			for (int x = 0; x < table.size(); x++) {

				System.out.print("");

				String name = table.get(x).get(1);
				String id = table.get(x).get(0);
				String distID = table.get(x).get(3); /////////                          <-------------------------------------------------------------
				String billing = table.get(x).get(5);
				String total = table.get(x).get(6);
				
				//txtpnStatus.setText("Dist  ID  Name             Billing Date  Monthly Total");

				// String output = "";
				int nameWht = 18;
				int dateWht = 15;

				if (distID.length() == 1) {
					output = output + (" " + distID + "    ");
				} else {
					output = output + (distID + "    ");
				}
				
				if (id.length() == 1) {
					output = output + ("  " + id + "    ");
				} else if(id.length() == 2){
					output = output + (" " + id + "     ");
				} else {
					output = output + (id + "      ");
				}

				output = output + name;

				nameWht = 18 - name.length();

				for (int y = nameWht; y > 0; y--) {
					output = output + (" ");
				}
				
				output = output + billing;
				
				dateWht = 15 - billing.length();

				for (int y = dateWht; y > 0; y--) {
					output = output + (" ");
				}

				output = output + total + "\n";

			}

			return output;
		}
	}

	public static String distFormatter(ArrayList<ArrayList<String>> table) {
		String output = "";

		if (table == null) {
			return "";
		} else {
			for (int x = 0; x < table.size(); x++) {

				System.out.print("");

				String name = table.get(x).get(1);
				String address = table.get(x).get(2);
				String distID = table.get(x).get(3);
				String pubs = table.get(x).get(7);
				
				

				// String output = "";
				int nameWht = 20;
				int addWht = 20;

				if (distID.length() == 1) {
					output = output + (" " + distID + "    ");
				} else {
					output = output + (distID + "    ");
				}

				output = output + (address);

				addWht = 20 - address.length();

				for (int y = addWht; y > 0; y--) {
					output = output + (" ");
				}

				output = output + name;

				nameWht = 20 - name.length();

				for (int y = nameWht; y > 0; y--) {
					output = output + (" ");
				}

				output = output + (pubs) +"\n";

			}

			return output;
		}
	}
}