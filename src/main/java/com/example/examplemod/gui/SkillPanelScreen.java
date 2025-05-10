package com.example.examplemod.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class SkillPanelScreen extends Screen {
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 20;
    private String selectedElement = "FIRE";

    protected SkillPanelScreen() {
        super(Component.translatable("screen.furrymod.skill_panel"));
    }

    @Override
    protected void init() {
        // 元素选择按钮
        addRenderableWidget(new ExtendedButton(width / 2 - BUTTON_WIDTH - 10, height / 2 - 60, BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.literal("火元素"), (btn) -> selectedElement = "FIRE"));
        addRenderableWidget(new ExtendedButton(width / 2 + 10, height / 2 - 60, BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.literal("水元素"), (btn) -> selectedElement = "WATER"));

        // 技能选择区域（示例）
        for (int i = 0; i < 5; i++) {
            int y = height / 2 - 30 + i * (BUTTON_HEIGHT + 5);
            addRenderableWidget(new ExtendedButton(width / 2 - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT,
                    Component.literal("技能" + (i + 1)), (btn) -> {
                        // 技能选择逻辑，保存选择结果
                    }));
        }

        // 确认按钮
        addRenderableWidget(new ExtendedButton(width / 2 - BUTTON_WIDTH / 2, height / 2 + 50, BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.literal("确认选择"), (btn) -> {
                    Player player = Minecraft.getInstance().player;
                    if (player != null) {
                        // 保存选择的元素和技能到玩家NBT
                        player.getPersistentData().putString("selectedElement", selectedElement);
                        // 示例保存5个技能ID（实际应根据具体技能配置）
                        int[] skillIds = new int[5];
                        for (int i = 0; i < 5; i++) {
                            skillIds[i] = i + 1; // 示例技能ID
                        }
                        player.getPersistentData().putIntArray("selectedSkills", skillIds);
                    }
                    onClose();
                }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);
        drawCenteredString(poseStack, font, title, width / 2, height / 2 - 80, 0xFFFFFF);
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}