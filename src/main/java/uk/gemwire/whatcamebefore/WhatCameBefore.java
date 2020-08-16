package uk.gemwire.whatcamebefore;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import uk.gemwire.whatcamebefore.init.ClientSetup;
import uk.gemwire.whatcamebefore.init.Registry;
import uk.gemwire.whatcamebefore.init.Setup;

@Mod("whatcamebefore")
public class WhatCameBefore {

    public WhatCameBefore() {
        MinecraftForge.EVENT_BUS.register(this);
        Registry.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(Setup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
        MinecraftForge.EVENT_BUS.register(WCBEvents.class);

    }
}
