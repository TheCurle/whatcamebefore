package uk.gemwire.whatcamebefore;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.gemwire.whatcamebefore.client.ClientEvents;
import uk.gemwire.whatcamebefore.compat.ticon.TiConCompatEvents;
import uk.gemwire.whatcamebefore.init.ClientSetup;
import uk.gemwire.whatcamebefore.init.registry.WCBRegistry;
import uk.gemwire.whatcamebefore.init.Setup;

@Mod(WhatCameBefore.ID)
public class WhatCameBefore {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String ID = "whatcamebefore";

    public WhatCameBefore() {
        MinecraftForge.EVENT_BUS.register(this);
        WCBRegistry.init();
        ClientEvents.poke();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(Setup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(TiConCompatEvents::setupBiomes);
        MinecraftForge.EVENT_BUS.addListener(TiConCompatEvents::playerTick);
        MinecraftForge.EVENT_BUS.register(WCBEvents.class);



    }
}
