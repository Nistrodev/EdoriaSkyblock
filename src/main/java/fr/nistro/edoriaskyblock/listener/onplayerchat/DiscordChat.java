package fr.nistro.edoriaskyblock.listener.onplayerchat;

import org.bukkit.entity.Player;

import fr.nistro.edoriaskyblock.util.ConfigUtil;
import fr.nistro.edoriaskyblock.util.DiscordWebhookUtil;
import fr.nistro.edoriaskyblock.util.FormatterUtil;

public class DiscordChat {

	public static void sendChatMessage(Player player, String message) {
		// Vérifie si le message est vide
		if ((message == null) || message.isEmpty()) {
			return;
		}

		// Crée un nouvel objet DiscordWebhookUtil
		final DiscordWebhookUtil webhook = new DiscordWebhookUtil(ConfigUtil.getString("discordChat.webhook.url"));

		// Définit le nom d'utilisateur
		webhook.setUsername(ConfigUtil.getString("discordChat.webhook.username"));
		webhook.setAvatarUrl(ConfigUtil.getString("discordChat.webhook.avatar_url"));

		// Formatage du message pour discord
		message = FormatterUtil.formatToDiscord(message, null);

		// Définit le contenu du message
		webhook.setContent(player.getName() + ": " + message);

		// Envoie le message
		try {
			webhook.execute();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
