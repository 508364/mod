package com.example.examplemod.reaction;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class ElementReactionHandler {
    public enum ElementType {
        FIRE, WATER, ELECTRO, ICE, GRASS, ROCK, WIND
    }

    public static void triggerReaction(Player attacker, ElementType attackerElement, ElementType targetElement, LivingEntity target) {
        boolean isFurry = attacker.getPersistentData().getBoolean("isFurry");
        float damageMultiplier = isFurry ? 1.0F : 0.8F;
        switch (attackerElement) {
            case FIRE:
                if (targetElement == ElementType.WATER) {
                    float damage = 5.0F * damageMultiplier;
                    target.hurt(DamageSource.MAGIC, damage); // 蒸发反应，造成额外伤害（非Furry状态削弱20%）
                } else if (targetElement == ElementType.GRASS) {
                    target.setSecondsOnFire((int)(5 * damageMultiplier)); // 燃烧反应（非Furry状态时间减少20%）
                }
                break;
            case ELECTRO:
                if (targetElement == ElementType.WATER) {
                    float damage = 3.0F * damageMultiplier;
                    target.hurt(DamageSource.MAGIC, damage); // 感电反应（非Furry状态削弱20%）
                    target.getLevel().explode(null, target.getX(), target.getY(), target.getZ(), 1.0F * damageMultiplier, false, net.minecraft.world.level.Explosion.BlockInteraction.NONE);
                }
                break;
            // 其他元素反应逻辑
        }
    }
}