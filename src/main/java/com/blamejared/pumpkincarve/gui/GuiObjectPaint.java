package com.blamejared.pumpkincarve.gui;


import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class GuiObjectPaint extends GuiObject {
    
    public GuiObjectPaint(GuiCarve parent, int x, int y, int width, int height) {
        super(parent, x, y, width, height);
    }
    
    @Override
    public void draw(int left, int top, int mouseX, int mouseY, float partialTicks) {
        if(parent.currentMode == EnumMode.PAINT) {
            GlStateManager.disableTexture2D();
            b.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
            b.pos(getX(), getY(), 0).color(0, 0.8f, 1, 1f).endVertex();
            b.pos(getX2(), getY(), 0).color(0, 0.8f, 1, 1f).endVertex();
            b.pos(getX2(), getY(), 0).color(0, 0.8f, 1, 1f).endVertex();
            b.pos(getX2(), getY2(), 0).color(0, 0.8f, 1, 1f).endVertex();
            b.pos(getX2(), getY2(), 0).color(0, 0.8f, 1, 1f).endVertex();
            b.pos(getX(), getY2(), 0).color(0, 0.8f, 1, 1f).endVertex();
            b.pos(getX(), getY(), 0).color(0, 0.8f, 1, 1f).endVertex();
            b.pos(getX(), getY2(), 0).color(0, 0.8f, 1, 1f).endVertex();
            Tessellator.getInstance().draw();
            GlStateManager.enableTexture2D();
        }
        mc.fontRenderer.drawString("P" , (int) getX()+ mc.fontRenderer.getStringWidth("P")/2, (int) getY()+10-mc.fontRenderer.FONT_HEIGHT, 0);
    }
    
    @Override
    public String getText() {
        return "Paint";
    }
    
    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        parent.currentMode = EnumMode.PAINT;
    }
}
