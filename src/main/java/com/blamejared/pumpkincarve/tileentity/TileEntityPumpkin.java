package com.blamejared.pumpkincarve.tileentity;

import com.blamejared.pumpkincarve.network.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TileEntityPumpkin extends TileEntity implements ITickable {
    
    public int[][] pixelMap = genArray();
    
    public TileEntityPumpkin() {
    
    }
    
    boolean sent = false;
    int count = 60;
    
    @Override
    public void update() {
        if(!sent && count-- < 0){
            sent = true;
            markDirty();
        }
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
        pixelMap = genArray();
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
    
    
    @Override
    public void markDirty() {
        super.markDirty();
        if(!world.isRemote)
            PacketHandler.INSTANCE.sendToAllAround(new MessagePumpkinSyncClient(getPos(), pixelMap), new NetworkRegistry.TargetPoint(getWorld().provider.getDimension(), getPos().getX(), getPos().getY(), getPos().getZ(), 128D));
        
    }
    
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        
        return oldState.getBlock() != newState.getBlock();
    }
    
    
}
