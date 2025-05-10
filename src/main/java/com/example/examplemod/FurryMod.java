package com.example.examplemod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FurryMod.MOD_ID)
public class FurryMod {
    public static final String MOD_ID = "examplemod";

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final RegistryObject<Item> FURRY_TRANSFORMATION_ITEM = ITEMS.register("furry_transformation_item", () -> new FurryTransformationItem(new Item.Properties().stacksTo(1)));

    public FurryMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus); // 注册物品

        // 注册键绑定
        KeyMapping openSkillPanelKey = new KeyMapping("key.furrymod.open_skill_panel", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_N, "category.furrymod.main");
        ClientRegistry.registerKeyBinding(openSkillPanelKey);

        // 检测TACZ模组是否存在
        boolean isTACZLoaded = ModList.get().isLoaded("tacz");
        if (isTACZLoaded) {
            // 注册TACZ兼容事件（示例：调整枪械使用时的技能效果）
            modEventBus.addListener((LivingEntityUseItemEvent.Finish event) -> {
                if (event.getEntity() instanceof Player player && player.getPersistentData().getBoolean("isFurry")) {
                    // Furry状态下枪械技能威力增强
                    event.getItem().hurtAndBreak(-1, player, p -> {});
                }
            });
        }

        // 注册按键事件监听
        modEventBus.addListener((InputEvent.Key event) -> {
            if (openSkillPanelKey.consumeClick()) {
                // 触发打开技能面板逻辑
                Minecraft.getInstance().setScreen(new SkillPanelScreen());
            }
        });
    }
}