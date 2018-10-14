package com.blamejared.pumpkincarve.proxy;

import com.blamejared.pumpkincarve.gui.GuiCarve;
import com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin;
import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy {
    
    
    @Override
    public void openCarvingGUI(TileEntityPumpkin tile) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiCarve(tile));
    }
}
