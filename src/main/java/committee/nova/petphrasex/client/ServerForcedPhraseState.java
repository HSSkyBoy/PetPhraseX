package committee.nova.petphrasex.client;

import committee.nova.petphrasex.network.ForcedPhraseSyncPayload;

public final class ServerForcedPhraseState {
    private static boolean forcedByServer;
    private static ForcedPhraseSyncPayload payload = new ForcedPhraseSyncPayload(false, "", true, "", "", "", "");

    private ServerForcedPhraseState() {
    }

    public static void apply(ForcedPhraseSyncPayload forcedPayload) {
        payload = forcedPayload;
        forcedByServer = forcedPayload.forcedByServer();
    }

    public static boolean isForcedByServer() {
        return forcedByServer;
    }

    public static ForcedPhraseSyncPayload payload() {
        return payload;
    }

    public static void clear() {
        forcedByServer = false;
        payload = new ForcedPhraseSyncPayload(false, "", true, "", "", "", "");
    }
}
