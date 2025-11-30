package committee.nova.petphrasex.client;

import committee.nova.petphrasex.config.PetPhraseConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class PetphraseClientX implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // 模组启动时，加载本地配置文件
        PetPhraseConfig.load();
        System.out.println("PetPhraseX initialized with native config system.");
    }

    // 注意：原来的 getConfig() 方法已经不需要了。
    // 其他类（如 Mixin）现在直接调用 PetPhraseConfig.get() 即可获取配置。
}