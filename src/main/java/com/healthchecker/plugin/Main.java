package com.healthchecker.plugin;

import com.healthchecker.plugin.commands.GenericCmd;
import com.healthchecker.plugin.misc.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Config getConfig;

    @Override
    public void onEnable() {
	    loadConfig();
	    registerCommands();
    }

    private void loadConfig() {
        getConfig = new Config(this, getDataFolder(), "config", "config.yml");
  }

    private void registerCommands() {
	    getCommand("health").setExecutor(new GenericCmd());
	    getCommand("hunger").setExecutor(new GenericCmd());
    }
}
