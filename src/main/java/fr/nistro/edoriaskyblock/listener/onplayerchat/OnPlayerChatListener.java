package fr.nistro.edoriaskyblock.listener.onplayerchat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnPlayerChatListener implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		final String message = event.getMessage();

		if ((message == null) || message.isEmpty()) {
			Bukkit.getServer().getConsoleSender()
					.sendMessage(ChatColor.RED + "§6[§eEdoriaSkyblock§6] §r§cThe message is empty");
			return;
		}

		// Envoie le message sur le discord
		DiscordChat.sendChatMessage(player, message);

		// Envoie le message sur le chat
		if (!ChatFormatter.sendChatMessage(player, message)) {
			Bukkit.getServer().getConsoleSender().sendMessage(
					ChatColor.RED + "§6[§eEdoriaSkyblock§6] §r§cAn error occurred while sending the message");
		}
	}
}
