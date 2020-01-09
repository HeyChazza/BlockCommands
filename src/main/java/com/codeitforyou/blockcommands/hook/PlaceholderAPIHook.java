package com.codeitforyou.blockcommands.hook;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderAPIHook {
    public static String setPlaceholders(Player p, String text) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return replace(p, text);
        }
        return text;
    }

    private static String replace(Player p, String text) {
        return PlaceholderAPI.setPlaceholders(p, text);
    }
}
