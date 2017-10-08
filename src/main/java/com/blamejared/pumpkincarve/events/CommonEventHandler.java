package com.blamejared.pumpkincarve.events;

import com.blamejared.pumpkincarve.SpookyJam;
import com.blamejared.pumpkincarve.blocks.PCBlocks;
import com.blamejared.pumpkincarve.reference.Reference;
import com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin;
import net.minecraft.block.*;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Arrays;

public class CommonEventHandler {
    
    
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        
        if(stack.getItem() instanceof ItemSword && stack.getItem() != Items.WOODEN_SWORD && (world.getBlockState(pos).getBlock() == PCBlocks.PUMPKIN_CARVED || world.getBlockState(pos).getBlock() instanceof BlockPumpkin)) {
            if(world.getBlockState(pos).getBlock() != PCBlocks.PUMPKIN_CARVED) {
                world.setBlockState(pos, PCBlocks.PUMPKIN_CARVED.getDefaultState());
                //                world.setTileEntity(pos, PCBlocks.PUMPKIN_CARVED.createTileEntity(world, world.getBlockState(pos)));
                //                TileEntityPumpkin tile = (TileEntityPumpkin) world.getTileEntity(pos);
            }
            if(world.isRemote)
                SpookyJam.PROXY.openCarvingGUI((TileEntityPumpkin) world.getTileEntity(pos));
        }
    }
    
    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(PCBlocks.PUMPKIN_CARVED);
        event.getRegistry().register(PCBlocks.PIXEL);
        GameRegistry.registerTileEntity(TileEntityPumpkin.class, "pumpkin_carved");
        
    }
    
    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
//        event.getRegistry().register(new ItemBlock(PCBlocks.PUMPKIN_CARVED).setRegistryName(new ResourceLocation(Reference.MODID, "pumpkin_carved")));
//        event.getRegistry().register(new ItemBlock(PCBlocks.PIXEL).setRegistryName(new ResourceLocation(Reference.MODID, "pixel")));
    
    }
    
}
