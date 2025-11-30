package committee.nova.petphrasex.client;

import committee.nova.petphrasex.config.PetPhraseConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PetPhraseConfigScreen extends Screen {
    private final Screen parent;
    private EditBox petPhraseField;
    private EditBox prefixesField;

    public PetPhraseConfigScreen(Screen parent) {
        super(Component.translatable("menu.petphrasex.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 4;

        // 1. 口癖设置 (Pet Phrase)
        this.addRenderableWidget(Button.builder(Component.literal("Pet Phrase"), button -> {})
                .bounds(centerX - 100, startY - 25, 200, 20).build()).active = false;

        this.petPhraseField = new EditBox(this.font, centerX - 100, startY, 200, 20, Component.literal("Pet Phrase"));
        this.petPhraseField.setMaxLength(256);
        // 读取当前配置
        this.petPhraseField.setValue(PetPhraseConfig.CLIENT.petPhrase.get());
        this.addRenderableWidget(this.petPhraseField);

        this.addRenderableWidget(Button.builder(Component.literal("Prefixes (Split by ;) / 忽略前缀(用;分隔)"), button -> {})
                .bounds(centerX - 100, startY + 35, 200, 20).build()).active = false;

        this.prefixesField = new EditBox(this.font, centerX - 100, startY + 60, 200, 20, Component.literal("Prefixes"));
        this.prefixesField.setMaxLength(1024);
        // 读取列表并转为字符串
        List<? extends String> currentPrefixes = PetPhraseConfig.CLIENT.filteredPrefix.get();
        String prefixStr = String.join(";", currentPrefixes);
        this.prefixesField.setValue(prefixStr);
        this.addRenderableWidget(this.prefixesField);

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.save())
                .bounds(centerX - 105, this.height - 50, 100, 20).build());

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> this.onClose())
                .bounds(centerX + 5, this.height - 50, 100, 20).build());
    }

    private void save() {
        PetPhraseConfig.CLIENT.petPhrase.set(this.petPhraseField.getValue());
        String rawPrefixes = this.prefixesField.getValue();
        List<String> newPrefixes = Arrays.stream(rawPrefixes.split(";"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        PetPhraseConfig.CLIENT.filteredPrefix.set(newPrefixes);
        PetPhraseConfig.CLIENT_SPEC.save();
        this.onClose();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }
}