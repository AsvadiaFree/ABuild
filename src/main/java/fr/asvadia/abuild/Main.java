package fr.asvadia.abuild;

import fr.asvadia.abuild.utils.Block;
import fr.asvadia.abuild.utils.BlockCommands;
import fr.asvadia.abuild.utils.BlockListeners;
import fr.asvadia.abuild.utils.file.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    private static Main instance;
    private static File schematics;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        try {
            FileManager.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        schematics = new File(getDataFolder().getPath() + "/Schematics");
        if (!schematics.exists()) {
            try {
                schematics.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Block.init();
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlockListeners(), this);
        getCommand("abuild").setExecutor(new BlockCommands());
    }

    public static File getSchematics() {
        return schematics;
    }

    public static Main getInstance() {
        return instance;
    }
}
