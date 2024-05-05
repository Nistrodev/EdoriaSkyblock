package fr.nistro.edoriaskyblock.listener.onplayerchat;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.nistro.edoriaskyblock.Main;
import fr.nistro.edoriaskyblock.util.ConfigUtil;
import fr.nistro.edoriaskyblock.util.DiscordWebhookUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ChatFormatter {
	public static boolean sendChatMessage(Player player, String message) {
		// Récupère le préfixe du joueur
		final String prefixPlayer = PlaceholderAPI.setPlaceholders(player, "%vault_prefix%").replace("&", "§");

		// Créer un textComponent pour reporter le message
		final TextComponent reportMessage = new TextComponent();
		reportMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] {
				new TextComponent(ConfigUtil.getString("report.prefix") + " " + player.getName()) }));
		reportMessage.setClickEvent(
				new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " + player.getName() + " " + message));
		reportMessage.addExtra(ConfigUtil.getString("report.symbol"));

		final TextComponent messageToSend = new TextComponent();
		messageToSend.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] { new TextComponent(
				new TextComponent(Main.getPrefix() + ConfigUtil.getString("messages.hoverMessageInChat"))) }));
		messageToSend
				.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + player.getName() + " "));
		// On ajoute le message de report
		messageToSend.addExtra(reportMessage);
		messageToSend.addExtra(prefixPlayer);
		messageToSend.addExtra(player.getName());
		messageToSend.addExtra(ConfigUtil.getString("messages.separatorInChat"));

		// On envoie le message à tous les joueurs
		for (final Player p : Bukkit.getOnlinePlayers()) {
			p.spigot().sendMessage(messageToSend);
		}

		return true;
	}

	// Ajout de l'écouteur pour intercepter la commande fictive
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		final String message = event.getMessage();
		final Player player = event.getPlayer();

		// Vérifie si la commande est la commande fictive utilisée pour le report
		if (message.startsWith("/report ")) {
			// Divise le message en parties
			final String[] parts = message.split(" ", 3);
			if (parts.length == 3) {
				final String targetName = parts[1];
				final String reportMessage = parts[2];

				final Player target = Bukkit.getPlayer(targetName);

				if (player.getName().equals(targetName)) {
					player.sendMessage(this.getReportPrefix() + ConfigUtil.getString("report.cantReportYourself"));
					event.setCancelled(true);
					return;
				}

				if (target != null) {
					// Appel de la méthode pour envoyer le report sur Discord
					this.reportOnDiscordWebhook(player, target, reportMessage);
					player.sendMessage(this.getReportPrefix() + ConfigUtil.getString("report.success"));
				} else {
					player.sendMessage(this.getReportPrefix() + ConfigUtil.getString("report.playerNotFound", target));
				}
			} else {
				player.sendMessage(this.getReportPrefix() + ConfigUtil.getString("report.usage"));
			}
			// Annule l'événement pour empêcher l'exécution de la commande fictive
			event.setCancelled(true);
		}
	}

	public void reportOnDiscordWebhook(Player player, Player target, String message) {
		if ((player == null) || (target == null) || (message == null)) {
			return;
		}

		final DiscordWebhookUtil webhook = new DiscordWebhookUtil(ConfigUtil.getString("report.webhook.url"));

		webhook.setUsername(ConfigUtil.getString("report.webhook.username"));
		webhook.setAvatarUrl(ConfigUtil.getString("report.webhook.avatarUrl"));

		webhook.addEmbed(
				new DiscordWebhookUtil.EmbedObject().setTitle(ConfigUtil.getString("report.embed.title", target))
						.addField(ConfigUtil.getString("report.embed.reporter", player), player.getName(), true)
						.addField(ConfigUtil.getString("report.embed.target", target), target.getName(), true)
						.addField(ConfigUtil.getString("report.embed.message"), message, false).setColor(Color.RED)
						.setColor(Color.RED).setThumbnail(ConfigUtil.getString("report.embed.thumbnail"))
						.setFooter(ConfigUtil.getString("report.embed.footerText"),
								ConfigUtil.getString("report.embed.footerIcon"))
						.setTimestampNow());

		try {
			webhook.execute();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public String getReportPrefix() {
		return ConfigUtil.getString("report.prefix");
	}
}
