package com.blamejared.pumpkincarve.network;


import com.blamejared.pumpkincarve.reference.Reference;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    
    public static SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(Reference.MODID);
    public static int ID = 0;
    
    public static void preInit() {
        INSTANCE.registerMessage(MessagePumpkinSyncServer.class, MessagePumpkinSyncServer.class, ID++, Side.SERVER);
        INSTANCE.registerMessage(MessagePumpkinSyncClient.class, MessagePumpkinSyncClient.class, ID++, Side.CLIENT);
    
    }
    
}
