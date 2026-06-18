package committee.nova.petphrasex.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ReclaimForcedPhrasePayload(boolean reclaim) implements CustomPacketPayload {
    public static final Type<ReclaimForcedPhrasePayload> TYPE = CustomPacketPayload.createType("petphrasex:reclaim_forced_phrase");
    public static final StreamCodec<RegistryFriendlyByteBuf, ReclaimForcedPhrasePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ReclaimForcedPhrasePayload::reclaim,
            ReclaimForcedPhrasePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
