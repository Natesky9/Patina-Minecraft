package com.natesky9.patina.Blocks;

import com.natesky9.patina.Menu.ContainerData.SieveContainerData;
import com.natesky9.patina.Menu.SieveMenu;
import com.natesky9.patina.Recipe.SieveRecipe;
import com.natesky9.patina.Recipe.SieveRecipeInput;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MachineSieveEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler handler;
    public final SieveContainerData data;
    private RecipeHolder<? extends SieveRecipe> recipe;

    public int heat;
    public int heatMax;
    public int progress;
    public int progressMax;

    public MachineSieveEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SIEVE_ENTITY.get(), pos, blockState);
        handler = new ItemStackHandler(4)
        {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return super.isItemValid(slot, stack);
            }

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                Optional<RecipeHolder<SieveRecipe>> valid = getRecipe(level);

                if (valid.isPresent() && valid.get() != recipe)
                {
                    recipe = valid.get();
                    progress = 0;
                }
                if (valid.isEmpty())
                {
                    progress = 0;
                }
            }
        };
        data = new SieveContainerData(this);
    }

    Optional<RecipeHolder<SieveRecipe>> getRecipe(Level level)
    {
        if (!(level instanceof ServerLevel server)) return Optional.empty();

        ItemStack slot1 = handler.getStackInSlot(0);
        ItemStack slot2 = handler.getStackInSlot(1);

        Optional<? extends RecipeHolder<? extends SieveRecipe>> valid;
        SieveRecipeInput recipe = new SieveRecipeInput(slot1,isWaterLogged());
        return server.recipeAccess().getRecipeFor(ModRecipeTypes.SIEVE_RECIPE.get(),recipe,server);
    }

    public boolean isWaterLogged()
    {
        return level.getBlockState(getBlockPos()).getValue(MachineSieveBlock.WATERLOGGED);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", handler.serializeNBT(registries));
        tag.putInt("progress", progress);
        tag.putInt("progressMax", progressMax);
        tag.putInt("heat",heat);
        tag.putInt("heatMax",heatMax);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        handler.deserializeNBT(registries, tag);
        progress = tag.getInt("progress");
        progressMax = tag.getInt("progressMax");
        heat = tag.getInt("heat");
        heatMax = tag.getInt("heatMax");
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
        boolean water = sieve.isWaterLogged();

        SieveRecipeInput recipeInput = new SieveRecipeInput(input,water);

        //Optional<RecipeHolder<SieveRecipe>> recipe = server.recipeAccess().getRecipeFor(ModRecipeTypes.SIEVE_RECIPE.get(), recipeInput, server);

        if (sieve.recipe != null)
        {
            ItemStack primary = sieve.recipe.value().output();

            boolean roomInPrimary = sieve.handler.insertItem(1, primary, true).isEmpty();

            if (roomInPrimary)
            {
                sieve.handler.extractItem(0, 1, false);
                sieve.handler.insertItem(1, sieve.recipe.value().assemble(recipeInput, server.registryAccess()), false);
            }

        }
    }
}
