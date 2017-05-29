import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.*;

@Path("socialmedia")
public class Hallo {
	
	@GET
	@Produces("text/plain")
	public String sayHello3(){
		return "Willkommen auf ihrer Social Media Plattform!\n\nBefehle:\n\n"
				+ "\tgetFriend -> Listet letzten hinzugefuegten Freund auf\n"
				+ "\taddFriend/{username} -> Fuegt einen Freund hinzu\n"
				+ "\tgetUsername/{username} -> Gibt deinen Namen zurueck";
	}
	
	@Path("getFriend")
	@GET
	@Produces("text/plain")
	public String sayHello1() throws SQLException{
		String ersteSpalte = "";

		try {
			Statement stmt;
			ResultSet rs;
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://linuxserver/DB2_ststeraf";
			Connection con = DriverManager.getConnection(url, "ststeraf",
					"mypass");
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM Friends");
			
			while (rs.next()) {	
				ersteSpalte = rs.getString("Name");
				System.out.println(ersteSpalte);
			}
			con.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return "Dein neuster Freund ist: " + ersteSpalte;
	}
	
	
	@Path("addFriend/{username}")
	@GET
	@Produces("text/plain")
	public String sayHello(@PathParam("username") String user) throws SQLException{
		
		Statement stmt;
		ResultSet rs;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://linuxserver/DB2_ststeraf";
			Connection con = DriverManager.getConnection(url, "ststeraf",
					"mypass");
			stmt = con.createStatement();

			
			String query = "insert into Friends (Name)"
					+ " values (?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, user);

			preparedStmt.execute();
			System.out.println(user + " wurde in deine Freundesliste eingefuegt!");
			
			con.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return user + " wurde in deine Freundesliste eingefuegt!";
	}
	
	
	@Path("getUser/{username}")
	@GET
	@Produces("text/plain")
	public String sayHelloUser(@PathParam("username") String user){
		return "You are logged in as User: " + user;
	}
	
}
