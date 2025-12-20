package committee.nova.petphrasex.client;

import committee.nova.petphrasex.config.PetPhraseConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.ArrayList;

public class PetPhraseConfigScreenX extends Screen {

    private final Screen parent;
    private EditBox petPhraseField;
    private EditBox prefixesField;

    public PetPhraseConfigScreenX(Screen parent) {
        super(Component.literal("PetPhrase Configuration"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 4;

        // 使用 ButtonWidget 模拟标签
        this.petPhraseField = new EditBox(this.font, centerX - 100, startY, 200, 20, Component.literal("Pet Phrase"));
        this.petPhraseField.setMaxLength(256);
        // 读取配置
        this.petPhraseField.setValue(PetPhraseConfig.get().petPhrase);
        this.addRenderableWidget(this.petPhraseField);

        this.prefixesField = new EditBox(this.font, centerX - 100, startY + 60, 200, 20, Component.literal("Prefixes"));
        this.prefixesField.setMaxLength(256);
        String currentPrefixes = String.join(" ", PetPhraseConfig.get().filteredPrefix);
        this.prefixesField.setValue(currentPrefixes);
        this.addRenderableWidget(this.prefixesField);

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.save())
                .bounds(centerX - 105, this.height - 50, 100, 20).build());

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> this.onClose())
                .bounds(centerX + 5, this.height - 50, 100, 20).build());
    }

    private void save() {
        PetPhraseConfig config = PetPhraseConfig.get();

        // 保存口癖
        config.petPhrase = this.petPhraseField.getValue();

        // 保存前缀
        String prefixesInput = this.prefixesField.getValue().trim();
        if (prefixesInput.isEmpty()) {
            config.filteredPrefix = new ArrayList<>();
        } else {
            config.filteredPrefix = new ArrayList<>(Arrays.asList(prefixesInput.split("\\s+")));
        }

        // 写入文件
        PetPhraseConfig.save();
        this.onClose();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFFFF);

        guiGraphics.drawCenteredString(this.font, Component.literal("Pet Phrase"), this.width / 2, this.height / 4 - 15, 0xFFFFFFFF);

        guiGraphics.drawCenteredString(this.font, Component.literal("Prefixes (Space Split) / 忽略前缀 (用空格分隔)"), this.width / 2, this.height / 4 + 45, 0xFFFFFFFF);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }
}