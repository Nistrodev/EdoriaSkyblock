package fr.nistro.edoriaskyblock.exception;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import fr.nistro.edoriaskyblock.util.ConfigUtil;

public class DatabaseException {
	private final SQLException exception;

	public DatabaseException(SQLException exception) {
		this.exception = exception;
	}

	public SQLException getException() {
		if (this.exception == null) {
			Bukkit.getServer().getConsoleSender()
					.sendMessage(ChatColor.RED + "§6[§eEdoriaSkyblock§6] §r§cYou need to provide a valid SQLException");
			return null;
		}

		// Si l'exécution de la requête a échoué car la base est inconnu
		if (this.exception.getErrorCode() == 1049) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "§6[§eEdoriaSkyblock§6] §r§cThe database "
					+ ConfigUtil.getString("database.database") + " is unknown");
		}

		// Si l'exécution de la requête a échoué car l'utilisateur est inconnu
		if (this.exception.getErrorCode() == 1045) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "§6[§eEdoriaSkyblock§6] §r§cThe user "
					+ ConfigUtil.getString("database.username") + " is unknown");
		}

		// Si l'exécution de la requête a échoué car le mot de passe est incorrect
		if (this.exception.getErrorCode() == 1045) {
			Bukkit.getServer().getConsoleSender()
					.sendMessage(ChatColor.RED + "§6[§eEdoriaSkyblock§6] §r§cThe password + "
							+ ConfigUtil.getString("database.password") + " is incorrect");
		}

		// Si l'exécution de la requête a échoué car l'hôte est inconnu
		if (this.exception.getErrorCode() == 1045) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "§6[§eEdoriaSkyblock§6] §r§cThe host "
					+ ConfigUtil.getString("database.host") + " is unknown");
		}

		// Désactivation du plugin
		Bukkit.getServer().getConsoleSender().sendMessage(
				ChatColor.RED + "§6[§eEdoriaSkyblock§6] §r§cThe plugin is disabled due to a database error");
		Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("EdoriaSkyblock"));

		return this.exception;
	}

}
