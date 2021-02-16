package folderParser;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;

public class OutputFrame extends JFrame {

	private JPanel contentPane;
	private JButton button_okButton;

	/**
	 * Create the frame.
	 */
	public OutputFrame(StringBuffer sb) {
		
		this.setTitle("Parsing Result");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 453, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JTextArea textArea = new JTextArea();
		textArea.setText(sb.toString());
		JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
		scrollPane.setSize(421, 190);
		scrollPane.setLocation(10, 10);
		getContentPane().add(scrollPane);
			
		button_okButton = new JButton("OK");
		button_okButton.setFont(new Font("Calibri", Font.BOLD, 16));
		button_okButton.setBounds(326, 225, 105, 29);
		button_okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                okButtonActionPerformed(event);            
            }
        });
			
		contentPane.add(button_okButton);
		setVisible(true);
	}
	
	private void okButtonActionPerformed(ActionEvent event) {
    	this.dispose();
    }

}
