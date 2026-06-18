package committee.nova.petphrasex.client;

import committee.nova.petphrasex.config.PetPhraseConfigX;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class PetPhraseConfigScreenX extends Screen {
    private final Screen parent;
    private boolean forcedByServer;
    private EditBox ignoreMarkField;
    private Button removeIgnoreMarkButton;
    private boolean removeIgnoreMark;
    private EditBox prefixField;
    private EditBox suffixField;
    private EditBox sPrefixField;
    private EditBox sSuffixField;

    public PetPhraseConfigScreenX(Screen parent) {
        super(Component.translatable("screen.petphrasex.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = 40;
        int spacing = 30;
        int buttonY = startY + spacing + 12;
        this.forcedByServer = ServerForcedPhraseState.isForcedByServer();

        PetPhraseConfigX config = PetPhraseConfigX.get();
        this.removeIgnoreMark = config.removeIgnoreMark;

        this.ignoreMarkField = new EditBox(this.font, centerX - 100, startY + 12, 200, 20, Component.translatable("screen.petphrasex.config.ignore_mark"));
        this.ignoreMarkField.setValue(config.ignoreMark);
        this.addRenderableWidget(this.ignoreMarkField);

        this.removeIgnoreMarkButton = Button.builder(this.getRemoveIgnoreMarkButtonText(config.removeIgnoreMark), button -> {
                    this.removeIgnoreMark = !this.removeIgnoreMark;
                    button.setMessage(this.getRemoveIgnoreMarkButtonText(this.removeIgnoreMark));
                })
                .bounds(centerX - 100, buttonY, 200, 20)
                .build();
        this.addRenderableWidget(this.removeIgnoreMarkButton);

        this.prefixField = new EditBox(this.font, centerX - 100, startY + spacing * 2 + 12, 200, 20, Component.translatable("screen.petphrasex.config.prefix"));
        this.prefixField.setValue(config.prefix);
        this.addRenderableWidget(this.prefixField);

        this.suffixField = new EditBox(this.font, centerX - 100, startY + spacing * 3 + 12, 200, 20, Component.translatable("screen.petphrasex.config.suffix"));
        this.suffixField.setValue(config.suffix);
        this.addRenderableWidget(this.suffixField);

        this.sPrefixField = new EditBox(this.font, centerX - 100, startY + spacing * 4 + 12, 200, 20, Component.translatable("screen.petphrasex.config.sentence_prefix"));
        this.sPrefixField.setValue(config.sentencePrefix);
        this.addRenderableWidget(this.sPrefixField);

        this.sSuffixField = new EditBox(this.font, centerX - 100, startY + spacing * 5 + 12, 200, 20, Component.translatable("screen.petphrasex.config.sentence_suffix"));
        this.sSuffixField.setValue(config.sentenceSuffix);
        this.addRenderableWidget(this.sSuffixField);

        if (this.forcedByServer) {
            this.ignoreMarkField.setEditable(false);
            this.removeIgnoreMarkButton.active = false;
            this.prefixField.setEditable(false);
            this.suffixField.setEditable(false);
            this.sPrefixField.setEditable(false);
            this.sSuffixField.setEditable(false);
            this.addRenderableWidget(Button.builder(Component.translatable("screen.petphrasex.config.reclaim"), button -> {
                        button.active = !ServerFeatureHooks.sendReclaimRequest();
                    })
                    .bounds(centerX - 100, this.height - 64, 200, 20)
                    .build());
        }

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.save())
                .bounds(centerX - 105, this.height - 40, 100, 20).build());
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> this.onClose())
                .bounds(centerX + 5, this.height - 40, 100, 20).build());
    }

    private void save() {
        PetPhraseConfigX config = PetPhraseConfigX.get();
        config.ignoreMark = this.ignoreMarkField.getValue();
        config.removeIgnoreMark = this.removeIgnoreMark;
        config.prefix = this.prefixField.getValue();
        config.suffix = this.suffixField.getValue();
        config.sentencePrefix = this.sPrefixField.getValue();
        config.sentenceSuffix = this.sSuffixField.getValue();
        PetPhraseConfigX.save();

        this.onClose();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        graphics.text(this.font, this.title, (this.width - this.font.width(this.title)) / 2, 10, 0xFFFFFF, true);
        if (this.forcedByServer) {
            Component warning = Component.translatable("screen.petphrasex.config.forced_warning");
            graphics.text(this.font, warning, (this.width - this.font.width(warning)) / 2, 24, 0xFFFF5555, true);
        }

        int centerX = this.width / 2;
        int startY = 40;
        int spacing = 30;
        int color = 0xFFA0A0A0;
        Component ignoreMarkDesc = Component.translatable("screen.petphrasex.config.ignore_mark.desc");
        graphics.text(this.font, ignoreMarkDesc, centerX - this.font.width(ignoreMarkDesc) / 2, startY + 2, color, true);
        Component removeIgnoreMarkDesc = Component.translatable("screen.petphrasex.config.remove_ignore_mark.desc");
        graphics.text(this.font, removeIgnoreMarkDesc, centerX - this.font.width(removeIgnoreMarkDesc) / 2, startY + spacing + 2, color, true);
        Component prefixDesc = Component.translatable("screen.petphrasex.config.prefix.desc");
        graphics.text(this.font, prefixDesc, centerX - this.font.width(prefixDesc) / 2, startY + spacing * 2 + 2, color, true);
        Component suffixDesc = Component.translatable("screen.petphrasex.config.suffix.desc");
        graphics.text(this.font, suffixDesc, centerX - this.font.width(suffixDesc) / 2, startY + spacing * 3 + 2, color, true);
        Component sPrefixDesc = Component.translatable("screen.petphrasex.config.sentence_prefix.desc");
        graphics.text(this.font, sPrefixDesc, centerX - this.font.width(sPrefixDesc) / 2, startY + spacing * 4 + 2, color, true);
        Component sSuffixDesc = Component.translatable("screen.petphrasex.config.sentence_suffix.desc");
        graphics.text(this.font, sSuffixDesc, centerX - this.font.width(sSuffixDesc) / 2, startY + spacing * 5 + 2, color, true);
    }

    private Component getRemoveIgnoreMarkButtonText(boolean removeIgnoreMark) {
        return Component.translatable("screen.petphrasex.config.remove_ignore_mark.button", Component.translatable(removeIgnoreMark ? "options.on" : "options.off"));
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) this.minecraft.setScreen(this.parent);
    }
}
