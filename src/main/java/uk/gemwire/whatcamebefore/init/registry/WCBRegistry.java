package uk.gemwire.whatcamebefore.init.registry;

import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.gemwire.whatcamebefore.compat.ticon.TiConCompatRegistry;
import uk.gemwire.whatcamebefore.item.JournalItem;

public class WCBRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "whatcamebefore");
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, "whatcamebefore");


    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());
        TiConCompatRegistry.init();
    }

    public static final RegistryObject<Item> JOURNAL_TEM = ITEMS.register("journal", JournalItem::new);
}
