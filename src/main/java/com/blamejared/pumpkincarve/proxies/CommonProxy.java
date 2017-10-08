package com.blamejared.pumpkincarve.proxies;

import com.blamejared.pumpkincarve.events.CommonEventHandler;
import com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
    
    public void registerEvents(){
        MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
    }
    public void openCarvingGUI(TileEntityPumpkin tile){
    
    }
    
    public void registerRenders(){
    
    }
}
