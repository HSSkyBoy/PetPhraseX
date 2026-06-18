package committee.nova.petphrasex.network;

import committee.nova.petphrasex.server.forced.ForcedPhraseData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ForcedPhraseSyncPayload(
        boolean forcedByServer,
        String ignoreMark,
        boolean removeIgnoreMark,
        String prefix,
        String suffix,
        String sentencePrefix,
        String sentenceSuffix
) implements CustomPacketPayload {
    public static final Type<ForcedPhraseSyncPayload> TYPE = CustomPacketPayload.createType("petphrasex:forced_phrase_sync");
    public static final StreamCodec<RegistryFriendlyByteBuf, ForcedPhraseSyncPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ForcedPhraseSyncPayload::forcedByServer,
            ByteBufCodecs.STRING_UTF8,
            ForcedPhraseSyncPayload::ignoreMark,
            ByteBufCodecs.BOOL,
            ForcedPhraseSyncPayload::removeIgnoreMark,
            ByteBufCodecs.STRING_UTF8,
            ForcedPhraseSyncPayload::prefix,
            ByteBufCodecs.STRING_UTF8,
            ForcedPhraseSyncPayload::suffix,
            ByteBufCodecs.STRING_UTF8,
            ForcedPhraseSyncPayload::sentencePrefix,
            ByteBufCodecs.STRING_UTF8,
            ForcedPhraseSyncPayload::sentenceSuffix,
            ForcedPhraseSyncPayload::new
    );

    public static ForcedPhraseSyncPayload fromData(ForcedPhraseData data) {
        if (data == null) {
            return new ForcedPhraseSyncPayload(false, "", true, "", "", "", "");
        }

        return new ForcedPhraseSyncPayload(
                data.active(),
                data.ignoreMark(),
                data.removeIgnoreMark(),
                data.prefix(),
                data.suffix(),
                data.sentencePrefix(),
                data.sentenceSuffix()
        );
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
