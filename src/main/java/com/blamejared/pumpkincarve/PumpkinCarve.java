package com.blamejared.pumpkincarve;

import com.blamejared.pumpkincarve.blocks.PCBlocks;
import com.blamejared.pumpkincarve.events.CommonEventHandler;
import com.blamejared.pumpkincarve.network.PacketHandler;
import com.blamejared.pumpkincarve.proxy.CommonProxy;
import com.blamejared.pumpkincarve.reference.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class PumpkinCarve {
    
    @SidedProxy(clientSide = "com.blamejared.pumpkincarve.proxy.ClientProxy", serverSide = "com.blamejared.pumpkincarve.proxy.CommonProxy")
    public static CommonProxy PROXY;
    
    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PCBlocks());
        MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
        PacketHandler.init();
    }
    
    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
    
    }
}
