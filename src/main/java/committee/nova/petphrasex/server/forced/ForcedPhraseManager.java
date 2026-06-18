package committee.nova.petphrasex.server.forced;

import committee.nova.petphrasex.config.PetPhraseConfigX;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public final class ForcedPhraseManager {
    private static final Map<MinecraftServer, Map<UUID, ForcedPhraseData>> TRANSIENT_STORES = new WeakHashMap<>();
    private static Consumer<ServerPlayer> syncHandler = player -> {
    };

    private ForcedPhraseManager() {
    }

    public static void installSyncHandler(Consumer<ServerPlayer> handler) {
        syncHandler = handler == null ? player -> {
        } : handler;
    }

    public static ForcedPhraseData get(ServerPlayer player) {
        return get(player.level().getServer(), player.getUUID());
    }

    public static ForcedPhraseData getActive(ServerPlayer player) {
        ForcedPhraseData data = get(player);
        return data != null && data.active() ? data : null;
    }

    public static void setField(ServerPlayer player, ForcedPhraseField field, String value) {
        MinecraftServer server = player.level().getServer();
        if (server == null) return;

        Map<UUID, ForcedPhraseData> store = getStore(server);
        ForcedPhraseData data = store.get(player.getUUID());
        if (data == null) {
            data = ForcedPhraseData.fromConfig(PetPhraseConfigX.get());
        }

        store.put(player.getUUID(), data.withField(field, value));
        markDirty(server);
        sync(player);
    }

    public static boolean clear(ServerPlayer player) {
        MinecraftServer server = player.level().getServer();
        if (server == null) return false;

        ForcedPhraseData removed = getStore(server).remove(player.getUUID());
        if (removed != null) {
            markDirty(server);
            sync(player);
            return true;
        }
        return false;
    }

    public static void release(ServerPlayer player) {
        MinecraftServer server = player.level().getServer();
        if (server == null) return;

        Map<UUID, ForcedPhraseData> store = getStore(server);
        ForcedPhraseData data = store.get(player.getUUID());
        if (data == null) return;

        store.put(player.getUUID(), data.withActive(false));
        markDirty(server);
        sync(player);
    }

    public static void sync(ServerPlayer player) {
        syncHandler.accept(player);
    }

    private static ForcedPhraseData get(MinecraftServer server, UUID uuid) {
        if (server == null) return null;
        return getStore(server).get(uuid);
    }

    private static Map<UUID, ForcedPhraseData> getStore(MinecraftServer server) {
        if (server.isDedicatedServer()) {
            return getPersistentStore(server).phrases();
        }
        return TRANSIENT_STORES.computeIfAbsent(server, ignored -> new HashMap<>());
    }

    private static void markDirty(MinecraftServer server) {
        if (server.isDedicatedServer()) {
            getPersistentStore(server).setDirty();
        }
    }

    private static ForcedPhraseSavedData getPersistentStore(MinecraftServer server) {
        ServerLevel level = server.getLevel(Level.OVERWORLD);
        if (level == null) {
            throw new IllegalStateException("Overworld is not available for forced phrase storage.");
        }

        var dataStorage = level.getDataStorage();
        return dataStorage.computeIfAbsent(ForcedPhraseSavedData.TYPE);
    }
}
