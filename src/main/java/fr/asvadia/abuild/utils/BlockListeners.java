package fr.asvadia.abuild.utils;

import org.bukkit.Chunk;
import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListeners implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlaceBlock(PlayerInteractEvent event) {
        if (!event.isCancelled()
                && event.getAction() == Action.RIGHT_CLICK_BLOCK
                && event.getItem() != null
                && event.getItem().equals(Block.item)
                && event.getClickedBlock() != null) {
            Chunk chunk = event.getClickedBlock().getLocation().getChunk();
            int y = event.getPlayer().getWorld().getHighestBlockYAt(chunk.getX() + 8, chunk.getZ() + 8, HeightMap.MOTION_BLOCKING_NO_LEAVES);
            Block.build(new Location(chunk.getWorld(), chunk.getX()+8, y, chunk.getZ()));
        }
    }
}
