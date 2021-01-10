package uk.gemwire.whatcamebefore.compat.ticon;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import uk.gemwire.whatcamebefore.WhatCameBefore;
import uk.gemwire.whatcamebefore.capabilities.neutrality.CapabilityEjection;

import java.util.Random;

public class TiConCompatEvents {

    public static void setupBiomes(FMLCommonSetupEvent e) {
        WhatCameBefore.LOGGER.info("Common Setup - preparing Biomes");
        TiConCompatRegistry.NEUTRAL_KEY = RegistryKey.getOrCreateKey(net.minecraft.util.registry.Registry.BIOME_KEY, new ResourceLocation("whatcamebefore", "neutral"));
        e.enqueueWork(() -> BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(TiConCompatRegistry.NEUTRAL_KEY, 2)));
    }

    public static void playerTick(TickEvent.PlayerTickEvent e) {
        // Only do this serverside!
        if(!e.player.world.isRemote) {
            // Is the player inside the Neutrality?
            if (e.player.world.getBiome(e.player.getPosition()).getRegistryName().equals(TiConCompatRegistry.NEUTRAL.get().getRegistryName())) {

                // Is it their first time here?
                e.player.getCapability(CapabilityEjection.PLAYER_EJECTION_TIMER).ifPresent((cap) -> {
                    WhatCameBefore.LOGGER.info("Neutrality and cap present");
                    boolean firstTime = true;
                    int timer = cap.getTimer();

                    if (timer > -1)
                        firstTime = false;

                    // if it is, schedule their demise.
                    if (firstTime) {
                        WhatCameBefore.LOGGER.info("Scheduling ejection");
                        cap.setTimer(new Random().nextInt(200) + 200);
                    } else if (timer > 0) {
                        // Otherwise, start counting them down.
                        cap.setTimer(timer - 1);
                        WhatCameBefore.LOGGER.warn("Time is running out! Time left: " + timer);
                    } else if (timer == 0) {
                        // If we get to 0, it's time to eject.
                        // First, find the nearest not-our own biome.
                        ServerWorld world = (ServerWorld) e.player.world;
                        BlockPos playerPos = e.player.getPosition();
                        BlockPos ejectionPoint = world.getChunkProvider().getChunkGenerator().getBiomeProvider().findBiomePosition(playerPos.getX(), playerPos.getY(), playerPos.getZ(), 6400, 8, (potentialBiome) ->
                            potentialBiome.getRegistryName().getNamespace() == "minecraft", e.player.world.rand, true);


                        Vector3d ejectionVelocity = new Vector3d(playerPos.getX() - ejectionPoint.getX(), playerPos.getY() + 20, playerPos.getZ() - ejectionPoint.getZ());
                        WhatCameBefore.LOGGER.warn("Bye! Have a nice life! " + ejectionVelocity.toString());
                        e.player.setMotion(ejectionVelocity);
                    }
                });

            }
        }
    }
}
