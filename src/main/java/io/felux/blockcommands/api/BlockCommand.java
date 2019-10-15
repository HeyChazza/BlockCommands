package io.felux.blockcommands.api;

import io.felux.blockcommands.hook.PlaceholderAPIHook;
import io.felux.blockcommands.util.ActionHandler;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class BlockCommand {

    private Location location;
    private List<String> commands;
    public static Map<Location, BlockCommand> blockCommands;
    public BlockCommand(Location location, List<String> commands) {
        this.location = location;
        this.commands = commands;
    }

    public Location getLocation() {
        return location;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void execute(Player player) {
        getCommands().forEach(command -> {
            command = PlaceholderAPIHook.setPlaceholders(player, command);
            command = command.replace("%player%", player.getName());
            command = command.replace("%uuid%", player.getUniqueId().toString());
            ActionHandler.executeActions(player, command);
        });
    }

    public static Map<Location, BlockCommand> getAll() {
        return blockCommands;
    }

    public static void add(BlockCommand blockCommand) {
        getAll().put(blockCommand.getLocation(), blockCommand);
    }
    public static void remove(BlockCommand blockCommand) {
        getAll().remove(blockCommand.getLocation());
    }
}
