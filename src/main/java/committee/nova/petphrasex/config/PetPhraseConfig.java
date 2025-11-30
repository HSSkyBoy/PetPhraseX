package committee.nova.petphrasex.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import java.util.Arrays;
import java.util.List;

public class PetPhraseConfig {
    public static final ModConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        final ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        CLIENT = new Client(builder);
        CLIENT_SPEC = builder.build();
    }

    public static class Client {
        public final ModConfigSpec.ConfigValue<String> petPhrase;
        public final ModConfigSpec.ConfigValue<List<? extends String>> filteredPrefix;

        public Client(ModConfigSpec.Builder builder) {
            builder.comment("PetPhraseX 用户端配置").push("client");

            petPhrase = builder
                    .comment("The suffix to add.")
                    .define("petPhrase", " nya~");

            filteredPrefix = builder
                    .comment("Messages starting with these will be ignored.")
                    .defineList("filteredPrefix",
                            Arrays.asList("/", ".", "#", "-", "+", "$"),
                            obj -> obj instanceof String);

            builder.pop();
        }
    }
}