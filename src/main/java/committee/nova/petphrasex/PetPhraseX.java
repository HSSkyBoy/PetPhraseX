package committee.nova.petphrasex;

import committee.nova.petphrasex.config.PetPhraseConfigX;
import net.fabricmc.api.ModInitializer;

public class PetPhraseX implements ModInitializer {
    @Override
    public void onInitialize() {
        PetPhraseConfigX.load();
        System.out.println("PetPhraseX initialized.");
    }
}
