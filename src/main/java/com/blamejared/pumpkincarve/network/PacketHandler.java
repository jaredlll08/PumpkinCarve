/*
 * Copyright (c) 2018. Jared Luboff. All Rights Reserved.
 */

package com.blamejared.pumpkincarve.network;


import com.blamejared.pumpkincarve.reference.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
    
    private static int id = 0;
    
    public static void init() {
        INSTANCE.registerMessage(MessageSetPx.class, MessageSetPx.class, id++, Side.SERVER);
        
    }
    
}