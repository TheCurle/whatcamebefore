package uk.gemwire.whatcamebefore.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ProgressProvider implements ICapabilitySerializable<CompoundNBT> {

    private final ProgressDefaultProvider progress = new ProgressDefaultProvider();
    private final LazyOptional<IProgress> progressOptional = LazyOptional.of(() -> progress);

    public void invalidate() {
        progressOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return progressOptional.cast();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if(CapabilityProgress.PLAYER_MOD_PROGRESS == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityProgress.PLAYER_MOD_PROGRESS.writeNBT(progress, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(CapabilityProgress.PLAYER_MOD_PROGRESS != null)
            CapabilityProgress.PLAYER_MOD_PROGRESS.readNBT(progress, null, nbt);
    }
}
