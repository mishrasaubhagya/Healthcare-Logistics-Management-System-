import java.awt.EventQueue;  
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;  

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import net.proteanit.sql.DbUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;



public class RecievedInventory {
	
	private static String Mfg_Date="",Exp_date="";
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	String 	MDate,EDate,TDate;
	String MyMedMfg, MyMedExp,MyMedToday;	
	 
	JFrame frmMainFrame;
	
	private JLabel lblNewLabel;
	private JLabel lblSku;
	private JLabel lblProductType;
	private JLabel lblQuantity;
	private JLabel lblCountry;
	private JLabel lblProductType_1;
	private JLabel lblMfgDate;
	private JLabel lblExpdate;
	private JTextField txtsupplier;
	private JTextField textsku;
	private JTextField texttype;
	private JTextField textquantity;
	private JTextField textcon;
	private JLabel lblNewLabel_1;
	private JTable table;
    private JScrollPane scrollPane;
    private JLabel lblTodayDate;
    private JLabel lblX;
    private JTextField today;
    private JTextField textPrice;
    private JLabel lblPrice;
    private JLabel lblNewLabel_2_3;
    private JLabel lblNewLabel_3;
    private JPanel panel;
    private JLabel lblNewLabel_2_4;
	
	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecievedInventory window = new RecievedInventory();
					window.frmMainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

	}

	/**
	 * Create the application.
	 */
	public RecievedInventory() {
		initialize();
		Connect();
		table_load();
		set_max_function();
 

		
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
		    pst = con.prepareStatement("select * from inventory");
		    rs = pst.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
		}
	    catch (SQLException e){ e.printStackTrace(); }
	}
	
	public static void infoBox(String infoMessage, String titleBar)
	{
	   JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
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

	String currDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    Date date = new Date();  
	    String cdate = formatter.format(date);
	    return cdate;
	}

	void set_max_function() {
	    try {
	        String query = "Select max(`SKU`) as max from inventory ";
	        PreparedStatement pst = con.prepareStatement(query);
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next()) {
	            int num = rs.getInt("max");
	            int inc = num + 1;
	            textsku.setText(Integer.toString(inc));
	            textsku.enableInputMethods(false);
	        }
	    } catch (Exception f) {        
	        f.printStackTrace();
	    }
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("rawtypes")
	private void initialize() {
		frmMainFrame = new JFrame();
		
		String languages[]={"Surgery","Medical Device","Medicine","Syrup"}; 
		@SuppressWarnings("unchecked")
		JComboBox comgroup = new JComboBox(languages);
		comgroup.setBounds(822, 88, 131, 21);
		frmMainFrame.getContentPane().add(comgroup);
		
		frmMainFrame.setTitle("Main Frame");
		frmMainFrame.setBounds(100, 100, 1098, 710);
		frmMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainFrame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("New label");
		label.setBounds(10, 362, 70, -289);
		frmMainFrame.getContentPane().add(label);
		
		lblNewLabel = new JLabel("Supplier");
		lblNewLabel.setForeground(new Color(0, 139, 139));
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblNewLabel.setBounds(203, 60, 54, 13);
		frmMainFrame.getContentPane().add(lblNewLabel);
		
		lblSku = new JLabel("SKU");
		lblSku.setForeground(new Color(0, 139, 139));
		lblSku.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblSku.setBounds(213, 89, 35, 13);
		frmMainFrame.getContentPane().add(lblSku);
		
		lblProductType = new JLabel("Product Type");
		lblProductType.setForeground(new Color(0, 139, 139));
		lblProductType.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblProductType.setBounds(173, 120, 84, 13);
		frmMainFrame.getContentPane().add(lblProductType);
		
		lblQuantity = new JLabel("Quantity");
		lblQuantity.setForeground(new Color(0, 139, 139));
		lblQuantity.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblQuantity.setBounds(203, 150, 54, 13);
		frmMainFrame.getContentPane().add(lblQuantity);
		
		lblCountry = new JLabel("Country");
		lblCountry.setForeground(new Color(0, 139, 139));
		lblCountry.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblCountry.setBounds(732, 62, 54, 13);
		frmMainFrame.getContentPane().add(lblCountry);
		
		lblProductType_1 = new JLabel("Product Group");
		lblProductType_1.setForeground(new Color(0, 139, 139));
		lblProductType_1.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblProductType_1.setBounds(702, 91, 84, 13);
		frmMainFrame.getContentPane().add(lblProductType_1);
		
		lblMfgDate = new JLabel("Mfg Date");
		lblMfgDate.setForeground(new Color(0, 139, 139));
		lblMfgDate.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblMfgDate.setBounds(732, 122, 54, 13);
		frmMainFrame.getContentPane().add(lblMfgDate);
		
		lblExpdate = new JLabel("Exp Date");
		lblExpdate.setForeground(new Color(0, 139, 139));
		lblExpdate.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblExpdate.setBounds(732, 152, 54, 13);
		frmMainFrame.getContentPane().add(lblExpdate);
		
		txtsupplier = new JTextField();
		txtsupplier.setBounds(288, 58, 128, 19);
		frmMainFrame.getContentPane().add(txtsupplier);
		txtsupplier.setColumns(10);
		
		textsku = new JTextField();
		textsku.setColumns(10);
		textsku.setBounds(288, 87, 128, 19);
		frmMainFrame.getContentPane().add(textsku);
		
		texttype = new JTextField();
		texttype.setColumns(10);
		texttype.setBounds(288, 118, 128, 19);
		frmMainFrame.getContentPane().add(texttype);
		
		textquantity = new JTextField();
		textquantity.setColumns(10);
		textquantity.setBounds(288, 148, 128, 19);
		frmMainFrame.getContentPane().add(textquantity);
		
		textcon = new JTextField();
		textcon.setColumns(10);
		textcon.setBounds(822, 60, 128, 19);
		frmMainFrame.getContentPane().add(textcon);
		
		lblNewLabel_1 = new JLabel("Received Inventory");
		lblNewLabel_1.setForeground(new Color(0, 128, 128));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblNewLabel_1.setBounds(478, 9, 178, 28);
		frmMainFrame.getContentPane().add(lblNewLabel_1);
		
		lblTodayDate = new JLabel("Today Date");
		lblTodayDate.setForeground(new Color(0, 139, 139));
		lblTodayDate.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblTodayDate.setBounds(716, 187, 70, 13);
		frmMainFrame.getContentPane().add(lblTodayDate);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(115, 337, 916, 326);
		frmMainFrame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		lblX = new JLabel("X");
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		lblX.setForeground(new Color(0, 139, 139));
		lblX.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblX.setBounds(1067, 11, 27, 28);
		frmMainFrame.getContentPane().add(lblX);
		
		today = new JTextField();
		today.setColumns(10);
		today.setBounds(822, 182, 128, 19);
		frmMainFrame.getContentPane().add(today);
		today.setText(currDate());  // Setting Current date
		today.enableInputMethods(false);  // Disabling Input


		txtsupplier.addKeyListener(new KeyAdapter() {
		      public void keyReleased(KeyEvent e) {
		    		String s= txtsupplier.getText();
		    		String stmtString="SELECT * FROM demo12.inventory WHERE Supllier LIKE "+ "'" +s+ "%';"; ;
		    		System.out.println(stmtString);
		    		
		    		try
		    		{
		    		    pst = con.prepareStatement(stmtString);
		    		    rs = pst.executeQuery();
		    		    table.setModel(DbUtils.resultSetToTableModel(rs));
		    		}
		    	    catch (SQLException e2){ e2.printStackTrace(); }
		      }

		      public void keyTyped(KeyEvent e) {
		    	  
		      }

		      public void keyPressed(KeyEvent e) {
		    	  
		      }
		    });

		
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
		Mfg.setBounds(822, 113, 131, 22);
		frmMainFrame.getContentPane().add(Mfg);
		
		JDateChooser Exp = new JDateChooser();
		Exp.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				final String ans;
				  if ("date".equals(evt.getPropertyName())) { 
				       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				       ans = sdf.format(Exp.getDate());
				       SetExpdate(ans);
				   }
			}
		});
		Exp.setBounds(822, 143, 131, 22);
		frmMainFrame.getContentPane().add(Exp);		
		
		// Submit Button Click
		JButton btnNewButton = new JButton("SUBMIT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  
				String supplier,ptype,country,pgroup,medmfg,medexp,medtoday;
				int sku,qnt,Price;
				
				supplier = txtsupplier.getText();
				ptype = texttype.getText();
				country = textcon.getText();
				
				String skuData = textsku.getText();
				String quantity = textquantity.getText();
				String PriceData = textPrice.getText();
				
				medmfg = getMfgDate();//mfg.getText();
				medexp = getExpDate();// exp.getText();
				medtoday = today.getText();				
				
				if(skuData.length()==0) {
					   infoBox("SKU can't be Blank","SKU Empty");
				}
				else if(ptype.length()==0) 
				{
					  infoBox("PRODUCT TYPE can't be Blank","Ptype Empty");
				}
				else if(country.length()==0) 
				{
					  infoBox("Country Name can't be Blank","Country Empty");
				}
		      	else if(medmfg.length()==0) 
				{
					  infoBox("Manufacturing Date can't be Blank","MFG Date Empty");
				}
				else if(medexp.length()==0) 
				{
					  infoBox("Expiary Date can't be Blank","EXP date Empty");
				}
				else 
				{
					sku = Integer.parseInt(skuData);
					qnt = Integer.valueOf(quantity);
					Price = Integer.parseInt(PriceData);
					pgroup = (String)comgroup.getItemAt(comgroup.getSelectedIndex());
					
					try
					{
						pst = con.prepareStatement("insert into inventory(Supllier,SKU,Product_Type,Quantity,Country,Product_Group,Mfg_Date,Exp_Date,Today_Date,Product_Price)values(?,?,?,?,?,?,?,?,?,?)");
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
						table_load();
						
				   }catch (SQLException e1) { e1.printStackTrace();}
				}
			}
		});
		
		btnNewButton.setBackground(new Color(0, 250, 154));
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 10));
		btnNewButton.setBounds(346, 278, 85, 21);   
		frmMainFrame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExit.setBackground(new Color(230, 230, 250));
		btnExit.setFont(new Font("Segoe UI", Font.BOLD, 10));
		btnExit.setBounds(504, 279, 85, 21);
		frmMainFrame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					con = DriverManager.getConnection("jdbc:mysql://localhost/demo12","root","Epilex@1");
					String skuData = textsku.getText(); 
					String Query = "Delete from demo12.inventory where SKU"+skuData;
					pst.executeUpdate(Query);
					
		        }
		        catch (Exception e1)
				{ 
		        	e1.printStackTrace();
		        }
			}
			
		});
		
		btnClear.setBackground(new Color(255, 0, 0));
		btnClear.setFont(new Font("Segoe UI", Font.BOLD, 10));
		btnClear.setBounds(649, 280, 85, 21);
		frmMainFrame.getContentPane().add(btnClear);		
		
		textPrice = new JTextField();
		textPrice.setColumns(10);
		textPrice.setBounds(288, 180, 128, 19);
		frmMainFrame.getContentPane().add(textPrice);
		
		lblPrice = new JLabel("Price");
		lblPrice.setForeground(new Color(0, 139, 139));
		lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblPrice.setBounds(203, 183, 54, 13);
		frmMainFrame.getContentPane().add(lblPrice);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.scrollbar);
		panel.setBounds(0, 0, 155, 335);
		frmMainFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2_2 = new JLabel("Shipment");
		lblNewLabel_2_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Shipment shipment = new Shipment();
				shipment.frame.setVisible(true);
				frmMainFrame.dispose();
			}
		});
		lblNewLabel_2_2.setBounds(12, 237, 110, 28);
		panel.add(lblNewLabel_2_2);
		lblNewLabel_2_2.setBackground(SystemColor.controlShadow);
		lblNewLabel_2_2.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_2.setFont(new Font("Segoe UI", Font.BOLD, 14));
		
		lblNewLabel_2_3 = new JLabel("Statistics");
		lblNewLabel_2_3.setBounds(12, 196, 110, 28);
		panel.add(lblNewLabel_2_3);
		lblNewLabel_2_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Stats st = new Stats();
				st.frame.setVisible(true);
				frmMainFrame.dispose();
				
			}
		});
		lblNewLabel_2_3.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_3.setFont(new Font("Segoe UI", Font.BOLD, 14));
		
		JLabel lblNewLabel_2_1 = new JLabel("Recall ");
		lblNewLabel_2_1.setBounds(12, 157, 128, 26);
		panel.add(lblNewLabel_2_1);
		lblNewLabel_2_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		lblNewLabel_2_1.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		
		lblNewLabel_3 = new JLabel("Inbound Audit");
		lblNewLabel_3.setBounds(12, 109, 128, 46);
		panel.add(lblNewLabel_3);
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Inbound inbound = new Inbound();
				inbound.frame.setVisible(true);
				frmMainFrame.dispose();
			}
		});
		lblNewLabel_3.setForeground(new Color(0, 128, 128));
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.BOLD, 14));
		
		JLabel lblNewLabel_2 = new JLabel("Hold Management");
		lblNewLabel_2.setBounds(12, 75, 128, 46);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Hold hold = new Hold();
				hold.frame.setVisible(true);
				frmMainFrame.dispose();
								
			}
		});
		lblNewLabel_2.setForeground(new Color(0, 128, 128));
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 14));
		
		lblNewLabel_2_4 = new JLabel("Dashboard");
		lblNewLabel_2_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Dashboard dashboard = new Dashboard();
				dashboard.frame.setVisible(true);
				frmMainFrame.dispose();
			}
		});
		lblNewLabel_2_4.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_4.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_2_4.setBounds(12, 55, 127, 20);
		panel.add(lblNewLabel_2_4);
	}
}
