package com.healthchecker.plugin.commands;

import com.healthchecker.plugin.services.Message.Message;
import com.healthchecker.plugin.services.Message.MessageService;
import com.healthchecker.plugin.utilities.Config;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GenericCmd implements CommandExecutor {

	@NonNull
	private final MessageService messageService;
	@NonNull
	private final Config         config;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender.hasPermission("healthchecker." + cmd.getName()) || sender.hasPermission("healthchecker.*"))) {
			messageService.sendMessage(sender, Message.GENERAL_NO_PERMISSION);
			return false;
		}
		if (args.length == 0) {
			messageService.sendMessage(sender, Message.GENERAL_ENTER_PLAYER);
			return false;
		}
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null || !target.isOnline()) {
			messageService.sendMessage(sender, Message.GENERAL_NO_PLAYER);
			return false;
		}
		String generic = cmd.getName().toUpperCase().charAt(0) + cmd.getName().substring(1).toLowerCase();
		boolean health = cmd.getName().equalsIgnoreCase("health");
		messageService.sendMessage(sender,
									health ? Message.CMD_HEALTH : Message.CMD_HUNGER,
									(s) -> s.replace("%target%", target.getName())
											.replace("%" + cmd.getName() + "%", String.valueOf(Math.round(health ? target.getHealth() : target.getFoodLevel()))));
		if (sender instanceof Player && config.getBoolean("Commands." + generic + ".Sound.Enabled"))
			((Player) sender).playSound(((Player) sender).getLocation(), Sound.valueOf(config.getString("Commands." + generic + ".Sound.Type").toUpperCase()), 1, 1);
		return false;
	}
}
