package committee.nova.petphrasex.integration;

import committee.nova.petphrasex.network.ForcedPhraseSyncPayload;
import committee.nova.petphrasex.network.ReclaimForcedPhrasePayload;
import committee.nova.petphrasex.server.PetPhraseForceCommand;
import committee.nova.petphrasex.server.forced.ForcedPhraseManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public final class FabricApiCommonBootstrap {
    private static boolean initialized;

    private FabricApiCommonBootstrap() {
    }

    public static void initialize() {
        if (initialized) {
            return;
        }

        initialized = true;
        PayloadTypeRegistry.clientboundPlay().register(ForcedPhraseSyncPayload.TYPE, ForcedPhraseSyncPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(ReclaimForcedPhrasePayload.TYPE, ReclaimForcedPhrasePayload.CODEC);
        ForcedPhraseManager.installSyncHandler(FabricApiCommonBootstrap::syncPlayer);
        CommandRegistrationCallback.EVENT.register(PetPhraseForceCommand::register);
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> syncPlayer(handler.player));
        ServerPlayNetworking.registerGlobalReceiver(ReclaimForcedPhrasePayload.TYPE, (payload, context) -> {
            if (payload.reclaim()) {
                ForcedPhraseManager.release(context.player());
            }
        });
    }

    private static void syncPlayer(ServerPlayer player) {
        ServerPlayNetworking.send(player, ForcedPhraseSyncPayload.fromData(ForcedPhraseManager.get(player)));
    }
}
