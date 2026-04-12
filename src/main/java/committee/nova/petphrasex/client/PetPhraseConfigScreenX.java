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
    private TextFieldWidget prefixField;
    private TextFieldWidget suffixField;
    private TextFieldWidget sPrefixField;
    private TextFieldWidget sSuffixField;

    public PetPhraseConfigScreenX(Screen parent) {
        super(Text.literal("PetPhraseX Configuration / 配置"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = 40;
        int spacing = 30;

        PetPhraseConfigX config = PetPhraseConfigX.get();

        this.ignoreMarkField = new TextFieldWidget(this.textRenderer, centerX - 100, startY + 12, 200, 20, Text.of("Ignore Mark"));
        this.ignoreMarkField.setText(config.ignoreMark);
        this.addDrawableChild(this.ignoreMarkField);

        this.prefixField = new TextFieldWidget(this.textRenderer, centerX - 100, startY + spacing + 12, 200, 20, Text.of("Prefix"));
        this.prefixField.setText(config.prefix);
        this.addDrawableChild(this.prefixField);

        this.suffixField = new TextFieldWidget(this.textRenderer, centerX - 100, startY + spacing * 2 + 12, 200, 20, Text.of("Suffix"));
        this.suffixField.setText(config.suffix);
        this.addDrawableChild(this.suffixField);

        this.sPrefixField = new TextFieldWidget(this.textRenderer, centerX - 100, startY + spacing * 3 + 12, 200, 20, Text.of("Sentence Prefix"));
        this.sPrefixField.setText(config.sentencePrefix);
        this.addDrawableChild(this.sPrefixField);

        this.sSuffixField = new TextFieldWidget(this.textRenderer, centerX - 100, startY + spacing * 4 + 12, 200, 20, Text.of("Sentence Suffix"));
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
        context.drawCenteredTextWithShadow(this.textRenderer, "Ignore Mark / 忽略標記", centerX, startY + 2, color);
        context.drawCenteredTextWithShadow(this.textRenderer, "Message Prefix / 消息前綴", centerX, startY + spacing + 2, color);
        context.drawCenteredTextWithShadow(this.textRenderer, "Message Suffix / 消息後綴", centerX, startY + spacing * 2 + 2, color);
        context.drawCenteredTextWithShadow(this.textRenderer, "Sentence Prefix / 短句前綴", centerX, startY + spacing * 3 + 2, color);
        context.drawCenteredTextWithShadow(this.textRenderer, "Sentence Suffix / 短句後綴", centerX, startY + spacing * 4 + 2, color);
    }

    @Override
    public void close() {
        if (this.client != null) this.client.setScreen(this.parent);
    }
}