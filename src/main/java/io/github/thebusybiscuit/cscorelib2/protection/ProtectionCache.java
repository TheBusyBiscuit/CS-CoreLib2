package io.github.thebusybiscuit.cscorelib2.protection;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.github.thebusybiscuit.cscorelib2.blocks.BlockPosition;
import io.github.thebusybiscuit.cscorelib2.collections.OptionalBoolean;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ProtectionCache {

    private static final CacheBuilder<Object, Object> defaultBuilder = CacheBuilder.newBuilder()
        .concurrencyLevel(1)
        .expireAfterAccess(5, TimeUnit.MINUTES);

    private final Cache<UUID, Cache<BlockPosition, Boolean>> permissionCache = defaultBuilder.build();

    @Nonnull
    public OptionalBoolean getCachedValue(@Nonnull UUID uuid, @Nonnull BlockPosition position) {
        Cache<BlockPosition, Boolean> map = permissionCache.getIfPresent(uuid);
        if (map != null && map.size() > 0) {
            Boolean allowed = map.getIfPresent(position);

            if (allowed != null) {
                return OptionalBoolean.of(allowed);
            }
        }
        return OptionalBoolean.empty();
    }

    public void storeValue(@Nonnull UUID uuid, @Nonnull BlockPosition position, boolean hasPermission) {
        Cache<BlockPosition, Boolean> map = permissionCache.getIfPresent(uuid);
        if (map == null) {
            map = defaultBuilder.build();
        }
        map.put(position, hasPermission);
        permissionCache.put(uuid, map);
    }
}
