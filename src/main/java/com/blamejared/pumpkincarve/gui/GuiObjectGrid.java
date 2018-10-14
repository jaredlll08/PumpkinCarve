package com.blamejared.pumpkincarve.gui;


import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class GuiObjectGrid extends GuiObject {
    
    public GuiObjectGrid(GuiCarve parent, int x, int y, int width, int height) {
        super(parent, x, y, width, height);
    }
    
    @Override
    public void draw(int left, int top, int mouseX, int mouseY, float partialTicks) {
        GlStateManager.disableTexture2D();
        b.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        b.pos(getX(), getY(), 0).color(0, 0, 0, 1f).endVertex();
        b.pos(getX2(), getY(), 0).color(0, 0, 0, 1f).endVertex();
        
        b.pos(getX2(), getY(), 0).color(0, 0, 0, 1f).endVertex();
        b.pos(getX2(), getY2(), 0).color(0, 0, 0, 1f).endVertex();
        
        b.pos(getX2(), getY2(), 0).color(0, 0, 0, 1f).endVertex();
        b.pos(getX(), getY2(), 0).color(0, 0, 0, 1f).endVertex();
        
        b.pos(getX(), getY(), 0).color(0, 0, 0, 1f).endVertex();
        b.pos(getX(), getY2(), 0).color(0, 0, 0, 1f).endVertex();
        
        b.pos(getCentreX(), getY(), 0).color(0, 0, 0, 1f).endVertex();
        b.pos(getCentreX(), getY2(), 0).color(0, 0, 0, 1f).endVertex();
        
        b.pos(getX(), getCentreY(), 0).color(0, 0, 0, 1f).endVertex();
        b.pos(getX2(), getCentreY(), 0).color(0, 0, 0, 1f).endVertex();
        
        Tessellator.getInstance().draw();
        GlStateManager.enableTexture2D();
    }
    
    @Override
    public String getText() {
        return "Toggle Grid";
    }
    
    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        parent.showGrid = !parent.showGrid;
    }
}
