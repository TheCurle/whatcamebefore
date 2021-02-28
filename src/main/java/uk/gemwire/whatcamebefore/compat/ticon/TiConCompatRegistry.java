package uk.gemwire.whatcamebefore.compat.ticon;

import com.google.common.collect.ImmutableSet;
import com.ibm.icu.text.MessagePattern;
import com.mojang.serialization.Codec;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.fml.RegistryObject;
import uk.gemwire.whatcamebefore.compat.ticon.entities.NeutralSlime;
import uk.gemwire.whatcamebefore.init.registry.WCBRegistry;

import java.awt.geom.IllegalPathStateException;


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

    public static final RegistryObject<EntityType<NeutralSlime>> NEUTRAL_SLIME = WCBRegistry.ENTITIES.register("neutral_slime",
            () -> new EntityType<>(NeutralSlime::new, EntityClassification.MONSTER, true, true, false, false, ImmutableSet.of(), EntitySize.fixed(2.04F, 2.04F), 10, 1));

    public static final RegistryObject<Item> NEUTRAL_SLIME_BALL = WCBRegistry.ITEMS.register("neutral_slime_ball",
            () -> new Item(new Item.Properties().group(ItemGroup.MISC))
    );

    public static final RegistryObject<ParticleType<?>> NEUTRAL_PARTICLE = WCBRegistry.PARTICLES.register("neutral_slime",
            () -> new BasicParticleType(false)
    );

    public static RegistryKey<Biome> NEUTRAL_KEY;

    public static void init() {}

}
