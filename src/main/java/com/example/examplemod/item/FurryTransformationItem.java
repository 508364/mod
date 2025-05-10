package com.example.examplemod.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FurryTransformationItem extends Item {
    public FurryTransformationItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            boolean isFurry = player.getPersistentData().getBoolean("isFurry");
            player.getPersistentData().putBoolean("isFurry", !isFurry);
            // 触发模型更新逻辑（后续补充）
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}