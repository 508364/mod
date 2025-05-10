package com.example.examplemod.util;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import com.mojang.blaze3d.platform.InputConstants;

public class FacialCaptureManager {
    private static boolean isEnabled = false;

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, FacialCaptureConfig.SPEC);
        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class,
                () -> new ConfigGuiHandler.ConfigGuiFactory((mc, screen) -> new FacialCaptureConfigScreen(screen)));
    }

    public static boolean isEnabled() {
        return isEnabled;
    }

    public static void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public static void updateCaptureData() {
        if (isEnabled && Minecraft.getInstance().player != null) {
            // 这里添加实际的面部捕捉数据获取逻辑（示例使用键盘模拟）
            float jawOpen = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.KEY_DOWN) ? 0.5F : 0.0F;
            float eyeBlink = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.KEY_SPACE) ? 1.0F : 0.0F;
            // 将数据存储到玩家NBT中
            Minecraft.getInstance().player.getPersistentData().putFloat("jawOpen", jawOpen);
            Minecraft.getInstance().player.getPersistentData().putFloat("eyeBlink", eyeBlink);
        }
    }
}