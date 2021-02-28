package uk.gemwire.whatcamebefore;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.gemwire.whatcamebefore.capabilities.neutrality.EjectionProvider;
import uk.gemwire.whatcamebefore.capabilities.progress.CapabilityProgress;
import uk.gemwire.whatcamebefore.capabilities.progress.ProgressProvider;
import uk.gemwire.whatcamebefore.compat.ticon.TiConCompatRegistry;
import uk.gemwire.whatcamebefore.compat.ticon.entities.NeutralSlime;

import java.util.HashMap;


@Mod.EventBusSubscriber(modid="whatcamebefore", bus= Mod.EventBusSubscriber.Bus.FORGE)
public class WCBEvents {

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof PlayerEntity) {
            ProgressProvider progress = new ProgressProvider();
            event.addCapability(new ResourceLocation("whatcamebefore", "progress"), progress);
            event.addListener(progress::invalidate);

            EjectionProvider ejection = new EjectionProvider();
            event.addCapability(new ResourceLocation("whatcamebefore", "ejectiontimer"), ejection);
            event.addListener(ejection::invalidate);
        }
    }

    @SubscribeEvent
    public static void itemUsed(PlayerInteractEvent.RightClickItem event) {
        Entity entity = event.getEntity();
        System.out.println("Item used");
        if(!entity.getEntityWorld().isRemote()) {
            System.out.println("Item used on server");
            if(event.getItemStack().getItem() == Items.STICK) {
                System.out.println("Stick used");
                entity.getCapability(CapabilityProgress.PLAYER_MOD_PROGRESS).ifPresent(progress -> {

                    System.out.println("Capability resolved");
                    int level = progress.getProgressLevel() + 1;
                    ((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Levelled up to ".concat(Integer.toString(level))), true);
                    System.out.println("Player levelled up!");
                    progress.setProgress(level, progress.getProgressXP());
                    entity.getEntityWorld().addParticle(ParticleTypes.EXPLOSION, entity.getPosX(), entity.getPosY() + 2, entity.getPosZ(), 0.0, 0.0, 0.0);
                });
            } else if(event.getItemStack().getItem() == Items.ARROW) {

                System.out.println("Arrow used");
                entity.getCapability(CapabilityProgress.PLAYER_MOD_PROGRESS).ifPresent(progress -> {
                    System.out.println("Capability resolved");
                    progress.setProgress(0, 0);
                    ((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Level reset!"), true);
                    System.out.println("Player progress reset");
                    entity.getEntityWorld().addParticle(ParticleTypes.BARRIER, entity.getPosX(), entity.getPosY() + 2, entity.getPosZ(), 0.0, 0.0, 0.0);
                });
            }
        }
    }

    @Mod.EventBusSubscriber(modid = "whatcamebefore", bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class WCBModEvents {
        @SubscribeEvent
        public static void entityAttributes(EntityAttributeCreationEvent e) {
            e.put(TiConCompatRegistry.NEUTRAL_SLIME.get(), NeutralSlime.setupAttributes());
        }
    }

}
