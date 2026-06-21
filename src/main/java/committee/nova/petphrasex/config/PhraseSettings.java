package committee.nova.petphrasex.config;

public record PhraseSettings(
        String ignoreMark,
        boolean removeIgnoreMark,
        String prefix,
        String suffix,
        String sentencePrefix,
        String sentenceSuffix
) {
    public static PhraseSettings empty() {
        return new PhraseSettings("", true, "", "", "", "");
    }
}
