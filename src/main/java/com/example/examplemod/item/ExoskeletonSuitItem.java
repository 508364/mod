package com.example.examplemod.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ExoskeletonSuitItem extends Item {
    public ExoskeletonSuitItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            player.getPersistentData().putBoolean("wearingExoskeleton", !player.getPersistentData().getBoolean("wearingExoskeleton"));
            // 外骨骼装备效果：增加跳跃高度
            player.setJumpPower(0.5F);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}