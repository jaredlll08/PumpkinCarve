package com.blamejared.pumpkincarve.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;


@SideOnly(Side.CLIENT)
public class RenderingUtils {
    
    public static Minecraft mc = Minecraft.getMinecraft();
    
    /**
     * draws a non-fading 2D line
     *
     * @param x         startX
     * @param y         startY
     * @param x2        endX
     * @param y2        endY
     * @param red       redColour
     * @param green     greenColour
     * @param blue      blueColour
     * @param lineWidth lineWidth
     * @param alpha     alpha
     */
    public static void drawLine(double x, double y, double x2, double y2, float red, float green, float blue, float lineWidth, float alpha) {
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buff = tess.getBuffer();
        
        GL11.glPushMatrix();
        GL11.glLineWidth(lineWidth);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        buff.begin(3, DefaultVertexFormats.POSITION_COLOR);
        buff.pos(x, y, 0).color(red, green, blue, alpha).endVertex();
        buff.pos(x2, y2, 0).color(red, green, blue, alpha).endVertex();
        tess.draw();
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(32826);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
    
    
}