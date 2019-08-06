package gg.plugins.blockcommands.util;

import org.bukkit.ChatColor;

public class Common {
    public static String translate(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
