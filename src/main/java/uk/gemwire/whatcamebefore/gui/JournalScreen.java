package uk.gemwire.whatcamebefore.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class JournalScreen extends Screen {
    private static final int SCREEN_WIDTH = 271;
    private static final int SCREEN_HEIGHT = 179;

    private static final ResourceLocation bg_texture = new ResourceLocation("whatcamebefore", "textures/gui/journal/journal_bg.png");

    public JournalScreen() {
        super(new TranslationTextComponent("whatcamebefore.title.journal_main"));
    }

    @Override
    protected void init() {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.minecraft.getTextureManager().bindTexture(bg_texture);
        int middleX = (this.width - SCREEN_WIDTH) / 8;
        int middleY = (this.height - SCREEN_HEIGHT) / 8;

        matrixStack.scale(3, 2.5F, 2);
        this.blit(matrixStack, middleX, middleY, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public static void open() {
        Minecraft.getInstance().displayGuiScreen(new JournalScreen());
    }
}

