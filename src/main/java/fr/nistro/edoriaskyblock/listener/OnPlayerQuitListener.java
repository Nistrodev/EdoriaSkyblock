package fr.nistro.edoriaskyblock.listener;

import java.awt.Color;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.nistro.edoriaskyblock.util.ActionBarUtil;
import fr.nistro.edoriaskyblock.util.ConfigUtil;
import fr.nistro.edoriaskyblock.util.DiscordWebhookUtil;

public class OnPlayerQuitListener implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		final Player player = event.getPlayer();

		ActionBarUtil.sendActionBarToAllPlayers(ConfigUtil.getString("joinQuit.quit.quitMessageActionBar", player));

		event.setQuitMessage(ConfigUtil.getString("joinQuit.quit.quitMessage", player));

		final DiscordWebhookUtil webhook = new DiscordWebhookUtil(
				ConfigUtil.getString("joinQuit.quit.quitWebhook.url"));

		webhook.setUsername(ConfigUtil.getString("joinQuit.quit.webhook.username"));
		webhook.setAvatarUrl(ConfigUtil.getString("joinQuit.quit.webhook.avatar_url"));

		webhook.addEmbed(
				new DiscordWebhookUtil.EmbedObject().setTitle(ConfigUtil.getString("joinQuit.quit.quitEmbed.title"))
						.setDescription(ConfigUtil.getString("joinQuit.quit.quitEmbed.description", player))
						.setColor(Color.RED));

		try {
			webhook.execute();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
