package committee.nova.petphrasex.client;

import committee.nova.petphrasex.config.PetPhraseConfigX;
import committee.nova.petphrasex.config.PhraseSettings;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class PetPhraseConfigScreenX extends Screen {
    private static final int PANEL_WIDTH = 340;
    private static final int LABEL_WIDTH = 108;
    private static final int CONTROL_WIDTH = 220;
    private static final int ROW_HEIGHT = 28;

    private final Screen parent;
    private boolean forcedByServer;
    private boolean removeIgnoreMark;
    private boolean enableClientPhrase;

    private EditBox ignoreMarkField;
    private EditBox prefixField;
    private EditBox suffixField;
    private EditBox sentencePrefixField;
    private EditBox sentenceSuffixField;
    private Button removeIgnoreMarkButton;
    private Button enableClientPhraseButton;
    private Button reclaimButton;
    private Button doneButton;
    private Button cancelButton;

    public PetPhraseConfigScreenX(Screen parent) {
        super(Component.translatable("screen.petphrasex.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        this.clearWidgets();

        PetPhraseConfigX config = PetPhraseConfigX.get();
        this.forcedByServer = ServerForcedPhraseState.isForcedByServer();
        this.removeIgnoreMark = config.removeIgnoreMark;
        this.enableClientPhrase = config.enableClientPhrase;

        int panelX = (this.width - PANEL_WIDTH) / 2;
        int labelX = panelX;
        int controlX = panelX + LABEL_WIDTH + 12;
        int y = this.forcedByServer ? 64 : 48;

        this.enableClientPhraseButton = this.addRenderableWidget(Button.builder(
                Component.literal(this.enableClientPhrase ? "客户端口癖：开启" : "客户端口癖：关闭"),
                button -> {
                    this.enableClientPhrase = !this.enableClientPhrase;
                    button.setMessage(Component.literal(this.enableClientPhrase ? "客户端口癖：开启" : "客户端口癖：关闭"));
                }
        ).bounds(controlX, y, CONTROL_WIDTH, 20).build());
        y += ROW_HEIGHT;

        this.ignoreMarkField = this.addRenderableWidget(new EditBox(this.font, controlX, y, CONTROL_WIDTH, 20, Component.translatable("screen.petphrasex.config.ignore_mark")));
        this.ignoreMarkField.setValue(config.ignoreMark);
        y += ROW_HEIGHT;

        this.removeIgnoreMarkButton = this.addRenderableWidget(Button.builder(
                this.getToggleText("screen.petphrasex.config.remove_ignore_mark", this.removeIgnoreMark),
                button -> {
                    this.removeIgnoreMark = !this.removeIgnoreMark;
                    button.setMessage(this.getToggleText("screen.petphrasex.config.remove_ignore_mark", this.removeIgnoreMark));
                }
        ).bounds(controlX, y, CONTROL_WIDTH, 20).build());
        y += ROW_HEIGHT;

        this.prefixField = this.addRenderableWidget(new EditBox(this.font, controlX, y, CONTROL_WIDTH, 20, Component.translatable("screen.petphrasex.config.prefix")));
        this.prefixField.setValue(config.prefix);
        y += ROW_HEIGHT;

        this.suffixField = this.addRenderableWidget(new EditBox(this.font, controlX, y, CONTROL_WIDTH, 20, Component.translatable("screen.petphrasex.config.suffix")));
        this.suffixField.setValue(config.suffix);
        y += ROW_HEIGHT;

        this.sentencePrefixField = this.addRenderableWidget(new EditBox(this.font, controlX, y, CONTROL_WIDTH, 20, Component.translatable("screen.petphrasex.config.sentence_prefix")));
        this.sentencePrefixField.setValue(config.sentencePrefix);
        y += ROW_HEIGHT;

        this.sentenceSuffixField = this.addRenderableWidget(new EditBox(this.font, controlX, y, CONTROL_WIDTH, 20, Component.translatable("screen.petphrasex.config.sentence_suffix")));
        this.sentenceSuffixField.setValue(config.sentenceSuffix);

        if (this.forcedByServer) {
            this.enableClientPhraseButton.active = false;
            this.ignoreMarkField.setEditable(false);
            this.removeIgnoreMarkButton.active = false;
            this.prefixField.setEditable(false);
            this.suffixField.setEditable(false);
            this.sentencePrefixField.setEditable(false);
            this.sentenceSuffixField.setEditable(false);

            this.reclaimButton = this.addRenderableWidget(Button.builder(
                    Component.translatable("screen.petphrasex.config.reclaim"),
                    button -> button.active = !ServerFeatureHooks.sendReclaimRequest()
            ).bounds(panelX, this.height - 64, PANEL_WIDTH, 20).build());
        }

        this.doneButton = this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.save())
                .bounds(this.width / 2 - 104, this.height - 32, 100, 20)
                .build());
        this.cancelButton = this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> this.onClose())
                .bounds(this.width / 2 + 4, this.height - 32, 100, 20)
                .build());

        this.setInitialFocus(this.ignoreMarkField);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);

        int panelX = (this.width - PANEL_WIDTH) / 2;
        int labelX = panelX;
        int y = this.forcedByServer ? 69 : 53;
        int color = 0xFFA0A0A0;

        graphics.centeredText(this.font, this.title, this.width / 2, 16, 0xFFFFFFFF);
        if (this.forcedByServer) {
            graphics.centeredText(
                    this.font,
                    Component.translatable("screen.petphrasex.config.forced_warning").withStyle(ChatFormatting.RED),
                    this.width / 2,
                    32,
                    0xFFFF5555
            );
        }

        graphics.text(this.font, Component.literal("客户端口癖"), labelX, y, color, true);
        y += ROW_HEIGHT;
        graphics.text(this.font, Component.translatable("screen.petphrasex.config.ignore_mark"), labelX, y, color, true);
        y += ROW_HEIGHT;
        graphics.text(this.font, Component.translatable("screen.petphrasex.config.remove_ignore_mark"), labelX, y, color, true);
        y += ROW_HEIGHT;
        graphics.text(this.font, Component.translatable("screen.petphrasex.config.prefix"), labelX, y, color, true);
        y += ROW_HEIGHT;
        graphics.text(this.font, Component.translatable("screen.petphrasex.config.suffix"), labelX, y, color, true);
        y += ROW_HEIGHT;
        graphics.text(this.font, Component.translatable("screen.petphrasex.config.sentence_prefix"), labelX, y, color, true);
        y += ROW_HEIGHT;
        graphics.text(this.font, Component.translatable("screen.petphrasex.config.sentence_suffix"), labelX, y, color, true);
    }

    private Component getToggleText(String key, boolean enabled) {
        return Component.translatable(key + ".button", Component.translatable(enabled ? "options.on" : "options.off"));
    }

    private void save() {
        PetPhraseConfigX config = PetPhraseConfigX.get();
        config.enableClientPhrase = this.enableClientPhrase;
        config.applyPhraseSettings(new PhraseSettings(
                this.ignoreMarkField.getValue(),
                this.removeIgnoreMark,
                this.prefixField.getValue(),
                this.suffixField.getValue(),
                this.sentencePrefixField.getValue(),
                this.sentenceSuffixField.getValue()
        ));
        PetPhraseConfigX.save();
        this.closeToParent();
    }

    @Override
    public void onClose() {
        this.closeToParent();
    }

    private void closeToParent() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.parent);
        }
    }
}
