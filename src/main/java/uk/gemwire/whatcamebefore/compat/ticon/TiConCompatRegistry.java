package uk.gemwire.whatcamebefore.compat.ticon;

import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.fml.RegistryObject;
import uk.gemwire.whatcamebefore.init.registry.WCBRegistry;


public class TiConCompatRegistry {

    public static final RegistryObject<Biome> NEUTRAL = WCBRegistry.BIOMES.register("neutral",
            () -> (new Biome.Builder()).category(Biome.Category.EXTREME_HILLS)
                                            .depth(0.5F)
                                            .temperature(-0.5F)
                                            .downfall(0.4F)
                                            .scale(0.05F)
                                            .precipitation(Biome.RainType.RAIN)
                                            .setEffects((new BiomeAmbience.Builder())
                                                    .withSkyColor(8818332)
                                                    .setFogColor(9080985)
                                                    .setWaterColor(5790821)
                                                    .setWaterFogColor(2763314)
                                                    .build())
                                            .withGenerationSettings(BiomeGenerationSettings.DEFAULT_SETTINGS)
                                            .withMobSpawnSettings(MobSpawnInfo.EMPTY)
                                            .withTemperatureModifier(Biome.TemperatureModifier.NONE)
                                            .build());

    public static RegistryKey<Biome> NEUTRAL_KEY;

    public static void init() {}

}
