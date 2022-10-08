import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

//import com.mysql.cj.xdevapi.Statement;
//import com.sun.tools.javac.resources.compiler;

import net.proteanit.sql.DbUtils;
//import sun.jvm.hotspot.oops.java_lang_Class;
//import sun.jvm.hotspot.code.Location.Where;
//import sun.jvmstat.perfdata.monitor.CountedTimerTaskUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Console;
import java.io.ObjectInputStream.GetField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;    
import javax.swing.event.*;  
import java.util.*;
import java.lang.*;

public class Shipment {
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	

	JFrame frame;
	private JTable table;
	private JTextField textField;
	private JTextField type_field;
	private JTable scrollPane;
	private JTextField seller_text;
	private JTextField qty;
	private JTextField adress;
	private JTextArea BillText;
	private JTextField textField_1;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Shipment window = new Shipment();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Shipment() {
		initialize();
		Connect();
		table_load();
//		table_data_selection();
//		update();
		
	}
	
	public void Connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/demo12","root","Epilex@1");
        }
        catch (Exception ex){ ex.printStackTrace(); }
    }

	public void table_load()
    {
		try
		{
		    pst = con.prepareStatement("select * from warehouse");
		    rs = pst.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
		}
	    catch (SQLException e){ e.printStackTrace(); }
	}
	
//	public void table_data_selection() {
//		DefaultTableModel model = (DefaultTableModel)scrollPane.getModel();
//		int myIndex = scrollPane.getSelectedRow();
//		product_type.setText(model.getValueAt(myIndex, 2).toString());
//		int price = Integer.valueOf(model.getValueAt(myIndex,9).toString());
//	} 
	public void update() {
		try {
			  int quantitydb=0;
			String qtyString=qty.getText();
			int quantity =Integer.parseInt(qtyString);
			int sku  = Integer.parseInt(textField.getText());
			String query = "select Quantity as quant from warehouse where warehouse.SKU = "+sku; 
            pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            int updatedStockString = 0;//stockQty-quantity;
            if(rs.next())
                quantitydb=rs.getInt("quant");
            
			int rem=quantitydb-quantity;			
			String updateQueryString = "Update warehouse set Quantity ='"+rem+"'where warehouse.SKU = '"+sku+"'";
			java.sql.Statement Add = con.createStatement();
     		Add.executeUpdate(updateQueryString);
     		 DefaultTableModel model= (DefaultTableModel)table.getModel();
				int myIdenxInteger = table.getSelectedRow();
				System.out.println("myindex "+myIdenxInteger);
				  model.setValueAt(rem, myIdenxInteger, 3);
     		           
     		//table_load();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 997, 629);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Outbound (Shipment)");
		lblNewLabel.setForeground(new Color(0, 139, 139));
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNewLabel.setBounds(315, 10, 207, 21);
		frame.getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			
			public void mouseClicked(MouseEvent event) {
				
				  JOptionPane.showMessageDialog(table, "Column header  is clicked");
				 	System.out.print("hello from try");
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				update();
				System.out.print("true");
			}
		});
		scrollPane.setBounds(458, 41, 511, 254);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		
		
		ListSelectionModel select= table.getSelectionModel();  
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        select.addListSelectionListener(new ListSelectionListener() {  
          public void valueChanged(ListSelectionEvent e) {  
       
          DefaultTableModel model = (DefaultTableModel)table.getModel();
          int myIdenxInteger = table.getSelectedRow();
    
          seller_text.setText(model.getValueAt(myIdenxInteger,0 ).toString());
          textField.setText(model.getValueAt(myIdenxInteger, 1).toString());
          type_field.setText(model.getValueAt(myIdenxInteger, 2).toString());
          textField_1.setText(model.getValueAt(myIdenxInteger, 9).toString());
       
     
       
          }       
        });  
     
		
		
		
		//end
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.scrollbar);
		panel.setBounds(0, 0, 155, 295);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Dashboard");
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Dashboard dashboard = new Dashboard();
				dashboard.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_2.setBounds(12, 34, 127, 20);
		lblNewLabel_2.setForeground(new Color(0, 128, 128));
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 14));
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Hold Management");
		lblNewLabel_2_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Hold hold = new Hold();
				hold.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_2_1.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_2_1.setBounds(12, 107, 127, 20);
		panel.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_5 = new JLabel("Received Inventory");
		lblNewLabel_2_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RecievedInventory window = new RecievedInventory();
				window.frmMainFrame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_2_5.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_5.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_2_5.setBounds(12, 67, 142, 27);
		panel.add(lblNewLabel_2_5);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Recall ");
		lblNewLabel_2_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Inbound inbound = new Inbound();
				inbound.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_2_1_1.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_1_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_2_1_1.setBounds(12, 140, 128, 26);
		panel.add(lblNewLabel_2_1_1);
		
		JLabel lblNewLabel_2_3 = new JLabel("Statistics");
		lblNewLabel_2_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Stats st = new Stats();
				st.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_2_3.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_3.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_2_3.setBounds(12, 215, 110, 28);
		panel.add(lblNewLabel_2_3);
		
		JLabel lblNewLabel_3 = new JLabel("Inbound Audit");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Inbound inbound = new Inbound();
				inbound.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_3.setForeground(new Color(0, 128, 128));
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_3.setBounds(11, 166, 128, 46);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_1 = new JLabel("SKU");
		lblNewLabel_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblNewLabel_1.setBounds(168, 52, 47, 19);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(332, 42, 116, 24);
		frame.getContentPane().add(textField);
		
		JLabel lblNewLabel_1_1 = new JLabel("Product Type");
		lblNewLabel_1_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblNewLabel_1_1.setBounds(165, 82, 122, 19);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		type_field = new JTextField();
		type_field.setColumns(10);
		type_field.setBounds(332, 77, 116, 24);
		frame.getContentPane().add(type_field);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Seller");
		lblNewLabel_1_1_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_1_1_1.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblNewLabel_1_1_1.setBounds(167, 150, 122, 19);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		JButton btnNewButton = new JButton("ADD TO BILL");
		
         
		
		btnNewButton.addActionListener(new ActionListener() {
			int i = 1,Product_Price;
			public void actionPerformed(ActionEvent e) {
				update();
				    BillText.setText("        *********************BILLING*********************************\n"
						+ "\tID Product_Type Seller  QTY  Address  Price  TOTAL\n\t"
						+ ""+i+"       "+textField.getText()+"     "+seller_text.getText()+"     "+qty.getText()+"    "+adress.getText()+"     "+textField_1.getText()+"     "+Integer.valueOf(qty.getText())*Integer.valueOf(textField_1.getText()));
				
				i++;
							
			}
		
			
		});
		btnNewButton.setBackground(Color.GREEN);
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnNewButton.setBounds(167, 270, 130, 25);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblBill = new JLabel("BILL");
		lblBill.setForeground(new Color(0, 139, 139));
		lblBill.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblBill.setBounds(408, 310, 47, 21);
		frame.getContentPane().add(lblBill);
		
		JButton PrintBtn = new JButton("PRINT");
		PrintBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					BillText.print();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		PrintBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
		PrintBtn.setBackground(Color.GREEN);
		PrintBtn.setForeground(Color.BLACK);
		PrintBtn.setBounds(713, 552, 97, 25);
		frame.getContentPane().add(PrintBtn);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Quantity");
		lblNewLabel_1_1_1_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_1_1_1_1.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblNewLabel_1_1_1_1.setBounds(167, 180, 122, 19);
		frame.getContentPane().add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Reciever Adress");
		lblNewLabel_1_1_1_1_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_1_1_1_1_1.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblNewLabel_1_1_1_1_1.setBounds(167, 210, 140, 19);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1);
		
		
		
		seller_text = new JTextField();
		seller_text.setColumns(10);
		seller_text.setBounds(332, 151, 116, 24);
		frame.getContentPane().add(seller_text);
		
		qty = new JTextField();
		qty.setColumns(10);
		qty.setBounds(332, 181, 116, 24);
		frame.getContentPane().add(qty);
		
		adress = new JTextField();
		adress.setColumns(10);
		adress.setBounds(332, 211, 116, 84);
		frame.getContentPane().add(adress);
		
		BillText = new JTextArea();
		BillText.setFont(new Font("Monospaced", Font.BOLD, 15));
		BillText.setText("*********************************************BILLING***********************************************************");
		BillText.setBounds(33, 352, 925, 182);
		frame.getContentPane().add(BillText);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Price");
		lblNewLabel_1_1_2.setForeground(new Color(0, 139, 139));
		lblNewLabel_1_1_2.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblNewLabel_1_1_2.setBounds(165, 120, 122, 19);
		frame.getContentPane().add(lblNewLabel_1_1_2);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(332, 116, 116, 24);
		frame.getContentPane().add(textField_1);
	}
}
