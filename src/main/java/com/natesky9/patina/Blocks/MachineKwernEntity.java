package com.natesky9.patina.Blocks;

import com.natesky9.patina.Menu.KwernMenu;
import com.natesky9.patina.Recipe.KwernRecipe;
import com.natesky9.patina.init.ModBlockEntities;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MachineKwernEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler handler;
    private RecipeHolder<? extends KwernRecipe> recipe;
    int secondaryCount;
    int secondaryMax;
    int progress;
    public MachineKwernEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.KWERN_ENTITY.get(), pos, blockState);
        handler = new ItemStackHandler(3)
        {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return super.isItemValid(slot, stack);
            }

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                if (slot > 0) return;//only apply to item1

                if (!(level instanceof ServerLevel server)) return;
                ItemStack input = handler.getStackInSlot(0);
                RecipeInput recipeInput = new SingleRecipeInput(input);

                Optional<? extends RecipeHolder<? extends KwernRecipe>> valid =
                        server.recipeAccess().getRecipeFor(ModRecipeTypes.KWERN_RECIPE.get(), recipeInput, server);

                if (recipe == null && valid.isPresent())
                {
                    recipe = valid.get();
                    secondaryCount = 0;
                    secondaryMax = valid.get().value().every();
                }
            }
        };
        recipe = null;
        secondaryCount = 0;
        secondaryMax = 0;
        progress = 0;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.patina.machine_kwern");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new KwernMenu(i, inventory, this);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MachineKwernEntity kwern) {
        if (!(level instanceof ServerLevel server)) return;

        ItemStack input = kwern.handler.getStackInSlot(0);
        ItemStack output = kwern.handler.getStackInSlot(1);

        RecipeInput recipeInput = new SingleRecipeInput(input);

        if (kwern.recipe != null)
        {
            KwernRecipe setting = kwern.recipe.value();
            ItemStack result = setting.assemble(recipeInput, level.registryAccess());
            boolean secondaryReady = kwern.secondaryCount >= kwern.secondaryMax;
            boolean secondaryFits = kwern.handler.insertItem(1, result, true).isEmpty();
            boolean room = kwern.handler.insertItem(1, result, true).isEmpty();
            boolean match = kwern.handler.getStackInSlot(0).is(setting.input().getItem());

            if (secondaryReady
                    && secondaryFits)
            {//push secondary to item3
                kwern.handler.insertItem(1, result, false);
                kwern.secondaryCount -= kwern.secondaryMax;
            }

            if (room && match && !secondaryReady)
                kwern.progress++;//general tick
            else
                kwern.progress = 0;//reset tick

            if (kwern.progress >= 20 && room && match)
            {//process
                kwern.progress = 0;
                kwern.secondaryCount++;
                kwern.handler.insertItem(1, result, false);
                kwern.handler.extractItem(0, 1, false);
            }
        }
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
}
