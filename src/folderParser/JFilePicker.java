package folderParser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Font;
 
public class JFilePicker extends JPanel {
     
    private JLabel instruction;
	private JLabel label;
    private JTextField textField;
    private String textFieldLabel;
    private JButton button_browseButton;
    private String buttonLabel_browse;
    private JButton button_parseButton;
     
    private JFileChooser fileChooser;
     
    private int mode;
    public static final int MODE_OPEN = 1;
    public static final int MODE_SAVE = 2;
    
    public JFilePicker(String textFieldLabel, String buttonLabel) {
    	this.textFieldLabel = textFieldLabel;
        this.buttonLabel_browse = buttonLabel;
         
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
 
        // creates the GUI
        instruction = new JLabel("Please choose a folder to parse.");
        instruction.setFont(new Font("Calibri", Font.BOLD, 16));
        instruction.setBounds(10, 10, 431, 15);
        
        label = new JLabel(textFieldLabel);
        label.setBounds(10, 42, 73, 20);
        label.setFont(new Font("Calibri", Font.BOLD, 16));
         
        textField = new JTextField(30);
        textField.setBounds(88, 42, 276, 21);
        
        button_browseButton = new JButton(buttonLabel_browse);
        button_browseButton.setBounds(369, 38, 105, 29);
        button_browseButton.setFont(new Font("Calibri", Font.BOLD, 16));
         
        button_browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                browseButtonActionPerformed(evt);            
            }
        });      
        
        button_parseButton = new JButton("Parser!");
        button_parseButton.setFont(new Font("Calibri", Font.BOLD, 16));
        button_parseButton.setBounds(369, 90, 105, 29);
                
        button_parseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parseButtonActionPerformed(e);            
            }
        });             
        
        setLayout(null);
        add(instruction); 
        add(label);
        add(textField);
        add(button_browseButton);
        add(button_parseButton);    
    }
     
    private void browseButtonActionPerformed(ActionEvent evt) {
        if (mode == MODE_OPEN) {
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        } else if (mode == MODE_SAVE) {
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
    }
    
    private void parseButtonActionPerformed(ActionEvent e) {
    	File file = new File(getSelectedFilePath());
    		folderParser parser = new folderParser(file);
    		parser.countFiles();
    		parser.printSummary();
    }
      
    public void setMode(int mode) {
        this.mode = mode;
    }
     
    public String getSelectedFilePath() {
        return textField.getText();
    }
     
    public JFileChooser getFileChooser() {
        return this.fileChooser;
    }
}