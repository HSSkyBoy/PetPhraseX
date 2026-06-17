package committee.nova.petphrasex.client;

import committee.nova.petphrasex.config.PetPhraseConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class PetPhraseConfigScreen extends Screen {
    private final Screen parent;
    private EditBox ignoreMarkField;
    private Button removeIgnoreMarkButton;
    private boolean removeIgnoreMark;
    private EditBox prefixField;
    private EditBox suffixField;
    private EditBox sPrefixField;
    private EditBox sSuffixField;

    public PetPhraseConfigScreen(Screen parent) {
        super(Component.translatable("screen.petphrasex.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = 40;
        int spacing = 30;
        int buttonY = startY + spacing + 12;
        this.removeIgnoreMark = PetPhraseConfig.CLIENT.removeIgnoreMark.get();

        this.ignoreMarkField = new EditBox(this.font, centerX - 100, startY + 12, 200, 20, Component.translatable("screen.petphrasex.config.ignore_mark"));
        this.ignoreMarkField.setMaxLength(256);
        this.ignoreMarkField.setValue(PetPhraseConfig.CLIENT.ignoreMark.get());
        this.addRenderableWidget(this.ignoreMarkField);

        this.removeIgnoreMarkButton = Button.builder(this.getRemoveIgnoreMarkButtonText(this.removeIgnoreMark), button -> {
                    this.removeIgnoreMark = !this.removeIgnoreMark;
                    button.setMessage(this.getRemoveIgnoreMarkButtonText(this.removeIgnoreMark));
                })
                .bounds(centerX - 100, buttonY, 200, 20)
                .build();
        this.addRenderableWidget(this.removeIgnoreMarkButton);

        this.prefixField = new EditBox(this.font, centerX - 100, startY + spacing * 2 + 12, 200, 20, Component.translatable("screen.petphrasex.config.prefix"));
        this.prefixField.setMaxLength(256);
        this.prefixField.setValue(PetPhraseConfig.CLIENT.prefix.get());
        this.addRenderableWidget(this.prefixField);

        this.suffixField = new EditBox(this.font, centerX - 100, startY + spacing * 3 + 12, 200, 20, Component.translatable("screen.petphrasex.config.suffix"));
        this.suffixField.setMaxLength(1024);
        this.suffixField.setValue(PetPhraseConfig.CLIENT.suffix.get());
        this.addRenderableWidget(this.suffixField);

        this.sPrefixField = new EditBox(this.font, centerX - 100, startY + spacing * 4 + 12, 200, 20, Component.translatable("screen.petphrasex.config.sentence_prefix"));
        this.sPrefixField.setMaxLength(256);
        this.sPrefixField.setValue(PetPhraseConfig.CLIENT.sentencePrefix.get());
        this.addRenderableWidget(this.sPrefixField);

        this.sSuffixField = new EditBox(this.font, centerX - 100, startY + spacing * 5 + 12, 200, 20, Component.translatable("screen.petphrasex.config.sentence_suffix"));
        this.sSuffixField.setMaxLength(1024);
        this.sSuffixField.setValue(PetPhraseConfig.CLIENT.sentenceSuffix.get());
        this.addRenderableWidget(this.sSuffixField);

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.save())
                .bounds(centerX - 105, this.height - 40, 100, 20).build());
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> this.onClose())
                .bounds(centerX + 5, this.height - 40, 100, 20).build());
    }

    private void save() {
        PetPhraseConfig.CLIENT.ignoreMark.set(this.ignoreMarkField.getValue());
        PetPhraseConfig.CLIENT.removeIgnoreMark.set(this.removeIgnoreMark);
        PetPhraseConfig.CLIENT.prefix.set(this.prefixField.getValue());
        PetPhraseConfig.CLIENT.suffix.set(this.suffixField.getValue());
        PetPhraseConfig.CLIENT.sentencePrefix.set(this.sPrefixField.getValue());
        PetPhraseConfig.CLIENT.sentenceSuffix.set(this.sSuffixField.getValue());
        PetPhraseConfig.CLIENT_SPEC.save();
        this.onClose();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 10, 0xFFFFFF);

        int centerX = this.width / 2;
        int startY = 40;
        int spacing = 30;
        int color = 0xFFA0A0A0;
        guiGraphics.drawCenteredString(this.font, Component.translatable("screen.petphrasex.config.ignore_mark.desc"), centerX, startY + 2, color);
        guiGraphics.drawCenteredString(this.font, Component.translatable("screen.petphrasex.config.remove_ignore_mark.desc"), centerX, startY + spacing + 2, color);
        guiGraphics.drawCenteredString(this.font, Component.translatable("screen.petphrasex.config.prefix.desc"), centerX, startY + spacing * 2 + 2, color);
        guiGraphics.drawCenteredString(this.font, Component.translatable("screen.petphrasex.config.suffix.desc"), centerX, startY + spacing * 3 + 2, color);
        guiGraphics.drawCenteredString(this.font, Component.translatable("screen.petphrasex.config.sentence_prefix.desc"), centerX, startY + spacing * 4 + 2, color);
        guiGraphics.drawCenteredString(this.font, Component.translatable("screen.petphrasex.config.sentence_suffix.desc"), centerX, startY + spacing * 5 + 2, color);
    }

    private Component getRemoveIgnoreMarkButtonText(boolean removeIgnoreMark) {
        return Component.translatable("screen.petphrasex.config.remove_ignore_mark.button", Component.translatable(removeIgnoreMark ? "options.on" : "options.off"));
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }
}
