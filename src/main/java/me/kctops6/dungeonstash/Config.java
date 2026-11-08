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

    // Drop Toggles & Chances
    public static final ForgeConfigSpec.BooleanValue ZOMBIE_DROP_ENABLED = BUILDER.define("zombieDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue ZOMBIE_DROP_CHANCE = BUILDER.defineInRange("zombieDropChance", 2.5, 0.0, 100.0);

    public static final ForgeConfigSpec.BooleanValue SKELETON_DROP_ENABLED = BUILDER.define("skeletonDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue SKELETON_DROP_CHANCE = BUILDER.defineInRange("skeletonDropChance", 2.5, 0.0, 100.0);

    public static final ForgeConfigSpec.BooleanValue SPIDER_DROP_ENABLED = BUILDER.define("spiderDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue SPIDER_DROP_CHANCE = BUILDER.defineInRange("spiderDropChance", 2.5, 0.0, 100.0);

    // Stronghold
    public static final ForgeConfigSpec.BooleanValue STRONGHOLD_DROP_ENABLED = BUILDER.define("strongholdDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue STRONGHOLD_SILVERFISH_CHANCE = BUILDER.defineInRange("strongholdSilverfishChance", 5.0, 0.0, 100.0);
    public static final ForgeConfigSpec.DoubleValue STRONGHOLD_OTHER_CHANCE = BUILDER.defineInRange("strongholdOtherChance", 0.5, 0.0, 100.0);

    // Nether Fortress
    public static final ForgeConfigSpec.BooleanValue NETHER_FORTRESS_DROP_ENABLED = BUILDER.define("netherFortressDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue NETHER_FORTRESS_CHANCE = BUILDER.defineInRange("netherFortressChance", 2.0, 0.0, 100.0);

    // End City
    public static final ForgeConfigSpec.BooleanValue END_CITY_DROP_ENABLED = BUILDER.define("endCityDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue SHULKER_CHANCE = BUILDER.defineInRange("shulkerChance", 5.0, 0.0, 100.0);
    public static final ForgeConfigSpec.DoubleValue ENDERMAN_CHANCE = BUILDER.defineInRange("endermanChance", 0.2, 0.0, 100.0);

    // Igloo
    public static final ForgeConfigSpec.BooleanValue IGLOO_DROP_ENABLED = BUILDER.define("iglooDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue IGLOO_CHANCE = BUILDER.defineInRange("iglooChance", 2.0, 0.0, 100.0);

    // Woodland Mansion & Pillager Outpost
    public static final ForgeConfigSpec.BooleanValue MANSION_DROP_ENABLED = BUILDER.define("mansionDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue EVOKER_CHANCE = BUILDER.defineInRange("evokerChance", 15.0, 0.0, 100.0);
    public static final ForgeConfigSpec.DoubleValue VINDICATOR_CHANCE = BUILDER.defineInRange("vindicatorChance", 5.0, 0.0, 100.0);
    public static final ForgeConfigSpec.DoubleValue ILLAGER_CHANCE = BUILDER.defineInRange("illagerChance", 1.0, 0.0, 100.0);

    public static final ForgeConfigSpec.BooleanValue PILLAGER_OUTPOST_DROP_ENABLED = BUILDER.define("pillagerOutpostDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue PILLAGER_OUTPOST_CHANCE = BUILDER.defineInRange("pillagerOutpostChance", 2.0, 0.0, 100.0);

    // Shipwreck & Buried Treasure
    public static final ForgeConfigSpec.BooleanValue SHIPWRECK_DROP_ENABLED = BUILDER.define("shipwreckDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue SHIPWRECK_CHANCE = BUILDER.defineInRange("shipwreckChance", 2.0, 0.0, 100.0);

    public static final ForgeConfigSpec.BooleanValue BURIED_TREASURE_DROP_ENABLED = BUILDER.define("buriedTreasureDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue BURIED_TREASURE_CHANCE = BUILDER.defineInRange("buriedTreasureChance", 2.0, 0.0, 100.0);

    // Bastion Remnant
    public static final ForgeConfigSpec.BooleanValue BASTION_DROP_ENABLED = BUILDER.define("bastionDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue BRUTE_CHANCE = BUILDER.defineInRange("bruteChance", 15.0, 0.0, 100.0);
    public static final ForgeConfigSpec.DoubleValue PIGLIN_CHANCE = BUILDER.defineInRange("piglinChance", 1.0, 0.0, 100.0);

    // Ruined Portal
    public static final ForgeConfigSpec.BooleanValue RUINED_PORTAL_DROP_ENABLED = BUILDER.define("ruinedPortalDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue RUINED_PORTAL_CHANCE = BUILDER.defineInRange("ruinedPortalChance", 2.0, 0.0, 100.0);

    // Ancient City
    public static final ForgeConfigSpec.BooleanValue ANCIENT_CITY_DROP_ENABLED = BUILDER.define("ancientCityDropEnabled", true);
    public static final ForgeConfigSpec.DoubleValue ANCIENT_CITY_STRAY_RARE = BUILDER.defineInRange("ancientCityStrayRareChance", 0.5, 0.0, 100.0);
    public static final ForgeConfigSpec.DoubleValue ANCIENT_CITY_VERY_RARE = BUILDER.defineInRange("ancientCityVeryRareChance", 0.05, 0.0, 100.0);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean guiMode = true;
    public static boolean zombieDropEnabled, skeletonDropEnabled, spiderDropEnabled;
    public static double zombieDropChance, skeletonDropChance, spiderDropChance;
    public static boolean strongholdDropEnabled, netherFortressDropEnabled, endCityDropEnabled, iglooDropEnabled;
    public static double strongholdSilverfishChance, strongholdOtherChance, netherFortressChance, shulkerChance, endermanChance, iglooChance;
    public static boolean mansionDropEnabled, pillagerOutpostDropEnabled, shipwreckDropEnabled, buriedTreasureDropEnabled;
    public static double evokerChance, vindicatorChance, illagerChance, pillagerOutpostChance, shipwreckChance, buriedTreasureChance;
    public static boolean bastionDropEnabled, ruinedPortalDropEnabled, ancientCityDropEnabledConfig;
    public static double bruteChance, piglinChance, ruinedPortalChance, ancientCityStrayRareChance, ancientCityVeryRareChance;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        guiMode = GUI_MODE_SETTING.get();
        zombieDropEnabled = ZOMBIE_DROP_ENABLED.get();
        zombieDropChance = ZOMBIE_DROP_CHANCE.get();
        skeletonDropEnabled = SKELETON_DROP_ENABLED.get();
        skeletonDropChance = SKELETON_DROP_CHANCE.get();
        spiderDropEnabled = SPIDER_DROP_ENABLED.get();
        spiderDropChance = SPIDER_DROP_CHANCE.get();

        strongholdDropEnabled = STRONGHOLD_DROP_ENABLED.get();
        strongholdSilverfishChance = STRONGHOLD_SILVERFISH_CHANCE.get();
        strongholdOtherChance = STRONGHOLD_OTHER_CHANCE.get();

        netherFortressDropEnabled = NETHER_FORTRESS_DROP_ENABLED.get();
        netherFortressChance = NETHER_FORTRESS_CHANCE.get();

        endCityDropEnabled = END_CITY_DROP_ENABLED.get();
        shulkerChance = SHULKER_CHANCE.get();
        endermanChance = ENDERMAN_CHANCE.get();

        iglooDropEnabled = IGLOO_DROP_ENABLED.get();
        iglooChance = IGLOO_CHANCE.get();

        mansionDropEnabled = MANSION_DROP_ENABLED.get();
        evokerChance = EVOKER_CHANCE.get();
        vindicatorChance = VINDICATOR_CHANCE.get();
        illagerChance = ILLAGER_CHANCE.get();

        pillagerOutpostDropEnabled = PILLAGER_OUTPOST_DROP_ENABLED.get();
        pillagerOutpostChance = PILLAGER_OUTPOST_CHANCE.get();

        shipwreckDropEnabled = SHIPWRECK_DROP_ENABLED.get();
        shipwreckChance = SHIPWRECK_CHANCE.get();

        buriedTreasureDropEnabled = BURIED_TREASURE_DROP_ENABLED.get();
        buriedTreasureChance = BURIED_TREASURE_CHANCE.get();

        bastionDropEnabled = BASTION_DROP_ENABLED.get();
        bruteChance = BRUTE_CHANCE.get();
        piglinChance = PIGLIN_CHANCE.get();

        ruinedPortalDropEnabled = RUINED_PORTAL_DROP_ENABLED.get();
        ruinedPortalChance = RUINED_PORTAL_CHANCE.get();

        ancientCityDropEnabledConfig = ANCIENT_CITY_DROP_ENABLED.get();
        ancientCityStrayRareChance = ANCIENT_CITY_STRAY_RARE.get();
        ancientCityVeryRareChance = ANCIENT_CITY_VERY_RARE.get();
    }

    public static void setGuiMode(boolean mode) {
        guiMode = mode;
        GUI_MODE_SETTING.set(mode);
    }
}