package com.natesky9.patina.init;

import com.natesky9.patina.entity.BearPrince.BearPrinceRenderer;
import com.natesky9.patina.entity.BeePrincess.BeePrincessRenderer;
import com.natesky9.patina.entity.Fishing.FishingRenderer;
import com.natesky9.patina.entity.SpiderNest.SpiderNestRenderer;
import com.natesky9.patina.entity.SpiderQueen.SpiderQueenRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class ModEntityRenderers {
    public static void register(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(ModEntityTypes.BEE_BOSS.get(), BeePrincessRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.BEAR_BOSS.get(), BearPrinceRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SPIDER_BOSS.get(), SpiderQueenRenderer::new);

        event.registerEntityRenderer(ModEntityTypes.BEAR_STAR.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SPIDER_NEST.get(), SpiderNestRenderer::new);

        event.registerEntityRenderer(ModEntityTypes.FISHING_BOBBLER.get(), FishingRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.FISHING_LOOT_BOBBLER.get(), FishingRenderer::new);
    }
}
