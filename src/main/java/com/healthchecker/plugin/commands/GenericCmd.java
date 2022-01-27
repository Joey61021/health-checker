package com.healthchecker.plugin.commands;

import com.healthchecker.plugin.services.Message.Message;
import com.healthchecker.plugin.services.Message.MessageService;
import com.healthchecker.plugin.services.SoundService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GenericCmd implements CommandExecutor {

	@NonNull
	private final MessageService messageService;
	@NonNull
	private final SoundService   soundService;

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
		if (sender instanceof Player) soundService.playSound(sender, "Commands." + generic + ".Sound");
		return false;
	}
}
