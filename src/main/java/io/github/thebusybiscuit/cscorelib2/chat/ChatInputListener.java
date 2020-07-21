package io.github.thebusybiscuit.cscorelib2.chat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

class ChatInputListener implements Listener {

    private Plugin plugin;
    protected Map<UUID, Set<IChatInput>> handlers;

    protected ChatInputListener(Plugin plugin) {
        this.plugin = plugin;
        this.handlers = new HashMap<>();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void addCallback(UUID uuid, IChatInput input) {
        Set<IChatInput> callbacks = handlers.computeIfAbsent(uuid, id -> new HashSet<>());
        callbacks.add(input);
    }

    @EventHandler
    public void onDisable(PluginDisableEvent e) {
        if (e.getPlugin() == plugin) {
            ChatInput.listener = null;
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        handlers.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        handlers.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e) {
        String msg = e.getMessage().replace(ChatColor.COLOR_CHAR, '&');
        checkInput(e, e.getPlayer(), msg);
    }

    @EventHandler
    public void onComamnd(PlayerCommandPreprocessEvent e) {
        checkInput(e, e.getPlayer(), e.getMessage());
    }

    private void checkInput(Cancellable e, Player p, String msg) {
        Set<IChatInput> callbacks = handlers.get(p.getUniqueId());

        if (callbacks != null) {
            Iterator<IChatInput> iterator = callbacks.iterator();

            while (iterator.hasNext()) {
                IChatInput handler = iterator.next();

                if (handler.test(msg)) {
                    iterator.remove();
                    plugin.getServer().getScheduler().runTask(plugin, () -> handler.onChat(p, msg));

                    e.setCancelled(true);
                    return;
                }
            }

            if (callbacks.isEmpty()) handlers.remove(p.getUniqueId());
        }
    }

}
