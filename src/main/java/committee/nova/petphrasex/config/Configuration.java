package committee.nova.petphrasex.config;

import java.util.List;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "petPhraseX")
public class Configuration implements ConfigData {
    @Comment("The pet phrase which should be added into your messages")
    public String petPhraseX = " nya";
    @Comment("If the words in the list contain the message to be sent, petPhrase won't activate on the message.")
    public List<String> filteredPrefix = List.of("/", "!", "-", ".");
}
