package committee.nova.petphrasex.client;

import committee.nova.petphrasex.config.PetPhraseConfigX;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public class PetphraseClientX implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PetPhraseConfigX.load();
        if (FabricLoader.getInstance().isModLoaded("fabric-api")) {
            try {
                Class<?> bootstrap = Class.forName("committee.nova.petphrasex.integration.FabricApiClientBootstrap");
                bootstrap.getMethod("initialize").invoke(null);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Failed to initialize optional Fabric API client features.", e);
            }
        }
        System.out.println("PetPhraseX initialized with native config system.");
    }

    // 其他类现在可直接调用 PetPhraseConfigX.get() 获取配置。
}
