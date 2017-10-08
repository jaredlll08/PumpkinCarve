package com.blamejared.pumpkincarve.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPumpkin extends TileEntity {
    
    public int[][] pixelMap = genArray();
    
    public TileEntityPumpkin() {
    
    }
    
    public static int[][] genArray() {
        int[][] pixelMap = new int[16][16];
        for(int i = 0; i < pixelMap.length; i++) {
            for(int i1 = 0; i1 < pixelMap[i].length; i1++) {
                pixelMap[i][i1] = 0xFFFFFF;
            }
        }
        return pixelMap;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++) {
                pixelMap[x][y] = compound.getInteger(x + ":" + y);
            }
        }
        super.readFromNBT(compound);
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++) {
                compound.setInteger(x + ":" + y, pixelMap[x][y]);
            }
        }
        return super.writeToNBT(compound);
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }
    
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new SPacketUpdateTileEntity(getPos(), 0, tag);
    }
}
