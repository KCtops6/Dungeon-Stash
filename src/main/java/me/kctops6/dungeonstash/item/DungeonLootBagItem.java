package me.kctops6.dungeonstash.item;

import me.kctops6.dungeonstash.Config;
import me.kctops6.dungeonstash.menu.DungeonBagMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DungeonLootBagItem extends Item {
    public DungeonLootBagItem(Properties properties) {
        super(properties);
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            LootTable loottable = serverLevel.getServer().getLootData().getLootTable(BuiltInLootTables.SIMPLE_DUNGEON);
            LootParams lootparams = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, player.position())
                    .create(LootContextParamSets.CHEST);
            List<ItemStack> generatedLoot = loottable.getRandomItems(lootparams);
            if (Config.guiMode) {
                SimpleContainer container = new SimpleContainer(27);
                int index = 0;
                for (ItemStack lootItem : generatedLoot) {
                    if (index < container.getContainerSize()) container.setItem(index++, lootItem);

                }
                player.openMenu(new SimpleMenuProvider(
                        (id, playerInv, p) -> new DungeonBagMenu(id, playerInv, container, itemstack),
                        Component.translatable("item.dungeon_stash.dungeon_loot_bag")
                ));
            } else {
                Inventory playerInventory = player.getInventory();
                for (ItemStack lootItem : generatedLoot) {
                    if (!playerInventory.add(lootItem)) {
                        BlockPos pos = player.blockPosition();
                        ItemEntity itementity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, lootItem);
                        level.addFreshEntity(itementity);
                    }
                }
                itemstack.shrink(1);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}