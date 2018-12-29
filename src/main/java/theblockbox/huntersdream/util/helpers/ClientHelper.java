package theblockbox.huntersdream.util.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

/**
 * Provides utilities for client-side only things like localization and rendering
 */
public class ClientHelper {
    public static final int GL_LINES = GL11.GL_LINES;

    /**
     * Draws a rectangle onto the screen between the given coordinates (from x, y to secondX, secondY).
     * Alpha is disabled, so there will be no transperancy.
     * @param x The x position where the rectangle should start.
     * @param y The y position where the rectangle should start.
     * @param secondX The x position where the rectangle should end.
     * @param secondY The y position where the rectangle should end.
     * @param zLevel The z where the rectangle should be drawn. (Determines if it's in the fore- or background.)
     */
    public static void drawRect(double x, double y, double secondX, double secondY, double zLevel){
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        GlStateManager.disableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();

        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(x, secondY, zLevel).endVertex();
        bufferbuilder.pos(secondX, secondY, zLevel).endVertex();
        bufferbuilder.pos(secondX, y, zLevel).endVertex();
        bufferbuilder.pos(x, y, zLevel).endVertex();

        tessellator.draw();
        GlStateManager.enableTexture2D();
    }

    public static int getMaxFontWidth(String text, int wrapWidth, FontRenderer fontRenderer) {
        int maxWidth = 0;
        for(String str : fontRenderer.listFormattedStringToWidth(text, wrapWidth)) {
            int width = fontRenderer.getStringWidth(str);
            if(width > maxWidth) {
                maxWidth = width;
            }
        }
        return maxWidth;
    }

    public static void drawConnection(int x1, int y1, int x2, int y2, float zLevel) {
        // TODO: Can you do all this in one draw?
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.disableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();

        bufferBuilder.begin(GL_LINES, DefaultVertexFormats.POSITION);
        GlStateManager.glLineWidth(11F);
        GlStateManager.color(0, 0, 0);
        bufferBuilder.pos(x1, y1, zLevel).endVertex();
        bufferBuilder.pos(x2, y2, zLevel).endVertex();
        tessellator.draw();

        bufferBuilder.begin(GL_LINES, DefaultVertexFormats.POSITION);
        GlStateManager.glLineWidth(3F);
        GlStateManager.color(1, 1, 1);
        bufferBuilder.pos(x1, y1, zLevel).endVertex();
        bufferBuilder.pos(x2, y2, zLevel).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
    }

    public static void drawCentralString(String text, int x, int y, int color, float size, FontRenderer fontRenderer) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(size, size, 1);
        fontRenderer.drawStringWithShadow(text, (x / size) - (fontRenderer.getStringWidth(text) / 2F),
                (y - ((fontRenderer.FONT_HEIGHT * size) / 2F)) / size, color);
        GlStateManager.popMatrix();
    }

    public static void drawCentralString(String text, int x, int y, int color, float size, int maximumSize,
                                         FontRenderer fontRenderer) {
        GlStateManager.pushMatrix();
        int textWidth = fontRenderer.getStringWidth(text);
        if((textWidth * size) > maximumSize)
            size = maximumSize / (float) textWidth;

        GlStateManager.scale(size, size, 1);
        fontRenderer.drawStringWithShadow(text, (x / size) - (fontRenderer.getStringWidth(text) / 2F),
                (y - ((fontRenderer.FONT_HEIGHT * size) / 2F)) / size, color);
        GlStateManager.popMatrix();
    }
}