package com.blamejared.pumpkincarve.client.gui;

import com.blamejared.pumpkincarve.network.*;
import com.blamejared.pumpkincarve.reference.Reference;
import com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin;
import com.blamejared.pumpkincarve.util.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.*;

import static com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin.genArray;

public class GuiCarving extends GuiScreen {
    
    private ResourceLocation textureBackground = new ResourceLocation(Reference.MODID, "textures/gui/gui_carving.png");
    private ResourceLocation texturePumpkin = new ResourceLocation("textures/blocks/pumpkin_side.png");
    
    public int guiLeft;
    public int guiTop;
    public int xSize;
    public int ySize;
    
    public int minX, maxX, minY, maxY;
    
    public final int gridSize = 8;
    private TileEntityPumpkin tile;
    public int[][] pixelMap = genArray();
    
    public GuiCarving(TileEntityPumpkin tile) {
        this.tile = tile;
        pixelMap = tile.pixelMap;
    }
    
    public boolean showGrid = true;
    
    public List<Action> actionsUndo = new LinkedList<>();
    public List<Action> actionsRedo = new LinkedList<>();
    
    private final int undoTimeDefault = 5;
    private int undoTime = undoTimeDefault;
    
    @Override
    public void initGui() {
        
        super.initGui();
        
        this.xSize = 248;
        this.ySize = 166;
        ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
        this.guiLeft = scaledresolution.getScaledWidth() / 2 - xSize / 2;
        this.guiTop = scaledresolution.getScaledHeight() / 2 - ySize / 2;
        minX = guiLeft + 102;
        maxX = minX + 128;
        minY = guiTop + 19;
        maxY = minY + 128;
        buttonList.add(new GuiCheckBox(2906, guiLeft + 7, guiTop + 7, "Show Grid", showGrid));
        
    }
    
    @Override
    public void drawBackground(int tint) {
        super.drawBackground(tint);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if(undoTime > 0)
            undoTime--;
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        
        GlStateManager.pushAttrib();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().renderEngine.bindTexture(textureBackground);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        Minecraft.getMinecraft().renderEngine.bindTexture(texturePumpkin);
        drawModalRectWithCustomSizedTexture(guiLeft + 102, guiTop + 19, 0, 0, 128, 128, 128, 128);
        
        GlStateManager.popAttrib();
        super.drawScreen(mouseX, mouseY, partialTicks);
        mc.fontRenderer.drawString("           " + mouseX + ":" + mouseY + " (" + ((mouseX - minX) / gridSize) + ":" + ((mouseY - minY) / gridSize) + ")", mouseX, mouseY, 0, false);
        for(int x = 0; x < pixelMap.length; x++) {
            for(int y = 0; y < pixelMap[x].length; y++) {
                int left = minX + x * gridSize;
                int top = minY + y * gridSize;
                drawRect(left, top, left + gridSize, top + gridSize, pixelMap[x][y]);
            }
        }
        if(showGrid) {
            float lineWidth = 1;
            for(int x = 0; x < 17; x++) {
                RenderingUtils.drawLine(minX + (x * gridSize), minY, minX + (x * gridSize), maxY, 0, 0, 0, lineWidth, 1);
            }
            for(int y = 0; y < 17; y++) {
                RenderingUtils.drawLine(minX, minY + (y * gridSize), maxX, minY + (y * gridSize), 0, 0, 0, lineWidth, 1);
            }
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    @Override
    protected void mouseClicked(int mx, int my, int mouseButton) throws IOException {
        super.mouseClicked(mx, my, mouseButton);
        if(checkConstraints(mx, my)) {
            int x = (mx - minX) / gridSize;
            int y = (my - minY) / gridSize;
            Action act = new Action(this, x, y, mouseButton);
            act.apply(pixelMap);
            actionsUndo.add(act);
            if(mouseButton != 0) {
                actionsUndo.clear();
            }
            actionsRedo.clear();
            tile.pixelMap = pixelMap;
            PacketHandler.INSTANCE.sendToServer(new MessagePumpkinSyncServer(tile.getPos(), pixelMap));
//            tile.markDirty();
        }
    }
    
    public boolean checkConstraints(int mouseX, int mouseY) {
        return mouseX > minX && mouseX < maxX && mouseY > minY && mouseY < maxY;
    }
    
    @Override
    public void handleInput() throws IOException {
        super.handleInput();
        Keyboard.enableRepeatEvents(false);
        if(undoTime > 0) {
            return;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
            if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
                if(!actionsUndo.isEmpty()) {
                    Action action = actionsUndo.get(actionsUndo.size() - 1);
                    action.undo(pixelMap);
                    actionsUndo.remove(action);
                    actionsRedo.add(action);
                    undoTime = undoTimeDefault;
                    tile.pixelMap = pixelMap;
                    PacketHandler.INSTANCE.sendToServer(new MessagePumpkinSyncServer(tile.getPos(), pixelMap));
//                    tile.markDirty();
                }
            } else if(Keyboard.isKeyDown(Keyboard.KEY_Y)) {
                if(!actionsRedo.isEmpty()) {
                    Action action = actionsRedo.get(actionsRedo.size() - 1);
                    action.apply(pixelMap);
                    actionsRedo.remove(action);
                    actionsUndo.add(action);
                    undoTime = undoTimeDefault;
                    tile.pixelMap = pixelMap;
                    PacketHandler.INSTANCE.sendToServer(new MessagePumpkinSyncServer(tile.getPos(), pixelMap));
//                    tile.markDirty();
                }
            }
        }
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        PacketHandler.INSTANCE.sendToServer(new MessagePumpkinSyncServer(tile.getPos(), pixelMap));
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch(button.id) {
            case 2906:
                showGrid = !showGrid;
                break;
        }
    }
    
}
