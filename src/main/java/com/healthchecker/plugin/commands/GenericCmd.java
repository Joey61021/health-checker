package com.healthchecker.plugin.commands;

import com.healthchecker.plugin.Main;
import com.healthchecker.plugin.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GenericCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender.hasPermission("healthchecker." + cmd.getName()) || sender.hasPermission("healthchecker.*"))) {
			sender.sendMessage(Utils.color(Main.getConfig.getString("Messages.No-Permission")));
			return false;
		}
		if (args.length == 0) {
			sender.sendMessage(Utils.color(Main.getConfig.getString("Messages.Enter-Player")));
			return false;
		}
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null || !target.isOnline()) {
			sender.sendMessage(Utils.color(Main.getConfig.getString("Messages.No-Player")));
			return false;
		}
		String generic = cmd.getName().toUpperCase().charAt(0) + cmd.getName().substring(1).toLowerCase();
		double value = generic.equals("Health") ? target.getHealth() : target.getFoodLevel();
		sender.sendMessage(Utils.color(Main.getConfig.getString("Commands." + generic + ".Message")
				.replace("%target%", target.getName()).replace("%" + cmd.getName() + "%", "" + Math.round(value))));
		if (sender instanceof Player && Main.getConfig.getBoolean("Commands." + generic + ".Sound.Enabled"))
			((Player) sender).playSound(((Player) sender).getLocation(), Sound.valueOf(Main.getConfig.getString("Commands." + generic + ".Sound.Type").toUpperCase()), 1, 1);
		return false;
	}
}
