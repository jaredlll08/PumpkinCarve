package com.blamejared.pumpkincarve.events;

import com.blamejared.pumpkincarve.PumpkinCarve;
import com.blamejared.pumpkincarve.blocks.PCBlocks;
import com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonEventHandler {
    
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        
        if(stack.getItem() instanceof ItemSword && stack.getItem() != Items.WOODEN_SWORD && (world.getBlockState(pos).getBlock() == PCBlocks.PUMPKIN || world.getBlockState(pos).getBlock() instanceof BlockPumpkin)) {
            if(world.getBlockState(pos).getBlock() != PCBlocks.PUMPKIN) {
                world.setBlockState(pos, PCBlocks.PUMPKIN.getDefaultState());
            }
            PumpkinCarve.PROXY.openCarvingGUI((TileEntityPumpkin) world.getTileEntity(pos));
        }
    }
}
