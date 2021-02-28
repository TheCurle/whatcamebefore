package uk.gemwire.whatcamebefore.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import uk.gemwire.whatcamebefore.client.render.NeutralSlimeRenderer;
import uk.gemwire.whatcamebefore.compat.ticon.TiConCompatRegistry;

@Mod.EventBusSubscriber(modid = "whatcamebefore", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent e) {
        e.getMinecraftSupplier().get().getRenderManager().register(TiConCompatRegistry.NEUTRAL_SLIME.get(), new NeutralSlimeRenderer(e.getMinecraftSupplier().get().getRenderManager()));
    }

    public static void poke() {}
}
