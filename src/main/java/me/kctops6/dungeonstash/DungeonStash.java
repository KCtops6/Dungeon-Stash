package me.kctops6.dungeonstash;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(DungeonStash.MODID)
public class DungeonStash {
    public static final String MODID = "dungeon_stash";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<Item> DUNGEON_LOOT_BAG = ITEMS.register("dungeon_loot_bag",
            () -> new ThemedLootBagItem(new Item.Properties().stacksTo(16), BuiltInLootTables.SIMPLE_DUNGEON));
    public static final RegistryObject<Item> STARTER_LOOT_BAG = ITEMS.register("starter_loot_bag",
            () -> new ThemedLootBagItem(new Item.Properties().stacksTo(16), BuiltInLootTables.SPAWN_BONUS_CHEST));
    public static final RegistryObject<Item> STRONGHOLD_LOOT_BAG = ITEMS.register("stronghold_loot_bag",
            () -> new ThemedLootBagItem(new Item.Properties().stacksTo(16), BuiltInLootTables.STRONGHOLD_CORRIDOR));
    public static final RegistryObject<Item> NETHER_FORTRESS_LOOT_BAG = ITEMS.register("nether_fortress_loot_bag",
            () -> new ThemedLootBagItem(new Item.Properties().stacksTo(16), BuiltInLootTables.NETHER_BRIDGE));
    public static final RegistryObject<Item> END_CITY_LOOT_BAG = ITEMS.register("end_city_loot_bag",
            () -> new ThemedLootBagItem(new Item.Properties().stacksTo(16), BuiltInLootTables.END_CITY_TREASURE));
    public static final RegistryObject<Item> IGLOO_LOOT_BAG = ITEMS.register("igloo_loot_bag",
            () -> new ThemedLootBagItem(new Item.Properties().stacksTo(16), BuiltInLootTables.IGLOO_CHEST));
    public static final RegistryObject<Item> WOODLAND_MANSION_LOOT_BAG = ITEMS.register("woodland_mansion_loot_bag",
            () -> new ThemedLootBagItem(new Item.Properties().stacksTo(16), BuiltInLootTables.WOODLAND_MANSION));
    public static final RegistryObject<Item> SHIPWRECK_LOOT_BAG = ITEMS.register("shipwreck_loot_bag",
            () -> new ThemedLootBagItem(new Item.Properties().stacksTo(16), BuiltInLootTables.SHIPWRECK_TREASURE));
    public static final RegistryObject<Item> BURIED_TREASURE_LOOT_BAG = ITEMS.register("buried_treasure_loot_bag",
            () -> new ThemedLootBagItem(new Item.Properties().stacksTo(16), BuiltInLootTables.BURIED_TREASURE));
    public static final RegistryObject<Item> PILLAGER_OUTPOST_LOOT_BAG = ITEMS.register("pillager_outpost_loot_bag",
            () -> new ThemedLootBagItem(new Item.Properties().stacksTo(16), BuiltInLootTables.PILLAGER_OUTPOST));
    public static final RegistryObject<Item> BASTION_REMNANT_LOOT_BAG = ITEMS.register("bastion_remnant_loot_bag",
            () -> new ThemedLootBagItem(new Item.Properties().stacksTo(16), BuiltInLootTables.BASTION_TREASURE));
    public static final RegistryObject<Item> RUINED_PORTAL_LOOT_BAG = ITEMS.register("ruined_portal_loot_bag",
            () -> new ThemedLootBagItem(new Item.Properties().stacksTo(16), BuiltInLootTables.RUINED_PORTAL));
    public static final RegistryObject<Item> ANCIENT_CITY_LOOT_BAG = ITEMS.register("ancient_city_loot_bag",
            () -> new AncientCityLootBagItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<CreativeModeTab> DUNGEON_STASH_TAB = CREATIVE_MODE_TABS.register("dungeon_stash_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> DUNGEON_LOOT_BAG.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.dungeon_stash.dungeon_stash_tab"))
            .displayItems((parameters, output) -> {
                output.accept(DUNGEON_LOOT_BAG.get());
                output.accept(STARTER_LOOT_BAG.get());
                output.accept(STRONGHOLD_LOOT_BAG.get());
                output.accept(NETHER_FORTRESS_LOOT_BAG.get());
                output.accept(END_CITY_LOOT_BAG.get());
                output.accept(IGLOO_LOOT_BAG.get());
                output.accept(WOODLAND_MANSION_LOOT_BAG.get());
                output.accept(SHIPWRECK_LOOT_BAG.get());
                output.accept(BURIED_TREASURE_LOOT_BAG.get());
                output.accept(PILLAGER_OUTPOST_LOOT_BAG.get());
                output.accept(BASTION_REMNANT_LOOT_BAG.get());
                output.accept(RUINED_PORTAL_LOOT_BAG.get());
                output.accept(ANCIENT_CITY_LOOT_BAG.get());
            }).build());

    public DungeonStash() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        ITEMS.register(modEventBus);
        DungeonBagMenu.MENUS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            net.minecraft.client.gui.screens.MenuScreens.register(
                    DungeonBagMenu.DUNGEON_BAG_MENU.get(),
                    DungeonBagScreen::new
            );
        }
    }
}