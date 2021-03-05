package uk.gemwire.whatcamebefore.compat.ticon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import uk.gemwire.whatcamebefore.WhatCameBefore;
import uk.gemwire.whatcamebefore.capabilities.neutrality.CapabilityEjection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TiConCompatEvents {

    public static void setupBiomes(FMLCommonSetupEvent e) {
        WhatCameBefore.LOGGER.info("Common Setup - preparing Biomes");
        TiConCompatRegistry.NEUTRAL_KEY = RegistryKey.getOrCreateKey(net.minecraft.util.registry.Registry.BIOME_KEY, new ResourceLocation("whatcamebefore", "neutral"));
        e.enqueueWork(() -> BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(TiConCompatRegistry.NEUTRAL_KEY, 2)));
    }

    public static void playerTick(TickEvent.PlayerTickEvent e) {
        // Only do this serverside!
        if(e.side == LogicalSide.SERVER && e.phase == TickEvent.Phase.END) {
            // Is the player inside the Neutrality?
            if (e.player.world.getBiome(e.player.getPosition()).getRegistryName().equals(TiConCompatRegistry.NEUTRAL.get().getRegistryName())) {

                // Is it their first time here?
                e.player.getCapability(CapabilityEjection.PLAYER_EJECTION_TIMER).ifPresent((cap) -> {
                    boolean firstTime = true;

                    int timer = cap.getTimer();

                    if (timer > -1)
                        firstTime = false;

                    // if it is, schedule their demise.
                    if (firstTime) {
                        WhatCameBefore.LOGGER.info("Scheduling ejection");
                        cap.setTimer(new Random().nextInt(200) + 200);
                    } else if (timer > 1) {
                        // Otherwise, start counting them down.
                        cap.setTimer(timer - 1);
                        WhatCameBefore.LOGGER.warn("Time is running out! Time left: " + timer);
                    } else if (timer == 1) {
                        // If we get to 1, it's time to eject.
                        // First, find the nearest not-our own biome.
                        ServerWorld world = (ServerWorld) e.player.world;
                        BlockPos playerPos = e.player.getPosition();
                        BlockPos ejectionPoint = world.getChunkProvider().getChunkGenerator().getBiomeProvider().findBiomePosition(playerPos.getX(), playerPos.getY(), playerPos.getZ(), 6400, 8, (potentialBiome) ->
                            potentialBiome != TiConCompatRegistry.NEUTRAL.get(), e.player.world.rand, true);
                        if(ejectionPoint == null) {
                            WhatCameBefore.LOGGER.warn("Unable to use vanilla mechanics to locate a safe ejection point.");
                            // Uh oh! No nearby safe biome to eject to!
                            // Search for water, grass and sand.
                            final int searchRadius = 60;
                            final ArrayList<Block> validDestinations = new ArrayList<>(Arrays.asList(new Block[]{Blocks.SAND, Blocks.WATER, Blocks.GRASS_BLOCK}));
                            final BlockPos lowerLeft = playerPos.south(searchRadius).west(searchRadius).down(searchRadius / 3);
                            final BlockPos upperRight = playerPos.north(searchRadius).east(searchRadius).up(searchRadius / 3);

                            for(int y = lowerLeft.getY(); y < upperRight.getY(); y++) {
                                for(int x = lowerLeft.getX(); x < upperRight.getX(); x++) {
                                    for(int z = lowerLeft.getZ(); z < upperRight.getZ(); z++) {
                                        final BlockPos potentialPos = new BlockPos(x, y, z);
                                        final BlockState potentialBlock = world.getBlockState(potentialPos);
                                        if(validDestinations.contains(potentialBlock.getBlock())) {
                                            // Found a valid block!
                                            ejectionPoint = potentialPos;

                                        }
                                    }
                                }
                            }
                        }

                        if(ejectionPoint == null) {
                            // There's nowhere safe to eject to! Lash out!
                            PhantomEntity buhBye = new PhantomEntity(EntityType.PHANTOM, world);
                            buhBye.setAttackTarget(e.player);
                            buhBye.setPosition(playerPos.getX() + 20, playerPos.getY() + 20, playerPos.getZ() + 20);
                            WhatCameBefore.LOGGER.warn("Go Go Gadget Phantoms!");
                            world.addEntity(buhBye); world.addEntity(buhBye); world.addEntity(buhBye);
                        }

                        Vector3d ejectionVelocity = new Vector3d(playerPos.getX() - ejectionPoint.getX(), playerPos.getY() + 30, playerPos.getZ() - ejectionPoint.getZ());
                        WhatCameBefore.LOGGER.warn("Bye! Have a nice life! " + ejectionVelocity.toString());
                        e.player.setMotion(ejectionVelocity.x, ejectionVelocity.y, ejectionVelocity.z);
                        e.player.velocityChanged = true;
                        e.player.isAirBorne = true;

                        cap.setTimer(0);
                    }
                });

            }
        }
    }
}
