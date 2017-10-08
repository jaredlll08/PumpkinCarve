package com.blamejared.pumpkincarve;

import com.blamejared.pumpkincarve.network.PacketHandler;
import com.blamejared.pumpkincarve.proxies.CommonProxy;
import com.blamejared.pumpkincarve.reference.Reference;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class SpookyJam {
    
    @Mod.Instance(Reference.MODID)
    public static SpookyJam INSTANCE;
    
    @SidedProxy(clientSide = "com.blamejared.pumpkincarve.proxies.ClientProxy", serverSide = "com.blamejared.pumpkincarve.proxies.CommonProxy")
    public static CommonProxy PROXY;
    
    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event) {
        PROXY.registerEvents();
        PacketHandler.preInit();
    }
    
    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        PROXY.registerRenders();
    }
}
