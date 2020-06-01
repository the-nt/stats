package fr.nt.stats;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import net.minecraft.server.v1_8_R3.MinecraftServer;


public class TimerTask extends BukkitRunnable {
	

	//Variables
	private int time;
	private Main main;

	//Constructeur
	public TimerTask(Main main,int time) {
		this.time = time;
		this.main = main;
	}
	
	
	//Boucle
	@Override
	public void run() {
		
		if(time == 0) {
			
			Runtime runtime = Runtime.getRuntime();

	        float free = runtime.freeMemory();
	        float total = runtime.totalMemory();
	        float max = runtime.maxMemory();
	        float used = total - free;

	        // MB RAM
	        String freeMB = String.valueOf(free / 1024 / 1024);
	        String totalMB = String.valueOf(total / 1024 / 1024);
	        String maxMB = String.valueOf(max / 1024 / 1024);
	        String usedMB = String.valueOf(used / 1024 / 1024);
	        
	        //PLAYER
	        int nbrpalyer = Bukkit.getOnlinePlayers().size();
	        
	        //TPS
	        Double tps = MinecraftServer.getServer().recentTps[0];
	        
	        //Date
	        LocalDateTime dateTime = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	        try {
				main.adddata(dateTime.format(formatter).toString(), nbrpalyer, tps, freeMB, totalMB, maxMB, usedMB);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
		}
		
		time--;
	}
	

}
