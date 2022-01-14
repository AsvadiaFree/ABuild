package fr.asvadia.abuild.utils;

import fr.asvadia.abuild.utils.file.FileManager;
import fr.asvadia.abuild.utils.file.Files;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BlockCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p && sender.isOp()) {
            FileManager.getValues().get(Files.Config).set("Item", p.getInventory().getItemInMainHand());
            FileManager.save(Files.Config);
        }
        return false;
    }
}
