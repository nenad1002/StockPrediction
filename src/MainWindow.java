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

import static information.GUIText.INTRO_TEXT;
import static information.GUIText.ABOUT_APP;
import static information.GUIText.ABOUT_APP_INFO;
import static information.GUIText.CLASSIFY;
import static information.GUIText.CORRECT_CLASSIFICATION;
import static information.GUIText.DATA_SAVED;
import static information.GUIText.INTRO_TEXT;
import static information.GUIText.PLEASE_WAIT;
import static information.GUIText.SAVE_DATABASE;
import static information.GUIText.WRONG_CLASSIFICATION;
import static information.GUIText.ERROR_OCCURRED;

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
import javax.swing.JOptionPane;

import java.awt.Font;






public class MainWindow {

	private JFrame frame;
	
	
	String[] stockIndexes = {AAPL, AMZN, FB, GOOG, IBM, INTC, MSFT, NVDA, TWTR, YHOO };
	
	JComboBox comboBox = null;
	
	JLabel infoLabel = null;
	
	JLabel infoClassLabel = null;
	
	JButton classifyButton = null;
	
	JButton saveButton = null;
	
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
		frame.setBounds(100, 100, 500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblChooseIndexOf = new JLabel(INTRO_TEXT);
		GridBagConstraints gbc_lblChooseIndexOf = new GridBagConstraints();
		gbc_lblChooseIndexOf.gridwidth = 21;
		gbc_lblChooseIndexOf.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseIndexOf.gridx = 1;
		gbc_lblChooseIndexOf.gridy = 1;
		frame.getContentPane().add(lblChooseIndexOf, gbc_lblChooseIndexOf);
		
		classifyButton = new JButton(CLASSIFY);
		classifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoLabel.setText(PLEASE_WAIT);
				infoClassLabel.setText("");
				
				classifyButton.setEnabled(false);
				saveButton.setEnabled(false);
				
				String stockIndex = comboBox.getSelectedItem().toString();
				
				Runnable runnable = new Runnable() {

					@Override
					public void run() {
						
						try {
							Runner.StockInfo classification = Runner.classify(stockIndex);
							
							if (classification == null) {
							infoLabel.setText("");
							infoClassLabel.setText(ERROR_OCCURRED);
							
							}
							else {
								infoLabel.setText("Stock is in reality " + (classification.isIncreasing ? " rising " : " faling ") + ", while my classifier got the stock"
										+ " is " + (classification.classified ? "rising." : "falling."));
							
								if (classification.isIncreasing == classification.classified) {
									infoLabel.setText("");
									infoClassLabel.setText(CORRECT_CLASSIFICATION);
									Color color = new Color(0, 204, 0);
									infoClassLabel.setForeground(color);
								}
								else {
									infoLabel.setText("");
									infoClassLabel.setText(WRONG_CLASSIFICATION);
									infoClassLabel.setForeground(Color.RED);
								}
							}
						
						} catch (ParserConfigurationException e1) {
							infoLabel.setText("");
							infoClassLabel.setText(ERROR_OCCURRED);
							e1.printStackTrace();
						} catch (SAXException e1) {
							infoLabel.setText("");
							infoClassLabel.setText(ERROR_OCCURRED);
							e1.printStackTrace();
						} catch (IOException e1) {
							infoLabel.setText("");
							infoClassLabel.setText(ERROR_OCCURRED);
							e1.printStackTrace();
						}
						finally {
							classifyButton.setEnabled(true);
							saveButton.setEnabled(true);
						}
						
						
					}
					
				};
				
				Thread fetchDataThread = new Thread(runnable);
				
				fetchDataThread.start();
				
			
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridheight = 5;
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.insets = new Insets(0, 0, 7, 5);
		gbc_btnNewButton.gridx = 18;
		gbc_btnNewButton.gridy = 2;
		frame.getContentPane().add(classifyButton, gbc_btnNewButton);
		
		saveButton = new JButton(SAVE_DATABASE);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoLabel.setText(PLEASE_WAIT);
				infoClassLabel.setText("");
				
				classifyButton.setEnabled(false);
				saveButton.setEnabled(false);
				
				String stockIndex = comboBox.getSelectedItem().toString();
				
				Runnable runnable = new Runnable() {

					@Override
					public void run() {
						try {
							Runner.saveStuffIntoDatabase(stockIndex);
						} catch (Exception e) {
							infoLabel.setText("");
							infoClassLabel.setText(ERROR_OCCURRED);
							e.printStackTrace();
						}
						finally {
							infoLabel.setText(DATA_SAVED);
							classifyButton.setEnabled(true);
							saveButton.setEnabled(true);	
						}
									
					}
					
				};
				
				Thread thread = new Thread(runnable);
				
				thread.start();
			}
		});
		
		comboBox = new JComboBox(stockIndexes);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 13;
		gbc_comboBox.insets = new Insets(0, 1, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 2;
		frame.getContentPane().add(comboBox, gbc_comboBox);
		
		infoLabel = new JLabel("");
		GridBagConstraints gbc_infoLabel = new GridBagConstraints();
		gbc_infoLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_infoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_infoLabel.gridwidth = 15;
		gbc_infoLabel.gridx = 5;
		gbc_infoLabel.gridy = 10;
		frame.getContentPane().add(infoLabel, gbc_infoLabel);
		saveButton.setBackground(Color.LIGHT_GRAY);
		saveButton.setForeground(Color.RED);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 19;
		gbc_btnNewButton_1.gridy = 7;
		frame.getContentPane().add(saveButton, gbc_btnNewButton_1);
		
		infoClassLabel = new JLabel("");
		infoClassLabel.setFont(new Font("Skia", Font.BOLD, 21));
		GridBagConstraints gbc_infoClassLabel = new GridBagConstraints();
		gbc_infoClassLabel.anchor = GridBagConstraints.WEST;
		gbc_infoClassLabel.insets = new Insets(0, 0, 5, 5);
		gbc_infoClassLabel.gridheight = 3;
		gbc_infoClassLabel.gridwidth = 15;
		gbc_infoClassLabel.gridx = 5;
		gbc_infoClassLabel.gridy = 11;
		frame.getContentPane().add(infoClassLabel, gbc_infoClassLabel);
		
		JButton btnAboutApp = new JButton(ABOUT_APP);
		btnAboutApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, ABOUT_APP_INFO);
			}
		});
		GridBagConstraints gbc_btnAboutApp = new GridBagConstraints();
		gbc_btnAboutApp.insets = new Insets(0, 0, 5, 5);
		gbc_btnAboutApp.gridx = 19;
		gbc_btnAboutApp.gridy = 18;
		frame.getContentPane().add(btnAboutApp, gbc_btnAboutApp);
		
		
	}

}
