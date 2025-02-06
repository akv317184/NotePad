/* This is a classic notepad application program created in Java which can be used like any other text editor  */ 
package maccess;

import java.io.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.*;
public class Notepad extends Frame
{
    Clipboard cBoard=getToolkit().getSystemClipboard(); // graphical interface of the notepad
    TextArea tArea=new TextArea();   // textarea of the notepad
    String fName; // text file name
    Notepad()    // default constructor
    {
        gaListener gListen=new gaListener();
        addWindowListener(gListen);
        add(tArea);
        MenuBar mBar=new MenuBar();     // creating the menu bar
        Menu fileMenu=new Menu("File"); // creating "File" option for menu bar
       
        MenuItem nOption=new MenuItem("New"); // initializing elements under "File" option
        MenuItem oOption=new MenuItem("Open");
        MenuItem sOption=new MenuItem("Save");
        MenuItem cOption=new MenuItem("Close");
       
        nOption.addActionListener(new Ne_option());   // adding the elements under "File" option
        fileMenu.add(nOption);
        oOption.addActionListener(new Ope_option());
        fileMenu.add(oOption);
        sOption.addActionListener(new Sav_option());
        fileMenu.add(sOption);
        cOption.addActionListener(new Clos_option());
        fileMenu.add(cOption);
        mBar.add(fileMenu);         // adding the "File" menu into the menu bar
       
        Menu editMenu=new Menu("Edit");         // creating the "Edit" option for menu bar
        MenuItem cutOption=new MenuItem("Cut"); // initializing elements under "Edit" option
        MenuItem copyOption=new MenuItem("Copy");
        MenuItem pasteOption=new MenuItem("Paste");
       
        cutOption.addActionListener(new Cu_option());// adding the elements under "Edit" option
        editMenu.add(cutOption);
        copyOption.addActionListener(new Cop_option());
        editMenu.add(copyOption);
        pasteOption.addActionListener(new Past_option());
        editMenu.add(pasteOption);
        mBar.add(editMenu);         // adding the "Edit" menu into menu bar
        setMenuBar(mBar);           // finalizing the menu bar
       
        setTitle("NOTEPAD IN JAVA");      // title displayed on top of the notepad
    }
   
    class gaListener extends WindowAdapter
    {
        public void windowClosing(WindowEvent closeNotepad)
        {
            System.exit(0);  // function for closing the notepad
        }
    }
   
    class Ne_option implements ActionListener  // class for "New"
    {
        public void actionPerformed(ActionEvent ne)
        {
            tArea.setText(" ");   // the notepad becomes empty when user click on "New"
        }
    }
   
    class Ope_option implements ActionListener  // class for "Open"
    {
        public void actionPerformed (ActionEvent ope)
        {
            FileDialog fDialog=new FileDialog(Notepad.this,"Select a text file",FileDialog.LOAD);
            fDialog.show(); // a message "Select a text file" is displayed when user clicks on "Open"
            if(fDialog.getFile()!=null)  // if text files exist in the directory
            {
                fName=fDialog.getDirectory()+fDialog.getFile(); // retrieving the text file from the directory
                setTitle(fName);  // title changes to name of the opened text file(if found)
                ReadFile();
            }
            tArea.requestFocus();
        }
    }
   
    class Clos_option implements ActionListener     //class for "Close"
    {
        public void actionPerformed(ActionEvent close_o)
        {
            System.exit(0);
        }
    }
   
    class Sav_option implements ActionListener
    {
        public void actionPerformed(ActionEvent sav_o)
        {
            FileDialog fDialog=new FileDialog(Notepad.this,"Save the text file with .txt extension",FileDialog.SAVE);
            fDialog.show();     // a message "Save the text file with .txt extension" is displayed when user clicks on "Save"
            if(fDialog.getFile()!=null)
            {
                fName=fDialog.getDirectory()+fDialog.getFile();
                setTitle(fName); // title changes to saved filename's
                try
                {
                    DataOutputStream dOutStream=new DataOutputStream(new FileOutputStream(fName));
                    String oLine=tArea.getText();
                    BufferedReader bReader=new BufferedReader(new StringReader(oLine));
                    while((oLine=bReader.readLine())!=null);
                    {
                        dOutStream.writeBytes(oLine+"\r\n");
                        dOutStream.close();
                    }
                }
                catch(Exception e)
                {
                        System.out.println("Required file not found");
                }
                tArea.requestFocus();
            }
        }
    }
   
    void ReadFile()  // function for reading the file
    {
        BufferedReader br;
        StringBuffer sBuffer=new StringBuffer();
        try
        {
            br=new BufferedReader(new FileReader(fName));
            String oLine;
            while((oLine=br.readLine())!=null)
                sBuffer.append(oLine+"\n");
                tArea.setText(sBuffer.toString());
                br.close();
        }
        catch(FileNotFoundException fe)
        {
            System.out.println("Required file not found");
        }
        catch(IOException e){}
    }
   
    class Cu_option implements ActionListener   // class for "Cut" option
    {
        public void actionPerformed(ActionEvent cut_o)
        {
            String sText=tArea.getSelectedText();
            StringSelection sSelection=new StringSelection(sText);
            cBoard.setContents(sSelection,sSelection);
            tArea.replaceRange(" ",tArea.getSelectionStart(),tArea.getSelectionEnd());  // highlighted text relaced by blank space
        }
    }
   
    class Cop_option implements ActionListener  // class for "Copy" option
    {
        public void actionPerformed(ActionEvent copy_o)
        {
            String sText=tArea.getSelectedText();
            StringSelection cString=new StringSelection(sText);
            cBoard.setContents(cString,cString);
        }
    }
   
    class Past_option implements ActionListener  // class for "Paste" option
    {
        public void actionPerformed(ActionEvent paste_o)
        {
            Transferable ctransfer=cBoard.getContents(Notepad.this);
            try
            {
                String sText=(String)ctransfer.getTransferData(DataFlavor.stringFlavor);
                tArea.replaceRange(sText,tArea.getSelectionStart(),tArea.getSelectionEnd());
            }
            catch(Exception e)
            {
                System.out.println("Not a string flavor");
            }
        }
    }
   
    public static void main(String args[])
    {
        Frame nFrame=new Notepad(); // creating object of the notepad
        nFrame.setSize(600,600);  // height=600 , width = 600
        nFrame.setVisible(true);
    }
}