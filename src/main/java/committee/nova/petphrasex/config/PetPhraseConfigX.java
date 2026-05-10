package committee.nova.petphrasex.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PetPhraseConfigX {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("petphrasex.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static PetPhraseConfigX INSTANCE;

    // 進階配置項
    public String ignoreMark = "#";
    public boolean removeIgnoreMark = true;
    public String prefix = "";
    public String suffix = " nya~; 喵~";
    public String sentencePrefix = "";
    public String sentenceSuffix = "";
    public boolean enableClientPhrase = true;
    public boolean enableServerPhrase = true;
    public ServerConditionRule[] serverConditionRules = new ServerConditionRule[]{
            ServerConditionRule.disabled("raining", "", " wet nya~"),
            ServerConditionRule.disabled("night", "", " good night nya~"),
            ServerConditionRule.disabled("low_health", "", " help nya~")
    };

    public static class ServerConditionRule {
        public boolean enabled = false;
        public String condition = "always";
        public String prefix = "";
        public String suffix = "";
        public String sentencePrefix = "";
        public String sentenceSuffix = "";

        public ServerConditionRule() {
        }

        private static ServerConditionRule disabled(String condition, String prefix, String suffix) {
            ServerConditionRule rule = new ServerConditionRule();
            rule.condition = condition;
            rule.prefix = prefix;
            rule.suffix = suffix;
            return rule;
        }
    }

    // 获取实例
    public static PetPhraseConfigX get() {
        if (INSTANCE == null) {
            load();
        }
        return INSTANCE;
    }

    // 加载配置
    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                INSTANCE = GSON.fromJson(Files.readString(CONFIG_PATH), PetPhraseConfigX.class);
            } catch (IOException e) {
                e.printStackTrace();
                INSTANCE = new PetPhraseConfigX();
            }
        } else {
            INSTANCE = new PetPhraseConfigX();
            save();
        }
    }

    // 保存配置
    public static void save() {
        if (INSTANCE == null) return;
        try {
            Files.writeString(CONFIG_PATH, GSON.toJson(INSTANCE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
