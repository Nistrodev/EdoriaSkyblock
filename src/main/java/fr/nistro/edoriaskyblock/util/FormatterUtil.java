package fr.nistro.edoriaskyblock.util;

import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.PlaceholderAPI;

public class FormatterUtil {
	public static String format(String message, OfflinePlayer player) {
		return PlaceholderAPI.setPlaceholders(player, message).replace("&", "§");
	}
}
