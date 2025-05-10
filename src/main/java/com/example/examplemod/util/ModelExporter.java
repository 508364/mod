package com.example.examplemod.util;

import com.example.examplemod.model.FurryModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ModelExporter {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String EXPORT_DIR = "furry_skins";

    public static void exportFurrySkin(Player player, String skinName) {
        CompoundTag skinData = new CompoundTag();
        // 获取当前玩家的Furry模型参数（示例）
        skinData.putBoolean("hasHorns", FurryModel.hasHorns(player));
        skinData.putInt("tailType", FurryModel.getTailType(player));
        skinData.putBoolean("isBentLegs", FurryModel.isBentLegs(player));

        Path exportPath = Paths.get(Minecraft.getInstance().gameDirectory.getPath(), EXPORT_DIR);
        if (!Files.exists(exportPath)) {
            try {
                Files.createDirectories(exportPath);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        String json = GSON.toJson(skinData);
        String zipFileName = exportPath.resolve(skinName + ".zip").toString();

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName))) {
            ZipEntry entry = new ZipEntry("skin_data.json");
            zos.putNextEntry(entry);
            zos.write(json.getBytes());
            zos.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CompoundTag importFurrySkin(String zipFilePath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("skin_data.json")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(zis));
                    return GSON.fromJson(reader, CompoundTag.class);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CompoundTag();
    }
}