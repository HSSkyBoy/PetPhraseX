package committee.nova.petphrasex.util;

import java.util.List;

public class StringUtil {
    private static final String PUNCTUATION = "!?.,;()！？。，；（）~”\u201C\u2018\u2019\"'";

    public static int getLastPunc(String string) {
        if (string == null || string.isEmpty()) return -1;
        for (int i = string.length() - 1; i >= 0; i--) {
            if (PUNCTUATION.indexOf(string.charAt(i)) == -1) {
                return i;
            }
        }
        return -1;
    }

    public static String fillPetPhraseIn(String original, String petPhrase, List<String> filteredPrefix) {
        if (original == null || original.isEmpty()) return original;

        for (String prefix : filteredPrefix) {
            if (original.startsWith(prefix)) return original;
        }

        final int lastTextIndex = getLastPunc(original);
        final int insertIndex = lastTextIndex + 1;
        String phraseToUse = (insertIndex < original.length()) ? petPhrase.trim() : petPhrase;

        StringBuilder sb = new StringBuilder(original.length() + phraseToUse.length());
        sb.append(original, 0, insertIndex);
        sb.append(phraseToUse);
        sb.append(original.substring(insertIndex));

        return sb.toString();
    }
}