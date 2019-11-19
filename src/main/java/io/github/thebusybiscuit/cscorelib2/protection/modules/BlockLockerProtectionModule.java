package io.github.thebusybiscuit.cscorelib2.protection.modules;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import io.github.thebusybiscuit.cscorelib2.protection.ProtectableAction;
import io.github.thebusybiscuit.cscorelib2.protection.ProtectionModule;
import nl.rutgerkok.blocklocker.BlockLockerAPI;
import nl.rutgerkok.blocklocker.BlockLockerPlugin;


public class BlockLockerProtectionModule implements ProtectionModule {
	
	private BlockLockerPlugin plugin;
	
	@Override
	public void load() {
		plugin = BlockLockerAPI.getPlugin();
	}

	@Override
	public String getName() {
		return "BlockLocker";
	}
	
	@Override
	public boolean hasPermission(OfflinePlayer p, Location l, ProtectableAction action) {
		if (!action.isBlockAction()) return true;

		return(BlockLockerAPI.isAllowed(p.getPlayer(), l.getBlock(), false));
	}
}
	
