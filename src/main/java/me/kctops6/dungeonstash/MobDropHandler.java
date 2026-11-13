package me.kctops6.dungeonstash;

import me.kctops6.dungeonstash.item.LootBagItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonStash.MODID)
public class MobDropHandler {
    @SubscribeEvent
    public static void onMobDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getSource().getEntity() instanceof Player)) return;
        Monster mob = event.getEntity() instanceof Monster ? (Monster) event.getEntity() : null;
        if (mob == null) return;
        Level level = mob.level();
        EntityType<?> type = mob.getType();
        double roll = level.random.nextDouble() * 100.0;
        Item droppedBag = null;
        int count = 1;
        if (type == EntityType.WARDEN && Config.ancientCityDropEnabledConfig) {
            droppedBag = DungeonStash.ANCIENT_CITY_LOOT_BAG.get();
            count = 3;
        }
        else if (type == EntityType.ZOMBIE && Config.zombieDropEnabled && roll < Config.zombieDropChance) {
            droppedBag = DungeonStash.DUNGEON_LOOT_BAG.get();
        } else if (type == EntityType.SKELETON) {
            if (Config.ancientCityDropEnabledConfig && roll < Config.ancientCityStrayRareChance) {
                droppedBag = DungeonStash.ANCIENT_CITY_LOOT_BAG.get();
            } else if (Config.iglooDropEnabled && roll < Config.iglooChance) {
                droppedBag = DungeonStash.IGLOO_LOOT_BAG.get();
            } else if (Config.skeletonDropEnabled && roll < Config.skeletonDropChance) {
                droppedBag = DungeonStash.DUNGEON_LOOT_BAG.get();
            }
        } else if (type == EntityType.STRAY && Config.iglooDropEnabled && roll < Config.iglooChance) {
            droppedBag = DungeonStash.IGLOO_LOOT_BAG.get();
        } else if (type == EntityType.SPIDER && Config.spiderDropEnabled && roll < Config.spiderDropChance) {
            droppedBag = DungeonStash.DUNGEON_LOOT_BAG.get();
        }
        else if (type == EntityType.SILVERFISH && Config.strongholdDropEnabled && roll < Config.strongholdSilverfishChance) {
            droppedBag = getRandomStrongholdTable(level);
        } else if (isOverworldMob(type) && Config.strongholdDropEnabled && roll < Config.strongholdOtherChance) {
            droppedBag = getRandomStrongholdTable(level);
        }
        else if ((type == EntityType.BLAZE || type == EntityType.WITHER_SKELETON) && Config.netherFortressDropEnabled && roll < Config.netherFortressChance) {
            droppedBag = DungeonStash.NETHER_FORTRESS_LOOT_BAG.get();
        }
        else if (type == EntityType.SHULKER && Config.endCityDropEnabled && roll < Config.shulkerChance) {
            droppedBag = DungeonStash.END_CITY_LOOT_BAG.get();
        } else if (type == EntityType.ENDERMAN && Config.endCityDropEnabled && roll < Config.endermanChance) {
            droppedBag = DungeonStash.END_CITY_LOOT_BAG.get();
        }
        else if (type == EntityType.EVOKER && Config.mansionDropEnabled && roll < Config.evokerChance) {
            droppedBag = DungeonStash.WOODLAND_MANSION_LOOT_BAG.get();
        } else if (type == EntityType.VINDICATOR && Config.mansionDropEnabled && roll < Config.vindicatorChance) {
            droppedBag = DungeonStash.WOODLAND_MANSION_LOOT_BAG.get();
        } else if (isIllager(type) && Config.mansionDropEnabled && roll < Config.illagerChance) {
            droppedBag = DungeonStash.WOODLAND_MANSION_LOOT_BAG.get();
        }
        else if (type == EntityType.DROWNED) {
            if (Config.buriedTreasureDropEnabled && roll < Config.buriedTreasureChance) {
                droppedBag = DungeonStash.BURIED_TREASURE_LOOT_BAG.get();
            } else if (Config.shipwreckDropEnabled && roll < Config.shipwreckChance) {
                droppedBag = DungeonStash.SHIPWRECK_LOOT_BAG.get();
            }
        }
        else if (isIllager(type) && Config.pillagerOutpostDropEnabled && roll < Config.pillagerOutpostChance) {
            droppedBag = DungeonStash.PILLAGER_OUTPOST_LOOT_BAG.get();
        }
        else if (type == EntityType.PIGLIN_BRUTE && Config.bastionDropEnabled && roll < Config.bruteChance) {
            droppedBag = getRandomBastionTable(level);
        } else if (type == EntityType.PIGLIN && Config.bastionDropEnabled && roll < Config.piglinChance) {
            droppedBag = getRandomBastionTable(level);
        }
        else if (type == EntityType.ZOMBIFIED_PIGLIN && Config.ruinedPortalDropEnabled && roll < Config.ruinedPortalChance) {
            droppedBag = DungeonStash.RUINED_PORTAL_LOOT_BAG.get();
        }
        else if (isOverworldMob(type) && Config.ancientCityDropEnabledConfig && roll < Config.ancientCityVeryRareChance) {
            droppedBag = DungeonStash.ANCIENT_CITY_LOOT_BAG.get();
        }
        if (droppedBag != null) {
            ItemStack bagStack = new ItemStack(droppedBag, count);
            ItemEntity itemEntity = new ItemEntity(level, mob.getX(), mob.getY(), mob.getZ(), bagStack);
            level.addFreshEntity(itemEntity);
        }
    }
    private static boolean isOverworldMob(EntityType<?> type) {
        return type == EntityType.ZOMBIE || type == EntityType.SKELETON || type == EntityType.SPIDER ||
                type == EntityType.CREEPER || type == EntityType.WITCH || type == EntityType.ZOMBIE_VILLAGER;
    }
    private static boolean isIllager(EntityType<?> type) {
        return type == EntityType.PILLAGER || type == EntityType.VINDICATOR || type == EntityType.EVOKER || type == EntityType.ILLUSIONER;
    }
    private static Item getRandomStrongholdTable(Level level) {
        ResourceLocation[] tables = {
                BuiltInLootTables.STRONGHOLD_CORRIDOR,
                BuiltInLootTables.STRONGHOLD_CROSSING,
                BuiltInLootTables.STRONGHOLD_LIBRARY
        };
        ResourceLocation chosen = tables[level.random.nextInt(tables.length)];
        return new LootBagItem(new Item.Properties().stacksTo(16), chosen);
    }
    private static Item getRandomBastionTable(Level level) {
        ResourceLocation[] tables = {
                BuiltInLootTables.BASTION_TREASURE,
                BuiltInLootTables.BASTION_OTHER,
                BuiltInLootTables.BASTION_BRIDGE,
                BuiltInLootTables.BASTION_HOGLIN_STABLE
        };
        ResourceLocation chosen = tables[level.random.nextInt(tables.length)];
        return new LootBagItem(new Item.Properties().stacksTo(16), chosen);
    }
}