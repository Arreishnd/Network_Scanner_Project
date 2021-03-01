/**
*Edward Andino Sierra
*Network Scanner
*Start Date: 2/22/2021 - 3/1/2021
*Version 0.1
*/
import java.util.Scanner;
import java.io.IOException;

//import for ipconfig class
import java.net.InetAddress;
//import java.io.InputStream;
//import java.io.BufferedInputStream;
//import java.io.IOException;
import java.text.ParseException;
//import java.util.StringTokenizer;

//imports for ports
import java.net.*;
import java.io.*;

//imports for listNets
import java.io.*;
import java.net.*;
import java.util.*;


public class NetworkScanner 
{
		static Integer user_choice = 1;
		static Scanner input = new Scanner(System.in);

	public static void main(String[]args){
		
		Menu_Welcome();
		do{
		Menu_Display();
		user_choice = input.nextInt();
		Menu_Choice(user_choice);
		}
		while(user_choice!=0);
		
	}//end main

	//display a welcome message to the user
	public static void Menu_Welcome(){
		 		System.out.println("\nWelcome to FX Network Scanner\n"
				+"With this program you will be able to learn about your network,\n"
				+"as well as, test your network with tets vulnerabilities and detect \n"
				+"misconfigurations in the network\n");
	}

	//display a menu to the user and the choice that the user can select from
	public static void Menu_Display(){
		 System.out.println("1. Display Network Information\n"+
				"2. Display Devices Connected in the Network \n"+
				"3. Display Used Ports in the Network\n"+
				//"4. \n"+
				"0. Exit\n");
	}

	/**
	@param userChoice is the user choice that want to perfrom from the menu
	*/
	public static void Menu_Choice(int userChoice){
		if(userChoice==1){
			Ipconfig();
		}
		else if(userChoice==2){
			DeviceConnected();
		}
		else if(userChoice==3){
			Ports();
		}
		//exit
		else if (userChoice==0) {
			exit();	
		}
		else
			System.out.println("Please select any option from  the menu.");
	}

	/*
	//add ipv6 + temp + local
	//subnet
	//gateway
	*/
	public static void Ipconfig(){
		clearScreen();
		  InetAddress ip;
		 // Inet6Address ipv6; *add a ipv6 to get the ipv6 address
    try {
            
        ip = InetAddress.getLocalHost();
        //ipv6 = InetAddress.getHostAddress();
        System.out.println("Current Name : " + ip.getHostName());
        System.out.println("Current IP address : " + ip.getHostAddress());
        System.out.println("Current IPv6 address : ");// + ipv6.getHostAddress());
        System.out.println("Current IPv6 Temp address : ");// + ipv6.getHostAddress());
        System.out.println("Current IPv6 Local : ");// + ipv6.getHostAddress());
        
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            
        byte[] mac = network.getHardwareAddress();
            
        System.out.print("Current MAC address : ");
            
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
        }
        System.out.println(sb.toString());
            
    } catch(Throwable se){
    		se.printStackTrace();
    	}
	}

	/**
	*explain what is arp and what it does
	*get https://github.com/silverwind/oui 
	*and pass oui by process like arp -a and find info about the device
	*/
	public static void DeviceConnected(){
		clearScreen();

    	String cmd = "arp -a";
    	try{
    	System.out.println(getARPTable2(cmd));
    	}
    	catch(Throwable se){
    		se.printStackTrace();
    	}
	}
	
	public static String getARPTable2(String cmd) throws IOException {
           Scanner s = new Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
                return s.hasNext() ? s.next() : "";
    }

    /*
	*add a description for each port and funtions. should they be open or not.
	*add more ports to the list
	*why a specific port should be close
	*/
	public static void Ports(){
		clearScreen();

		String[] portNames = {
			"File Transfer Protocol (FTP) Data Transfer"
			,"File Transfer Protocol (FTP) Command Control"
			,"Secure Shell (SSH) Secure Login"
			,"Telnet remote login service, unencrypted text messages"
			,"Simple Mail Transfer Protocol (SMTP) E-mail routing"
			,"Domain Name System (DNS) service"
			,"Dynamic Host Configuration Protocol (DHCP)"
			,"Dynamic Host Configuration Protocol (DHCP)"
			,"Hypertext Transfer Protocol (HTTP) used in the World Wide Web"
			,"Post Office Protocol (POP3)"
			,"Network News Transfer Protocol (NNTP)"
			,"Network Time Protocol (NTP)"
			,"Internet Message Access Protocol (IMAP) Management of digital mail"
			,"Simple Network Management Protocol (SNMP)"
			,"Internet Relay Chat (IRC)"
			,"HTTP Secure (HTTPS) HTTP over TLS/SSL"
			,"MS Networking access"};	

		int [] commonPortsList = new int[] {20,21,22,23,25,53,67,68,80,110,119,123,143,161,194,443,445};
		for(int i=0; i<commonPortsList.length;i++){
				System.out.println("\n"+portNames[i]);	
				available(commonPortsList[i]);
		}
	}	
//###########################################################

	//allow to clear the console screen
	public static void clearScreen() {  
    	System.out.print("\033[H\033[2J");  
    	System.out.flush();  
	}  

	//allow to exit or terminate the program
	public static void exit(){
		System.out.print("Thank you");
		System.exit(0);
	}

	private static boolean available(int port) {
    System.out.println("--------------Testing port " + port);
    Socket s = null;
    try {
	    s = new Socket("localhost", port);

        // If the code makes it this far without an exception it means
        // something is using the port and has responded.
        System.out.println("--------------Port " + port + " is not available");
        return false;
	    } catch (IOException e) {
	        System.out.println("--------------Port " + port + " is available");
	        return true;
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
	    }
	}

}//end class