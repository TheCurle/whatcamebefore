package uk.gemwire.whatcamebefore.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import uk.gemwire.whatcamebefore.WCBEvents;
import uk.gemwire.whatcamebefore.capabilities.CapabilityProgress;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

@Mod.EventBusSubscriber(modid="whatcamebefore", bus=Mod.EventBusSubscriber.Bus.FORGE)
public class Setup {

    public static void init(final FMLCommonSetupEvent e) {
        CapabilityProgress.register();

        EVENT_BUS.addGenericListener(null, WCBEvents::attachCapabilities);

    }


}
