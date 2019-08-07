package gg.plugins.blockcommands;

import gg.plugins.blockcommands.api.BlockCommand;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class BlockListener implements Listener {

    private BlockCommands blockCommands;

    public BlockListener(BlockCommands blockCommands) {
        this.blockCommands = blockCommands;
        Bukkit.getPluginManager().registerEvents(this, blockCommands);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();

        if (!Bukkit.getVersion().contains("1.8")) {
            if (e.getHand() == EquipmentSlot.OFF_HAND) {
                return; // off hand packet, ignore.
            }
        }

        if (block == null) return;

        BlockCommand blockCommand = BlockCommand.getAll().get(block.getLocation());
        if (blockCommand == null) return;
        blockCommand.execute(player);
    }
}
