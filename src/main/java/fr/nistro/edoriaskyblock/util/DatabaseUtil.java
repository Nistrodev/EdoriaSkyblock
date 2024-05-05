package fr.nistro.edoriaskyblock.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import fr.nistro.edoriaskyblock.exception.DatabaseException;

public class DatabaseUtil {
	private static DatabaseUtil instance;
	private Connection connection;

	public static DatabaseUtil getInstance() {
		if (DatabaseUtil.instance == null) {
			DatabaseUtil.instance = new DatabaseUtil();
		}

		return DatabaseUtil.instance;
	}

	public DatabaseUtil() {
		this.connectToDatabase();
	}

	public Connection getConnection() {
		return this.connection;
	}

	private void connectToDatabase() {
		final String host = ConfigUtil.getString("database.host");
		final String database = ConfigUtil.getString("database.database");
		final String username = ConfigUtil.getString("database.username");
		final String password = ConfigUtil.getString("database.password");

		final String url = "jdbc:mysql://" + host + "/" + database + "?useSSL=false";

		try {
			this.connection = DriverManager.getConnection(url, username, password);
			Bukkit.getServer().getConsoleSender()
					.sendMessage(ChatColor.GREEN + "§6[§eEdoriaSkyblock§6] §r§aDatabase connection successful");
		} catch (final SQLException e) {
			final DatabaseException exception = new DatabaseException(e);
			exception.getException();
		}
	}
}
