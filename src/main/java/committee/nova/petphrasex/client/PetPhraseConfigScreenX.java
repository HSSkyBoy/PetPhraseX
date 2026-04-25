package committee.nova.petphrasex.client;

import committee.nova.petphrasex.config.PetPhraseConfigX;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class PetPhraseConfigScreenX extends Screen {
    private final Screen parent;
    private TextFieldWidget ignoreMarkField;
    private ButtonWidget removeIgnoreMarkButton;
    private boolean removeIgnoreMark;
    private TextFieldWidget prefixField;
    private TextFieldWidget suffixField;
    private TextFieldWidget sPrefixField;
    private TextFieldWidget sSuffixField;

    public PetPhraseConfigScreenX(Screen parent) {
        super(Text.translatable("screen.petphrasex.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = 40;
        int spacing = 30;
        int buttonY = startY + spacing + 12;

        PetPhraseConfigX config = PetPhraseConfigX.get();
        this.removeIgnoreMark = config.removeIgnoreMark;

        this.ignoreMarkField = new TextFieldWidget(this.textRenderer, centerX - 100, startY + 12, 200, 20, Text.translatable("screen.petphrasex.config.ignore_mark"));
        this.ignoreMarkField.setText(config.ignoreMark);
        this.addDrawableChild(this.ignoreMarkField);

        this.removeIgnoreMarkButton = ButtonWidget.builder(this.getRemoveIgnoreMarkButtonText(config.removeIgnoreMark), button -> {
                    this.removeIgnoreMark = !this.removeIgnoreMark;
                    button.setMessage(this.getRemoveIgnoreMarkButtonText(this.removeIgnoreMark));
                })
                .dimensions(centerX - 100, buttonY, 200, 20)
                .build();
        this.addDrawableChild(this.removeIgnoreMarkButton);

        this.prefixField = new TextFieldWidget(this.textRenderer, centerX - 100, startY + spacing * 2 + 12, 200, 20, Text.translatable("screen.petphrasex.config.prefix"));
        this.prefixField.setText(config.prefix);
        this.addDrawableChild(this.prefixField);

        this.suffixField = new TextFieldWidget(this.textRenderer, centerX - 100, startY + spacing * 3 + 12, 200, 20, Text.translatable("screen.petphrasex.config.suffix"));
        this.suffixField.setText(config.suffix);
        this.addDrawableChild(this.suffixField);

        this.sPrefixField = new TextFieldWidget(this.textRenderer, centerX - 100, startY + spacing * 4 + 12, 200, 20, Text.translatable("screen.petphrasex.config.sentence_prefix"));
        this.sPrefixField.setText(config.sentencePrefix);
        this.addDrawableChild(this.sPrefixField);

        this.sSuffixField = new TextFieldWidget(this.textRenderer, centerX - 100, startY + spacing * 5 + 12, 200, 20, Text.translatable("screen.petphrasex.config.sentence_suffix"));
        this.sSuffixField.setText(config.sentenceSuffix);
        this.addDrawableChild(this.sSuffixField);

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> this.save())
                .dimensions(centerX - 105, this.height - 40, 100, 20).build());
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, button -> this.close())
                .dimensions(centerX + 5, this.height - 40, 100, 20).build());
    }

    private void save() {
        PetPhraseConfigX config = PetPhraseConfigX.get();
        config.ignoreMark = this.ignoreMarkField.getText();
        config.removeIgnoreMark = this.removeIgnoreMark;
        config.prefix = this.prefixField.getText();
        config.suffix = this.suffixField.getText();
        config.sentencePrefix = this.sPrefixField.getText();
        config.sentenceSuffix = this.sSuffixField.getText();
        PetPhraseConfigX.save();

        this.close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        // Yarn 映射中 drawCenteredString 对应 drawCenteredTextWithShadow
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 10, 0xFFFFFF);

        int centerX = this.width / 2;
        int startY = 40;
        int spacing = 30;
        int color = 0xFFA0A0A0;
        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("screen.petphrasex.config.ignore_mark.desc"), centerX, startY + 2, color);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("screen.petphrasex.config.remove_ignore_mark.desc"), centerX, startY + spacing + 2, color);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("screen.petphrasex.config.prefix.desc"), centerX, startY + spacing * 2 + 2, color);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("screen.petphrasex.config.suffix.desc"), centerX, startY + spacing * 3 + 2, color);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("screen.petphrasex.config.sentence_prefix.desc"), centerX, startY + spacing * 4 + 2, color);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("screen.petphrasex.config.sentence_suffix.desc"), centerX, startY + spacing * 5 + 2, color);
    }

    private Text getRemoveIgnoreMarkButtonText(boolean removeIgnoreMark) {
        return Text.translatable("screen.petphrasex.config.remove_ignore_mark.button", Text.translatable(removeIgnoreMark ? "options.on" : "options.off"));
    }

    @Override
    public void close() {
        if (this.client != null) this.client.setScreen(this.parent);
    }
}