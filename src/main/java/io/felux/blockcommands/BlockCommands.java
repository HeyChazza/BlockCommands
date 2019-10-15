package io.felux.blockcommands;

import io.felux.blockcommands.api.BlockCommand;
import io.felux.blockcommands.util.Common;
import io.felux.blockcommands.util.LocSerializer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;

public class BlockCommands extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        new BlockListener(this);
        this.getCommand("blockcommands").setExecutor(this);
        pull(false);
    }

    public void pull(boolean reload) {
        if (reload) reloadConfig();
        else saveDefaultConfig();

        BlockCommand.blockCommands = new HashMap<>();

        if (getConfig().getConfigurationSection("blocks") != null) {
            getConfig().getConfigurationSection("blocks").getKeys(false).forEach(block -> {
                Location loc = LocSerializer.fromString(block);
                BlockCommand.getAll().put(loc, new BlockCommand(loc, getConfig().getStringList("blocks." + block + ".commands")));
            });
        }
    }

    public void push() {
        BlockCommand.getAll().forEach((location, blockCommand) -> {
            getConfig().set("blocks." + LocSerializer.toString(location) + ".commands", blockCommand.getCommands());
        });

        saveConfig();
    }

    @Override
    public void onDisable() {

    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Common.translate(getConfig().getString("messages.main", "&8[&fBlockCommands&8] &7A simple, easy to use plugin for adding commands to blocks.")));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("bcommands.reload")) {
                pull(true);
                sender.sendMessage(Common.translate(getConfig().getString("messages.reload", "&7Configuration reloaded.")));
            } else {
                sender.sendMessage(Common.translate(getConfig().getString("messages.permission", "&7You don't have permission to do that.")));
            }
            return true;
        } else if (args[0].equalsIgnoreCase("add")) {
            if (sender.hasPermission("bcommands.add")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    Block block = player.getTargetBlock(null, 5);
                    Location location = block.getLocation();

                    BlockCommand blockCommand = new BlockCommand(location, Arrays.asList("[CONSOLE] msg %player% Go to 'config.yml' to edit this.", "[PLAYER] me Yay!"));
                    BlockCommand.add(blockCommand);
                    getConfig().set("blocks." + LocSerializer.toString(location) + ".commands", blockCommand.getCommands());
                    saveConfig();

                    player.sendMessage(Common.translate(getConfig().getString("messages.add", "&7Block added.")));
                } else {
                    sender.sendMessage(Common.translate(getConfig().getString("messages.player", "&7You must be a player to do that.")));
                }
            } else {
                sender.sendMessage(Common.translate(getConfig().getString("messages.permission", "&7You don't have permission to do that.")));
            }
            return true;
        } else if (args[0].equalsIgnoreCase("remove")) {
            if (sender.hasPermission("bcommands.remove")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    Block block = player.getTargetBlock(null, 5);
                    Location location = block.getLocation();

                    BlockCommand blockCommand = BlockCommand.getAll().get(block.getLocation());
                    if (blockCommand == null) return false;

                    BlockCommand.remove(blockCommand);
                    getConfig().set("blocks." + LocSerializer.toString(location), null);
                    saveConfig();

                    player.sendMessage(Common.translate(getConfig().getString("messages.remove", "&7Block removed.")));
                } else {
                    sender.sendMessage(Common.translate(getConfig().getString("messages.player", "&7You must be a player to do that.")));
                }
            } else {
                sender.sendMessage(Common.translate(getConfig().getString("messages.permission", "&7You don't have permission to do that.")));
            }

        }
        return false;
    }
}
