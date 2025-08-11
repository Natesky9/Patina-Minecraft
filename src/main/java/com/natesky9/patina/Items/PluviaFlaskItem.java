package com.natesky9.patina.Items;

import com.natesky9.patina.Event.packets.SendParticlePacket;
import com.natesky9.patina.init.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public class PluviaFlaskItem extends CrystalFlaskItem{
    public PluviaFlaskItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 8;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (!(livingEntity instanceof Player player)) return stack;

        PotionContents potionContents = stack.getOrDefault(DataComponents.POTION_CONTENTS,PotionContents.EMPTY);
        if (potionContents == PotionContents.EMPTY) return stack;

        level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.PLAYERS);
        if (getUses(stack) <= 0)
            return stack;
        if (!player.isCreative())
            setUses(stack, getUses(stack)-1);

        LivingEntity entity = sprayEntity(player, potionContents);
        if (entity != null)
            potionContents.onConsume(level,entity,stack,null);

        return stack;
    }
    LivingEntity sprayEntity(Player player, PotionContents contents)
    {
        int distance = 16;
        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(player.getLookAngle().scale(distance));
        AABB aabb = player.getBoundingBox().expandTowards(player.getLookAngle().scale(distance)).inflate(1);
        EntityHitResult result = ProjectileUtil.getEntityHitResult(player,start,end,aabb, Entity::isAttackable,distance*distance);
        if (result != null && result.getEntity() instanceof LivingEntity livingEntity)
        {
            int color = contents.getColor();
            if (player.level() instanceof ServerLevel server)
            {
                for (int i=1;i<8;i++)
                {
                    double x = player.getX();
                    double y = player.getEyeY()-.5;
                    double z = player.getZ();
                    double xS = (livingEntity.getX()-player.getX())/20*i;
                    double yS = (livingEntity.getY()-player.getY())/20*i;
                    double zS = (livingEntity.getZ()-player.getZ())/20*i;
                    double x2 = x+(livingEntity.getX()-player.getX())/8*i;
                    double y2 = y+(livingEntity.getY()-player.getY())/8*i;
                    double z2 = z+(livingEntity.getZ()-player.getZ())/8*i;
                    System.out.println(xS + ":" + yS + ":" + zS);

                    PacketDistributor.sendToAllPlayers(new SendParticlePacket(
                            ParticleTypes.SPIT,
                            x,y,z,xS,yS,zS));
                }
            }
            return livingEntity;
        }
        return null;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BOW;
    }
}
