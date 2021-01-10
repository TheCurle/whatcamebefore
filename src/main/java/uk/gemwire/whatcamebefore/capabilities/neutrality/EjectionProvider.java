package uk.gemwire.whatcamebefore.capabilities.neutrality;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EjectionProvider implements ICapabilitySerializable<CompoundNBT> {

    private final EjectionDefaultProvider ejectionTimer = new EjectionDefaultProvider();
    private final LazyOptional<IEjection> ejectionOptional = LazyOptional.of(() -> ejectionTimer);

    public void invalidate() {
        ejectionOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityEjection.PLAYER_EJECTION_TIMER)
            return ejectionOptional.cast();
        else
            return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if(CapabilityEjection.PLAYER_EJECTION_TIMER == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityEjection.PLAYER_EJECTION_TIMER.writeNBT(ejectionTimer, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(CapabilityEjection.PLAYER_EJECTION_TIMER != null)
            CapabilityEjection.PLAYER_EJECTION_TIMER.readNBT(ejectionTimer, null, nbt);
    }
}
