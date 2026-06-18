package committee.nova.petphrasex.integration;

import committee.nova.petphrasex.client.ServerFeatureHooks;
import committee.nova.petphrasex.client.ServerForcedPhraseState;
import committee.nova.petphrasex.network.ForcedPhraseSyncPayload;
import committee.nova.petphrasex.network.ReclaimForcedPhrasePayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public final class FabricApiClientBootstrap {
    private static boolean initialized;

    private FabricApiClientBootstrap() {
    }

    public static void initialize() {
        if (initialized) {
            return;
        }

        initialized = true;
        ClientPlayNetworking.registerGlobalReceiver(ForcedPhraseSyncPayload.TYPE, (payload, context) ->
                ServerForcedPhraseState.apply(payload));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> ServerForcedPhraseState.clear());
        ServerFeatureHooks.installReclaimRequestSender(() -> ClientPlayNetworking.send(new ReclaimForcedPhrasePayload(true)));
    }
}
