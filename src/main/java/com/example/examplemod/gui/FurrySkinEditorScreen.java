package com.example.examplemod.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import net.minecraft.world.entity.player.Player;

public class FurrySkinEditorScreen extends Screen {
    private EditBox hornPositionX; 
    private EditBox hornPositionY; 
    private Checkbox hasHornsCheckbox; 
    private ExtendedButton tailTypeButton; 
    private int tailTypeIndex = 0; 
    private final Player player; 

    protected FurrySkinEditorScreen(Player player) {
        super(Component.translatable("screen.furrymod.skin_editor"));
        this.player = player;
    }

    @Override
    protected void init() {
        // 角存在性复选框
        hasHornsCheckbox = new Checkbox(width / 2 - 100, height / 2 - 100, 200, 20,
                Component.literal("是否有角"), player.getPersistentData().getBoolean("hasHorns"));
        addRenderableWidget(hasHornsCheckbox);

        // 角位置输入框
        hornPositionX = new EditBox(font, width / 2 - 100, height / 2 - 70, 80, 20,
                Component.literal("角X坐标"));
        hornPositionX.setValue(String.valueOf(player.getPersistentData().getFloat("hornX")));
        addRenderableWidget(hornPositionX);

        hornPositionY = new EditBox(font, width / 2 + 30, height / 2 - 70, 80, 20,
                Component.literal("角Y坐标"));
        hornPositionY.setValue(String.valueOf(player.getPersistentData().getFloat("hornY")));
        addRenderableWidget(hornPositionY);

        // 尾巴类型按钮
        tailTypeButton = new ExtendedButton(width / 2 - 100, height / 2 - 40, 200, 20,
                Component.literal("尾巴类型: 蓬松尾"), (btn) -> {
                    tailTypeIndex = (tailTypeIndex + 1) % 3; 
                    String[] types = {"蓬松尾", "短尾", "火焰尾"};
                    btn.setMessage(Component.literal("尾巴类型: " + types[tailTypeIndex]));
                });
        addRenderableWidget(tailTypeButton);

        // 保存按钮
        addRenderableWidget(new ExtendedButton(width / 2 - 100, height / 2 + 50, 200, 20,
                Component.literal("保存皮肤"), (btn) -> saveSkinSettings()));
    }

    private void saveSkinSettings() {
        player.getPersistentData().putBoolean("hasHorns", hasHornsCheckbox.selected());
        player.getPersistentData().putFloat("hornX", Float.parseFloat(hornPositionX.getValue()));
        player.getPersistentData().putFloat("hornY", Float.parseFloat(hornPositionY.getValue()));
        player.getPersistentData().putInt("tailType", tailTypeIndex);
        onClose();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);
        drawCenteredString(poseStack, font, title, width / 2, height / 2 - 130, 0xFFFFFF);
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}