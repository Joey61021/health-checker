package com.healthchecker.plugin;

import com.healthchecker.plugin.commands.GenericCmd;
import com.healthchecker.plugin.services.Message.MessageService;
import com.healthchecker.plugin.services.SoundService;
import com.healthchecker.plugin.utilities.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Config config;

    private MessageService messageService;
    private SoundService   soundService;

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
        soundService   = new SoundService(config);
    }

    void registerCommands() {
	    getCommand("health").setExecutor(new GenericCmd(messageService, soundService));
	    getCommand("hunger").setExecutor(new GenericCmd(messageService, soundService));
    }
}
