package ks.statistics;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;

import ks.common.games.Solitaire;

public class StatsFrame extends JFrame {

	JPanel contentPane;
	JTextField stateSearchField;
	JTextField variationField;
	JTextField numberTrialsField;
	JTextField statusField;
	JTextArea  winningSeedsField;
	JLabel     errorLabel;
	JButton    btnStartTrials;
	
	Solitaire  variation;
	
	/**
	 * Create the frame.
	 */
	public StatsFrame(Solitaire variation) {
		this.variation = variation;
		setTitle("Statistics Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 249);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		btnStartTrials = new JButton("Start Trials");
		
		JLabel lblNewLabel = new JLabel("Size of State Search:");
		
		stateSearchField = new JTextField();
		stateSearchField.setText("500");
		stateSearchField.setToolTipText("Enter an integer representing the number of states to search (500-5000 seems ok)");
		stateSearchField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Variation Class:");
		
		variationField = new JTextField();
		variationField.setColumns(10);
		variationField.setEditable(false);
		if (variation != null) {
			variationField.setText(variation.getClass().getCanonicalName());
		}
		
		JLabel lblNewLabel_2 = new JLabel("Winning Seeds:");
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblNewLabel_3 = new JLabel("Number Of Trials:");
		
		numberTrialsField = new JTextField();
		numberTrialsField.setText("100");
		numberTrialsField.setToolTipText("How many distinct trials to run?");
		numberTrialsField.setColumns(10);
		
		JLabel statusLabel = new JLabel("Status:");
		
		statusField = new JTextField();
		statusField.setEditable(false);
		statusField.setColumns(10);
		
		errorLabel = new JLabel("    ");
		errorLabel.setForeground(Color.RED);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel_3)
								.addComponent(statusLabel))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(statusField)
								.addComponent(numberTrialsField)
								.addComponent(variationField)
								.addComponent(stateSearchField, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
								.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnStartTrials)
							.addGap(18)
							.addComponent(errorLabel, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(stateSearchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1)
								.addComponent(variationField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_3)
								.addComponent(numberTrialsField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(statusLabel)
								.addComponent(statusField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(scrollPane))
					.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStartTrials)
						.addComponent(errorLabel))
					.addContainerGap())
		);
		
		winningSeedsField = new JTextArea("");
		winningSeedsField.setEditable(false);
		scrollPane.setViewportView(winningSeedsField);
		contentPane.setLayout(gl_contentPane);
		
		btnStartTrials.addActionListener(new StartTrialController(this));
	}
	
	public JTextField getStateSearchField () { return stateSearchField; }
	public JTextField getVariationField () { return variationField; }
	public JTextField getNumberTrialsField () { return numberTrialsField; }
	public JTextField getStatusField () { return statusField; }
	public JLabel     getErrorLabel () { return errorLabel; }
	public JTextArea  getWinningSeeds () { return winningSeedsField; }
	public JButton    getStartButton() { return btnStartTrials; }
	
	public Solitaire  getVariation () { return variation; }
	
	
}
