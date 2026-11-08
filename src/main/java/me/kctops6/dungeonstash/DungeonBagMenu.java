package me.kctops6.dungeonstash;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DungeonBagMenu extends AbstractContainerMenu {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, DungeonStash.MODID);
    public static final RegistryObject<MenuType<DungeonBagMenu>> DUNGEON_BAG_MENU = MENUS.register("dungeon_bag_menu", () -> new MenuType<>(DungeonBagMenu::new, net.minecraft.world.flag.FeatureFlags.DEFAULT_FLAGS));

    private final Container bagContainer;
    private final ItemStack bagStack;

    public DungeonBagMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(27), ItemStack.EMPTY);
    }

    public DungeonBagMenu(int containerId, Inventory playerInventory, Container container, ItemStack bagStack) {
        super(DUNGEON_BAG_MENU.get(), containerId);
        checkContainerSize(container, 27);
        this.bagContainer = container;
        this.bagStack = bagStack;

        // Bag slots (3 rows of 9) - Read-only
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(container, col + row * 9, 8 + col * 18, 18 + row * 18) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                });
            }
        }

        // Player Inventory slots
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Player Hotbar slots
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();

        if (!bagStack.isEmpty() && bagStack.getItem() instanceof Item) {
            // Save current container state to item NBT
            ListTag listTag = new ListTag();
            boolean isEmpty = true;
            for (int i = 0; i < bagContainer.getContainerSize(); i++) {
                ItemStack stack = bagContainer.getItem(i);
                if (!stack.isEmpty()) {
                    isEmpty = false;
                    CompoundTag itemTag = new CompoundTag();
                    itemTag.putByte("Slot", (byte) i);
                    stack.save(itemTag);
                    listTag.add(itemTag);
                }
            }

            if (isEmpty) {
                // Close player container immediately and consume bag
                for (Slot slot : this.slots) {
                    if (slot.container instanceof Inventory playerInv) {
                        Player player = playerInv.player;
                        if (player instanceof ServerPlayer serverPlayer) {
                            serverPlayer.closeContainer();
                        }
                        break;
                    }
                }
                bagStack.setTag(null);
                bagStack.shrink(1);
            } else {
                CompoundTag tag = bagStack.getOrCreateTag();
                tag.put("BagInventory", listTag);
            }
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.bagContainer.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 27) {
                if (!this.moveItemStackTo(itemstack1, 27, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }
}