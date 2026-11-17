package net.hidestream;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public record StreamListener(HideStream plugin) implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (plugin.getConfig().getBoolean("settings.hide-join", true)) {
            event.joinMessage(null);

            if (plugin.getConfig().getBoolean("settings.send-to-console", true)) {
                plugin.getComponentLogger().info(plugin.getMessage("").append(
                        net.kyori.adventure.text.Component.text(event.getPlayer().getName() + " joined the game (Hidden from chat)")
                ));
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (plugin.getConfig().getBoolean("settings.hide-quit", true)) {
            event.quitMessage(null);

            if (plugin.getConfig().getBoolean("settings.send-to-console", true)) {
                plugin.getComponentLogger().info(plugin.getMessage("").append(
                        net.kyori.adventure.text.Component.text(event.getPlayer().getName() + " left the game (Hidden from chat)")
                ));
            }
        }
    }

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        if (plugin.getConfig().getBoolean("settings.hide-advancements", true)) {
            event.message(null);
        }
    }

    // НОВЫЙ МЕТОД: Скрытие сообщений о смерти
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (plugin.getConfig().getBoolean("settings.hide-deaths", true)) {
            event.deathMessage(null);

            if (plugin.getConfig().getBoolean("settings.send-to-console", true)) {
                plugin.getComponentLogger().info(plugin.getMessage("").append(
                        net.kyori.adventure.text.Component.text(event.getEntity().getName() + " died (Hidden from chat)")
                ));
            }
        }
    }
}