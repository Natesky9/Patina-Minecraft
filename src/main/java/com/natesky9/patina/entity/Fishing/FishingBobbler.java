package com.natesky9.patina.entity.Fishing;

import com.natesky9.patina.Patina;
import com.natesky9.patina.init.ModEntityTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;

public class FishingBobbler extends FishingHook {
    protected Entity hooked;
    protected int timer;
    protected State currentState = State.FLYING;

    public FishingBobbler(EntityType<? extends FishingBobbler> entityType,Level level)
    {
        super(entityType,level);
    }

    public FishingBobbler(Player player, Level pLevel) {
        this(ModEntityTypes.FISHING_BOBBLER.get(), pLevel);
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
    enum State
    {
        FLYING,
        HOOKED,
        BOBBING
    }

    @Override
    public int retrieve(ItemStack pStack) {
        if (!(this.getOwner() instanceof Player player)) return 0;

        boolean isHooked = hooked != null;

        boolean nearby = distanceTo(player) < 2;
        double d0 = player.getX() - this.getX();
        double d1 = player.getY() - this.getY();
        double d2 = player.getZ() - this.getZ();
        double verticalHop = Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2));
        if (isHooked)
        {
            if (nearby)
            {
                //hooked.setDeltaMovement(d0 * 0.2, 1.2, d2 * 0.2);
                if (hooked instanceof Mob mob)
                {
                    LootParams params = new LootParams.Builder((ServerLevel) this.level())
                            .withParameter(LootContextParams.THIS_ENTITY,this)
                            .withParameter(LootContextParams.ORIGIN,this.position())
                            .withParameter(LootContextParams.DAMAGE_SOURCE,mob.damageSources().mobAttack(player))
                            .withParameter(LootContextParams.ATTACKING_ENTITY,this.getOwner())
                            .create(LootContextParamSets.ENTITY);
                    ResourceKey<LootTable> resourcekey = mob.getLootTable();
                    LootTable loottable = this.level().getServer().reloadableRegistries().getLootTable(resourcekey);
                    ObjectArrayList<ItemStack> list = new ObjectArrayList<>();
                    for (double i = 0.5; i < mob.getAttributeValue(Attributes.SCALE);i+= i)
                    {
                        list.addAll(loottable.getRandomItems(params));
                    }

                    for (ItemStack stack : list)
                    {
                        ItemEntity entity = new ItemEntity(this.level(),this.getX(),this.getY(),this.getZ(),stack);
                        entity.setDeltaMovement(d0*.1,d1 * 0.1 + verticalHop * 0.08, d2 * 0.1);
                        this.level().addFreshEntity(entity);
                    }
                }
                hooked.remove(RemovalReason.DISCARDED);
                this.remove(RemovalReason.DISCARDED);
                player.awardStat(Stats.FISH_CAUGHT,1);
            }
            else
                hooked.addDeltaMovement(new Vec3(d0 * 0.1,d1 * 0.1, d2 * 0.1));
        }
        else
        {
            this.setDeltaMovement(d0*.1,d1*.1,d2*.1);
            if (nearby)
                this.remove(RemovalReason.DISCARDED);
        }
        return 0;
    }

    @Override
    public void tick() {
        if (!(this.getOwner() instanceof Player player))
        {this.discard();return;}

        if (!this.shouldStop(player))
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
                        return;
                    }
                    this.checkCollision();
                }
                case HOOKED -> {
                    if (this.hooked == null || this.hooked.isRemoved() || this.hooked.level() != this.level())
                    {
                        this.currentState = State.FLYING;
                        timer = 0;
                        return;
                    }
                    this.setPos(this.hooked.getX(),this.hooked.getY(),this.hooked.getZ());
                    return;
                }
                case BOBBING -> {

                    Vec3 vec3 = this.getDeltaMovement();
                    boolean water = fluid.is(FluidTags.WATER);
                    if (water)
                    {
                        if (player.getMainHandItem().has(DataComponents.LODESTONE_TRACKER))
                        {
                            BlockPos spot = player.getMainHandItem().get(DataComponents.LODESTONE_TRACKER).target().get().pos();
                            if (this.position().distanceTo(spot.getCenter()) < 3)
                            {
                                //System.out.println(spot);
                                timer++;
                            }
                        }
                        //10% chance
                        //if (Math.random() < .1)
                        //    timer++;
                        this.setDeltaMovement(vec3.x*.9,vec3.y+.01,vec3.z*.9);
                        if (timer > 40)
                        {
                            this.currentState = State.HOOKED;
                            EntityType<?> targetType = getFishFromBiome();

                            Entity target = targetType.create(level());// new Cod(EntityType.COD,level());

                            //Cod cod = new Cod(EntityType.COD,level());
                            assert target != null;
                            target.setPos(this.getX(),this.getY()-.5,this.getZ());

                            if (!(target instanceof Mob mob)) return;
                            ResourceLocation scale = ResourceLocation.fromNamespaceAndPath(Patina.MODID,"fish_scale");
                            double size = -.8 ;
                            double add = Math.sqrt(getWaterDepth())/4;
                            size += add;
                            AttributeModifier modifier = new AttributeModifier(scale, size, AttributeModifier.Operation.ADD_VALUE);

                            mob.getAttribute(Attributes.SCALE).addPermanentModifier(modifier);
                            mob.hurt(target.damageSources().sting(player),1);
                            level().addFreshEntity(mob);
                            this.hooked = target;
                            System.out.println("created: " + target.getId());
                            this.getEntityData().set(DATA_HOOKED_ENTITY,hooked.getId()+1);
                            //this.level().broadcastEntityEvent(this,(byte)31);
                        }
                    }
                    else
                        this.setDeltaMovement(vec3.x*.9,vec3.y-.01,vec3.z*.9);
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
    EntityType<?> getFishFromBiome()
    {
        Holder<Biome> biome = this.level().getBiome(this.blockPosition());

        if (biome.is(Biomes.RIVER))
            return EntityType.SALMON;
        if (biome.is(Biomes.DEEP_OCEAN))
            return EntityType.GUARDIAN;
        if (biome.is(Biomes.WARM_OCEAN))
            return EntityType.TROPICAL_FISH;
        if (biome.is(Biomes.SWAMP))
            return EntityType.SLIME;
        return EntityType.COD;
    }
    int getWaterDepth()
    {
        int depth = 0;
        for (int y = this.getBlockY();level().getFluidState(this.blockPosition().below(depth)).is(FluidTags.WATER);y--)
        {
            depth++;
        }
        return depth;
    }

    @Override
    public void setOwner(@Nullable Entity pOwner) {
        super.setOwner(pOwner);
        if (!( getOwner() instanceof Player player)) return;
        player.fishing = this;
    }

    protected void checkCollision()
    {
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this,this::canHitEntity);
        if (hitResult.getType() == HitResult.Type.MISS || ForgeEventFactory.onProjectileImpact(this,hitResult))
            this.hitTargetOrDeflectSelf(hitResult);
    }
    protected boolean shouldStop(Player player)
    {
        ItemStack itemStack = player.getMainHandItem();
        ItemStack offhand = player.getOffhandItem();
        boolean main = itemStack.canPerformAction(ToolActions.FISHING_ROD_CAST);
        boolean off = offhand.canPerformAction(ToolActions.FISHING_ROD_CAST);
        if (!player.isRemoved() && player.isAlive() && (main || off) && (this.distanceTo(player) < 1024))
        {
            return false;
        }
        this.discard();
        return true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(DATA_HOOKED_ENTITY,0);
        pBuilder.define(DATA_BITING,false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_HOOKED_ENTITY.equals(pKey))
        {
            int i = this.getEntityData().get(DATA_HOOKED_ENTITY);
            System.out.println("recieved: " + i);
            //this.hooked = this.level().getEntity(i-1);
            this.hooked = i > 0 ? this.level().getEntity(i-1) : null;
            if (this.hooked == null)
                discard();
            System.out.println("hooked is: " + this.getEntityData().get(DATA_HOOKED_ENTITY));
        }
        if (DATA_BITING.equals(pKey))
        {
            //this.biting = this.getEntityData().get(DATA_BITING);
            //if (this.biting)
            //    this.setDeltaMovement(this.getDeltaMovement().x,-.5,this.getDeltaMovement().z);
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    protected boolean canHitEntity(Entity p_37135_) {
        return false;
    }
}