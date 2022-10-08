import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.SystemColor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.border.MatteBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;


public class Dashboard {
	
	private static String start_date="",end_date="";
	JFrame frame;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel;
	private JLabel lblEnd;
	private JPanel barChart_1;
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
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	private JButton btnBarChart;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_2_1;
	private JLabel lblNewLabel_2_2;
	private JLabel lblNewLabel_2_3;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblinventory;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTable table;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_8;
	private JLabel lblNewLabel_2_4;
	private JLabel lblNewLabel_2_5;
	private JLabel lblNewLabel_2_6;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboard window = new Dashboard();
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
	public Dashboard() {
		initialize();
		Connect();
		setDataToCards();
		table_load();
	}
	
	public void Connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/demo12","root","Epilex@1");
        }
        catch (Exception ex){ ex.printStackTrace(); }
    }
	
	public void setDataToCards() {
		String ab = "select COUNT(SKU) from demo12.warehouse;";
		
		try {
	          Class.forName("com.mysql.cj.jdbc.Driver");
	          con = DriverManager.getConnection("jdbc:mysql://localhost/demo12","root","Epilex@1");
	      }
	      catch (Exception ex){ ex.printStackTrace(); 
	      
	      }
		try {
			
			pst = con.prepareStatement(ab);
			System.out.println(ab);
		    rs = pst.executeQuery();
		    if(rs.next()){
		    	System.out.println(rs.getInt(1));
		    	 lblNewLabel_2_1.setText(Integer.toString(rs.getInt(1)));
		    	}
		    
		    
//		   
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void table_load()
    {
		try
		{
		
		    pst = con.prepareStatement("select * from demo12.warehouse where Quantity < 100 order by Quantity desc");
		    rs = pst.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
		}
	    catch (SQLException e){ e.printStackTrace(); }
		
	}
	
	public void showPieChart(){
		String x,y;
		x=getStartDate();
		y=getEndDate();
		
		String ste="Select COUNT(Today_Date) ,Today_Date from demo12.inventory where Today_Date between '"+x+"' and '"+y+ "' group by Today_Date;"; 
		DefaultPieDataset barDataset = new DefaultPieDataset( );
	    JFreeChart piechart = ChartFactory.createPieChart("Products",barDataset, false,true,false);
	    PiePlot piePlot =(PiePlot) piechart.getPlot();

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
			    int i=10;
			    while (rs.next()) { 
			    	i+=10;
			        barDataset.setValue( rs.getString(2) ,rs.getInt(1)); 
			        piePlot.setSectionPaint(rs.getString(1), new Color(240,150+i,102+i));       
			    }
			}
		    catch (SQLException e){ e.printStackTrace(); }
	        piePlot.setBackgroundPaint(Color.white);
	        
	        ChartPanel barChartPanel = new ChartPanel(piechart);
	        barChart_1.removeAll();
	        barChart_1.add(barChartPanel, BorderLayout.CENTER);
	        barChartPanel.setLayout(null);
	        barChart_1.validate();
	    }
	public void showLineChart(){
	   String x,y;
	   x = getStartDate();
	   y = getEndDate();
		String ste="Select COUNT(Today_Date) ,Today_Date from demo12.inventory where Today_Date between '"+x+"' and '"+y+  "' group by Today_Date;"; 

	      DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
	    
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
			    	linedataset.setValue( rs.getInt(1),"Product", rs.getString(2)); 
			    }
			}
		    catch (SQLException e){ e.printStackTrace(); }

	     JFreeChart linechart = ChartFactory.createLineChart("Products","Date","Number", 
	             linedataset, PlotOrientation.VERTICAL, false,true,false);
	     
	     CategoryPlot lineCategoryPlot = linechart.getCategoryPlot();
	     lineCategoryPlot.setRangeGridlinePaint(Color.BLUE);
	     lineCategoryPlot.setBackgroundPaint(Color.white);
	     
	     LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
	     Color lineChartColor = new Color(204,0,51);
	     lineRenderer.setSeriesPaint(0, lineChartColor);
	     
	     ChartPanel lineChartPanel = new ChartPanel(linechart);
	     barChart_1.removeAll();
	     barChart_1.add(lineChartPanel, BorderLayout.CENTER);
	     barChart_1.validate();
	}


	public void showBarChart(){
		String x,y;
		   x = getStartDate();
		   y = getEndDate();
			String ste="Select COUNT(Today_Date) ,Today_Date from demo12.inventory where Today_Date between '"+x+"' and '"+y+  "' group by Today_Date;"; 

		      DefaultCategoryDataset bardataset = new DefaultCategoryDataset();
		    
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
				    	bardataset.setValue( rs.getInt(1),"Product", rs.getString(2)); 
				    }
				}
			    catch (SQLException e){ e.printStackTrace(); }
	    
	    JFreeChart chart = ChartFactory.createBarChart("Products","Date","Number", 
	            bardataset, PlotOrientation.VERTICAL, false,true,false);
	    
	    CategoryPlot categoryPlot = chart.getCategoryPlot();
//	    categoryPlot.setRangeGridlinePaint(Color.BLUE);
	    categoryPlot.setBackgroundPaint(Color.WHITE);
	    BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
	    Color clr3 = new Color(204,0,51);
	    renderer.setSeriesPaint(0, clr3);
	    
	    ChartPanel barpChartPanel = new ChartPanel(chart);
	    barChart_1.removeAll();
	    barChart_1.add(barpChartPanel, BorderLayout.CENTER);
	    barChart_1.validate();    
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1162, 689);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.scrollbar);
		panel_1.setBounds(0, 0, 172, 320);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		lblNewLabel_6 = new JLabel("Received Management");
		lblNewLabel_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RecievedInventory window = new RecievedInventory();
				window.frmMainFrame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_6.setBounds(10, 25, 156, 20);
		lblNewLabel_6.setForeground(new Color(0, 128, 128));
		lblNewLabel_6.setFont(new Font("Segoe UI", Font.BOLD, 14));
		panel_1.add(lblNewLabel_6);
		
		lblNewLabel_7 = new JLabel("Hold Management");
		lblNewLabel_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Hold hold = new Hold();
				hold.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_7.setForeground(new Color(0, 128, 128));
		lblNewLabel_7.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_7.setBounds(10, 58, 128, 46);
		panel_1.add(lblNewLabel_7);
		
		lblNewLabel_8 = new JLabel("Inbound Audit");
		lblNewLabel_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Inbound inbound = new Inbound();
				inbound.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_8.setForeground(new Color(0, 128, 128));
		lblNewLabel_8.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_8.setBounds(10, 102, 117, 37);
		panel_1.add(lblNewLabel_8);
		
		lblNewLabel_2_4 = new JLabel("Statistics");
		lblNewLabel_2_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Stats st = new Stats();
				st.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_2_4.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_4.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_2_4.setBounds(10, 149, 110, 28);
		panel_1.add(lblNewLabel_2_4);
		
		lblNewLabel_2_5 = new JLabel("Recall ");
		lblNewLabel_2_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Inbound inbound = new Inbound();
				inbound.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_2_5.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_5.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_2_5.setBounds(10, 187, 128, 26);
		panel_1.add(lblNewLabel_2_5);
		
		lblNewLabel_2_6 = new JLabel("Shipment");
		lblNewLabel_2_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Shipment shipment = new Shipment();
				shipment.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_2_6.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_6.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_2_6.setBounds(10, 223, 110, 28);
		panel_1.add(lblNewLabel_2_6);
		
		JButton btnNewButton_1 = new JButton("Line Chart");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showLineChart();
			}
		});
		btnNewButton_1.setFont(new Font("Segoe UI", Font.BOLD, 10));
		btnNewButton_1.setBackground(new Color(0, 250, 154));
		btnNewButton_1.setBounds(682, 469, 85, 21);
		frame.getContentPane().add(btnNewButton_1);
		
		btnBarChart = new JButton("Bar Chart");
		btnBarChart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showBarChart();
			}
		});
		btnBarChart.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnBarChart.setBackground(new Color(0, 250, 154));
		btnBarChart.setBounds(828, 469, 101, 21);
		frame.getContentPane().add(btnBarChart);
		
		JButton btnNewButton = new JButton("Pie Chart");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showPieChart();
			}
		});
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnNewButton.setBackground(new Color(0, 250, 154));
		btnNewButton.setBounds(989, 469, 85, 21);
		frame.getContentPane().add(btnNewButton);
		
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
		Mfg_start.setBounds(908, 525, 131, 22);
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
		Mfg_end.setBounds(908, 584, 131, 22);
		frame.getContentPane().add(Mfg_end);
		
		lblNewLabel = new JLabel("Start Date");
		lblNewLabel.setForeground(new Color(0, 139, 139));
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNewLabel.setBounds(760, 528, 76, 19);
		frame.getContentPane().add(lblNewLabel);
		
		lblEnd = new JLabel("End Date");
		lblEnd.setForeground(new Color(0, 139, 139));
		lblEnd.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblEnd.setBounds(760, 584, 76, 19);
		frame.getContentPane().add(lblEnd);
		
		lblNewLabel_1 = new JLabel("Dashboard");
		lblNewLabel_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNewLabel_1.setBounds(352, 10, 114, 27);
		frame.getContentPane().add(lblNewLabel_1);
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(230, 230, 250));
		panel_2.setBorder(new MatteBorder(15, 0, 0, 0, (Color) Color.RED));
		panel_2.setBounds(182, 123, 172, 123);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		lblNewLabel_2_1 = new JLabel("10");
		lblNewLabel_2_1.setFont(new Font("Segoe UI", Font.PLAIN, 50));
		lblNewLabel_2_1.setBounds(34, 42, 101, 52);
		panel_2.add(lblNewLabel_2_1);
		
		lblNewLabel_2 = new JLabel("Products In warehouse");
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblNewLabel_2.setBounds(185, 86, 194, 27);
		frame.getContentPane().add(lblNewLabel_2);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBackground(new Color(230, 230, 250));
		panel_2_1.setLayout(null);
		panel_2_1.setBorder(new MatteBorder(15, 0, 0, 0, (Color) Color.RED));
		panel_2_1.setBounds(425, 123, 172, 123);
		frame.getContentPane().add(panel_2_1);
		
		lblinventory = new JLabel("82");
		lblinventory.setFont(new Font("Segoe UI", Font.PLAIN, 50));
		lblinventory.setBounds(41, 44, 96, 52);
		panel_2_1.add(lblinventory);
		
		lblNewLabel_2_2 = new JLabel("Products In Stock Alert");
		lblNewLabel_2_2.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblNewLabel_2_2.setBounds(428, 86, 216, 27);
		frame.getContentPane().add(lblNewLabel_2_2);
		
		barChart_1 = new JPanel();
		barChart_1.setBounds(678, 0, 470, 447);
		frame.getContentPane().add(barChart_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 322, 662, 330);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		lblNewLabel_5 = new JLabel("Stock Alert");
		lblNewLabel_5.setForeground(new Color(255, 0, 0));
		lblNewLabel_5.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNewLabel_5.setBounds(352, 287, 120, 21);
		frame.getContentPane().add(lblNewLabel_5);
	}
}
