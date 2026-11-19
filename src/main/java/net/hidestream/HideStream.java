package net.hidestream;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class HideStream extends JavaPlugin {

    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new StreamListener(this), this);

        PluginCommand cmd = getCommand("hidestream");
        if (cmd != null) {
            cmd.setExecutor((sender, command, label, args) -> {
                if (!sender.hasPermission("hidestream.admin")) {
                    sender.sendMessage(getMessage("no-permission"));
                    return true;
                }
                if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    sender.sendMessage(getMessage("reload-success"));
                    return true;
                }

                sender.sendMessage(getMessage("usage"));
                return true;
            });
        } else {
            getLogger().severe("Command /hidestream not found in plugin.yml!");
        }

        getLogger().info("HideStream plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("HideStream plugin disabled.");
    }

    public Component getMessage(String path) {
        String prefix = getConfig().getString("messages.prefix", "");
        String message = getConfig().getString("messages." + path, "&cMessage not found: " + path);
        return LEGACY.deserialize(prefix + message);
    }
}
