package com.blamejared.pumpkincarve.client.render;

import com.blamejared.pumpkincarve.blocks.*;
import com.blamejared.pumpkincarve.reference.Reference;
import com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class RenderCarvedPumpkin extends TileEntitySpecialRenderer<TileEntityPumpkin> {
    
    private final int size = 1 / 16;
    
    public RenderCarvedPumpkin() {
    }
    
    @Override
    public void func_192841_a(TileEntityPumpkin tile, double x, double y, double z, float partialTicks, int destroyStage, float partial) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glTranslatef((float) x + 0.5f, (float) y + 1f, (float) z + 0.5f);
        GL11.glRotatef(180f, 1f, 0f, 0f);
        
        for(int px = 0; px < tile.pixelMap.length; px++) {
            for(int py = 0; py < tile.pixelMap[px].length; py++) {
                int color = tile.pixelMap[px][py];
                float a = (float) (color >> 24 & 255) / 255.0F;
                double xOff = px * 0.0625;
                double yOff = py * 0.0625;
                int blockColour = 0;
                switch(color) {
                    case 0xFF441300:
                        blockColour = 0;
                        break;
                    case 0xFF2d0003:
                        blockColour = 1;
                        break;
                }
                if(a != 0)
                    renderBlockModel(tile.getWorld(), BlockPos.ORIGIN, PCBlocks.PIXEL.getDefaultState().withProperty(BlockPixel.COLOUR, blockColour), true, -0.5 - 0.0001 + xOff, -1 + yOff, -0.5 - 0.0001);
                //                        drawRect(-0.5 - 0.0001 + xOff, 0 + yOff, -0.5 - 0.0001, 0.0625, 0.0625, r, g, b, a, 16);
            }
        }
        GL11.glPopAttrib();
        GL11.glPopMatrix();
        
    }
    
    
    public static void renderBlockModel(World world, BlockPos pos, IBlockState state, boolean translateToOrigin, double xOff, double yOff, double zOff) {
    
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        BufferBuilder wr = Tessellator.getInstance().getBuffer();
        wr.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        if(translateToOrigin) {
            wr.setTranslation(-pos.getX() + xOff, -pos.getY() + yOff + 0.0625, -pos.getZ() - (0.0625 / 2) + zOff);
        }
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        BlockModelShapes modelShapes = blockrendererdispatcher.getBlockModelShapes();
        IBakedModel ibakedmodel = modelShapes.getModelForState(state);
        final IBlockAccess worldWrapper = world;
        for(BlockRenderLayer layer : BlockRenderLayer.values()) {
            if(state.getBlock().canRenderInLayer(state, layer)) {
                ForgeHooksClient.setRenderLayer(layer);
                blockrendererdispatcher.getBlockModelRenderer().renderModel(worldWrapper, ibakedmodel, state, pos, wr, false);
            }
        }
        ForgeHooksClient.setRenderLayer(null);
        if(translateToOrigin) {
            wr.setTranslation(0, 0, 0);
        }
        Tessellator.getInstance().draw();
    }
    
    /**
     * Draws a 3D rectange
     *
     * @param x         startX
     * @param y         startY
     * @param z         startZ
     * @param width     width
     * @param height    height
     * @param red       redColour
     * @param green     greenColour
     * @param blue      blueColour
     * @param lineWidth lineWidth
     */
    public static void drawRect(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha, float lineWidth) {
        double left = x;
        double right = x + width;
        double top = y;
        double bottom = y + height;
        if(left < right) {
            double i = left;
            left = right;
            right = i;
        }
        
        if(top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GL11.glPushMatrix();
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_ALPHA_TEST);
        glDisable(GL_CULL_FACE);
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(left, bottom, z).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(right, bottom, z).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(right, top, z).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(left, top, z).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        glPopAttrib();
        GL11.glPopMatrix();
    }
    
}
