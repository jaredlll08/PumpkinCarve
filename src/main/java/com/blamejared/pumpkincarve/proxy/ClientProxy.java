package com.blamejared.pumpkincarve.proxy;

import com.blamejared.pumpkincarve.client.RenderPumpkin;
import com.blamejared.pumpkincarve.gui.GuiCarve;
import com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
    
    
    @Override
    public void openCarvingGUI(TileEntityPumpkin tile) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiCarve(tile));
    }
    
    @Override
    public void registerRender() {
        super.registerRender();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPumpkin.class, new RenderPumpkin());
    }
}
