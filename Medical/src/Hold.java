import java.awt.EventQueue;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;

import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import java.awt.SystemColor;

public class Hold extends JFrame{
	
	private static String Mfg_Date="",Exp_date="";
	static Connection con;
	PreparedStatement pst;
	ResultSet rs;
	String 	MDate,EDate,TDate;
	String MyMedMfg, MyMedExp,MyMedToday;
	

	JFrame frame;
	private JTextField textsup;
	private JTextField textsku;
	private JTextField texttype;
	private JTextField textqty;  
	private JTextField textcon;
	private JTextField today;
	private JTextField textPrice;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hold window = new Hold();
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
	public Hold() {
		initialize();
		Connect();
		
		
	}
	
	public static void Connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/demo12","root","Epilex@1");
        }
        catch (Exception ex){ ex.printStackTrace(); }
    }
	
	
	void SetMfgdate(String s) {
		Mfg_Date = s;
	}
	
	String getMfgDate() {
		return Mfg_Date;
	}

	void SetExpdate(String s){
		Exp_date = s;
	}
	
	String getExpDate() {
		return Exp_date;
	}
	
	public static void infoBox(String infoMessage, String titleBar)
	{
	   JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}

	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 963, 682);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hold Management");
		lblNewLabel.setForeground(new Color(0, 139, 139));
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblNewLabel.setBounds(432, 25, 166, 31);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Received Inventory");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				RecievedInventory window = new RecievedInventory();
				window.frmMainFrame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNewLabel_1.setBounds(20, 154, 143, 31);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Recall");
		lblNewLabel_1_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(20, 193, 132, 31);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Shipment");
		lblNewLabel_1_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Shipment shipment = new Shipment();
				shipment.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_1_1_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_1_1_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNewLabel_1_1_1.setBounds(20, 232, 132, 31);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Supplier");
		lblNewLabel_2.setForeground(new Color(0, 139, 139));
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNewLabel_2.setBounds(215, 124, 54, 13);
		frame.getContentPane().add(lblNewLabel_2);
		
		textsup = new JTextField();
		textsup.setBounds(336, 122, 125, 19);
		frame.getContentPane().add(textsup);
		textsup.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("SKU");
		lblNewLabel_2_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_2_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNewLabel_2_1.setBounds(215, 165, 54, 13);
		frame.getContentPane().add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Product Type");
		lblNewLabel_2_1_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_2_1_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNewLabel_2_1_1.setBounds(212, 202, 83, 13);
		frame.getContentPane().add(lblNewLabel_2_1_1);
		
		JLabel lblNewLabel_2_1_1_1 = new JLabel("Quantity");
		lblNewLabel_2_1_1_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_2_1_1_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNewLabel_2_1_1_1.setBounds(215, 238, 54, 13);
		frame.getContentPane().add(lblNewLabel_2_1_1_1);
		
		JLabel lblNewLabel_2_1_1_1_1 = new JLabel("Country");
		lblNewLabel_2_1_1_1_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_2_1_1_1_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNewLabel_2_1_1_1_1.setBounds(617, 125, 54, 13);
		frame.getContentPane().add(lblNewLabel_2_1_1_1_1);
		
		JLabel lblNewLabel_2_1_1_1_1_1 = new JLabel("Product Group");
		lblNewLabel_2_1_1_1_1_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_2_1_1_1_1_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNewLabel_2_1_1_1_1_1.setBounds(615, 165, 95, 13);
		frame.getContentPane().add(lblNewLabel_2_1_1_1_1_1);
		
		JLabel lblNewLabel_2_1_1_1_1_1_1 = new JLabel("Mfg Date");
		lblNewLabel_2_1_1_1_1_1_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_2_1_1_1_1_1_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNewLabel_2_1_1_1_1_1_1.setBounds(617, 209, 71, 13);
		frame.getContentPane().add(lblNewLabel_2_1_1_1_1_1_1);
		
		JLabel lblNewLabel_2_1_1_1_1_1_1_1 = new JLabel("Exp Date");
		lblNewLabel_2_1_1_1_1_1_1_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_2_1_1_1_1_1_1_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNewLabel_2_1_1_1_1_1_1_1.setBounds(617, 250, 71, 13);
		frame.getContentPane().add(lblNewLabel_2_1_1_1_1_1_1_1);
		
		textsku = new JTextField();
		textsku.setColumns(10);
		textsku.setBounds(336, 161, 125, 19);
		frame.getContentPane().add(textsku);
		
		texttype = new JTextField();
		texttype.setColumns(10);
		texttype.setBounds(336, 198, 125, 19);
		frame.getContentPane().add(texttype);
		
		textqty = new JTextField();
		textqty.setColumns(10);
		textqty.setBounds(336, 234, 125, 19);
		frame.getContentPane().add(textqty);
		
		textcon = new JTextField();
		textcon.setColumns(10);
		textcon.setBounds(746, 124, 125, 19);
		frame.getContentPane().add(textcon);
		
		String languages[]={"Surgery","Machine","Medicine","Syrup"}; 
		JComboBox comgroup = new JComboBox(languages);
		comgroup.setBounds(746, 161, 131, 21);
		frame.getContentPane().add(comgroup);
		
		final JDateChooser Mfg = new JDateChooser();
		
		Mfg.addPropertyChangeListener(new PropertyChangeListener() 
		{
			public void propertyChange(PropertyChangeEvent evt)
			{
				final String ans;
				  if ("date".equals(evt.getPropertyName())) { 
				       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				       ans = sdf.format(Mfg.getDate());
				       SetMfgdate(ans);
				   }
			}
		
		});
		Mfg.setBounds(746, 209, 131, 22);
		frame.getContentPane().add(Mfg);
		
		JDateChooser Expi = new JDateChooser();
		Expi.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				final String ans;
				  if ("date".equals(evt.getPropertyName())) { 
				       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				       ans = sdf.format(Expi.getDate());
				       SetExpdate(ans);
				   }
			}
		});
		Expi.setBounds(746, 250, 131, 22);
		frame.getContentPane().add(Expi);
		
		JLabel lblTodayDate = new JLabel("Today Date");
		lblTodayDate.setForeground(new Color(0, 139, 139));
		lblTodayDate.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblTodayDate.setBounds(617, 290, 70, 13);
		frame.getContentPane().add(lblTodayDate);
		
		today = new JTextField();
		today.setText((String) null);
		today.setColumns(10);
		today.setBounds(749, 285, 128, 19);
		frame.getContentPane().add(today);
		
		JButton btnNewButton = new JButton("SUBMIT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select * from inventory where SKU=?"; 
		            pst = con.prepareStatement(query);
		            pst.setInt(1,Integer.parseInt(textsku.getText()));
		            ResultSet rs = pst.executeQuery();
//					pst = con.prepareStatement("Select * from inventory where SKU= "+textsku.getText()+"");
//					rs = pst.executeQuery();
					if(rs.next()) {  
						textsup.setText(rs.getString("Supllier"));
						textcon.setText(rs.getString("Country"));
						texttype.setText(rs.getString("Product_Type"));
						textPrice.setText(rs.getString("Product_Price"));
						textqty.setText(rs.getString("Quantity"));
						today.setText(rs.getString("Today_Date"));
						comgroup.setSelectedItem(rs.getString("Product_Group").toString());
//					    comgroup.addItem(rs.getString("Product_Group"));
						Mfg.setDate(rs.getDate("Mfg_Date"));
						Expi.setDate(rs.getDate("Exp_Date"));						
						
												
						JOptionPane.showMessageDialog(null, "Product can Enter in Warehouse");
						}
					else
						JOptionPane.showMessageDialog(null, "Product Cannot Enter In Warehouse");
					//con.close();					
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
		btnNewButton.setBackground(new Color(0, 250, 154));
		btnNewButton.setBounds(341, 339, 85, 21);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("SAVE");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String supplier,ptype,country,pgroup,medmfg,medexp,medtoday;
				int sku,qnt,Price;
				
				supplier = textsup.getText();
				ptype = texttype.getText();
				country = textcon.getText();
				
				String skuData = textsku.getText();
				String quantity = textqty.getText();
				String PriceData = textPrice.getText();
				
				medmfg = getMfgDate();//mfg.getText();
				medexp = getExpDate();// exp.getText();
				medtoday = today.getText();				
					
					sku = Integer.parseInt(skuData);
					qnt = Integer.valueOf(quantity);
					Price = Integer.parseInt(PriceData);
					pgroup = (String)comgroup.getItemAt(comgroup.getSelectedIndex());
					
					try
					{
						pst = con.prepareStatement("insert into warehouse(Supplier,SKU,Product_Type,Quantity,Country,Product_Group,Mfg_Date,Exp_Date,Today_Date,Product_Price)values(?,?,?,?,?,?,?,?,?,?)");
						pst.setString(1, supplier);
						pst.setInt(2, sku);
						pst.setString(3, ptype);
						pst.setInt(4, qnt);
						pst.setString(5, country);
						pst.setString(6, pgroup);
						pst.setString(7, medmfg);
						pst.setString(8, medexp);
						pst.setString(9, medtoday);
						pst.setInt(10, Price);
						pst.executeUpdate();
						infoBox("New Record Successfully Added","Saved");
						new Inbound().table_load();
						
				   }catch (SQLException e1) { e1.printStackTrace();}
				
			
				
				
			}
		});
		btnExit.setFont(new Font("Segoe UI", Font.BOLD, 10));
		btnExit.setBackground(new Color(230, 230, 250));
		btnExit.setBounds(488, 336, 85, 21);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.setFont(new Font("Segoe UI", Font.BOLD, 10));
		btnClear.setBackground(Color.RED);
		btnClear.setBounds(625, 336, 85, 21);
		frame.getContentPane().add(btnClear);
		
		JLabel lblNewLabel_2_1_1_1_2 = new JLabel("Price");
		lblNewLabel_2_1_1_1_2.setForeground(new Color(0, 139, 139));
		lblNewLabel_2_1_1_1_2.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNewLabel_2_1_1_1_2.setBounds(215, 267, 54, 13);
		frame.getContentPane().add(lblNewLabel_2_1_1_1_2);
		
		textPrice = new JTextField();
		textPrice.setColumns(10);
		textPrice.setBounds(336, 264, 125, 19);
		frame.getContentPane().add(textPrice);
		
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
		lblNewLabel_2_3.setBounds(20, 271, 110, 28);
		frame.getContentPane().add(lblNewLabel_2_3);
		
		JLabel lblNewLabel_1_2 = new JLabel("Inbound Audit");
		lblNewLabel_1_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Inbound inbound = new Inbound();
				inbound.frame.setVisible(true);
				frame.dispose();
				
				
			}
		});
		lblNewLabel_1_2.setForeground(new Color(0, 139, 139));
		lblNewLabel_1_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNewLabel_1_2.setBounds(20, 113, 143, 31);
		frame.getContentPane().add(lblNewLabel_1_2);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.scrollbar);
		panel.setBounds(0, 0, 166, 356);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2_6 = new JLabel("Dashboard");
		lblNewLabel_2_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Dashboard dashboard = new Dashboard();
				dashboard.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_2_6.setBounds(22, 81, 74, 20);
		lblNewLabel_2_6.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_6.setFont(new Font("Segoe UI", Font.BOLD, 14));
		panel.add(lblNewLabel_2_6);
	}
}
