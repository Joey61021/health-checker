package com.healthchecker.plugin;

import com.healthchecker.plugin.commands.GenericCmd;
import com.healthchecker.plugin.services.Message.MessageService;
import com.healthchecker.plugin.utilities.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Config config;

    private MessageService messageService;

    @Override
    public void onEnable() {
	    loadConfig();
        setupServices();
	    registerCommands();
    }

    void loadConfig() {
        config = new Config(this, getDataFolder(), "config", "config.yml");
  }

    void setupServices() {
        messageService = new MessageService(config);
    }

    void registerCommands() {
	    getCommand("health").setExecutor(new GenericCmd(messageService, config));
	    getCommand("hunger").setExecutor(new GenericCmd(messageService, config));
    }
}
