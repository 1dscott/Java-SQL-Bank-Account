//Bank ATM program with SQL.
//a simple Bank Account database with only 1 table: accounts: 
//accountID, PIN, balance.
//client and menu program
//Set up a socket connection and user enters accountID and PIN (at ATM) to login bank server
//after login, enter "balance", "withdraw", "deposit", or "quit" to use the menu



import java.util.Scanner;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

//bank
public class bankAccount {
    
	public static void main(String[] args)
    {     
		String url = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String password = "";
        
		//Declare Scanner
		Scanner scan = new Scanner(System.in);		
		//Declare accountID String
		String name;
		//Declare menu input String
		String menu;
		//Declare PIN number
		String PIN;
		//Declare menu doubles for operations		
		double bal;
		double depositAmount;
		double withdrawAmount;
		
		//Get user accountID and PIN number
		System.out.println("Enter your accountID name");
		name = scan.next();
		
		System.out.println("Enter your PIN");
		PIN = scan.next();
		
		//connect to MYSQL server here
		try
		(
        //Class.forName("com.mysql.jdbc.Driver");		
		Connection con = DriverManager.getConnection(url, user, password);
		PreparedStatement pst = con.prepareStatement("UPDATE accounts SET balance = ? WHERE id = 1");
		)		
		{		
			boolean MenuActive = true;
			while(MenuActive == true)
			{
				System.out.println("Enter balance to display your current balance");
				System.out.println("Enter withdraw to withdraw funds");
				System.out.println("Enter deposit to deposit funds");
				System.out.println("Enter quit to exit");
				
				menu = scan.next();
				
						if(menu.equals("balance"))
						{	
							Statement stmt = con.createStatement();
							ResultSet rs = stmt.executeQuery("SELECT balance FROM accounts WHERE id = 1");
							
							if(rs.next())
							{
								InputStream stream = rs.getBinaryStream(1);			
								String rsBalance = rs.getString("balance");
								bal = Double.parseDouble(rsBalance);
								System.out.println("Your current balance is: " + bal);
								System.out.println();
								MenuActive = true;
							}							
						}
						else if(menu.equals("withdraw"))
						{				
							Statement stmt = con.createStatement();
							ResultSet rs = stmt.executeQuery("SELECT balance FROM accounts WHERE id = 1");
						
							if(rs.next())
							{
								InputStream stream = rs.getBinaryStream(1);			
								String rsBalance = rs.getString("balance");					
								bal = Double.parseDouble(rsBalance);
								System.out.println("Your current balance is: " + bal);
								System.out.println("How much would you like to withdraw?");
								withdrawAmount = scan.nextDouble();	
								bal = bal - withdrawAmount;
							
								//insert new balance into mySQL							
								pst.setDouble(1, bal);								
								pst.executeUpdate();
						
								System.out.println("Your new balance is: " + bal);
								System.out.println();
								MenuActive = true;
							}
						}
						else if(menu.equals("deposit"))
						{
							Statement stmt = con.createStatement();
							ResultSet rs = stmt.executeQuery("SELECT balance FROM accounts WHERE id = 1");
							
							if(rs.next())
							{
								InputStream stream = rs.getBinaryStream(1);			
								String rsBalance = rs.getString("balance");
										
								bal = Double.parseDouble(rsBalance);
								System.out.println("Your current balance is: " + bal);
								System.out.println("How much would you like to deposit?");
								depositAmount = scan.nextDouble();
								bal = bal + depositAmount;
							
							//insert new balance into mySQL							
							pst.setDouble(1, bal);							
							pst.executeUpdate();
					
							System.out.println("Your new balance is: " + bal);
							System.out.println();
							MenuActive = true;
							}
					
						}	
						else if(menu.equals("quit"))
						{
							//terminate program
							System.out.println("Thank you, Program Terminated.");
							MenuActive = false;
					
						}
						else
						{
							System.out.println("Error: You did not enter balance, withdraw, deposit, or quit!");
							System.out.println("Please try again");
							MenuActive = true;
						}
					
					}//end while loop
			
    }
	catch (SQLException ex) 
	{        
        Logger lgr = Logger.getLogger(bankAccount.class.getName());
        lgr.log(Level.SEVERE, ex.getMessage(), ex);
    } 

}
}
