package committee.nova.petphrasex;

import committee.nova.petphrasex.config.PetPhraseConfigX;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class PetPhraseX implements ModInitializer {
    public static final String MOD_ID = "petphrasex";

    @Override
    public void onInitialize() {
        PetPhraseConfigX.load();
        if (FabricLoader.getInstance().isModLoaded("fabric-api")) {
            try {
                Class<?> bootstrap = Class.forName("committee.nova.petphrasex.integration.FabricApiCommonBootstrap");
                bootstrap.getMethod("initialize").invoke(null);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Failed to initialize optional Fabric API features.", e);
            }
        }
        System.out.println("PetPhraseX initialized.");
    }
}
