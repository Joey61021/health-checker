package com.healthchecker.plugin.commands;

import com.healthchecker.plugin.utilities.Config;
import com.healthchecker.plugin.utilities.Utils;
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
	private final Config config;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender.hasPermission("healthchecker." + cmd.getName()) || sender.hasPermission("healthchecker.*"))) {
			sender.sendMessage(Utils.color(config.getString("Messages.No-Permission")));
			return false;
		}
		if (args.length == 0) {
			sender.sendMessage(Utils.color(config.getString("Messages.Enter-Player")));
			return false;
		}
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null || !target.isOnline()) {
			sender.sendMessage(Utils.color(config.getString("Messages.No-Player")));
			return false;
		}
		String generic = cmd.getName().toUpperCase().charAt(0) + cmd.getName().substring(1).toLowerCase();
		double value = generic.equals("Health") ? target.getHealth() : target.getFoodLevel();
		sender.sendMessage(Utils.color(config.getString("Commands." + generic + ".Message")
				.replace("%target%", target.getName()).replace("%" + cmd.getName() + "%", "" + Math.round(value))));
		if (sender instanceof Player && config.getBoolean("Commands." + generic + ".Sound.Enabled"))
			((Player) sender).playSound(((Player) sender).getLocation(), Sound.valueOf(config.getString("Commands." + generic + ".Sound.Type").toUpperCase()), 1, 1);
		return false;
	}
}
