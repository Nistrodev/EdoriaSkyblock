package fr.nistro.edoriaskyblock;

import java.sql.Connection;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import fr.nistro.edoriaskyblock.listener.OnPlayerJoinListener;
import fr.nistro.edoriaskyblock.listener.OnPlayerQuitListener;
import fr.nistro.edoriaskyblock.listener.onplayerchat.OnPlayerChatListener;
import fr.nistro.edoriaskyblock.util.ConfigUtil;
import fr.nistro.edoriaskyblock.util.DatabaseUtil;
import fr.nistro.edoriaskyblock.util.DependenciesUtil;
import fr.nistro.edoriaskyblock.util.FormatterUtil;

public class Main extends JavaPlugin {

	private Connection connection;
	private static String prefix;

	@Override
	public void onEnable() {
		// Vérification des dépendances
		DependenciesUtil.checkDependencies();

		this.saveDefaultConfig();

		final ConfigUtil config = new ConfigUtil(this, "config.yml");
		config.getConfig().options().copyDefaults(true);
		config.save();

		// Récupère le prefix
		Main.prefix = FormatterUtil.format(config.getConfig().getString("prefix"), null);

		// Connexion à la base de données
		this.connection = DatabaseUtil.getInstance().getConnection();

		// Enregistrement des événements
		this.getServer().getPluginManager().registerEvents(new OnPlayerChatListener(), this);
		this.getServer().getPluginManager().registerEvents(new OnPlayerJoinListener(), this);
		this.getServer().getPluginManager().registerEvents(new OnPlayerQuitListener(), this);

		// Si le plugin est désactivé, ne pas continuer
		if (!this.isEnabled()) {
			return;
		}

		this.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "§6[§eEdoriaSkyblock§6] §r§aPlugin enabled");
	}

	@Override
	public void onDisable() {
		this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "§6[§eEdoriaSkyblock§6] §r§cPlugin disabled");
	}

	public Connection getConnection() {
		if (this.connection == null) {
			this.connection = DatabaseUtil.getInstance().getConnection();
		}
		return this.connection;
	}

	public static String getPrefix() {
		return Main.prefix;
	}
}
