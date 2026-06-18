package committee.nova.petphrasex.server.forced;

public enum ForcedPhraseField {
    IGNORE_MARK("ignore_mark") {
        @Override
        ForcedPhraseData apply(ForcedPhraseData data, String value) {
            return new ForcedPhraseData(data.active(), value, data.removeIgnoreMark(), data.prefix(), data.suffix(), data.sentencePrefix(), data.sentenceSuffix());
        }
    },
    PREFIX("prefix") {
        @Override
        ForcedPhraseData apply(ForcedPhraseData data, String value) {
            return new ForcedPhraseData(data.active(), data.ignoreMark(), data.removeIgnoreMark(), value, data.suffix(), data.sentencePrefix(), data.sentenceSuffix());
        }
    },
    SUFFIX("suffix") {
        @Override
        ForcedPhraseData apply(ForcedPhraseData data, String value) {
            return new ForcedPhraseData(data.active(), data.ignoreMark(), data.removeIgnoreMark(), data.prefix(), value, data.sentencePrefix(), data.sentenceSuffix());
        }
    },
    SENTENCE_PREFIX("sentence_prefix") {
        @Override
        ForcedPhraseData apply(ForcedPhraseData data, String value) {
            return new ForcedPhraseData(data.active(), data.ignoreMark(), data.removeIgnoreMark(), data.prefix(), data.suffix(), value, data.sentenceSuffix());
        }
    },
    SENTENCE_SUFFIX("sentence_suffix") {
        @Override
        ForcedPhraseData apply(ForcedPhraseData data, String value) {
            return new ForcedPhraseData(data.active(), data.ignoreMark(), data.removeIgnoreMark(), data.prefix(), data.suffix(), data.sentencePrefix(), value);
        }
    };

    private final String commandName;

    ForcedPhraseField(String commandName) {
        this.commandName = commandName;
    }

    public String commandName() {
        return this.commandName;
    }

    abstract ForcedPhraseData apply(ForcedPhraseData data, String value);

    public static ForcedPhraseField fromCommandName(String commandName) {
        for (ForcedPhraseField field : values()) {
            if (field.commandName.equalsIgnoreCase(commandName)) {
                return field;
            }
        }
        return null;
    }
}
