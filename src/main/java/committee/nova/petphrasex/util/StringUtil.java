package committee.nova.petphrasex.util;

import org.apache.commons.lang3.StringUtils;
import java.util.List;

public class StringUtil {
    static final List<Character> punctuations = List.of('!', '?', '.', '(', ')', '！', '？', '。', '（', '）', '~', '”', '“', '‘', '’', '"', '\'');
    public static int getLastPunc(String string) {
        if (string == null || string.isEmpty()) return -1;
        for (int i = string.length() - 1; i >= 0; i--) {
            if (!punctuations.contains(string.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public static String fillPetPhraseIn(String original, String petPhrase, List<String> filteredPrefix) {
        if (original.isEmpty() || original.charAt(0) == '/' || original.charAt(0) == '!') return original;

        for (String s : filteredPrefix) {
            if (original.contains(s)) return original;
        }
        final int index = getLastPunc(original) + 1;
        return StringUtils.substring(original, 0, index) + petPhrase + StringUtils.substring(original, index);
    }
}