package fr.nistro.edoriaskyblock.util;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigUtil {
	private final File file;
	private final FileConfiguration config;
	
	public ConfigUtil(Plugin plugin, String path) {
		this(plugin.getDataFolder().getAbsolutePath() + "/" + path);
	}
	
	public ConfigUtil(String path) {
		this.file = new File(path);
		this.config = YamlConfiguration.loadConfiguration(this.file);
		this.config.options().copyDefaults(true);
	}
	
	public boolean save() {
		try {
			this.config.options().copyDefaults(true);
			this.config.save(this.file);
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public FileConfiguration getConfig() {
		return this.config;
	}
	
	public static String getString(String path) {
		return FormatterUtil.format(Bukkit.getPluginManager().getPlugin("EdoriaSkyblock").getConfig().getString(path), null);
	}
}
