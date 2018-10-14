package com.blamejared.pumpkincarve.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;

public abstract class GuiObject {
    
    protected boolean enabled;
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    
    protected boolean alwaysVisible;
    
    protected final GuiCarve parent;
    
    protected Minecraft mc;
    
    protected BufferBuilder b;
    
    public GuiObject(GuiCarve parent, int x, int y, int width, int height) {
        
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.enabled = true;
        this.mc = Minecraft.getMinecraft();
        this.b = Tessellator.getInstance().getBuffer();
    }
    
    public boolean collides(GuiObject other) {
        if(other == null) {
            return false;
        }
        
        return this.collides(other.getX(), other.getY(), other.getX() + other.getWidth(), other.getY() + other.getHeight());
    }
    
    public boolean collides(float x, float y) {
        return collides(x, y, x, y);
    }
    
    public boolean collides(float x1, float y1, float x2, float y2) {
        
        if(x2 > getX() && x1 < getX() + getWidth()) {
            if(y2 > getY() && y1 < getY() + getHeight()) {
                return true;
            }
        }
        return false;
    }
    
    public void update() {
    
    }
    
    public abstract void draw(int left, int top, int mouseX, int mouseY, float partialTicks);
    
    public void drawText(int left, int top, int mouseX, int mouseY, float partialTicks) {
        if(collides(mouseX, mouseY)) {
            if(!getText().isEmpty()) {
                parent.drawHoveringText(getText(), mouseX, mouseY);
            }
        }
    }
    
    public String getText() {
        return "";
    }
    
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    
    }
    
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
    
    }
    
    public void mouseReleased(int mouseX, int mouseY, int state) {
    
    }
    
    public boolean isEnabled() {
        
        return this.enabled;
    }
    
    public void setEnabled(boolean enabled) {
        
        this.enabled = enabled;
    }
    
    public float getX() {
        
        return this.x;
    }
    
    public void setX(float x) {
        
        this.x = x;
    }
    
    public float getY() {
        
        return this.y;
    }
    
    public void setY(float y) {
        
        this.y = y;
    }
    
    public int getWidth() {
        
        return this.width;
    }
    
    public void setWidth(int width) {
        
        this.width = width;
    }
    
    public int getHeight() {
        
        return this.height;
    }
    
    public void setHeight(int height) {
        
        this.height = height;
    }
    
    public boolean isAlwaysVisible() {
        
        return this.alwaysVisible;
    }
    
    public void setAlwaysVisible(boolean alwaysVisible) {
        
        this.alwaysVisible = alwaysVisible;
    }
    
    public float getCentreX() {
        return getX2() - getWidth() / 2;
    }
    
    public float getCentreY() {
        return getY2() - getHeight() / 2;
    }
    
    public float getX2() {
        return getX() + getWidth();
    }
    
    public float getY2() {
        return getY() + getHeight();
    }
    
}
