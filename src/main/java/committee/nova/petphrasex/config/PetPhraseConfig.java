package committee.nova.petphrasex.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class PetPhraseConfig {
    public static final ModConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        final ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        CLIENT = new Client(builder);
        CLIENT_SPEC = builder.build();
    }

    public static class Client {
        public final ModConfigSpec.ConfigValue<String> ignoreMark;
        public final ModConfigSpec.BooleanValue removeIgnoreMark;
        public final ModConfigSpec.ConfigValue<String> prefix;
        public final ModConfigSpec.ConfigValue<String> suffix;
        public final ModConfigSpec.ConfigValue<String> sentencePrefix;
        public final ModConfigSpec.ConfigValue<String> sentenceSuffix;

        public Client(ModConfigSpec.Builder builder) {
            builder.comment("PetPhraseX Client Config").push("client");

            ignoreMark = builder
                    .comment("Ignore Mark")
                    .define("ignoreMark", "#");

            removeIgnoreMark = builder
                    .comment("Remove Ignore Mark before sending")
                    .define("removeIgnoreMark", true);

            prefix = builder
                    .comment("Message Prefix")
                    .define("prefix", "");

            suffix = builder
                    .comment("Message Suffix")
                    .define("suffix", " nya~; 喵~");

            sentencePrefix = builder
                    .comment("Sentence Prefix")
                    .define("sentencePrefix", "");

            sentenceSuffix = builder
                    .comment("Sentence Suffix")
                    .define("sentenceSuffix", "");

            builder.pop();
        }
    }
}
