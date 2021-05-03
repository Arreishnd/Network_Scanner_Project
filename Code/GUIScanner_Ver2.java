/**
*Edward Andino Sierra
*Network Scanner - NS21
*Start Date: 05/03/2021
*Version 2.1
*java -Dswing.defaultlaf=javax.swing.plaf.nimbus.NimbusLookAndFeel GUIScanner | For a modern look using Numbus LOOK&FEEL
*/

/*--------------------------------------------------------------------
*Imports
*/
import java.awt.*;        // Using AWT containers and components
import java.awt.event.*;  // Using AWT events classes and listener interfaces
import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.text.ParseException;
import java.io.*;
import java.net.*;
import java.util.*;

/*--------------------------------------------------------------------
*Graphic User Interface 
*/
// An GUI program inherits the top-level container java.awt.Frame
public class GUIScanner_Ver2 extends JFrame{
   Image img = Toolkit.getDefaultToolkit().getImage("E:\\cw.jpg"); // backgroung picture frame
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
   private JTextField jfield;

   // Constructor to setup the GUI components and event handlers
   public GUIScanner_Ver2() {
      
      scannerFrame = new JFrame("Network Scanner"); //create main frame
      scannerFrame.setContentPane(new JLabel(new ImageIcon("E:\\pic2.jpg"))); //background image
      scannerFrame.getContentPane().setLayout(new FlowLayout()); // Frame sets to FlowLayout
      scannerFrame.add(new JLabel("Select: "));   // Frame adds an anonymous Label

      /* text file to enter info as input
      tfCount = new TextField("0", 10); // Construct the TextField
      tfCount.setEditable(false);       // read-only
      add(tfCount);                     // "super" Frame adds TextField
      */
      btnWelcome = new JButton("Welcome");  // Construct the Button
      scannerFrame.add(btnWelcome);              // Frame adds Button

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
      
      btnTips = new JButton("Tips to Protect the Network");  // Construct the Button
      scannerFrame.add(btnTips);                   // Frame adds Button


      jfield = new JTextField(18);
      scannerFrame.add(jfield);

      toDisplay = new JTextArea(50, 50);
      toDisplay.setEditable(false);
      toDisplay.setLineWrap(true);  //allow line swap in the text area
      scpText = new JScrollPane(toDisplay);  //create a scroll panel from the text are
      scpText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //add the scroll bar
      scannerFrame.add(scpText); //add the scroll panel in the frame
      toDisplay.append(intro());

      // Just for refresh. Do not use.
      setSize(399,399);
      setSize(400,400);

      /*
      *btn* (source object) fires ActionEvent upon clicking
      *btn* adds an anonymous instance of Btn*Listener
      *as an ActionEvent listener
      */
      btnWelcome.addActionListener(new BtnWelcomeListener());
      btnInfo.addActionListener(new BtnInfoListener());
      btnDevices.addActionListener(new BtnDevicesListener());
      btnPort.addActionListener(new BtnPortsListener());
      btnShodan.addActionListener(new BtnShodanListener());
      btnRouter.addActionListener(new BtnRouterListener());
      btnTips.addActionListener(new BtnTipsListener());

      /* "tfInput" is the source object that fires an ActionEvent upon entered.
      * The source add an anonymous instance of TFInputListener as an ActionEvent
      *   listener, which provides an ActionEvent handler called actionPerformed().
      * Hitting "enter" on tfInput invokes actionPerformed().
      */
      jfield.addActionListener(new TFInputListener());
         
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
      scannerFrame.setTitle("NS21 - Network Scanner");  
      scannerFrame.setSize(700, 950);     
      scannerFrame.setVisible(true);  

   }

/*--------------------------------------------------------------------
*main
*/
   // The entry main() method
   public static void main(String[] args) {
      try {
            // Set System L&F
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } 
    catch (UnsupportedLookAndFeelException e) {
       // handle exception
    }
    catch (ClassNotFoundException e) {
       // handle exception
    }
    catch (InstantiationException e) {
       // handle exception
    }
    catch (IllegalAccessException e) {
       // handle exception
    }
        
    new GUIScanner_Ver2(); //Create and show the GUI.
   }

/*--------------------------------------------------------------------
*methods for each bottom
*/
   /*
   */
   public static String Ipconfig(){
      StringBuilder sbuf = new StringBuilder();
      InetAddress ip;
      String hostname;

      String systemIpAddress = "";
      String space = "\n";
      // Inet6Address ipv6; *add a ipv6 to get the ipv6 address
    try {
         ip = InetAddress.getLocalHost();
         hostname = ip.getCanonicalHostName();
         byte [] ipv6Address = getIPV6Address(hostname);

         NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ip);
         byte[] hardwareAddress = networkInterface.getHardwareAddress();
         
         String macAddress = getMacAddress(hardwareAddress);
         
         Inet6Address ipv6 = Inet6Address.getByAddress(hostname, ipv6Address, networkInterface); 
         
         URL url_name = new URL("http://bot.whatismyipaddress.com"); //go to whatismyipaddress.com and get the public ip
  
         BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));
  
         // reads system IPAddress
         systemIpAddress = sc.readLine().trim();
         
         sbuf.append("Device Name : " + ip.getHostName()).append(space);
         sbuf.append("\nAdapter Name : "+networkInterface.getDisplayName()).append(space);
         sbuf.append("\nMac Address : "+macAddress).append(space);
         sbuf.append("\nLocal IPv4 address : " + ip.getHostAddress()).append(space);
         sbuf.append("\nPublic IPv4 Address: " + systemIpAddress).append(space);         
         sbuf.append("\nCurrent IPv6 Local : "+ipv6.getHostAddress()).append(space);// + ipv6.getHostAddress());
         sbuf.append("\nCurrent gateway address : "+getGateway()).append(space);
         sbuf.append(space);
         sbuf.append("What is a mac address?\n -A mac address is a unique identifier assigned to a network interface controller.\n"
                     +"\nWhat is a local ip address?\n -A local ip address is used inside a private network to locate the computers and devices connected\n  to it. If your computer is connected to a router with default settings, that router will automatically\n  assign a local IP address to your computer.\n"
                     +"\nWhat is a public ip address?\n -A public IP address is used across the entire Internet to locate computer systems and devices.\n"
                     +"\nWhat is an ipv6 address?\n -Ipv6 address is a numeric label that is used to identify and locate a network interface of a computer\n  or a network node participating in an computer network using IPv6.\n"
                     +"\nWhat is a gateway address?\n -The gateway address is a router interface connected to the local network\n");
      } catch(Throwable se){
         se.printStackTrace();
      }
      return sbuf.toString();
   }//end Ipconfig method

   /*
   *get the mac address/physical address using an array of bytes form a ip address 
   */
   public static String getMacAddress(byte[] hardwareAddress){
      String[] hexadecimal = new String[hardwareAddress.length];
      for (int i = 0; i < hardwareAddress.length; i++) {
          hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
      }
      String macAddress = String.join("-", hexadecimal);
      return macAddress;
   }

   /*
   *returns the ipv6
   */
   private static byte[] getIPV6Address(String hostName) throws Exception {
      InetAddress[] ipv4 = null;
      try {
         ipv4 = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
      } catch (UnknownHostException e) {
         e.printStackTrace();
         throw e;
      }
      byte[] ipv6 = null;
      for (InetAddress inetAddress : ipv4) {
         if (inetAddress instanceof Inet6Address) {
            if (inetAddress.getHostName().equals(hostName)) {
               ipv6 = inetAddress.getAddress();
               break;
            }
         }
      }
      return ipv6;
   }//end getIpv6address

   /**
   *execute the route print command from the shell
   */
   public static String DeviceConnected(){
      StringBuilder outputDC = new StringBuilder();
      String cmd = "arp -a";
      try{
      outputDC.append(getCommand(cmd)+"\n");
      }
      catch(Throwable se){
         se.printStackTrace();
      }

      outputDC.append("\nIntenet address starting with 192::: are the devices in the network such as:"
                     +" -Smartphones\n"
                     +" -Tablets or Ipads\n"
                     +" -SmartTVs\n"
                     +" -additional Laptops and desktops\n"
                     +" -Any other devices that are connected to the netwrok\n"
                     +"\n -Subnet address between 224::: to 255::: are addresses for the router to broadcast and receive.\n");
      return outputDC.toString();
   }//end method Devices Connected

   /*
   *executes a windows commmand
   */
   public static String getCommand(String cmd) throws IOException {
      Scanner s = new Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
      return s.hasNext() ? s.next() : "";
   }//end getCommand method

   public static String getGateway(){
      StringBuilder outputGateway = new StringBuilder();
      String gatewayString = "Default Gateway";
      try {
            Process process = Runtime.getRuntime().exec("ipconfig");
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
               String line;
               while ((line = bufferedReader.readLine()) != null) {
                  if (line.trim().startsWith(gatewayString)) {
                     String ipAddress = line.substring(line.indexOf(":") + 1).trim();
                     outputGateway.append(ipAddress);
                  }
               }
            }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return outputGateway.toString();
   }//end getGateway Method

   /*
   *execute the shodan command using the getcommmand method
   */
   public static String UsingShodan(String cmd){
      StringBuilder outputShodan = new StringBuilder();
      //String cmd = "shodan count port:443 louisiana "; // get string from textfield
      try{
      outputShodan.append(getCommand(cmd));
      }
      catch(Throwable se){
         se.printStackTrace();
      }
      return outputShodan.toString();
   }//end UsingShoda Method

   public static String UsingShodanDisplay(){
      StringBuilder outputShodanDisplay = new StringBuilder();
      outputShodanDisplay.append("How to use shodan: \n -Use the keyword 'shodan' following with the parameters\n"
                                 +" -Use -b to use the browser or go to specific ip address.\n"
                                 +" -You can specified the port to use by using ':' + port number -> www.google.com:443\n"+"\n");
      outputShodanDisplay.append("Example: shodan stats --facets org Country:US --limit 5\n");
      outputShodanDisplay.append("         shodan stats --facets org product:apache --limit 5\n");
      outputShodanDisplay.append("         shodan stats --facets Device:webcam Country:US --limit 5\n");
      outputShodanDisplay.append("         shodan search --fields ip_str,port,org,hostnames,location:US webcamxp --limit 20\n");
      outputShodanDisplay.append("         webcam open: 81.182.214.37:8080 or 51.148.163.13:8080\n"+"\n");
      return outputShodanDisplay.toString();
      //Source: https://null-byte.wonderhowto.com/how-to/find-vulnerable-webcams-across-globe-using-shodan-0154830/
   }
    /*
   *an array with commonly abused ports
   */
   public static int[] commonPortsList(){
      int [] portsArray = {20,21,22,23,25,53,80,139,443,445,1433,1434,3306,3389,8080};
      return portsArray;
   }

   /*
   *a list of common ports with their vulnerabilities  
   */
   public static String[] commonPortsListNames(){
   String[] portNames = {
         "File Transfer Protocol (FTP) Data Transfer\nAn outdated and insecure protocol, which utilize no encryption for both data transfer and authentication."
         ,"File Transfer Protocol (FTP) Command Control\nAn outdated and insecure protocol, which utilize no encryption for both data transfer and authentication."
         ,"Secure Shell (SSH) Secure Login\nTypically, it is used for remote management. While it is generally considered secure, it requires proper key management."
         ,"Telnet remote login service, unencrypted text messages\nA predecessor to SSH, is no longer considered secure and is frequently abused by malware."
         ,"Simple Mail Transfer Protocol (SMTP) E-mail routing\nIf not properly secured, it can be abused for spam e-mail distribution."
         ,"Domain Name System (DNS) service\nVery often used for amplification DDoS attacks."
         ,"Hypertext Transfer Protocol (HTTP) used in World Wide Web\nUsed by HTTP and HTTPS. HTTP servers and their various components are very exposed and often sources of attacks.Used by HTTP and HTTPS. HTTP servers and their various components are very exposed and often sources of attacks."
         ,"Protocol standard for a NetBIOS service on a TCP/UDP transport: Concepts and methods\nLegacy protocol primarily used for file and printer sharing."
         ,"HTTP Secure (HTTPS) HTTP over TLS/SSL\nUsed by HTTP and HTTPS. HTTP servers and their various components are very exposed and often sources of attacks."
         ,"MS Networking access\nProvides sharing capabilities of files and printers. Used in the 2017 WannaCry attack."
         ,"Microsoft SQL Server database management system (MSSQL) server\nSQL Server and MySQL default ports - used for malware distribution."
         ,"Microsoft SQL Server database management system (MSSQL) monitor\nSQL Server and MySQL default ports - used for malware distribution."
         ,"MySQL database system\nSQL Server and MySQL default ports - used for malware distribution."
         ,"Microsoft Terminal Server (RDP) officially registered as Windows Based Terminal (WBT)\nRemote Desktop. Utilized to exploit various vulnerabilities in remote desktop protocols, as well as weak user authentication. Remote desktop vulnerabilities are commonly used in real world attacks, with the last example being the BlueKeep vulnerability."
         ,"Alternative port for HTTP\nUsed by HTTP and HTTPS. HTTP servers and their various components are very exposed and often sources of attacks."};
         return portNames;
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
                  outputPorts.append("*Port------------ " + lp[i] + " is not available\n");
                  } catch (IOException e) {
                     outputPorts.append("Port------------ " + lp[i] + " is available\n"); 
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
   *a welcome page with the infomation about the program
   */
   public static String  intro(){
      StringBuilder logoAndWelcometext = new StringBuilder();
      String lines = "==============================================================================";
      String space = " \n";
      logoAndWelcometext.append("Welcome to NS21 Network Scanner\n").append(lines).append(space); 
      logoAndWelcometext.append("\nNS21 is 100% Free, non-intrusive program that scan the computer looking for vulnerabilities\n"
                               +" and misconfiguration that put at risk the compuer.\n").append(lines).append(space); 
      logoAndWelcometext.append("\n -A list of the features that the scanner provides:\n");
      logoAndWelcometext.append("\n -Network Information: It displays the infomation about the system.\n"
                               +"\n -Connected Devices: It displays the information about the network and every active connection that      the computer is using.\n"
                               +"\n -Ports: It displays common ports that are known for being vulnerables and common to attacks.\n  (Takes around a minute to scan).\n"
                               +"\n -Shodan: It is a tool that allows to search and/or querry using a specific command. (Limited: not free)\n"
                               +"\n -Router: it displays infomation about the router and how to protect it reducing the risk of possible          attacks.\n"
                               +"\n -Tips: it displays tips and advices to the user to reduce the risk of attacks.\n");
     return logoAndWelcometext.toString();
   }

   /*
   *a page with several tips to theuser to prevent attacks and improve security 
   */
   public static String Router(){
      StringBuilder routerText = new StringBuilder();
      String space = "\n";
      routerText.append("How to access to the router settings.\n"
                        +" -Type: -r (in the textField above).\n").append(space);
      routerText.append("Change the Default password.\n"
                        +" 1. Log in with the default username, Security and password (both admin, usually or look for a label         under the device).\n"
                        +" 2. Go to settings.\n"
                        +" 3. Select Change Router Password or a similar option.\n"
                        +" 4. Enter the new password.\n"
                        +" 5. Save the new settings.\n").append(space);
      routerText.append("Change the Default SSID.\n"
                        +" 1. Log in as administrator.\n"
                        +" 2. Go to settings and look for an option titled 'WiFi name' or 'SSID'\n"
                        +" 3. Enter your new WiFi name.\n"
                        +" 4. Save the new settings.\n"
                        +" 5. Verify the change scannig the network with a cellpone other devices alike\n").append(space);
      routerText.append("Turn Off Features You Don't Use.\n");
      routerText.append(" -Disable Remote Access.\n"
                        +" -Disable the WPS buttom.\n").append(space);
      //Source: https://www.consumerreports.org/digital-security/ways-to-boost-router-security/
      return routerText.toString();
   }

   /*
   *open the router setup page using the gateway address
   */
   public static void goToBrowser(String url){
      //String url = getGateway();
      if (Desktop.isDesktopSupported()) {
         try{
            String routerURL = String.format("http://%s", url);
            // opening router setup in browser
            Desktop.getDesktop().browse(new URI(routerURL));

         } catch (Exception e) {
            e.printStackTrace();
         }
      }
    }//end gotobrowser

   /*
   *a page that displays tips on how to protect the system and be aware of vulnerabilities 
   *expand---------------
   */
   public static String tips(){
      StringBuilder vulnAwareness = new StringBuilder();
      String space = " \n";
      
      vulnAwareness.append("Keep your sofware up-to-date.\n");
      vulnAwareness.append(" -Sofware updates often patches holes in the security.\n"
                           +" -Sofware updates helps reducing  malware activity by patching security flaws.\n"
                           +" -Sofware updates helps to keep your data protected.\n"
                           +" -Sofware updates usually contains newer features improving the experience of the user.\n").append(space);
      //Source: https://us.norton.com/internetsecurity-how-to-the-importance-of-general-software-updates-and-patches.html#:~:text=Software%20updates%20do%20a%20lot%20of%20things&text=These%20might%20include%20repairing%20security,is%20running%20the%20latest%20version.
      vulnAwareness.append("Practice good password management.\n");
      vulnAwareness.append(" -Do not share passwords with anybody.\n"
                           +" -Do not leave passwords in plane sight of anybody (post sticks on top of the monitor).\n"
                           +" -Do not use personal information such as first name, last name or nicknames.\n"
                           +" -Do not use any of the following: password, 12354, qwerty, iloveyou, abc123, default, or admin.\n"
                           +" -Use different passwords for different accounts.\n"
                           +" -Use a mixed of number, letters in uppercase and lowercase, and special symbols.\n"
                           +" -Use a password lenght of 9 characters or longer.\n"
                           +" -Make use of password managers to keep them in a unique and secured place.\n").append(space);
      //Source: https://www.it.ucsb.edu/secure-compute-research-environment-user-guide/password-best-practices
      vulnAwareness.append("Never leave your devices unattended.\n");
      vulnAwareness.append(" -Make use of lock screens with timer, so nobody can use it when you are away.\n"
                           +" -Use screen privacy filter for public places and office enviroments to avoid shoulder surfing.\n").append(space);
      vulnAwareness.append("Safe-guard your data.\n");
      vulnAwareness.append(" -Make use of a VPN.\n"
                           +" -Use incognito Browsers mode to avoid storing browsing data or 'cookies'.\n"
                           +" -Back up your data once per month.\n"
                           +" -Stay away from Unknown or suspicious websites.\n"
                           +" -Only download sofware from the official website.\n").append(space);
      vulnAwareness.append("Avoid phishing and scams.\n");
      vulnAwareness.append(" -Do not share your infomration via e-mail with any company.\n"
                           +" -If it sounds too good, it is a scam.\n"
                           +" -Stay aware of log-in attempts and activity for your acocunts.\n"
                           +" -Do not click any link to confirm, claim or redeem a free product without.\n"
                           +" -Do not download any file from an Unknown company or unusual email address.\n").append(space);
      return vulnAwareness.toString();
   }

/*--------------------------------------------------------------------
*Listeners
*/
   // Define an inner class to handle ActionEvent of btnwelcome
   private class BtnWelcomeListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent evt) {
         toDisplay.setText(""); //clear text area
         toDisplay.append(intro()); //invoke method and print
      }
   }

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
         toDisplay.append(UsingShodanDisplay());
      }
   }

   // Define an inner class to handle ActionEvent of btnRouter
   private class BtnRouterListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent evt) {
         toDisplay.setText("");
         toDisplay.append(Router());
      }
   }


   // Define an inner class to handle ActionEvent of btnCount
   private class BtnTipsListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent evt) {
         toDisplay.setText("");
         toDisplay.append(tips());
      }
   }

   // Define an inner class to handle the input TextField.
   // An ActionEvent listener must implement ActionListener interface.
   private class TFInputListener implements ActionListener {
      // ActionEvent handler - Called back upon hitting "enter" key on TextField
      @Override
      public void actionPerformed(ActionEvent evt) {
         // Get the String entered into the TextField tfInput, convert to int
         new BtnShodanListener();
         String g = getGateway();
         if(jfield.getText().startsWith("-r")){
            goToBrowser(g);
         }
         else if(jfield.getText().equals("clear")){
            toDisplay.setText("");
         } 
         else if(jfield.getText().startsWith("-b")){
             goToBrowser(jfield.getText().trim().substring(3));
         } 
         else
         {
         toDisplay.append(UsingShodan(jfield.getText()));
         new BtnShodanListener();
         }
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
      //this will return the value in the commmand prompt to see what is doing each action(bottom)
      @Override public void windowIconified(WindowEvent evt) { System.out.println("Window Iconified"); }
      @Override public void windowDeiconified(WindowEvent evt) { System.out.println("Window Deiconified"); }
      @Override public void windowActivated(WindowEvent evt) { System.out.println("Window Activated"); }
      @Override public void windowDeactivated(WindowEvent evt) { System.out.println("Window Deactivated"); }
   }
}