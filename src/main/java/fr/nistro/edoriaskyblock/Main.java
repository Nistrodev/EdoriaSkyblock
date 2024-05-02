package fr.nistro.edoriaskyblock;

import java.sql.Connection;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import fr.nistro.edoriaskyblock.util.ConfigUtil;
import fr.nistro.edoriaskyblock.util.DatabaseUtil;
import fr.nistro.edoriaskyblock.util.DependenciesUtil;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private Connection connection;
	
	public static Main getInstance() {
		if (Main.instance == null) {
			Main.instance = new Main();
		}
		return Main.instance;
	}
	

	@Override
	public void onEnable() {
		// Vérification des dépendances
    	DependenciesUtil.checkDependencies();
    	
    	this.saveDefaultConfig();
    	
    	final ConfigUtil config = new ConfigUtil(this, "config.yml");
    	config.getConfig().options().copyDefaults(true);
    	config.save();
    	
    	
		this.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "EdoriaSkyblock enabled");
	}

	@Override
	public void onDisable() {
		this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "EdoriaSkyblock disabled");
	}
	
	public Connection getConnection() {
		if (this.connection == null) {
			this.connection = DatabaseUtil.getInstance().getConnection();
		}
		return this.connection;
	}
}
