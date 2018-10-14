/*
 * Copyright (c) 2018. Jared Luboff. All Rights Reserved.
 */

package com.blamejared.pumpkincarve.network;


import com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class MessageSetPx implements IMessage, IMessageHandler<MessageSetPx, IMessage> {
    
    private BlockPos pos;
    
    private int x;
    private int y;
    private int colour;
    
    public MessageSetPx() {
    
    }
    
    public MessageSetPx(BlockPos pos, int x, int y, int colour) {
        this.pos = pos;
        this.x = x;
        this.y = y;
        this.colour = colour;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = BlockPos.fromLong(buf.readLong());
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.colour = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.pos.toLong());
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.colour);
    }
    
    @Override
    public IMessage onMessage(MessageSetPx message, MessageContext ctx) {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> handle(message, ctx));
        return null;
    }
    
    public void handle(MessageSetPx message, MessageContext ctx) {
        World world = ctx.getServerHandler().player.world;
        TileEntity tileEntity = world.getTileEntity(message.pos);
        if(tileEntity instanceof TileEntityPumpkin) {
            TileEntityPumpkin tile = (TileEntityPumpkin) tileEntity;
            tile.setPixel(message.x, message.y, message.colour);
            tile.markDirty();
        }
        
    }
}