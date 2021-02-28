package uk.gemwire.whatcamebefore.client.render;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.ResourceLocation;

public class NeutralSlimeRenderer extends SlimeRenderer {
    private static final ResourceLocation SLIME_TEXTURES = new ResourceLocation("whatcamebefore", "textures/entity/neutral_slime.png");

    public NeutralSlimeRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(SlimeEntity entity) {
        return SLIME_TEXTURES;
    }
}
