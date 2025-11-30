package committee.nova.petphrasex.mixin;

import committee.nova.petphrasex.client.PetphraseClient;
import committee.nova.petphrasex.config.Configuration;
import committee.nova.petphrasex.util.StringUtil;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChatScreen.class)
public abstract class MixinChatScreen {

    @ModifyVariable(
            method = "sendMessage",
            at = @At("HEAD"),
            argsOnly = true
    )
    private String modifyMsg(String chatMessage) {
        Configuration config = PetphraseClient.getConfig();
        return StringUtil.fillPetPhraseIn(
                chatMessage,
                config.petPhraseX,
                config.filteredPrefix
        );
    }
}