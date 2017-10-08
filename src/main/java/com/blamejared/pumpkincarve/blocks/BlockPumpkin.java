package com.blamejared.pumpkincarve.blocks;

import com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockPumpkin extends Block implements ITileEntityProvider {
    
    public BlockPumpkin() {
        super(Material.GOURD, MapColor.ADOBE);
    }
    
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityPumpkin();
    }
}
