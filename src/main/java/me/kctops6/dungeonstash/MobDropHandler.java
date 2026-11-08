package me.kctops6.dungeonstash;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonStash.MODID)
public class MobDropHandler {

    @SubscribeEvent
    public static void onMobDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }

        // Only drop if killed by a player
        if (!(event.getSource().getEntity() instanceof Player)) {
            return;
        }

        Monster mob = event.getEntity() instanceof Monster ? (Monster) event.getEntity() : null;
        if (mob == null) {
            return;
        }

        Level level = mob.level();
        EntityType<?> type = mob.getType();
        double roll = level.random.nextDouble() * 100.0; // 0.0 to 100.0

        boolean shouldDrop = false;

        if (type == EntityType.ZOMBIE && Config.zombieDropEnabled) {
            if (roll < Config.zombieDropChance) {
                shouldDrop = true;
            }
        } else if (type == EntityType.SKELETON && Config.skeletonDropEnabled) {
            if (roll < Config.skeletonDropChance) {
                shouldDrop = true;
            }
        } else if (type == EntityType.SPIDER && Config.spiderDropEnabled) {
            if (roll < Config.spiderDropChance) {
                shouldDrop = true;
            }
        }

        if (shouldDrop) {
            ItemStack bagStack = new ItemStack(DungeonStash.DUNGEON_LOOT_BAG.get());
            ItemEntity itemEntity = new ItemEntity(
                    level,
                    mob.getX(),
                    mob.getY(),
                    mob.getZ(),
                    bagStack
            );
            level.addFreshEntity(itemEntity);
        }
    }
}