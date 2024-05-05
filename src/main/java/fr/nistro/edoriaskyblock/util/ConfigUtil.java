package fr.nistro.edoriaskyblock.util;

import java.awt.Color;
import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
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

	public void set(String path, Object value) {
		this.config.set(path, value);
	}

	public static String getString(String path) {
		if (Bukkit.getPluginManager().getPlugin("EdoriaSkyblock") == null) {
			Bukkit.getServer().getConsoleSender().sendMessage("§6[§eEdoriaSkyblock§6] §r§cThe plugin is not loaded");
			Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("EdoriaSkyblock"));
			return null;
		}

		// Si le path retourne null, alors le message est vide
		if (Bukkit.getPluginManager().getPlugin("EdoriaSkyblock").getConfig().getString(path) == null) {
			Bukkit.getServer().getConsoleSender()
					.sendMessage("§6[§eEdoriaSkyblock§6] §r§cMissing path: " + path + " in config.yml");
			Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("EdoriaSkyblock"));
			return null;
		}

		System.out.println("path: " + path);

		return FormatterUtil.format(Bukkit.getPluginManager().getPlugin("EdoriaSkyblock").getConfig().getString(path),
				null);
	}

	public static String getString(String path, Player player) {
		if (Bukkit.getPluginManager().getPlugin("EdoriaSkyblock") == null) {
			Bukkit.getServer().getConsoleSender().sendMessage("§6[§eEdoriaSkyblock§6] §r§cThe plugin is not loaded");
			Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("EdoriaSkyblock"));
			return null;
		}

		// Si le path retourne null, alors le message est vide
		if (Bukkit.getPluginManager().getPlugin("EdoriaSkyblock").getConfig().getString(path) == null) {
			Bukkit.getServer().getConsoleSender()
					.sendMessage("§6[§eEdoriaSkyblock§6] §r§cMissing path: " + path + " in config.yml");
			Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("EdoriaSkyblock"));
			return null;
		}

		return FormatterUtil.format(Bukkit.getPluginManager().getPlugin("EdoriaSkyblock").getConfig().getString(path),
				player);
	}

	public static Color getColor(String path) {
		if (Bukkit.getPluginManager().getPlugin("EdoriaSkyblock") == null) {
			Bukkit.getServer().getConsoleSender().sendMessage("§6[§eEdoriaSkyblock§6] §r§cThe plugin is not loaded");
			Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("EdoriaSkyblock"));
			return null;
		}

		// Si le path retourne null, alors le message est vide
		if (Bukkit.getPluginManager().getPlugin("EdoriaSkyblock").getConfig().getString(path) == null) {
			Bukkit.getServer().getConsoleSender()
					.sendMessage("§6[§eEdoriaSkyblock§6] §r§cMissing path: " + path + " in config.yml");
			Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("EdoriaSkyblock"));
			return null;
		}

		final String color = Bukkit.getPluginManager().getPlugin("EdoriaSkyblock").getConfig().getString(path);

		final Color c = null;

		try {
			return Color.decode(color);
		} catch (final NumberFormatException e) {
			Bukkit.getServer().getConsoleSender()
					.sendMessage("§6[§eEdoriaSkyblock§6] §r§cThe color " + color + " is not a valid color");
		}

		return c;
	}
}
