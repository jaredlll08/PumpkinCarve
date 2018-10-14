package com.blamejared.pumpkincarve.client;

import com.blamejared.pumpkincarve.gui.GuiCarve;
import com.blamejared.pumpkincarve.reference.Reference;
import com.blamejared.pumpkincarve.tileentity.TileEntityPumpkin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderPumpkin extends TileEntitySpecialRenderer<TileEntityPumpkin> {
    
    
    @Override
    public void render(TileEntityPumpkin te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0, 0, -0.0001);
        BufferBuilder b = Tessellator.getInstance().getBuffer();
        GlStateManager.disableTexture2D();
        GlStateManager.color(1, 1, 1, 1f);
        GlStateManager.disableLighting();
        bindTexture(new ResourceLocation(Reference.MODID, "textures/pixel.png"));
        
        int baseLight = Minecraft.getMinecraft().world.getLight(te.getPos());//, te.getWorld().getLight(te,g));
        float size = 0.0625f;
        
        for(int xx = 1; xx <= te.getPixelGrid().length; xx++) {
            for(int yy = 1; yy <= te.getPixelGrid()[xx - 1].length; yy++) {
                float[] rgba = GuiCarve.getRGBA(te.getPixelGrid()[xx - 1][yy - 1]);
                if(rgba[3] == 0) {
                    continue;
                }
                b.begin(7, DefaultVertexFormats.BLOCK);
                int l2 = baseLight & 0xFFFF;
                int l1 = baseLight >> 0x10 & 0xFFFF;
                b.pos(1 - (xx / 16f), 1 - (yy / 16f) + size, 0).color(rgba[0], rgba[1], rgba[2], rgba[3]).tex(0, 1).lightmap(l1, l2).endVertex();
                b.pos(1 - (xx / 16f) + size, 1 - (yy / 16f) + size, 0).color(rgba[0], rgba[1], rgba[2], rgba[3]).tex(1, 1).lightmap(l1, l2).endVertex();
                b.pos(1 - (xx / 16f) + size, 1 - (yy / 16f), 0).color(rgba[0], rgba[1], rgba[2], rgba[3]).tex(1, 0).lightmap(l1, l2).endVertex();
                b.pos(1 - (xx / 16f), 1 - (yy / 16f), 0).color(rgba[0], rgba[1], rgba[2], rgba[3]).tex(0, 0).lightmap(l1, l2).endVertex();
                
                int light = te.getWorld().getBlockState(te.getPos()).getPackedLightmapCoords(te.getWorld(), te.getPos().north());
                b.putBrightness4(light, light, light, light);
                Tessellator.getInstance().draw();
            }
        }
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.translate(-x, -y, -z);
        GlStateManager.popMatrix();
    }
}
