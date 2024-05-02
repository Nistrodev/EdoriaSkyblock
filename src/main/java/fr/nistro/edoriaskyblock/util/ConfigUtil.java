package fr.nistro.edoriaskyblock.util;

import java.io.File;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import me.clip.placeholderapi.PlaceholderAPI;

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
	
	public String getString(String path, OfflinePlayer player) {
		return PlaceholderAPI.setPlaceholders(player, this.config.getString(path)).replace("&", "§");
	}
	
	public List<String> getStringList(String path, OfflinePlayer player) {
		final List<String> list = this.config.getStringList(path);
		for (int i = 0; i < list.size(); i++) {
			list.set(i, PlaceholderAPI.setPlaceholders(player, list.get(i))).replace("&", "§");
		}
		return list;
	}
}
