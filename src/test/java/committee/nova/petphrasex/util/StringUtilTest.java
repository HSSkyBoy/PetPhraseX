package committee.nova.petphrasex.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilTest {
    @Test
    void removesOnlyMatchedIgnoreMarkWhenMultipleMarksAreConfigured() {
        assertEquals("baritone", StringUtil.processMessage("!baritone", "#;!", true, "", "", "", ""));
    }

    @Test
    void prefersLongestMatchingIgnoreMark() {
        assertEquals("baritone", StringUtil.processMessage("##baritone", "#;##", true, "", "", "", ""));
    }

    @Test
    void keepsMatchedIgnoreMarkWhenStripIsDisabled() {
        assertEquals("!baritone", StringUtil.processMessage("!baritone", "#;!", false, "", "", "", ""));
    }
}
