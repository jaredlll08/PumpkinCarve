package com.blamejared.pumpkincarve.blocks;

import com.blamejared.pumpkincarve.reference.Reference;
import com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.*;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.*;

public class PCBlocks {
    
    public static Block PUMPKIN = new BlockPC();
    
    @SubscribeEvent
    public void onRegistryRegisterBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(PUMPKIN.setRegistryName(Reference.MODID + ":" + "pumpkin"));
        GameRegistry.registerTileEntity(TileEntityPumpkin.class, Reference.MODID + ":" + "pumpkin");
    }
    
    @SubscribeEvent
    public void onRegistryRegisterItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(PUMPKIN).setRegistryName(Reference.MODID + ":" + "pumpkin"));
    }
    
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onModelRegistry(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(PUMPKIN), 0, new ModelResourceLocation(Reference.MODID + ":pumpkin", "inventory"));
    }
}
