package com.blamejared.pumpkincarve.gui;

import com.blamejared.pumpkincarve.network.*;
import com.blamejared.pumpkincarve.reference.Reference;
import com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.*;

public class GuiCarve extends GuiScreen {
    
    private TileEntityPumpkin tile;
    
    public GuiCarve(TileEntityPumpkin tile) {
        this.tile = tile;
    }
    
    
    private ResourceLocation textureBackground = new ResourceLocation(Reference.MODID, "textures/gui/gui_carving.png");
    private ResourceLocation texturePumpkin = new ResourceLocation("textures/blocks/pumpkin_side.png");
    
    public int guiLeft;
    public int guiTop;
    public int xSize;
    public int ySize;
    
    public final int gridSize = 8;
    
    public boolean showGrid = true;
    
    
    public EnumMode currentMode;
    
    private List<GuiObject> objects;
    
    
    public int colour;
    public int colourSec;
    
    @Override
    public void initGui() {
        
        super.initGui();
        
        this.xSize = 128;
        this.ySize = 128;
        ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
        this.guiLeft = scaledresolution.getScaledWidth() / 2 - xSize / 2;
        this.guiTop = scaledresolution.getScaledHeight() / 2 - ySize / 2;
        objects = new LinkedList<>();
        objects.add(new GuiObjectGrid(this, guiLeft - 20, guiTop + 5, 10, 10));
        objects.add(new GuiObjectErase(this, guiLeft - 35, guiTop + 5, 10, 10));
        objects.add(new GuiObjectPaint(this, guiLeft - 50, guiTop + 5, 10, 10));
        colour = 0xFF441300;
        colourSec = 0xFF2d0003;
        currentMode = EnumMode.PAINT;
        int countX = 0;
        int countY = 0;
        for(EnumDyeColor value : EnumDyeColor.values()) {
            objects.add(new GuiObjectColour(this, guiLeft - 95 + (countX * 15), guiTop + 20 + (countY * 15), 10, 10, value.getName(), value.getColorValue()));
            if(countX++ > 4) {
                countX = 0;
                countY++;
            }
        }
        objects.add(new GuiObjectColour(this, guiLeft - 95 + (countX * 15), guiTop + 20 + (countY * 15), 10, 10, "Inner Pumpkin", 0xFF441300));
        if(countX++ > 4) {
            countX = 0;
            countY++;
        }
        objects.add(new GuiObjectColour(this, guiLeft - 95 + (countX * 15), guiTop + 20 + (countY * 15), 10, 10, "Inner Pumpkin Edge", 0xFF2d0003));
        if(countX++ > 4) {
            countX = 0;
            countY++;
        }
    }
    
    @Override
    public void drawBackground(int tint) {
        super.drawBackground(tint);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        objects.forEach(GuiObject::update);
        
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.color(1, 1, 1, 1f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texturePumpkin);
        BufferBuilder b = Tessellator.getInstance().getBuffer();
        {
            b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            b.pos(guiLeft, guiTop + ySize, 0).tex(0, 1).color(1f, 1f, 1f, 1f).endVertex();
            b.pos(guiLeft + xSize, guiTop + ySize, 0).tex(1, 1).color(1f, 1f, 1f, 1f).endVertex();
            b.pos(guiLeft + xSize, guiTop, 0).tex(1, 0).color(1f, 1f, 1f, 1f).endVertex();
            b.pos(guiLeft, guiTop, 0).tex(0, 0).color(1f, 1f, 1f, 1f).endVertex();
            Tessellator.getInstance().draw();
        }
        GlStateManager.color(1, 1, 1, 1f);
        
        {
            GlStateManager.disableTexture2D();
            b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
            for(int x = 0; x < tile.getPixelGrid().length; x++) {
                for(int y = 0; y < tile.getPixelGrid()[x].length; y++) {
                    float[] rgba = getRGBA(tile.getPixelGrid()[x][y]);
                    b.pos(guiLeft + (x * gridSize), guiTop + (y * gridSize) + gridSize, 0).color(rgba[0], rgba[1], rgba[2], rgba[3]).endVertex();
                    b.pos(guiLeft + (x * gridSize) + gridSize, guiTop + (y * gridSize) + gridSize, 0).color(rgba[0], rgba[1], rgba[2], rgba[3]).endVertex();
                    b.pos(guiLeft + (x * gridSize) + gridSize, guiTop + (y * gridSize), 0).color(rgba[0], rgba[1], rgba[2], rgba[3]).endVertex();
                    b.pos(guiLeft + (x * gridSize), guiTop + (y * gridSize), 0).color(rgba[0], rgba[1], rgba[2], rgba[3]).endVertex();
                }
            }
            Tessellator.getInstance().draw();
            GlStateManager.enableTexture2D();
        }
        
        if(showGrid) {
            b.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
            for(int x = 0; x <= xSize; x += gridSize) {
                b.pos(guiLeft + x, guiTop, 0).color(0f, 0f, 0f, 1f).endVertex();
                b.pos(guiLeft + x, guiTop + ySize, 0).color(0f, 0f, 0f, 1f).endVertex();
            }
            for(int y = 0; y <= ySize; y += gridSize) {
                b.pos(guiLeft, guiTop + y, 0).color(0f, 0f, 0f, 1f).endVertex();
                b.pos(guiLeft + xSize, guiTop + y, 0).color(0f, 0f, 0f, 1f).endVertex();
            }
            Tessellator.getInstance().draw();
        }
        
        objects.forEach(guiObject -> guiObject.draw(this.guiLeft, this.guiTop, mouseX, mouseY, partialTicks));
        objects.forEach(guiObject -> guiObject.drawText(this.guiLeft, this.guiTop, mouseX, mouseY, partialTicks));
        
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
    
    public float getRed(int hex) {
        return ((hex >> 16) & 0xFF) / 255f;
    }
    
    public float getGreen(int hex) {
        return ((hex >> 8) & 0xFF) / 255f;
    }
    
    public float getBlue(int hex) {
        return ((hex) & 0xFF) / 255f;
    }
    
    public float getAlpha(int hex) {
        return ((hex >> 24) & 0xff) / 255f;
    }
    
    public float[] getRGBA(int hex) {
        return new float[]{getRed(hex), getGreen(hex), getBlue(hex), getAlpha(hex)};
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    @Override
    protected void mouseClicked(int mx, int my, int mouseButton) throws IOException {
        super.mouseClicked(mx, my, mouseButton);
        if(mouseButton == 2) {
            tile.clearGrid();
        }
        boolean valid = true;
        
        for(final GuiObject object : this.objects) {
            if(object.collides(mx, my)) {
                valid = false;
                object.mouseClicked(mx, my, mouseButton);
            }
            
        }
        if(valid) {
            if(mx > guiLeft && mx < guiLeft + xSize) {
                if(my > guiTop && my < guiTop + ySize) {
                    int normalX = (int) Math.floor(((mx - guiLeft + 0f) / gridSize));
                    int normalY = (int) Math.floor(((my - guiTop + 0f) / gridSize));
                    int setColour = mouseButton == 0 ? colour : colourSec;
                    if(currentMode == EnumMode.ERASE) {
                        setColour = 0;
                    }
                    tile.setPixel(normalX, normalY, setColour);
                    PacketHandler.INSTANCE.sendToServer(new MessageSetPx(tile.getPos(), normalX, normalY, setColour));
                    IBlockState state = tile.getWorld().getBlockState(tile.getPos());
                    tile.getWorld().notifyBlockUpdate(tile.getPos(), state, state, 8);
                }
            }
        }
    }
    
    @Override
    protected void mouseReleased(int mx, int my, int state) {
        
        super.mouseReleased(mx, my, state);
        for(final GuiObject object : this.objects) {
            if(object.collides(mx, my)) {
                object.mouseReleased(mx, my, state);
            }
        }
    }
    
    @Override
    public void handleInput() throws IOException {
        super.handleInput();
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
    }
}
