package committee.nova.petphrasex.util;

import committee.nova.petphrasex.config.PetPhraseConfigX;
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
                    i++; // 跳過下一個分號
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
        if (original == null || original.isEmpty()) return original;
        if (!ignoreMark.isEmpty() && original.startsWith(ignoreMark)) {
            PetPhraseConfigX config = PetPhraseConfigX.get();
            return config.removeIgnoreMark ? original.substring(ignoreMark.length()) : original;
        }

        // 短句處理 (以空格分隔)
        String[] parts = original.split(" ");
        StringBuilder processedBody = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                processedBody.append(randomChoose(sPrefix))
                        .append(parts[i])
                        .append(randomChoose(sSuffix));
            }
            if (i < parts.length - 1) processedBody.append(" ");
        }

        String body = processedBody.toString();
        String chosenPrefix = randomChoose(prefix);
        String chosenSuffix = randomChoose(suffix);

        int lastTextIndex = getLastPunc(body);
        int insertIndex = lastTextIndex + 1;

        return chosenPrefix + body.substring(0, insertIndex) + chosenSuffix + body.substring(insertIndex);
    }
}