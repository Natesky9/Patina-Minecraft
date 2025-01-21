package com.natesky9.patina.entity.Fishing;

import com.natesky9.patina.Patina;
import com.natesky9.patina.init.ModEntityTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class FishingLootBobbler extends FishingBobbler{
    public FishingLootBobbler(EntityType<? extends FishingBobbler> entityType, Level level) {
        super(entityType, level);
    }
    public FishingLootBobbler(Player player, Level level)
    {
        this(ModEntityTypes.FISHING_LOOT_BOBBLER.get(), level);
        //do the stuff vanilla bobbers do
        this.setOwner(player);
        float f = player.getXRot();
        float f1 = player.getYRot();
        float f2 = Mth.cos(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float f4 = -Mth.cos(-f * (float) (Math.PI / 180.0));
        float f5 = Mth.sin(-f * (float) (Math.PI / 180.0));
        double d0 = player.getX() - (double)f3 * 0.3;
        double d1 = player.getEyeY();
        double d2 = player.getZ() - (double)f2 * 0.3;
        this.moveTo(d0, d1, d2, f1, f);
        Vec3 vec3 = new Vec3((double)(-f3), (double)Mth.clamp(-(f5 / f4), -5.0F, 5.0F), (double)(-f2));
        double d3 = vec3.length();
        vec3 = vec3.multiply(
                0.6 / d3 + this.random.triangle(0.5, 0.0103365),
                0.6 / d3 + this.random.triangle(0.5, 0.0103365),
                0.6 / d3 + this.random.triangle(0.5, 0.0103365)
        );
        this.setDeltaMovement(vec3);
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
        this.setXRot((float)(Mth.atan2(vec3.y, vec3.horizontalDistance()) * 180.0F / (float)Math.PI));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    @Override
    public void tick() {
        if (!(this.getOwner() instanceof Player player))
        {this.discard();return;}

        if (this.level().isClientSide || !this.shouldStop(player))
        {
            //life
            if (this.onGround())
            {
                this.life++;
                if (this.life >= 1200)
                {
                    this.discard();
                    return;
                }
            }
            BlockPos pos = this.blockPosition();
            FluidState fluid = this.level().getFluidState(pos);

            //flying
            switch (this.currentState)
            {
                case FLYING -> {
                    //if we're caught on something
                    if (this.hooked != null)
                    {
                        this.setDeltaMovement(Vec3.ZERO);
                        this.currentState = State.HOOKED;
                        return;
                    }
                    //add new conditions here for new fluid types?
                    //if we land in water
                    if (fluid.is(FluidTags.WATER))
                    {
                        this.setDeltaMovement(this.getDeltaMovement().multiply(.2,.2,.2));
                        this.currentState = State.BOBBING;
                        timer = (int)distanceTo(player)*20;
                        return;
                    }
                    this.checkCollision();
                }
                case HOOKED -> {
                    if (this.hooked == null || this.hooked.isRemoved() || this.hooked.level() != this.level())
                    {
                        this.remove(RemovalReason.DISCARDED);
                        return;
                    }
                    this.setPos(this.hooked.getX(),this.hooked.getY(),this.hooked.getZ());
                    return;
                }
                case BOBBING -> {
                    boolean inWater = level().isWaterAt(this.blockPosition());
                    this.setDeltaMovement(0,-.1,0);
                    if (this.onGround() && inWater)
                    {
                        this.currentState = State.HOOKED;
                        ItemStack stack = new ItemStack(Items.TRIPWIRE_HOOK);
                        ItemEntity treasure = new ItemEntity(level(),this.getX(),this.getY(),this.getZ(),stack);
                        level().addFreshEntity(treasure);
                        this.hooked = treasure;
                        this.getEntityData().set(DATA_HOOKED_ENTITY,hooked.getId()+1);
                        return;
                    }
                }
                default -> {
                    System.out.println("not sure how we got here");
                    return;
                }
            }

            if (!fluid.is(FluidTags.WATER))
            {
                this.setDeltaMovement(this.getDeltaMovement().add(0,-0.03,0));
            }
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.updateRotation();
            //set zero if hit block
            if (this.currentState == State.FLYING && (this.onGround() || this.horizontalCollision))
                this.setDeltaMovement(Vec3.ZERO);
            this.setDeltaMovement(this.getDeltaMovement().scale(.92));
            this.reapplyPosition();
        }
    }

    @Override
    public int retrieve(ItemStack pStack) {
        if (!(this.getOwner() instanceof Player player)) return 0;

        boolean isHooked = hooked != null;

        double d0 = player.getX() - this.getX();
        double d1 = player.getY() - this.getY();
        double d2 = player.getZ() - this.getZ();
        double verticalHop = Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2))*.2;
        if (isHooked)
        {
            hooked.addDeltaMovement(new Vec3(d0 * 0.1,d1 * 0.1+verticalHop, d2 * 0.1));
        }
        else
        {
            this.setDeltaMovement(d0*.1,d1*.1,d2*.1);
        }
        return 0;
    }
}
