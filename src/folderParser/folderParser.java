/******************************************************************************
 *  Compilation:  javac PageCounter.java
 *  Execution:   java PageCounter
 *  Dependencies:  none
 *  Prompted user input:  file path to  denote the target file/directory
 *  Function:   The program counts the number of each type of files at the path and the numbers of pages of pdf/Word files.
 *
 *  Remarks - external libraries
 *  -------
 *  Open source java library Apache PDFBox 2.0.22 is used to handle PDF files.
 * https://pdfbox.apache.org/
 *  Open source java library Apache  POI 5.0.0  is used to handle Windows documents, in this case, word files.
 * https://poi.apache.org/download.html#POI-5.0.0
 ******************************************************************************/
package folderParser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class folderParser {
	protected StringBuffer sb;
    protected int docPages;
    protected int pdfPages;
    private Queue<File> fileList;
    protected static HashMap<String, Integer> hm;

    // constructor
    protected folderParser(File f) {
        docPages = 0;
        pdfPages = 0;
        fileList = new LinkedList<File>();
        hm = new HashMap<String, Integer>();
        fileList.add(f);
    }

    protected void countFiles() {
        // Breadth first search.
        // when the queue fileList is not empty, pop out the first item to process.
        // If the first item is a file, count pages.
        // If it is a directory, add its children items to the queue.
    	while (fileList.peek() != null) {
            File f = fileList.remove();

            if (f.isFile()) {
                String filePath = f.getPath();
                String fileExtension = filePath.substring(filePath.lastIndexOf('.') + 1);
                updateTally(fileExtension);

                if (f.isHidden()) {
                    System.out.println("Attention: file " + f.getName() + " is hidden, file is being processed.");
                }

                if (fileExtension.equals("pdf")) {
                    countPdfPages(f);
                } else if (fileExtension.equals("docx") || fileExtension.equals("doc")) {
                    countWordPages(f);

                }
                //} else if (fileName.endsWith(".odt")) {
                // OdfDocument odt = (OdfDocument) OdfDocument.loadDocument("filePath");
                // docFiles = docFiles + 1;
                // docPages = docPages + odt.getOfficeMetadata()
                //}

            } else if (f.isDirectory()) {
                // For test
                // System.out.println(f.getName() + " is a directory. ");

                // Tracking the number of layers
                updateTally("directory");

                File[] children = f.listFiles(); // if the directory is empty, the return array will be empty array.
                if (children != null) {
                    for (File child : children) {
                        fileList.add(child);
                    }
                }
            }
        }
    }
    
    private void updateTally(String fileExtension) {
        // For new file type, register in the hash map
        if (!hm.containsKey(fileExtension)) {
            hm.put(fileExtension, 0);
        }
        // Whether new or not, add 1 to the corresponding tracking number
        hm.put(fileExtension, hm.get(fileExtension) + 1);
    }

    private void countPdfPages(File f) {
        try {
            PDDocument pdf = PDDocument.load(f);
            int numberOfPages = pdf.getNumberOfPages();
            //System.out.println("File " + f.getName() + " has " + numberOfPages + " pages.");
            pdfPages = pdfPages + numberOfPages;
            pdf.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not load file : " + f.getName() + " , number and pages not counted.");
        }
    }

    private void countWordPages(File f) {
    	String fileName = f.getName();
        int numberOfPages = 0;
        try {
            if (f.getName().endsWith(".docx")) {
                XWPFDocument docx = new XWPFDocument(POIXMLDocument.openPackage(f.getPath()));
                numberOfPages = docx.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
            } else if (f.getName().endsWith(".doc")) {
                HWPFDocument wordDoc = new HWPFDocument(new FileInputStream(f.getPath()));
                numberOfPages = wordDoc.getSummaryInformation().getPageCount();
            }
            //System.out.println("File " + fileName + " has " + numberOfPages + " pages.");
            docPages = docPages + numberOfPages;
            
        } catch (IOException e) {
            sb.append("Failed to read file : " + fileName);
        }
    }
    
    protected void printSummary() {
    	    sb = new StringBuffer("The file/folder contains : ");
            sb.append(System.lineSeparator());
            Iterator it = hm.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                if (!pair.getKey().equals("directory")) {
                    sb.append(pair.getValue() + " " + pair.getKey() + " files.");
                    sb.append(System.lineSeparator());
                    it.remove(); // avoids a ConcurrentModificationException
                }
            }
            sb.append("The depth of the path is " + hm.get("directory") + " layer(s).");
            sb.append(System.lineSeparator());
            sb.append("Totally " + docPages + " pages of Word and " + pdfPages + " pages of PDF to read.");
            sb.append(System.lineSeparator());
            OutputFrame outputFrame = new OutputFrame(sb);
    }
}
