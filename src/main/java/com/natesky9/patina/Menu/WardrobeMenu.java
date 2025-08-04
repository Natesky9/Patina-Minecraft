package com.natesky9.patina.Menu;

import com.natesky9.patina.Blocks.ApplianceWardrobeEntity;
import com.natesky9.patina.init.ModBlocks;
import com.natesky9.patina.init.ModMenuTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.SlotItemHandler;

public class WardrobeMenu extends ModContainerMenu {
    ApplianceWardrobeEntity wardrobe;
    public WardrobeMenu(int containerId, Inventory inv, FriendlyByteBuf buf)
    {
        this(containerId, inv, inv.player.level().getBlockEntity(buf.readBlockPos()));
    }
    public WardrobeMenu(int containerId, Inventory inv, BlockEntity entity)
    {
        super(ModMenuTypes.WARDROBE_MENU.get(), containerId);
        inventory = inv;
        wardrobe = (ApplianceWardrobeEntity) entity;
        //add slots
        for (int i=0; i<20; i++)
        {
            addSlot(new WardrobeSlot(wardrobe.handler, i, 80+i%5*18, 8+i/5*18));
        }
        //add player armor
        for (int i=0; i<4; ++i)
        {
            addSlot(new ArmorSlot(inv, 39-i, 8, 8+i*18, i));
        }
        addPlayerInventory(inv);
    }
    @Override
    public ItemStack quickMoveStack(Player player, int index) {

        ItemStack item;
        if (index >= 0 && index < 20)
        {
            //click inside wardrobe
            item = wardrobe.handler.getStackInSlot(index);
            if (item.isEmpty()) return ItemStack.EMPTY;
            int row = index/5;
            int armorIndex = 20+row;

            ItemStack equipped = getSlot(armorIndex).getItem();
            //prevent curse of binding...for now
            if (EnchantmentHelper.has(item, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE))
                return ItemStack.EMPTY;
            getSlot(index).set(equipped);
            getSlot(armorIndex).set(item);
        }
        if (index >= 20 && index < 24)
        {
            //click on armor
            item = getSlot(index).getItem();
            if (item.isEmpty()) return ItemStack.EMPTY;
            if (EnchantmentHelper.has(item, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE))
                return ItemStack.EMPTY;
            int row = index-20;
            //find the first open slot
            for (int i=0; i<5; i++)
            {
                int slot = 5*row+i;
                ItemStack armor = wardrobe.handler.getStackInSlot(slot);
                if (!armor.isEmpty()) continue;
                wardrobe.handler.setStackInSlot(slot, item);
                getSlot(index).set(ItemStack.EMPTY);
                break;
            }
        }
        if (index >= 24)
        {
            //click in inventory
            //TODO: test if this is correct
            item = getSlot(index).getItem();
            if (item.isEmpty()) return ItemStack.EMPTY;

            Equippable equippable = item.get(DataComponents.EQUIPPABLE);
            //get the row based on which slot it goes on
            //eventually add a config option to whitelist items
            int row = switch (equippable == null ? EquipmentSlot.HEAD : equippable.slot())
            {
                case HEAD, BODY, MAINHAND, OFFHAND -> 0;
                case FEET -> 3;
                case LEGS -> 2;
                case CHEST -> 1;
            };

            //find the first open slot
            for (int i=0; i<5; i++)
            {
                int slot = 5*row+i;
                ItemStack armor = wardrobe.handler.getStackInSlot(slot);
                if (!armor.isEmpty()) continue;
                wardrobe.handler.setStackInSlot(slot, item);
                getSlot(index).set(ItemStack.EMPTY);
                break;
            }

        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.canInteractWithBlock(wardrobe.getBlockPos(), 4);
    }
}
