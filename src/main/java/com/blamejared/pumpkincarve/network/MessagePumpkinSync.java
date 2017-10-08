package com.blamejared.pumpkincarve.network;

import com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class MessagePumpkinSync implements IMessage, IMessageHandler<MessagePumpkinSync, IMessage> {
    
    
    public int[][] pixelMap;
    public BlockPos pos;
    
    public MessagePumpkinSync() {
    }
    
    public MessagePumpkinSync(BlockPos pos, int[][] pixelMap) {
        this.pos = pos;
        this.pixelMap = pixelMap;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        pixelMap = TileEntityPumpkin.genArray();
        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++) {
                pixelMap[x][y] = buf.readInt();
            }
        }
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++) {
                buf.writeInt(pixelMap[x][y]);
            }
        }
    }
    
    @Override
    public IMessage onMessage(MessagePumpkinSync message, MessageContext ctx) {
        TileEntity tileEntity = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().getTileEntity(message.pos);
        if(tileEntity instanceof TileEntityPumpkin) {
            TileEntityPumpkin tile = (TileEntityPumpkin) tileEntity;
            tile.pixelMap = TileEntityPumpkin.genArray();
            for(int x = 0; x < 16; x++) {
                for(int y = 0; y < 16; y++) {
                    if(message.pixelMap[x][y] != 0)
                        tile.pixelMap[x][y] = message.pixelMap[x][y];
                }
            }
            
        }
        return null;
    }
}
