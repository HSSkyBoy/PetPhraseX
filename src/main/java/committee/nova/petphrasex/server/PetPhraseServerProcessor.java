package committee.nova.petphrasex.server;

import committee.nova.petphrasex.config.PetPhraseConfigX;
import committee.nova.petphrasex.util.StringUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public final class PetPhraseServerProcessor {
    private PetPhraseServerProcessor() {
    }

    public static String process(ServerPlayer player, String message) {
        PetPhraseConfigX config = PetPhraseConfigX.get();
        if (!config.enableServerPhrase) return message;

        PetPhraseConfigX.ServerConditionRule rule = getFirstMatchedRule(config, player);
        if (rule != null) {
            return StringUtil.processMessage(
                    message,
                    config.ignoreMark,
                    config.removeIgnoreMark,
                    rule.prefix,
                    rule.suffix,
                    rule.sentencePrefix,
                    rule.sentenceSuffix
            );
        }

        return StringUtil.processMessage(
                message,
                config.ignoreMark,
                config.removeIgnoreMark,
                config.prefix,
                config.suffix,
                config.sentencePrefix,
                config.sentenceSuffix
        );
    }

    private static PetPhraseConfigX.ServerConditionRule getFirstMatchedRule(PetPhraseConfigX config, ServerPlayer player) {
        if (config.serverConditionRules == null) return null;

        for (PetPhraseConfigX.ServerConditionRule rule : config.serverConditionRules) {
            if (rule == null || !rule.enabled) continue;
            if (matches(rule.condition, player)) return rule;
        }
        return null;
    }

    private static boolean matches(String condition, ServerPlayer player) {
        if (condition == null || condition.isBlank()) return false;

        Level level = player.level();
        return switch (condition.trim().toLowerCase()) {
            case "always" -> true;
            case "raining", "rain" -> level.isRaining();
            case "thundering", "thunder" -> level.isThundering();
            case "day" -> isDay(level);
            case "night" -> !isDay(level);
            case "low_health" -> player.getHealth() <= 6.0F;
            case "underwater" -> player.isUnderWater();
            case "in_water" -> player.isInWater();
            case "on_fire", "burning" -> player.isOnFire();
            case "sneaking", "crouching" -> player.isCrouching();
            case "sprinting" -> player.isSprinting();
            case "in_overworld", "overworld" -> level.dimension() == Level.OVERWORLD;
            case "in_nether", "nether" -> level.dimension() == Level.NETHER;
            case "in_end", "end" -> level.dimension() == Level.END;
            default -> false;
        };
    }

    private static boolean isDay(Level level) {
        return !level.isDarkOutside();
    }
}
