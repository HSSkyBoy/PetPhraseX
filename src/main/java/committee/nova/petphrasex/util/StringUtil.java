package committee.nova.petphrasex.util;

import org.apache.commons.lang3.StringUtils;
import java.util.List;

public class StringUtil {
    static final List<Character> punctuations = List.of('!', '?', '.', '(', ')', '！', '？', '。', '（', '）', '~', '”', '“', '‘', '’', '"', '\'');

    public static int getLastPunc(String string) {
        final int length = string.length();
        if (length == 1) return (punctuations.contains(string.charAt(0))) ? -1 : 0;
        return getPunc(string, length - 1);
    }

    public static int getPunc(String string, int index) {
        if (StringUtils.isEmpty(string) || index < 0 || index >= string.length()) {
            return -1;
        }
        for (int i = index; i >= 0; i--) {
            if (!punctuations.contains(string.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public static String fillPetPhraseIn(String original, String petPhrase, List<String> filteredPrefix) {
        if (StringUtils.isEmpty(original) || StringUtils.isEmpty(petPhrase)) {
            return original;
        }
        final char firstChar = original.charAt(0);
        if (firstChar == '/' || firstChar == '!') {
            return original;
        }
        for (String s : filteredPrefix) {
            if (StringUtils.isNotEmpty(s) && original.contains(s)) {
                return original;
            }
        }
        final int lastNonPuncIndex = getLastPunc(original);
        final int insertIndex = lastNonPuncIndex + 1;

        String prefix = StringUtils.substring(original, 0, insertIndex);
        String suffix = StringUtils.substring(original, insertIndex);

        return prefix + petPhrase + suffix;
    }
}
