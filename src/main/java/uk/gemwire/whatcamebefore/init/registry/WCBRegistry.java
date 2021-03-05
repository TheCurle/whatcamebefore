package uk.gemwire.whatcamebefore.init.registry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.gemwire.whatcamebefore.WhatCameBefore;
import uk.gemwire.whatcamebefore.block.BoilingCauldron;
import uk.gemwire.whatcamebefore.block.tileentity.BoilingCauldronTE;
import uk.gemwire.whatcamebefore.compat.ticon.TiConCompatRegistry;
import uk.gemwire.whatcamebefore.item.JournalItem;
import uk.gemwire.whatcamebefore.item.crafting.EnderGel;

import java.util.*;

public class WCBRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WhatCameBefore.ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WhatCameBefore.ID);
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, WhatCameBefore.ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, WhatCameBefore.ID);

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, WhatCameBefore.ID);

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, WhatCameBefore.ID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        PARTICLES.register(FMLJavaModLoadingContext.get().getModEventBus());
        TiConCompatRegistry.init();
    }

    public static final RegistryObject<Item> JOURNAL_ITEM = ITEMS.register("journal", JournalItem::new);

    public static final RegistryObject<Item> ENDER_GEL = ITEMS.register("ender_gel", EnderGel::new);

    public static final RegistryObject<Block> CAULDRON_BLOCK = BLOCKS.register("cauldron", () ->
            new CauldronBlock(AbstractBlock.Properties.create(Material.IRON))
    );

    public static final RegistryObject<Item> CAULDRON_ITEM = ITEMS.register("cauldron", () ->
            new BlockItem(CAULDRON_BLOCK.get(), new Item.Properties().group(ItemGroup.MISC))
    );

    public static final RegistryObject<TileEntityType<BoilingCauldronTE>> CAULDRON_TE = TILE_ENTITIES.register("cauldron", () ->
            new TileEntityType<>(BoilingCauldronTE::new, new HashSet<>(Arrays.asList(CAULDRON_BLOCK.get())), null)
    );
}
