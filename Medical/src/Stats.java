import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.awt.BorderLayout;

import javax.swing.JFrame;  
import javax.swing.SwingUtilities;  
import javax.swing.WindowConstants;  
  
import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.labels.PieSectionLabelGenerator;  
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;  
import org.jfree.data.general.PieDataset;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;  
public class Stats extends JFrame {
	private static String start_date="",end_date="";
	JFrame frame;
	private JLabel lblNewLabel;
	private JLabel lblEnd;
	private JLabel lblNewLabel_1;
	private JPanel barChart;
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
	private JButton btnNewButton_1;
	private JButton btnBarChart;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_2_1;
	private JLabel lblNewLabel_2_2;
	private JLabel lblNewLabel_2_3;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JPanel panel;
	private JLabel lblNewLabel_2_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stats window = new Stats();
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
	public Stats() {
		initialize();
		
	}
	
@SuppressWarnings("removal")
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
        barChart.removeAll();
        barChart.add(barChartPanel, BorderLayout.CENTER);
        barChartPanel.setLayout(null);
        barChart.validate();
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
     barChart.removeAll();
     barChart.add(lineChartPanel, BorderLayout.CENTER);
     barChart.validate();
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
//    categoryPlot.setRangeGridlinePaint(Color.BLUE);
    categoryPlot.setBackgroundPaint(Color.WHITE);
    BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
    Color clr3 = new Color(204,0,51);
    renderer.setSeriesPaint(0, clr3);
    
    ChartPanel barpChartPanel = new ChartPanel(chart);
    barChart.removeAll();
    barChart.add(barpChartPanel, BorderLayout.CENTER);
    barChart.validate();    
}
	
	public void createplot() {
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 979, 654);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblNewLabel = new JLabel("Start Date");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNewLabel.setForeground(new Color(0, 139, 139));
		lblNewLabel.setBounds(186, 566, 76, 19);
		frame.getContentPane().add(lblNewLabel);
		
		lblEnd = new JLabel("End Date");
		lblEnd.setForeground(new Color(0, 139, 139));
		lblEnd.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblEnd.setBounds(652, 569, 76, 19);
		frame.getContentPane().add(lblEnd);
		
		lblNewLabel_1 = new JLabel("Product Statistics");
		lblNewLabel_1.setForeground(new Color(0, 139, 139));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblNewLabel_1.setBounds(473, 13, 165, 46);
		frame.getContentPane().add(lblNewLabel_1);
		
		barChart = new JPanel();
		barChart.setBounds(213, 67, 664, 438);
		frame.getContentPane().add(barChart);
		
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
		Mfg_start.setBounds(288, 563, 131, 22);
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
		Mfg_end.setBounds(754, 566, 131, 22);
		frame.getContentPane().add(Mfg_end);
		
		JButton btnNewButton = new JButton("Pie Chart");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPieChart();
			}
		});
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnNewButton.setBackground(new Color(0, 250, 154));
		btnNewButton.setBounds(490, 513, 85, 21);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("Line Chart");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showLineChart();
			}
		});
		btnNewButton_1.setFont(new Font("Segoe UI", Font.BOLD, 10));
		btnNewButton_1.setBackground(new Color(0, 250, 154));
		btnNewButton_1.setBounds(233, 514, 85, 21);
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
		btnBarChart.setBounds(356, 514, 101, 21);
		frame.getContentPane().add(btnBarChart);
		
		lblNewLabel_2_2 = new JLabel("");
		lblNewLabel_2_2.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_2.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_2_2.setBounds(10, 232, 110, 28);
		frame.getContentPane().add(lblNewLabel_2_2);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.scrollbar);
		panel.setBounds(0, 0, 169, 339);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		lblNewLabel_2_3 = new JLabel("Shipment");
		lblNewLabel_2_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Shipment shipment = new Shipment();
				shipment.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_2_3.setBounds(12, 272, 110, 28);
		panel.add(lblNewLabel_2_3);
		lblNewLabel_2_3.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_3.setFont(new Font("Segoe UI", Font.BOLD, 14));
		
		lblNewLabel_2_1 = new JLabel("Recall ");
		lblNewLabel_2_1.setBounds(12, 233, 128, 26);
		panel.add(lblNewLabel_2_1);
		lblNewLabel_2_1.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		
		lblNewLabel_2 = new JLabel("Hold Management");
		lblNewLabel_2.setBounds(12, 184, 128, 46);
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
		
		lblNewLabel_3 = new JLabel("Received Management");
		lblNewLabel_3.setBounds(12, 148, 159, 37);
		panel.add(lblNewLabel_3);
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RecievedInventory window = new RecievedInventory();
				window.frmMainFrame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_3.setForeground(new Color(0, 128, 128));
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.BOLD, 14));
		
		lblNewLabel_4 = new JLabel("Inbound Audit");
		lblNewLabel_4.setBounds(12, 107, 159, 37);
		panel.add(lblNewLabel_4);
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Inbound inbound = new Inbound();
				inbound.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_4.setForeground(new Color(0, 128, 128));
		lblNewLabel_4.setFont(new Font("Segoe UI", Font.BOLD, 14));
		
		lblNewLabel_2_4 = new JLabel("Dashboard");
		lblNewLabel_2_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Dashboard dashboard = new Dashboard();
				dashboard.frame.setVisible(true);
				frame.dispose();
			}
		});
		lblNewLabel_2_4.setForeground(new Color(0, 128, 128));
		lblNewLabel_2_4.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblNewLabel_2_4.setBounds(12, 73, 127, 20);
		panel.add(lblNewLabel_2_4);
	}
}
