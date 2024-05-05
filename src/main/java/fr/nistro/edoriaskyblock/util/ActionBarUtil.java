package fr.nistro.edoriaskyblock.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class ActionBarUtil {
	public static void sendActionBarToAllPlayers(String message) {
		for (final Player player : Bukkit.getOnlinePlayers()) {
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(message).create());
		}
	}
}
