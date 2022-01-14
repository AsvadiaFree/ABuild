package fr.asvadia.abuild.utils;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import fr.asvadia.abuild.Main;
import fr.asvadia.abuild.utils.file.FileManager;
import fr.asvadia.abuild.utils.file.Files;
import fr.skyfighttv.simpleitem.ItemCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public class Block {
    public static ItemStack item;

    public static void init() {
        YamlConfiguration config = FileManager.getValues().get(Files.Config);
        if (config.get("Item") != null)
            item = (ItemStack) config.get("Item");
    }

    public static void build(Location location) {
        try {
            File[] files = Main.getSchematics().listFiles();
            assert files != null;
            File base = files[new Random().nextInt(files.length)];
            ClipboardFormat format = ClipboardFormats.findByFile(base);
            assert format != null;
            try (ClipboardReader reader = format.getReader(new FileInputStream(base))) {
                Clipboard clipboard = reader.read();
                try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(location.getWorld()))) {
                    Operation operation = new ClipboardHolder(clipboard)
                            .createPaste(editSession)
                            .to(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()))
                            .copyEntities(true)
                            .ignoreAirBlocks(false)
                            .build();
                    Operations.complete(operation);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
