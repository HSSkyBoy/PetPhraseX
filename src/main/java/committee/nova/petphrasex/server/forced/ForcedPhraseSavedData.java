package committee.nova.petphrasex.server.forced;

import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class ForcedPhraseSavedData extends SavedData {
    private static final Codec<UUID> UUID_CODEC = Codec.STRING.xmap(UUID::fromString, UUID::toString);
    private static final Codec<ForcedPhraseSavedData> CODEC = Codec.unboundedMap(UUID_CODEC, ForcedPhraseData.CODEC)
            .xmap(ForcedPhraseSavedData::new, ForcedPhraseSavedData::phrases);
    public static final SavedDataType<ForcedPhraseSavedData> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath("petphrasex", "forced_phrases"),
            ForcedPhraseSavedData::new,
            CODEC,
            null
    );

    private final Map<UUID, ForcedPhraseData> phrases;

    public ForcedPhraseSavedData() {
        this(new HashMap<>());
    }

    private ForcedPhraseSavedData(Map<UUID, ForcedPhraseData> phrases) {
        this.phrases = new HashMap<>(phrases);
    }

    public Map<UUID, ForcedPhraseData> phrases() {
        return this.phrases;
    }
}
