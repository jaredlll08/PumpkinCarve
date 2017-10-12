package com.blamejared.pumpkincarve.network;

import com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class MessagePumpkinSyncClient implements IMessage, IMessageHandler<MessagePumpkinSyncClient, IMessage> {
    
    
    public int[][] pixelMap;
    public BlockPos pos;
    
    public MessagePumpkinSyncClient() {
    }
    
    public MessagePumpkinSyncClient(BlockPos pos, int[][] pixelMap) {
        this.pos = pos;
        this.pixelMap = pixelMap;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        pixelMap = TileEntityPumpkin.genArray();
        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++) {
                int i = buf.readInt();
                if(i == 0) {
                    i = 0xFFFFFF;
                }
                pixelMap[x][y] = i;
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
    public IMessage onMessage(MessagePumpkinSyncClient message, MessageContext ctx) {
        TileEntity tileEntity = FMLClientHandler.instance().getClient().world.getTileEntity(message.pos);
        if(tileEntity instanceof TileEntityPumpkin) {
            TileEntityPumpkin tile = (TileEntityPumpkin) tileEntity;
            tile.pixelMap = message.pixelMap;
        }
        return null;
    }
}
