package com.blamejared.pumpkincarve.gui;


import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class GuiObjectColour extends GuiObject {
    
    private String name;
    private int colour;
    
    public GuiObjectColour(GuiCarve parent, int x, int y, int width, int height, String name, int colour) {
        super(parent, x, y, width, height);
        this.name = name;
        this.colour = colour;
        
        this.colour += ((0xFF) << 24);
    }
    
    @Override
    public void draw(int left, int top, int mouseX, int mouseY, float partialTicks) {
        
        //        if(parent.colour == colour) {
        //            GlStateManager.disableTexture2D();
        //            b.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        //            b.pos(getX(), getY(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            b.pos(getX2(), getY(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            b.pos(getX2(), getY(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            b.pos(getX2(), getY2(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            b.pos(getX2(), getY2(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            b.pos(getX(), getY2(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            b.pos(getX(), getY(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            b.pos(getX(), getY2(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            Tessellator.getInstance().draw();
        //            GlStateManager.enableTexture2D();
        //        } else if(parent.colourSec == colour) {
        //            GlStateManager.disableTexture2D();
        //            b.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        //            b.pos(getX(), getY(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            b.pos(getX2(), getY(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            b.pos(getX2(), getY(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            b.pos(getX2(), getY2(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            b.pos(getX2(), getY2(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            b.pos(getX(), getY2(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            b.pos(getX(), getY(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            b.pos(getX(), getY2(), 0).color(rgba[0], rgba[1], rgba[2], 1f).endVertex();
        //            Tessellator.getInstance().draw();
        //            GlStateManager.enableTexture2D();
        //        }
        float[] rgba = parent.getRGBA(colour);
        boolean primary = parent.colour == this.colour;
        boolean secondary = parent.colourSec == this.colour;
        
        GlStateManager.disableTexture2D();
        b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        float startX = getX();
        float startY = getY();
        float endX = getX2();
        float endY = getY2();
        if(primary) {
            startX -= 2;
            endX += 2;
            startY -= 2;
            endY += 2;
        } else if(secondary) {
            startX += 2;
            endX -= 2;
            startY += 2;
            endY -= 2;
        }
        b.pos(startX, endY, 0).color(rgba[0], rgba[1], rgba[2], 1).endVertex();
        b.pos(endX, endY, 0).color(rgba[0], rgba[1], rgba[2], 1).endVertex();
        b.pos(endX, startY, 0).color(rgba[0], rgba[1], rgba[2], 1).endVertex();
        b.pos(startX, startY, 0).color(rgba[0], rgba[1], rgba[2], 1).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.enableTexture2D();
    }
    
    @Override
    public String getText() {
        return name;
    }
    
    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        switch(mouseButton) {
            case 0:
                if(parent.colourSec != colour)
                    parent.colour = colour;
                break;
            case 1:
                if(parent.colour != colour)
                    parent.colourSec = colour;
                break;
        }
    }
}
