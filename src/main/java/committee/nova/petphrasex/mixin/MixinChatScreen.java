package committee.nova.petphrasex.mixin;

import committee.nova.petphrasex.config.PetPhraseConfigX;
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
        PetPhraseConfigX config = PetPhraseConfigX.get();

        return StringUtil.processMessage(
                chatMessage,
                config.ignoreMark,
                config.prefix,
                config.suffix,
                config.sentencePrefix,
                config.sentenceSuffix
        );
    }
}