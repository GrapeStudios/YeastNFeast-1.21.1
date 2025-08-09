package net.grapes.yeastnfeast.event;

import net.grapes.yeastnfeast.YeastNFeastMod;
import net.grapes.yeastnfeast.particle.ModParticleType;
import net.grapes.yeastnfeast.particle.custom.DrippingSyrupParticle;
import net.grapes.yeastnfeast.particle.custom.MapleLeavesParticle;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = YeastNFeastMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticleType.MAPLE_LEAVES.get(), MapleLeavesParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticleType.DRIPPING_SYRUP.get(), DrippingSyrupParticle.Factory::new);
    }
}
