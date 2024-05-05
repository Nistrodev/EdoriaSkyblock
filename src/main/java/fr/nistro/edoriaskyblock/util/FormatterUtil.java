package fr.nistro.edoriaskyblock.util;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class FormatterUtil {
	public static String format(String message, Player player) {
		return PlaceholderAPI.setPlaceholders(player, message).replace("&", "§");
	}

	public static String formatToDiscord(String message, Player player) {
		message = PlaceholderAPI.setPlaceholders(player, message);

		final String[] colors = { "&0", "&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&a", "&b", "&c", "&d",
				"&e", "&f" };
		final String[] formats = { "&l", "&o", "&n", "&m", "&k", "§r" };

		for (final String color : colors) {
			message = message.replace(color, "");
		}

		for (final String format : formats) {
			message = message.replace(format, "");
		}

		return message;
	}
}
