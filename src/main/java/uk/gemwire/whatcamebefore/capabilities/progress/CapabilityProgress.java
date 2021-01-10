package uk.gemwire.whatcamebefore.capabilities.progress;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityProgress {

    @CapabilityInject(IProgress.class)
    public static Capability<IProgress> PLAYER_MOD_PROGRESS = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IProgress.class, new ProgressStorage(), ProgressDefaultProvider::new);
    }

    public static class ProgressStorage implements Capability.IStorage<IProgress> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<IProgress> capability, IProgress instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            tag.putString("progress", Integer.toString(instance.getProgressLevel()).concat(":").concat(Integer.toString(instance.getProgressXP())));
            return tag;
        }

        @Override
        public void readNBT(Capability<IProgress> capability, IProgress instance, Direction side, INBT nbt) {
            String progress = ((CompoundNBT) nbt).getString("wcbProgress");
            if (progress.length() == 0) {
                instance.setProgress(0, 0);
            } else {
                instance.setProgress(Integer.parseInt(progress.substring(0, progress.indexOf(":"))), Integer.parseInt(progress.substring(progress.indexOf(":"))));
            }

        }
    }
}
