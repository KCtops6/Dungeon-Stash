package me.kctops6.dungeonstash.item;

import me.kctops6.dungeonstash.Config;
import me.kctops6.dungeonstash.menu.DungeonBagMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
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
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LootBagItem extends Item {
    private final ResourceLocation singleLootTableId;
    private final List<ResourceLocation> lootTableIds;

    public LootBagItem(Properties properties, ResourceLocation lootTableId) {
        super(properties);
        this.singleLootTableId = lootTableId;
        this.lootTableIds = null;
    }

    public LootBagItem(Properties properties, List<ResourceLocation> lootTableIds) {
        super(properties);
        this.singleLootTableId = null;
        this.lootTableIds = lootTableIds;
    }

    private ResourceLocation getSelectedLootTable() {
        if (lootTableIds != null && !lootTableIds.isEmpty()) {
            return lootTableIds.get(ThreadLocalRandom.current().nextInt(lootTableIds.size()));
        }
        return singleLootTableId;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            SimpleContainer container = new SimpleContainer(27);
            CompoundTag tag = itemstack.getTag();
            if (tag != null && tag.contains("BagInventory", 9)) {
                ListTag listTag = tag.getList("BagInventory", 10);
                for (int i = 0; i < listTag.size(); i++) {
                    CompoundTag itemTag = listTag.getCompound(i);
                    int slot = itemTag.getByte("Slot") & 255;
                    if (slot < container.getContainerSize()) container.setItem(slot, ItemStack.of(itemTag));
                }
            } else {
                ResourceLocation chosenTable = getSelectedLootTable();
                LootTable loottable = serverLevel.getServer().getLootData().getLootTable(chosenTable);
                LootParams lootparams = new LootParams.Builder(serverLevel)
                        .withParameter(LootContextParams.ORIGIN, player.position())
                        .create(LootContextParamSets.CHEST);
                List<ItemStack> generatedLoot = loottable.getRandomItems(lootparams);
                int index = 0;
                for (ItemStack lootItem : generatedLoot) {
                    if (index < container.getContainerSize()) container.setItem(index++, lootItem);
                }
            }
            if (Config.guiMode) {
                player.openMenu(new SimpleMenuProvider(
                        (id, playerInv, p) -> new DungeonBagMenu(id, playerInv, container, itemstack),
                        itemstack.getHoverName()
                ));
            } else {
                Inventory playerInventory = player.getInventory();
                for (int i = 0; i < container.getContainerSize(); i++) {
                    ItemStack lootItem = container.getItem(i);
                    if (!lootItem.isEmpty()) {
                        if (!playerInventory.add(lootItem)) {
                            BlockPos pos = player.blockPosition();
                            ItemEntity itementity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, lootItem);
                            level.addFreshEntity(itementity);
                        }
                    }
                }
                itemstack.shrink(1);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}