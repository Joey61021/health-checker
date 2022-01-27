package com.healthchecker.plugin.services;

import com.healthchecker.plugin.utilities.Config;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class SoundService {

    @NonNull
    private final Config config;

    public void playSound(CommandSender receiver, String soundRoot) {
        if (!(receiver instanceof Player)) return;
        Player player = (Player) receiver;
        if (config.getBoolean(soundRoot + ".Enabled"))
            player.playSound(player.getLocation(), Sound.valueOf(config.getString(soundRoot + ".Type").toUpperCase()), 1, 1);
    }
}
