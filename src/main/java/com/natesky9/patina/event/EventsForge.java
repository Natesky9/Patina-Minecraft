package com.natesky9.patina.event;

import com.natesky9.patina.Block.Gravestone.GravestoneBlock;
import com.natesky9.patina.Item.BeeShieldItem;
import com.natesky9.patina.Item.Charms;
import com.natesky9.patina.Item.EssenceItem;
import com.natesky9.patina.Item.PigWeaponItem;
import com.natesky9.patina.Item.flasks.ImpetusFlask;
import com.natesky9.patina.Item.flasks.SemiVitaFlask;
import com.natesky9.patina.Patina;
import com.natesky9.patina.init.ModItems;
import com.natesky9.patina.init.ModPotions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraftforge.event.VanillaGameEvent;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Patina.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventsForge {
    @SubscribeEvent
    public static void potions(BrewingRecipeRegisterEvent event)
    {
        ModPotions.addNormalPotions(event);
        ModPotions.addSaltPotions(event);
        //ModPotions.removePotions(event);
        ModPotions.addVoidPotions(event);
    }
    @SubscribeEvent
    public static void experienceEvent(LivingExperienceDropEvent event)
    {

        event.setCanceled(EssenceItem.shouldCreate(event));
    }
    @SubscribeEvent
    public static void LivingDropsEvent(LivingDropsEvent event) {
        GravestoneBlock.create(event);
        //GravestoneBlock.create(event);
        //TODO: enchantments
        //AvariceEnchantment.doEffect(event);
        //GreedEnchantment.doEffect(event);
        //SoulboundEnchantment.store(event);

        EssenceItem.create(event);

    }
    @SubscribeEvent
    public static void PlayerXpEvent(PlayerXpEvent.PickupXp event)
    {
        Charms.experienceCharm(event);
    }

    @SubscribeEvent
    public static void PlayerCloneEvent(PlayerEvent.Clone event) {
        //TODO: Enchantments
        //SoulboundEnchantment.restore(event);
    }

    @SubscribeEvent
    public static void LivingDamageEvent(LivingDamageEvent event) {
        //just before damage is applied

        ImpetusFlask.trigger(event);
        SemiVitaFlask.trigger(event);

        //TODO: Enchantments
        //ApathyEnchantment.doEffect(event);
        //WrathEnchantment.doEffect(event);
        //PrideEnchantment.doEffect(event);
        //CoercionEnchantment.doEffect(event);
        //HumilityEnchantment.doEffect(event);

        if (event.getSource().getEntity() instanceof Player player)
        {
            if (player.getItemInHand(player.getUsedItemHand()).is(ModItems.PIG_SWORD.get()))
                PigWeaponItem.trigger(event);
        }
    }

    @SubscribeEvent
    public static void LivingAttackEvent(LivingAttackEvent event) {
        //when an entity is being attacked

        LivingEntity entity = event.getEntity();
        DamageSource source = event.getSource();
        if (!(source.getEntity() instanceof Player player)) return;
        if (entity instanceof TamableAnimal pet) {
            if (!(pet.isOwnedBy(player))) return;
            //can't hurt your own pets unless you're crouching
            event.setCanceled(!player.isCrouching());
        }
        if (entity instanceof AbstractHorse horse) {
            if (!horse.isTamed()) return;
            //can't hurt horses unless you're crouching
            event.setCanceled(!player.isCrouching());
        }

    }

    @SubscribeEvent
    public static void LivingHurtEvent(LivingHurtEvent event) {
        //when an entity is attacking
        //allows you to set the amount

        BismuthPassive.doBismuthEffect(event);
        //TODO: Enchantments
        //RetributionEnchantment.doEffect(event);
        //LustEnchantment.doEffect(event);
        //ModEnchantmentUtil.netherCursePlayer(event);

        Charms.detonationCharm(event);
        Charms.ambushCharm(event);
    }

    @SubscribeEvent
    public static void LivingUseTotemEvent(LivingUseTotemEvent event) {
        Charms.spawnFragment(event);
        //when a totem is used, drop a charm?
    }

    @SubscribeEvent
    public static void LivingHealEvent(LivingHealEvent event) {
        //TODO: Enchantments
        //SlothEnchantment.doEffect(event);
        Charms.vitalityCharm(event);
    }

    @SubscribeEvent
    public static void EatEvent(VanillaGameEvent event) {
        //TODO: Enchantments
        //eat happens after the food is consumed
        //if (event.getVanillaEvent() == GameEvent.EAT) {
        //    GluttonyEnchantment.doEffect(event);
        //    BrimfulEnchantment.doEffect(event);
        //}
    }

    @SubscribeEvent
    public static void MobEffectEvent(MobEffectEvent.Added event) {
        Charms.alchemyCharm(event);
    }

    @SubscribeEvent
    public static void BabyEntitySpawnEvent(BabyEntitySpawnEvent event) {
        Charms.fertilityCharm(event);
    }

    @SubscribeEvent
    public static void WandererTradesEvent(WandererTradesEvent event) {
        //event.getRareTrades().add()
    }

    @SubscribeEvent
    public static void Interact(PlayerInteractEvent.EntityInteract event) {
        Charms.contrabandCharm(event);
    }
    @SubscribeEvent
    public static void ShieldBlockEvent(ShieldBlockEvent event)
    {
        if (event.getEntity() instanceof Player player)
        {
            if (player.getUseItem().is(ModItems.BEE_SHIELD.get()))
                BeeShieldItem.trigger(event);
        }
    }

    @SubscribeEvent
    public static void LootEvent(PlayerInteractEvent.RightClickBlock event)
    {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!(level.dimension() == Level.NETHER)) return;
        //only curse when opening loot chests in the nether
        if (event.getLevel().getBlockEntity(event.getPos()) instanceof RandomizableContainerBlockEntity lootChest)
        {
            boolean loot = lootChest.saveWithId(level.registryAccess()).contains("LootTable");
            if (loot)
            {
                //TODO: Enchantments
                //curseItem(event.getEntity());
            }
        }
    }

    @SubscribeEvent
    public static void ExplosionEvent(ExplosionEvent event)
    {
        Entity entity = event.getExplosion().getIndirectSourceEntity();
        if (!(entity instanceof  Player player)) return;
        if (!(player.getInventory().hasAnyMatching(itemStack -> itemStack.is(ModItems.CHARM_DETONATION.get())))) return;
        Explosion explosion = event.getExplosion();
        //TODO: Access transform explosion radius
        //explosion.radius = explosion.radius*2;
    }
}
