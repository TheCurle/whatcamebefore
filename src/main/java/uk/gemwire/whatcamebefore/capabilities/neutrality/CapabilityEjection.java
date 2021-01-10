package uk.gemwire.whatcamebefore.capabilities.neutrality;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityEjection {

    @CapabilityInject(IEjection.class)
    public static Capability<IEjection> PLAYER_EJECTION_TIMER = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IEjection.class, new EjectionStorage(), EjectionDefaultProvider::new);
    }

    public static class EjectionStorage implements Capability.IStorage<IEjection> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<IEjection> capability, IEjection instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            tag.putInt("wcbEjectionTimer", instance.getTimer());
            return tag;
        }

        @Override
        public void readNBT(Capability<IEjection> capability, IEjection instance, Direction side, INBT nbt) {
            int timer = ((CompoundNBT) nbt).getInt("wcbEjectionTimer");

            instance.setTimer(timer);
        }
    }
}
