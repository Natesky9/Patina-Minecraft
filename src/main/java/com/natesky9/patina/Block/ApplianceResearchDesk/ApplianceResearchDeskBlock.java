package com.natesky9.patina.Block.ApplianceResearchDesk;

import com.mojang.serialization.MapCodec;
import com.natesky9.patina.Block.Benchmark.ApplianceBenchmarkBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ApplianceResearchDeskBlock extends Block {
    public static final MapCodec<ApplianceBenchmarkBlock> CODEC = simpleCodec(ApplianceBenchmarkBlock::new);
    private static final Component CONTAINER_TITLE = Component.translatable("container.appliance_research_desk");
    public ApplianceResearchDeskBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult p_60508_) {
        if (level.isClientSide())
            return InteractionResult.SUCCESS;
        else
        {
            //can alter the menu type here using the state
            player.openMenu(state.getMenuProvider(level, pos));
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        ContainerData data = new SimpleContainerData(0);
        return new SimpleMenuProvider(
                (id, inv, c) -> new ApplianceResearchDeskMenu(id, inv, level, pos, data), CONTAINER_TITLE
        );
    }
}
