package com.natesky9.patina.Blocks;

import com.natesky9.patina.Menu.AlembicMenu;
import com.natesky9.patina.Recipe.FoundryRecipe;
import com.natesky9.patina.init.ModBlockEntities;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MachineAlembicEntity extends BlockEntity implements MenuProvider {
    public ItemStackHandler handler = new ItemStackHandler(2)
    {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return super.isItemValid(slot, stack);
        }
    };
    private ItemStackHandler automationHandler;

    private Item reagent;
    private int leftover;
    private int progress;
    public MachineAlembicEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ALEMBIC_ENTITY.get(), pos, blockState);
        reagent = Items.AIR;
        leftover = 0;
        progress = 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", handler.serializeNBT(registries));
        tag.putInt("reagent", BuiltInRegistries.ITEM.getId(reagent));
        tag.putInt("reagentCount", leftover);
        tag.putInt("progress", progress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        handler.deserializeNBT(registries, tag);
        reagent = BuiltInRegistries.ITEM.byId(tag.getInt("reagent"));
        leftover = tag.getInt("reagentCount");
        progress = tag.getInt("progress");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.patina.machine_alembic");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new AlembicMenu(i, inventory, this);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MachineAlembicEntity alembic) {
        if (!(level instanceof ServerLevel server)) return;
        ItemStack input = alembic.handler.getStackInSlot(0);
        ItemStack output = alembic.handler.getStackInSlot(1);

        if (server.potionBrewing().hasMix(input, output))
        {
            //replace item1 to use fractional ingredients through the machine buffer
            ItemStack brew = server.potionBrewing().mix(output, input);
            output.shrink(1);
            alembic.handler.setStackInSlot(0, brew);
        }

    }
}
