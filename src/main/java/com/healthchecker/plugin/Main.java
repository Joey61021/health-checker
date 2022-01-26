package com.healthchecker.plugin;

import com.healthchecker.plugin.commands.GenericCmd;
import com.healthchecker.plugin.utilities.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
	    loadConfig();
	    registerCommands();
    }

    void loadConfig() {
        config = new Config(this, getDataFolder(), "config", "config.yml");
  }

    void registerCommands() {
	    getCommand("health").setExecutor(new GenericCmd(config));
	    getCommand("hunger").setExecutor(new GenericCmd(config));
    }
}
