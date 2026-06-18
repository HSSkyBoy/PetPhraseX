package committee.nova.petphrasex.server.forced;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import committee.nova.petphrasex.config.PetPhraseConfigX;

public record ForcedPhraseData(
        boolean active,
        String ignoreMark,
        boolean removeIgnoreMark,
        String prefix,
        String suffix,
        String sentencePrefix,
        String sentenceSuffix
) {
    public static final Codec<ForcedPhraseData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("active", true).forGetter(ForcedPhraseData::active),
            Codec.STRING.optionalFieldOf("ignore_mark", "#").forGetter(ForcedPhraseData::ignoreMark),
            Codec.BOOL.optionalFieldOf("remove_ignore_mark", true).forGetter(ForcedPhraseData::removeIgnoreMark),
            Codec.STRING.optionalFieldOf("prefix", "").forGetter(ForcedPhraseData::prefix),
            Codec.STRING.optionalFieldOf("suffix", "").forGetter(ForcedPhraseData::suffix),
            Codec.STRING.optionalFieldOf("sentence_prefix", "").forGetter(ForcedPhraseData::sentencePrefix),
            Codec.STRING.optionalFieldOf("sentence_suffix", "").forGetter(ForcedPhraseData::sentenceSuffix)
    ).apply(instance, ForcedPhraseData::new));

    public static ForcedPhraseData fromConfig(PetPhraseConfigX config) {
        return new ForcedPhraseData(
                true,
                normalize(config.ignoreMark),
                config.removeIgnoreMark,
                normalize(config.prefix),
                normalize(config.suffix),
                normalize(config.sentencePrefix),
                normalize(config.sentenceSuffix)
        );
    }

    public ForcedPhraseData withActive(boolean active) {
        return new ForcedPhraseData(active, ignoreMark, removeIgnoreMark, prefix, suffix, sentencePrefix, sentenceSuffix);
    }

    public ForcedPhraseData withField(ForcedPhraseField field, String value) {
        return field.apply(this, normalize(value)).withActive(true);
    }

    private static String normalize(String value) {
        return value == null ? "" : value;
    }
}
