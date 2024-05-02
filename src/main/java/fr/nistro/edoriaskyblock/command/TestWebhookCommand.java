package fr.nistro.edoriaskyblock.command;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nistro.edoriaskyblock.util.ConfigUtil;
import fr.nistro.edoriaskyblock.util.DiscordWebhookUtil;

public class TestWebhookCommand implements CommandExecutor {
	
	private final String webhookUrl = ConfigUtil.getString("webhook.url");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Player player = (Player) sender;
        
        final DiscordWebhookUtil webhook = new DiscordWebhookUtil();
        
        webhook.setUsername(ConfigUtil.getString("webhook.embed.username"));

        webhook.addEmbed(
                new DiscordWebhookUtil.EmbedObject()
                        .setTitle("Test Webhook")
                        .setDescription("This is a test webhook")
                        .setColor(Color.GREEN)
                        .setTimestampNow()
                        );
        
        try {
            webhook.execute();
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "§6[§eEdoriaSkyblock§6] §r§aWebhook sent");
        } catch (final Exception e) {
        	Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "§6[§eEdoriaSkyblock§6] §r§cError while sending the webhook");
        	player.sendMessage(ChatColor.RED + "§6[§eEdoriaSkyblock§6] §r§cError while sending the webhook");
            e.printStackTrace();
        }
        
        return true;
	}

}
