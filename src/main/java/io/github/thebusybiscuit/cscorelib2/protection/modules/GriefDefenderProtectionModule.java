package io.github.thebusybiscuit.cscorelib2.protection.modules;

import com.griefdefender.api.Core;
import com.griefdefender.api.GriefDefender;
import com.griefdefender.api.claim.Claim;
import com.griefdefender.api.claim.ClaimTypes;
import io.github.thebusybiscuit.cscorelib2.protection.ProtectableAction;
import io.github.thebusybiscuit.cscorelib2.protection.ProtectionModule;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

public class GriefDefenderProtectionModule implements ProtectionModule {
    private final Plugin plugin;
    private Core core;

    public GriefDefenderProtectionModule(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void load() {
        core = GriefDefender.getCore();
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public boolean hasPermission(OfflinePlayer p, Location l, ProtectableAction action) {
        final Claim claim = core.getClaimManager(l.getWorld().getUID()).getClaimAt(l.getBlockX(), l.getBlockY(), l.getBlockZ());
        return  claim.getType() == ClaimTypes.WILDERNESS || claim.isTrusted(p.getUniqueId());
    }
}
