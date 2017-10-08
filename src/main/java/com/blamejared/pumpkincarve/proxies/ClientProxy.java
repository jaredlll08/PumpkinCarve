package com.blamejared.pumpkincarve.proxies;

import com.blamejared.pumpkincarve.blocks.*;
import com.blamejared.pumpkincarve.client.gui.GuiCarving;
import com.blamejared.pumpkincarve.client.render.RenderCarvedPumpkin;
import com.blamejared.pumpkincarve.events.ClientEventHandler;
import com.blamejared.pumpkincarve.reference.Reference;
import com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void registerEvents() {
        super.registerEvents();
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }
    
    @Override
    public void openCarvingGUI(TileEntityPumpkin tile) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiCarving(tile));
    }
    
    @Override
    public void registerRenders() {
        super.registerRenders();
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        renderItem.getItemModelMesher().register(Item.getItemFromBlock(PCBlocks.PUMPKIN_CARVED), 0, new ModelResourceLocation(Reference.MODID + ":" + PCBlocks.PUMPKIN_CARVED.getRegistryName(), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPumpkin.class, new RenderCarvedPumpkin());
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                switch(state.getValue(BlockPixel.COLOUR)) {
                    case 0:
                        return 0xFF441300;
                    case 1:
                        return 0xFF2d0003;
                    default:
                        return 0xFF55FF55;
                }
            }
        }, PCBlocks.PIXEL);
    }
}
