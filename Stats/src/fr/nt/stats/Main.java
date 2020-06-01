package fr.nt.stats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	//Variables BDD
	private Connection connection;
    private String host = getConfig().getString("mysql.host");
    private String database = getConfig().getString("mysql.database");
    private String username = getConfig().getString("mysql.username");
    private String password = getConfig().getString("mysql.password");
    private int port = getConfig().getInt("mysql.port");
    
    //Variable ajout de temps
    private int time = getConfig().getInt("timer");
    
	//A l'activation du plugin
	@Override
	public void onEnable() {
 		super.onEnable();
 		//Fichier de config
 		saveDefaultConfig();
 		 		
 		//Connexion BDD
 		 try {
 			 Class.forName("com.mysql.jdbc.Driver");
 		 }
	     catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        System.err.println("jdbc driver unavailable!");
	        return;
	     }
	     try {
	    	 connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database, this.username, this.password);
	     } 
	     catch (SQLException e) {
	    	 e.printStackTrace();
	     }
	            
	     //Création table BDD
	     try {
			createtable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	    TimerTask task = new TimerTask(this, this.getTime());
		task.runTaskTimer(this, 0, 20);
 		
	}
	
	//A la désactivation du plugin
	@Override
	public void onDisable() {
		super.onDisable();
		
		//Off BDD
	    try { 
	        if (connection!=null && !connection.isClosed()){ 
	           
	            connection.close();
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	
	//Focntion de création de table BDD
	public void createtable() throws SQLException {
		
		String sql = "CREATE TABLE IF NOT EXISTS stats(date datetime null,nbrplayer int null,tps double null,ramfree varchar(64) null,ramtotal varchar(64) null,rammax varchar(64) null,ramused varchar(64) null);";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.executeUpdate();
	}
	
	//Fonction ajout d'une nv entrée stats
	public void adddata(String date, int nbrplayer, Double tps, String ramfree, String ramtotal, String rammax, String ramused) throws SQLException {
		
		String sql = "INSERT INTO stats(date,nbrplayer,tps,ramfree,ramtotal,rammax,ramused) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, date);
		statement.setInt(2, nbrplayer);
		statement.setDouble(3, tps);
		statement.setString(4, ramfree);
		statement.setString(5, ramtotal);
		statement.setString(6, rammax);
		statement.setString(7, ramused);
		statement.executeUpdate();
	}	

    //Getter
	public int getTime() {
		return time;
	}
   

}
