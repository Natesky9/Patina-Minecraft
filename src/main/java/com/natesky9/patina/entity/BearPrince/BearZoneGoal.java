package com.natesky9.patina.entity.BearPrince;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction8;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.EnumSet;

public class BearZoneGoal extends Goal {
    BearPrince bear;
    int attackTimer;
    public BearZoneGoal(BearPrince bear)
    {
        this.bear = bear;
    }
    @Override
    public boolean canUse() {
        return bear.hpbar % 2 == 1;
    }

    @Override
    public void start() {
        super.start();
        attackTimer = 0;
        System.out.println("started zone");
        bear.level().playSound(null, bear.blockPosition(),SoundEvents.FIRECHARGE_USE, SoundSource.HOSTILE,1,1);

        bear.attackCounter++;
    }

    @Override
    public void stop() {
        System.out.println("spawning zones");
        bear.attackCounter++;
        if (bear.level() instanceof ServerLevel level && canUse())
        {
            BlockPos pos;
            int start = bear.getRandom().nextInt(bear.effectList.size());
            for (int i=0;i < 4;i++) {
                pos = bear.blockPosition().relative(Direction.from2DDataValue(i),8);
                AreaEffectCloud cloud = new AreaEffectCloud(EntityType.AREA_EFFECT_CLOUD,level);
                //AreaEffectCloud cloud = EntityType.AREA_EFFECT_CLOUD.spawn(level,items, MobSpawnType.MOB_SUMMONED);
                MobEffectInstance effect = bear.effectList.get((start+i) % bear.effectList.size());
                cloud.addEffect(effect);
                cloud.setRadius(1F);
                cloud.setRadiusOnUse(-5f);
                cloud.setWaitTime(20-bear.hpbar);
                cloud.setDuration(80-bear.hpbar*4);
                cloud.setDurationOnUse(-200);
                cloud.setRadiusPerTick(0);
                cloud.moveTo(pos.getX(),pos.getY(),pos.getZ());
                level.addFreshEntity(cloud);
            }
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        attackTimer++;
        if (bear.level() instanceof ServerLevel level)
        {
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK,bear.getX()+attackTimer/8f,bear.getY(),bear.getZ(),
                    1,.5,.5,.5,0);
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK,bear.getX()-attackTimer/8f,bear.getY(),bear.getZ(),
                    1,.5,.5,.5,0);
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK,bear.getX(),bear.getY(),bear.getZ()+attackTimer/8f,
                    1,.5,.5,.5,0);
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK,bear.getX(),bear.getY(),bear.getZ()-attackTimer/8f,
                    1,.5,.5,.5,0);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return attackTimer < 80-bear.hpbar*4;
    }
}
