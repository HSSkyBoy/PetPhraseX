package committee.nova.petphrasex.mixin;

import committee.nova.petphrasex.server.PetPhraseServerProcessor;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class MixinServerGamePacketListenerImpl {
    @Shadow
    public ServerPlayer player;

    @Inject(method = "getSignedMessage", at = @At("RETURN"), cancellable = true)
    private void petphrasex$applyServerPhrase(
            ServerboundChatPacket packet,
            LastSeenMessages lastSeenMessages,
            CallbackInfoReturnable<PlayerChatMessage> cir
    ) {
        PlayerChatMessage original = cir.getReturnValue();
        String processed = PetPhraseServerProcessor.process(this.player, original.signedContent());
        if (!processed.equals(original.signedContent())) {
            cir.setReturnValue(original.withUnsignedContent(Component.literal(processed)));
        }
    }
}
