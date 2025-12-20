package committee.nova.petphrasex.mixin;

import committee.nova.petphrasex.config.PetPhraseConfig;
import committee.nova.petphrasex.util.StringUtil;
import net.minecraft.client.gui.screens.ChatScreen; // Yarn: screen.ChatScreen -> Mojang: screens.ChatScreen
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChatScreen.class)
public abstract class MixinChatScreen {

    @ModifyVariable(
            method = "handleChatInput",
            at = @At("HEAD"),
            argsOnly = true
    )
    private String modifyMsg(String chatMessage) {
        PetPhraseConfig config = PetPhraseConfig.get();

        return StringUtil.fillPetPhraseIn(
                chatMessage,
                config.petPhrase,
                config.filteredPrefix
        );
    }
}