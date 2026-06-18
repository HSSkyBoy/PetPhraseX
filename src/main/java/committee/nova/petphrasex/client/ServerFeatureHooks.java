package committee.nova.petphrasex.client;

public final class ServerFeatureHooks {
    private static Runnable reclaimRequestSender;

    private ServerFeatureHooks() {
    }

    public static void installReclaimRequestSender(Runnable sender) {
        reclaimRequestSender = sender;
    }

    public static boolean sendReclaimRequest() {
        if (reclaimRequestSender == null) {
            return false;
        }

        reclaimRequestSender.run();
        return true;
    }

    public static boolean hasReclaimRequestSender() {
        return reclaimRequestSender != null;
    }

    public static void clear() {
        reclaimRequestSender = null;
    }
}
