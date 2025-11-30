package committee.nova.petphrasex;

import committee.nova.petphrasex.client.PetPhraseConfigScreen;
import committee.nova.petphrasex.config.PetPhraseConfig;
import committee.nova.petphrasex.util.StringUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.ClientChatEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.List;

@Mod(PetPhraseX.MODID)
public class PetPhraseX {
    public static final String MODID = "petphrasex";

    public PetPhraseX(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, PetPhraseConfig.CLIENT_SPEC);
        container.registerExtensionPoint(IConfigScreenFactory.class, (client, parent) -> new PetPhraseConfigScreen(parent));
    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onChatMessage(ClientChatEvent event) {
            String original = event.getMessage();

            String suffix = PetPhraseConfig.CLIENT.petPhrase.get();
            @SuppressWarnings("unchecked")
            List<String> prefixes = (List<String>) PetPhraseConfig.CLIENT.filteredPrefix.get();

            String modified = StringUtil.fillPetPhraseIn(original, suffix, prefixes);

            if (!original.equals(modified)) {
                event.setMessage(modified);
            }
        }
    }
}