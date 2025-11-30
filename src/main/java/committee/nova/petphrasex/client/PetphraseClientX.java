package committee.nova.petphrasex.client;

import committee.nova.petphrasex.config.Configuration;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class PetphraseClientX implements ClientModInitializer {
    private static ConfigHolder<Configuration> holder;

    @Override
    public void onInitializeClient() {
        holder = AutoConfig.register(Configuration.class, GsonConfigSerializer::new);
        System.out.println("PetphraseClient initialized with config.");
    }

    public static Configuration getConfig() {
        return holder.getConfig();
    }
}
