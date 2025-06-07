package com.natesky9.patina.Blocks;

import com.natesky9.patina.Menu.SieveMenu;
import com.natesky9.patina.Recipe.SieveRecipe;
import com.natesky9.patina.init.ModBlockEntities;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MachineSieveEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler handler;
    int secondaryCount;
    int secondaryMax;
    int progress;

    public MachineSieveEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SIEVE_ENTITY.get(), pos, blockState);
        handler = new ItemStackHandler(4)
        {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return super.isItemValid(slot, stack);
            }
        };
        secondaryCount = 0;
        secondaryMax = 0;
        progress = 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", handler.serializeNBT(registries));
        tag.putInt("secondaryCount", secondaryCount);
        tag.putInt("secondaryMax", secondaryMax);
        tag.putInt("progress", progress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        handler.deserializeNBT(registries, tag);
        secondaryCount = tag.getInt("secondaryCount");
        secondaryMax = tag.getInt("secondaryMax");
        progress = tag.getInt("progress");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.patina.machine_sieve");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new SieveMenu(i, inventory, this);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MachineSieveEntity sieve)
    {
        if (!(level instanceof ServerLevel server)) return;
        ItemStack input = sieve.handler.getStackInSlot(0);
        ItemStack output = sieve.handler.getStackInSlot(1);

        RecipeInput recipeInput = new SingleRecipeInput(input);

        Optional<RecipeHolder<SieveRecipe>> recipe = server.recipeAccess().getRecipeFor(ModRecipeTypes.SIEVE_RECIPE.get(), recipeInput, server);

        if (recipe.isPresent())
        {
            ItemStack primary = recipe.get().value().output();
            ItemStack secondary = recipe.get().value().secondary();

            boolean roomInPrimary = sieve.handler.insertItem(1, primary, true).isEmpty();
            boolean roomInSecondary = sieve.handler.insertItem(2, secondary, true).isEmpty();

            if (roomInPrimary && roomInSecondary)
            {
                sieve.handler.extractItem(0, 1, false);
                sieve.handler.insertItem(1, recipe.get().value().assemble(recipeInput, server.registryAccess()), false);
                sieve.handler.insertItem(2, recipe.get().value().secondary().copy(), false);
            }

        }
    }
}
