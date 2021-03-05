package uk.gemwire.whatcamebefore.block.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.LightType;
import uk.gemwire.whatcamebefore.WhatCameBefore;
import uk.gemwire.whatcamebefore.init.registry.WCBRegistry;


public class BoilingCauldronTE extends TileEntity implements ITickableTileEntity {
    private int boilingTimer = 0;
    private boolean boiling = false;

    private boolean hasPearl = false;
    private int pearlTimer = 0;

    public BoilingCauldronTE() {
        super(WCBRegistry.CAULDRON_TE.get());
    }


    @Override
    public void tick() {
        // If we don't have enough light / temperature, reset and try again
        assert world != null;
        WhatCameBefore.LOGGER.debug("Light for cauldron at " + pos.toString() + " is " + world.getLightFor(LightType.BLOCK, pos));
        if (!(world.getLightFor(LightType.BLOCK, pos) > 11)) {
            boilingTimer = 0;
            boiling = false;
            return;
        }

        // If we're not already boiling, start counting
        if(!boiling) boilingTimer += 1;
        WhatCameBefore.LOGGER.info("Timer at " + boilingTimer);

        // If we hit the limit, stop counting
        if(boilingTimer >= 1200) boiling = true;

        // Do the things now that we're ready
        if(boiling) {
            world.addParticle(ParticleTypes.DRIPPING_WATER,
                    pos.getX() + world.getRandom().nextDouble(),
                    pos.getY() + world.getRandom().nextDouble(),
                    pos.getZ() + world.getRandom().nextDouble(),
                    0, 0.1, 0);
            WhatCameBefore.LOGGER.debug("Boiling");
        }

        if(hasPearl) pearlTimer += 1;

        if(pearlTimer >= 600) {
            hasPearl = false;
            pearlTimer = 0;
            ItemEntity gelEntity = new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ() + 1);
            gelEntity.setMotion(0.5, 1, 0.5);
            gelEntity.velocityChanged = true;
            WhatCameBefore.LOGGER.debug("Returning Ender Gel");
            world.addEntity(gelEntity);
        }
    }

    public void handleItemDropped(Entity entity) {
        if(entity instanceof ItemEntity) {
            ItemEntity item = (ItemEntity) entity;
            if(item.getItem().getItem() == Items.ENDER_PEARL) {
                entity.remove();
                hasPearl = true;
                WhatCameBefore.LOGGER.debug("Eating ender pearl");
            }
        }
    }

}
