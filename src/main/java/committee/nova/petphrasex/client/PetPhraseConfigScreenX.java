package committee.nova.petphrasex.client;

import committee.nova.petphrasex.config.PetPhraseConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PetPhraseConfigScreenX extends Screen {
    private final Screen parent;
    private TextFieldWidget petPhraseField;
    private TextFieldWidget prefixesField;

    public PetPhraseConfigScreenX(Screen parent) {
        super(Text.translatable("menu.petphrasex.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 4;

        // 使用 ButtonWidget 模拟标签
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Pet Phrase"), button -> {})
                .dimensions(centerX - 100, startY - 25, 200, 20).build()).active = false;

        this.petPhraseField = new TextFieldWidget(this.textRenderer, centerX - 100, startY, 200, 20, Text.literal("Pet Phrase"));
        this.petPhraseField.setMaxLength(256);
        // 读取配置
        this.petPhraseField.setText(PetPhraseConfig.get().petPhrase);
        this.addDrawableChild(this.petPhraseField);
        // 忽略前缀设置 (Prefixes)
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Prefixes (Space Split) / 忽略前缀 (用空格分隔)"), button -> {})
                .dimensions(centerX - 100, startY + 35, 200, 20).build()).active = false;

        this.prefixesField = new TextFieldWidget(this.textRenderer, centerX - 100, startY + 60, 200, 20, Text.literal("Prefixes"));
        this.prefixesField.setMaxLength(1024);

        List<String> currentPrefixes = PetPhraseConfig.get().filteredPrefix;
        String prefixStr = String.join(" ", currentPrefixes);

        this.prefixesField.setText(prefixStr);
        this.addDrawableChild(this.prefixesField);
        // 保存
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> this.save())
                .dimensions(centerX - 105, this.height - 50, 100, 20).build());
        // 取消
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, button -> this.close())
                .dimensions(centerX + 5, this.height - 50, 100, 20).build());
    }

    private void save() {
        PetPhraseConfig config = PetPhraseConfig.get();

        // 保存口癖
        config.petPhrase = this.petPhraseField.getText();

        // 保存前缀
        String rawPrefixes = this.prefixesField.getText();
        // 【优化】使用正则 "\\s+" 按空白字符分割 (支持空格、Tab、多个空格)
        List<String> newPrefixes = Arrays.stream(rawPrefixes.split("\\s+"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        config.filteredPrefix = newPrefixes;

        // 写入文件
        PetPhraseConfig.save();

        this.close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        // Yarn 映射中 drawCenteredString 对应 drawCenteredTextWithShadow
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}