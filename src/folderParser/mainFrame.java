package folderParser;
 
import java.io.File;
 
import javax.swing.JFileChooser;
import javax.swing.JFrame;
 
public class mainFrame extends JFrame {

	public mainFrame() {
        super("Folder Parser");
                
        // set up a file picker component
        JFilePicker filePicker = new JFilePicker("Pick a file", "Browse...");
        filePicker.setMode(JFilePicker.MODE_SAVE);
        
        // access JFileChooser class directly
        JFileChooser fileChooser = filePicker.getFileChooser();
        fileChooser.setCurrentDirectory(new File("D:/"));
        
        // add the component to the frame
        getContentPane().add(filePicker);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(522, 171);
        setLocationRelativeTo(null);    // center on screen
        setVisible(true);
    }
	
	public static void main(String[] args) {
		mainFrame mainFrame = new mainFrame();
	}
}
