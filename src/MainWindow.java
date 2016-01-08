import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


import static information.StockIndexes.MSFT;
import static information.StockIndexes.AAPL;
import static information.StockIndexes.AMZN;
import static information.StockIndexes.FB;
import static information.StockIndexes.GOOG;
import static information.StockIndexes.IBM;
import static information.StockIndexes.INTC;
import static information.StockIndexes.YHOO;
import static information.StockIndexes.TWTR;
import static information.StockIndexes.NVDA;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Font;






public class MainWindow {

	private JFrame frame;
	
	
	String[] stockIndexes = {AAPL, AMZN, FB, GOOG, IBM, INTC, MSFT, NVDA, TWTR, YHOO };
	
	JComboBox comboBox = null;
	
	JLabel infoLabel = null;
	
	JLabel infoClassLabel = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblChooseIndexOf = new JLabel("Choose index of a stock you wish to classify");
		GridBagConstraints gbc_lblChooseIndexOf = new GridBagConstraints();
		gbc_lblChooseIndexOf.gridwidth = 17;
		gbc_lblChooseIndexOf.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseIndexOf.gridx = 1;
		gbc_lblChooseIndexOf.gridy = 0;
		frame.getContentPane().add(lblChooseIndexOf, gbc_lblChooseIndexOf);
		
		comboBox = new JComboBox(stockIndexes);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 12;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 4;
		gbc_comboBox.gridy = 1;
		frame.getContentPane().add(comboBox, gbc_comboBox);
		
		JButton btnNewButton = new JButton("Classify");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stockIndex = comboBox.getSelectedItem().toString();
				
				try {
					infoLabel.setText("Please wait");
					infoClassLabel.setText("");
					Runner.StockInfo classification = Runner.classify(stockIndex);
					infoLabel.setText("Stock is in reality " + (classification.isIncreasing ? " rising " : " faling ") + ", while my classifier got the stock"
							+ " is " + (classification.classified ? "rising." : "falling."));
					
					if (classification.isIncreasing == classification.classified) {
						infoClassLabel.setText("CORRECT CLASSIFICATION");
						infoClassLabel.setForeground(Color.GREEN);
					}
					else {
						infoClassLabel.setText("WRONG CLASSIFICATION");
						infoClassLabel.setForeground(Color.RED);
					}
				} catch (ParserConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SAXException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 3;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 16;
		gbc_btnNewButton.gridy = 1;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		infoLabel = new JLabel("New label");
		GridBagConstraints gbc_infoLabel = new GridBagConstraints();
		gbc_infoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_infoLabel.gridheight = 3;
		gbc_infoLabel.gridwidth = 17;
		gbc_infoLabel.gridx = 2;
		gbc_infoLabel.gridy = 3;
		frame.getContentPane().add(infoLabel, gbc_infoLabel);
		
		infoClassLabel = new JLabel("klasifikacija");
		infoClassLabel.setFont(new Font("Skia", Font.BOLD, 21));
		GridBagConstraints gbc_infoClassLabel = new GridBagConstraints();
		gbc_infoClassLabel.insets = new Insets(0, 0, 5, 5);
		gbc_infoClassLabel.gridheight = 3;
		gbc_infoClassLabel.gridwidth = 13;
		gbc_infoClassLabel.gridx = 6;
		gbc_infoClassLabel.gridy = 8;
		frame.getContentPane().add(infoClassLabel, gbc_infoClassLabel);
		
		
	}

}
