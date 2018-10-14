package com.blamejared.pumpkincarve.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityPumpkin extends TileEntity {
    
    public int[][] pixelGrid = new int[16][16];
    
    public TileEntityPumpkin() {
        //        clearGrid();
    }
    
    public void clearGrid() {
        clearGrid(0);
    }
    
    public void setPixel(int x, int y, int pixel) {
        pixelGrid[x][y] = pixel;
    }
    
    public void clearGrid(int value) {
        for(int i = 0; i < pixelGrid.length; i++) {
            for(int j = 0; j < pixelGrid[i].length; j++) {
                pixelGrid[i][j] = value;
            }
        }
    }
    
    public int[][] getPixelGrid() {
        return pixelGrid;
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        for(int i = 0; i < pixelGrid.length; i++) {
            for(int j = 0; j < pixelGrid[i].length; j++) {
                compound.setInteger(i + ":" + j, pixelGrid[i][j]);
            }
        }
        return super.writeToNBT(compound);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        for(int i = 0; i < pixelGrid.length; i++) {
            for(int j = 0; j < pixelGrid[i].length; j++) {
                pixelGrid[i][j] = compound.getInteger(i + ":" + j);
            }
        }
    }
    
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        super.onDataPacket(net, packet);
        this.readFromNBT(packet.getNbtCompound());
    }
    
    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }
    
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
