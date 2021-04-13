/**
*Edward Andino Sierra
*Network Scanner
*Start Date: 4/12/2021
*Version 2.0
*/

/*
*imports for the gui
*/
import java.awt.*;        // Using AWT containers and components
import java.awt.event.*;  // Using AWT events classes and listener interfaces
import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

/*
*imports for methods
*/
import java.text.ParseException;
import java.io.*;
import java.net.*;
import java.util.*;

// An GUI program inherits the top-level container java.awt.Frame
public class GUIScanner extends JFrame{
   Image img = Toolkit.getDefaultToolkit().getImage("E:\\cw.jpg");
   private JFrame scannerFrame; //Main frame
   private JButton btnWelcome;    // Declare a Button component
   private JButton btnInfo;    // Declare a Button component
   private JButton btnDevices;    // Declare a Button component
   private JButton btnPort;    // Declare a Button component
   private JButton btnShodan;    // Declare a Button component
   private JButton btnRouter;    // Declare a Button component
   private JButton btnTips;    // Declare a Button component
   private JTextArea toDisplay; // Multi-line TextArea to taDisplay result
   private JScrollPane scpText;  // Scroll pane for text area

   // Constructor to setup the GUI components and event handlers
   public GUIScanner() {
      
      scannerFrame = new JFrame("Network Scanner"); //create main frame
      scannerFrame.setContentPane(new JLabel(new ImageIcon("E:\\pic2.jpg"))); //background image
      scannerFrame.getContentPane().setLayout(new FlowLayout()); // Frame sets to FlowLayout
      scannerFrame.add(new JLabel("Select: "));   // Frame adds an anonymous Label

      /* text file to enter info as input
      tfCount = new TextField("0", 10); // Construct the TextField
      tfCount.setEditable(false);       // read-only
      add(tfCount);                     // "super" Frame adds TextField
      */

      btnInfo = new JButton("Network Information");  // Construct the Button
      scannerFrame.add(btnInfo);                   // Frame adds Button

      btnDevices = new JButton("Connected Devices");  // Construct the Button
      scannerFrame.add(btnDevices);                   // Frame adds Button
      
      btnPort = new JButton("Open Ports");  // Construct the Button
      scannerFrame.add(btnPort);                   // Frame adds Button
      
      btnShodan = new JButton("Use Shodan");  // Construct the Button
      scannerFrame.add(btnShodan);                   // Frame adds Button
      
      btnRouter = new JButton("Router Information");  // Construct the Button
      scannerFrame.add(btnRouter);                   // "Frame adds Button
      
      btnTips = new JButton("Tips to Protect Network");  // Construct the Button
      scannerFrame.add(btnTips);                   // Frame adds Button
      
      toDisplay = new JTextArea(50, 50);
      toDisplay.setLineWrap(true);  //allow line swap in the text area
      scpText = new JScrollPane(toDisplay);  //create a scroll panel from the text are
      scpText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //add the scroll bar
      scannerFrame.add(scpText); //add the scroll panel in the frame

      // Just for refresh. Do not use.
      setSize(399,399);
      setSize(400,400);

      /*
      *btn* (source object) fires ActionEvent upon clicking
      *btn* adds an anonymous instance of Btn*Listener
      *as an ActionEvent listener
      */
      btnInfo.addActionListener(new BtnInfoListener());
      btnDevices.addActionListener(new BtnDevicesListener());
      btnPort.addActionListener(new BtnPortsListener());
      btnShodan.addActionListener(new BtnShodanListener());
      btnRouter.addActionListener(new BtnRouterListener());
      btnTips.addActionListener(new BtnTipsListener());

      /**
      *Frame (source object) fires WindowEvent.
      *Frame adds an anonymous instance of MyWindowListener
      *as a WindowEvent listener.
      */
      scannerFrame.addWindowListener(new MyWindowListener());
           
      /**
      *Frame Adjusts frame to size of components
      *Frame sets title
      *Frame sets initial size
      *Frame shows
      */
      scannerFrame.pack();
      scannerFrame.setTitle("Network Scanner");  
      scannerFrame.setSize(700, 950);     
      scannerFrame.setVisible(true);   
   }

   // The entry main() method
   public static void main(String[] args) {
      new GUIScanner();
   }

   /*
   //add ipv6 + temp + local
   //subnet
   //gateway
   */
   public static String Ipconfig(){
      StringBuilder sbuf = new StringBuilder();
      InetAddress ip;
      String hostname;
      // Inet6Address ipv6; *add a ipv6 to get the ipv6 address
    try {
         ip = InetAddress.getLocalHost();
         hostname = ip.getHostName();

         //NetworkInterface network = NetworkInterface.getByInetAddress(ip);
         NetworkInterface nif = NetworkInterface.getByName("localhost");
         //InetAddress[] inet = InetAddress.getAllByName(ip.getHostName());
         //String address = getAddress(inet).getHostAddress();

         sbuf.append("Default gateway: "+ip.getLocalHost());
         //fmt.format("MAc address:" + nif.getHardwareAddress());
         sbuf.append("\nCurrent Name : " + ip.getHostName());
         sbuf.append("\nCurrent IP address : " + ip.getHostAddress());
         //System.out.println("Current IPv6 address : "+ getByAddress(hostname,inet,nets));// + ipv6.getHostAddress());
         //System.out.println("Current IPv6 Temp address : ");// + ipv6.getHostAddress());
         sbuf.append("\nCurrent IPv6 Local : ");// + ipv6.getHostAddress());
        
      } catch(Throwable se){
         se.printStackTrace();
      }
        return sbuf.toString();
   }//end Ipconfig method

   /**
   *execute the arp -a command using the getCommmand function
   */
   public static String DeviceConnected(){
      StringBuilder outputDC = new StringBuilder();
      String cmd = "route print";
      try{
      outputDC.append(getCommand(cmd));
      }
      catch(Throwable se){
         se.printStackTrace();
      }
      return outputDC.toString();
   }//end method Devices Connected

   /*
   *executes a windows commmand
   */
   public static String getCommand(String cmd) throws IOException {
           Scanner s = new Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
                return s.hasNext() ? s.next() : "";
    }//end getCommand method

   /*
   *execute the shodan command using the getcommmand method
   */
   public static String UsingShodan(){
      StringBuilder outputShodan = new StringBuilder();
      String cmd = "shodan host 193.210.227.108";
      try{
      outputShodan.append(getCommand(cmd));
      }
      catch(Throwable se){
         se.printStackTrace();
      }
      return outputShodan.toString();
   }//end UsingShoda Method

    /*
   *an array with commonly abussed ports
   */
   public static int[] commonPortsList(){
      int [] portsArray = {20,21,22,23,25,53,80,139,443,445,1433,1434,3306,3389,8080};
      return portsArray;
   }

   /*
   *name of port list
   */
   public static String[] commonPortsListNames(){
   String[] portNames = {
         "File Transfer Protocol (FTP) Data Transfer"
         ,"File Transfer Protocol (FTP) Command Control"
         ,"Secure Shell (SSH) Secure Login"
         ,"Telnet remote login service, unencrypted text messages"
         ,"Simple Mail Transfer Protocol (SMTP) E-mail routing"
         ,"Domain Name System (DNS) service"
         ,"Hypertext Transfer Protocol (HTTP) used in World Wide Web"
         ,"Protocol standard for a NetBIOS service on a TCP/UDP transport: Concepts and methods"
         ,"HTTP Secure (HTTPS) HTTP over TLS/SSL"
         ,"MS Networking access"
         ,"Microsoft SQL Server database management system (MSSQL) server"
         ,"Microsoft SQL Server database management system (MSSQL) monitor"
         ,"MySQL database system"
         ,"Microsoft Terminal Server (RDP) officially registered as Windows Based Terminal (WBT)"
         ," Alternative port for HTTP"};
         return portNames;
   }

   /*
   *known vulnerabilities for such ports
   */
   public static String[] portListVul(){
      String [] infoVuln = {""}; 
      return infoVuln;
   }

   public static String Ports(){
      StringBuilder outputPorts = new StringBuilder();
      System.out.println("Scanning ports... Do Not close the program.");
         int once = 1;
            int [] lp = commonPortsList();
            String [] lpn = commonPortsListNames();
            for(int i=0; i<lp.length ;i++){
               outputPorts.append("\n"+lpn[i]+"\n");            
               Socket s = null;
               try {
                  s = new Socket("localhost", lp[i]);
                  // If the code makes it this far without an exception it means
                  // something is using the port and has responded.
                  outputPorts.append("*Port--- " + lp[i] + " is not available\n");
                  } catch (IOException e) {
                     outputPorts.append("Port--- " + lp[i] + " is available\n"); 
                  } 
                  finally {
                     if( s != null){
                        try {
                              s.close();
                     } 
                     catch (IOException e) {
                           throw new RuntimeException("You should handle this error." , e);
                        }
                     }
                  }//end finally
            }//end for
      return outputPorts.toString();
   }//end ports method

   /*
   *checks every port if it is open or not
   */

   // Define an inner class to handle ActionEvent of btnCount
   private class BtnInfoListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent evt) {
         toDisplay.setText(""); //clear text area
         toDisplay.append(Ipconfig()); //invoke method and print
      }
   }

   // Define an inner class to handle ActionEvent of btnCount
   private class BtnDevicesListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent evt) {
         toDisplay.setText("");
         toDisplay.append(DeviceConnected());
      }
   }

   // Define an inner class to handle ActionEvent of btnCount
   private class BtnPortsListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent evt) {
         toDisplay.setText("");
         toDisplay.append(Ports());
      }
   }

   // Define an inner class to handle ActionEvent of btnCount
   private class BtnShodanListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent evt) {
         toDisplay.setText("");
         toDisplay.append(UsingShodan());
      }
   }

   // Define an inner class to handle ActionEvent of btnCount
   private class BtnRouterListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent evt) {
         toDisplay.setText("");
         toDisplay.append("router");
      }
   }

   // Define an inner class to handle ActionEvent of btnCount
   private class BtnTipsListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent evt) {
         toDisplay.setText("");
         toDisplay.append("tips");
      }
   }

   /**
   * Define an inner class to handle WindowEvent of this Frame
   */
   private class MyWindowListener implements WindowListener {
      // Called back upon clicking close-window button
      @Override
      public void windowClosing(WindowEvent evt) {
         System.exit(0);  // Terminate the program
      }

      // Not Used, BUT need to provide an empty body to compile.
      @Override public void windowOpened(WindowEvent evt) { }
      @Override public void windowClosed(WindowEvent evt) { }
      
      // For Debugging
      @Override public void windowIconified(WindowEvent evt) { System.out.println("Window Iconified"); }
      @Override public void windowDeiconified(WindowEvent evt) { System.out.println("Window Deiconified"); }
      @Override public void windowActivated(WindowEvent evt) { System.out.println("Window Activated"); }
      @Override public void windowDeactivated(WindowEvent evt) { System.out.println("Window Deactivated"); }
   }
}