package committee.nova.petphrasex.util;

import committee.nova.petphrasex.config.PetPhraseConfigX;
import committee.nova.petphrasex.config.PhraseSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringUtil {
    private static final String PUNCTUATION = "!?.,;()！？。，；（）~”\u201C\u2018\u2019\"'";
    private static final Random RANDOM = new Random();

    public static int getLastPunc(String string) {
        if (string == null || string.isEmpty()) return -1;
        for (int i = string.length() - 1; i >= 0; i--) {
            if (PUNCTUATION.indexOf(string.charAt(i)) == -1) {
                return i;
            }
        }
        return -1;
    }

    // 分割以單個分號分隔的字串，支援 ;; 轉義
    public static List<String> splitWithEscape(String text) {
        List<String> results = new ArrayList<>();
        if (text == null || text.isEmpty()) return results;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ';') {
                if (i + 1 < text.length() && text.charAt(i + 1) == ';') {
                    sb.append(';');
                    i++;
                } else {
                    results.add(sb.toString());
                    sb.setLength(0);
                }
            } else {
                sb.append(c);
            }
        }
        results.add(sb.toString());
        return results;
    }

    public static String randomChoose(String input) {
        List<String> pool = splitWithEscape(input);
        if (pool.isEmpty()) return "";
        return pool.get(RANDOM.nextInt(pool.size()));
    }

    public static String processMessage(String original, String ignoreMark, String prefix, String suffix, String sPrefix, String sSuffix) {
        PetPhraseConfigX config = PetPhraseConfigX.get();
        return processMessage(original, ignoreMark, config.removeIgnoreMark, prefix, suffix, sPrefix, sSuffix);
    }

    public static String processMessage(String original, String ignoreMark, boolean removeIgnoreMark, String prefix, String suffix, String sPrefix, String sSuffix) {
        return processMessage(original, new PhraseSettings(ignoreMark, removeIgnoreMark, prefix, suffix, sPrefix, sSuffix));
    }

    public static String processMessage(String original, PhraseSettings settings) {
        if (original == null || original.isEmpty()) return original;

        String matchedIgnoreMark = findMatchedIgnoreMark(original, settings.ignoreMark());
        if (matchedIgnoreMark != null) {
            return settings.removeIgnoreMark() ? original.substring(matchedIgnoreMark.length()) : original;
        }

        String[] parts = original.split(" ");
        StringBuilder processedBody = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                processedBody.append(randomChoose(settings.sentencePrefix()))
                        .append(parts[i])
                        .append(randomChoose(settings.sentenceSuffix()));
            }
            if (i < parts.length - 1) processedBody.append(" ");
        }

        String body = processedBody.toString();
        String chosenPrefix = randomChoose(settings.prefix());
        String chosenSuffix = randomChoose(settings.suffix());

        int lastTextIndex = getLastPunc(body);
        int insertIndex = lastTextIndex + 1;
        return chosenPrefix + body.substring(0, insertIndex) + chosenSuffix + body.substring(insertIndex);
    }

    private static String findMatchedIgnoreMark(String original, String ignoreMark) {
        if (ignoreMark == null || ignoreMark.isEmpty()) return null;

        String matched = null;
        for (String candidate : splitWithEscape(ignoreMark)) {
            if (candidate.isEmpty() || !original.startsWith(candidate)) continue;
            if (matched == null || candidate.length() > matched.length()) {
                matched = candidate;
            }
        }
        return matched;
    }
}
