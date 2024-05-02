package fr.nistro.edoriaskyblock.util;

import org.bukkit.Bukkit;

public class DependenciesUtil {
	public static void checkDependencies() {
    	if (!VaultUtil.setupEconomy() ) {
            Bukkit.getServer().getConsoleSender().sendMessage("§c[EdoriaSkyblock] Vault not found, disabling plugin...");
            Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("Exostia"));
        } else {
        	Bukkit.getServer().getConsoleSender().sendMessage("§6[§eEdoriaSkyblock§6] §r§aVault found !");
        }
    	
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[EdoriaSkyblock] PlaceholderAPI not found, disabling plugin...");
			Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("Exostia"));
		} else {
			Bukkit.getServer().getConsoleSender().sendMessage("§6[§eEdoriaSkyblock§6] §r§aPlaceholderAPI found !");
        }
	}
}
