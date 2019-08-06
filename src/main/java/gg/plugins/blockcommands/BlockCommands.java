package gg.plugins.blockcommands;

import gg.plugins.blockcommands.api.BlockCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class BlockCommands extends JavaPlugin {

    @Override
    public void onEnable() {
        BlockCommand.blockCommands = new HashMap<>();
    }

    @Override
    public void onDisable() {

    }
}
