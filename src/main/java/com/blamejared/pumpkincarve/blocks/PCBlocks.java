package com.blamejared.pumpkincarve.blocks;

import com.blamejared.pumpkincarve.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class PCBlocks {
    
    public static final Block PUMPKIN_CARVED = new BlockPumpkin().setRegistryName(new ResourceLocation(Reference.MODID, "pumpkin_carved")).setUnlocalizedName("pumpkin_carved");
    public static final Block PIXEL = new BlockPixel().setRegistryName(new ResourceLocation(Reference.MODID, "pixel")).setUnlocalizedName("pixel");
    
}
