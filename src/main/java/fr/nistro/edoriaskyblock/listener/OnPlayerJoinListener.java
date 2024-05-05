package fr.nistro.edoriaskyblock.listener;

import java.awt.Color;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.nistro.edoriaskyblock.util.ActionBarUtil;
import fr.nistro.edoriaskyblock.util.ConfigUtil;
import fr.nistro.edoriaskyblock.util.DiscordWebhookUtil;

public class OnPlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		ActionBarUtil.sendActionBarToAllPlayers(ConfigUtil.getString("joinQuit.join.joinMessageActionBar", player));

		event.setJoinMessage(ConfigUtil.getString("joinQuit.join.joinMessage", player));

		final DiscordWebhookUtil webhook = new DiscordWebhookUtil(ConfigUtil.getString("joinQuit.webhook.url"));

		webhook.setUsername(ConfigUtil.getString("joinQuit.webhook.username"));
		webhook.setAvatarUrl(ConfigUtil.getString("joinQuit.webhook.avatar_url"));

		webhook.addEmbed(
				new DiscordWebhookUtil.EmbedObject().setTitle(ConfigUtil.getString("joinQuit.join.joinEmbed.title"))
						.setDescription(ConfigUtil.getString("joinQuit.join.joinEmbed.description", player))
						.setColor(Color.GREEN));
		try {
			webhook.execute();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
