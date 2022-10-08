import java.awt.EventQueue;

import javax.swing.JFrame;
import net.proteanit.sql.DbUtils;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;

public class Inbound {
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;

	JFrame frame;
	private static String start_date="",end_date="";
	private JTable table;
    private JScrollPane scrollPane;
    private JTable table_1;
    private JPanel Mfg_start;
	private JPanel Mfg_end;
	
	
	public JPanel getMfg_start() {
		return Mfg_start;
	}

	public void setMfg_start(JPanel mfg_start) {
		Mfg_start = mfg_start;
	}

	public JPanel getMfg_end() {
		return Mfg_end;
	}

	public void setMfg_end(JPanel mfg_end) {
		Mfg_end = mfg_end;
	}
	
	void SetStartDate(String s) {
		start_date = s;
	}
	
	String getStartDate() {
		return start_date;
	}

	void SetEndDate(String s){
		end_date = s;
	}
	
	String getEndDate() {
		return end_date;
	}
	
    

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inbound window = new Inbound();
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
	public Inbound() {
		initialize();
		Connect();
		table_load();
		
	}
	
	public void Connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/demo12","root","Epilex@1");
        }
        catch (Exception ex)
        { ex.printStackTrace(); 
        }
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
	
	public void ShowData(){
		String x,y;
		x=getStartDate();
		y=getEndDate();
		
		String ste="Select * from demo12.warehouse where Today_Date between '"+x+"' and '"+y+ "' group by Today_Date;"; 
	      try {
	          Class.forName("com.mysql.cj.jdbc.Driver");
	          con = DriverManager.getConnection("jdbc:mysql://localhost/demo12","root","Epilex@1");
	      }
	      catch (Exception ex){ ex.printStackTrace(); 
	      
	      }
			try
			{
			    pst = con.prepareStatement(ste);
			    rs = pst.executeQuery();
			    
			    while (rs.next()) { 
			    	table_1.setModel(DbUtils.resultSetToTableModel(rs));
			    	
//			        barDataset.setValue( rs.getString(2) ,rs.getInt(1)); 
			            
			    }
			}
		    catch (SQLException e){ e.printStackTrace(); }
	        
	        
	        
	    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1084, 767);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_2_4 = new JLabel("Recall");
		lblNewLabel_2_4.setBounds(499, 0, 71, 46);
		lblNewLabel_2_4.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_4.setFont(new Font("Segoe UI", Font.BOLD, 19));
		frame.getContentPane().add(lblNewLabel_2_4);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 155, 272);
		panel.setBackground(SystemColor.scrollbar);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2_2 = new JLabel("Shipment");
		lblNewLabel_2_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Shipment shipment = new Shipment();
				shipment.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_2_2.setBounds(12, 192, 110, 28);
		panel.add(lblNewLabel_2_2);
		lblNewLabel_2_2.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_2.setFont(new Font("Segoe UI", Font.BOLD, 14));
		
		JLabel lblNewLabel_2_3 = new JLabel("Statistics");
		lblNewLabel_2_3.setBounds(12, 155, 110, 28);
		panel.add(lblNewLabel_2_3);
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
		
		JLabel lblNewLabel_2_5 = new JLabel("Received Inventory");
		lblNewLabel_2_5.setBounds(12, 113, 142, 40);
		panel.add(lblNewLabel_2_5);
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
		
		JLabel lblNewLabel_2 = new JLabel("Hold Management");
		lblNewLabel_2.setBounds(12, 72, 128, 46);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Hold hold = new Hold();
				hold.frame.setVisible(true);
				frame.dispose();
			}
			
		});
		lblNewLabel_2.setForeground(new Color(0, 128, 128));
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 14));
		
		JLabel lblNewLabel_2_6 = new JLabel("Dashboard");
		lblNewLabel_2_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Dashboard dashboard = new Dashboard();
				dashboard.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_2_6.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_6.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_2_6.setBounds(13, 40, 127, 20);
		panel.add(lblNewLabel_2_6);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 305, 962, 412);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_2_4_1 = new JLabel("Inbound Audit");
		lblNewLabel_2_4_1.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_4_1.setFont(new Font("Segoe UI", Font.BOLD, 19));
		lblNewLabel_2_4_1.setBounds(472, 256, 147, 46);
		frame.getContentPane().add(lblNewLabel_2_4_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(167, 41, 744, 202);
		frame.getContentPane().add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		JButton btnPrint = new JButton("PRINT");
		btnPrint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					table_1.print();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				
			}
		});
		btnPrint.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnPrint.setBackground(Color.GREEN);
		btnPrint.setBounds(167, 247, 129, 25);
		frame.getContentPane().add(btnPrint);
		
		JLabel lblNewLabel = new JLabel("Start Date");
		lblNewLabel.setForeground(new Color(0, 139, 139));
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNewLabel.setBounds(945, 41, 76, 19);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblEnd = new JLabel("End Date");
		lblEnd.setForeground(new Color(0, 139, 139));
		lblEnd.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblEnd.setBounds(945, 133, 76, 19);
		frame.getContentPane().add(lblEnd);
		
		JDateChooser Mfg_start = new JDateChooser();
		Mfg_start.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				  final String ans;
				  if ("date".equals(evt.getPropertyName())) { 
				       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				       ans = sdf.format(Mfg_start.getDate());
				       SetStartDate(ans);
				   }
			}
		});
		Mfg_start.setBounds(923, 64, 131, 22);
		frame.getContentPane().add(Mfg_start);
		
		JDateChooser Mfg_end = new JDateChooser();
		
		Mfg_end.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				final String ans;
				  if ("date".equals(evt.getPropertyName())) { 
				       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				       ans = sdf.format(Mfg_end.getDate());
				       SetEndDate(ans);
				   }
			}
		});
		Mfg_end.setBounds(923, 159, 131, 22);
		frame.getContentPane().add(Mfg_end);
		
		JButton btnNewButton = new JButton("SEARCH");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ShowData();
				
				
			}
		});
		btnNewButton.setBackground(new Color(0, 255, 0));
		btnNewButton.setForeground(Color.BLUE);
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnNewButton.setBounds(937, 218, 97, 25);
		frame.getContentPane().add(btnNewButton);
	}
}
