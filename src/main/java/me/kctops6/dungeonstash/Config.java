package me.kctops6.dungeonstash;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = DungeonStash.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue GUI_MODE_SETTING = BUILDER
            .comment("True = Open GUI inventory that destroys item when emptied; false = Populate items directly into player inventory or drop on ground.")
            .define("guiMode", true);

    // Mob drop configurations
    public static final ForgeConfigSpec.BooleanValue ZOMBIE_DROP_ENABLED = BUILDER
            .comment("Enable loot bag drops from Zombies")
            .define("zombieDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue ZOMBIE_DROP_CHANCE = BUILDER
            .comment("Drop chance percentage for Zombies (0.0 to 100.0)")
            .defineInRange("zombieDropChance", 2.5, 0.0, 100.0);

    public static final ForgeConfigSpec.BooleanValue SKELETON_DROP_ENABLED = BUILDER
            .comment("Enable loot bag drops from Skeletons")
            .define("skeletonDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue SKELETON_DROP_CHANCE = BUILDER
            .comment("Drop chance percentage for Skeletons (0.0 to 100.0)")
            .defineInRange("skeletonDropChance", 2.5, 0.0, 100.0);

    public static final ForgeConfigSpec.BooleanValue SPIDER_DROP_ENABLED = BUILDER
            .comment("Enable loot bag drops from Spiders")
            .define("spiderDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue SPIDER_DROP_CHANCE = BUILDER
            .comment("Drop chance percentage for Spiders (0.0 to 100.0)")
            .defineInRange("spiderDropChance", 2.5, 0.0, 100.0);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean guiMode = true;
    public static boolean zombieDropEnabled;
    public static double zombieDropChance;
    public static boolean skeletonDropEnabled;
    public static double skeletonDropChance;
    public static boolean spiderDropEnabled;
    public static double spiderDropChance;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        guiMode = GUI_MODE_SETTING.get();
        zombieDropEnabled = ZOMBIE_DROP_ENABLED.get();
        zombieDropChance = ZOMBIE_DROP_CHANCE.get();
        skeletonDropEnabled = SKELETON_DROP_ENABLED.get();
        skeletonDropChance = SKELETON_DROP_CHANCE.get();
        spiderDropEnabled = SPIDER_DROP_ENABLED.get();
        spiderDropChance = SPIDER_DROP_CHANCE.get();
    }

    public static void setGuiMode(boolean mode) {
        guiMode = mode;
        GUI_MODE_SETTING.set(mode);
    }
}