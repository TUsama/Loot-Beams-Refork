package com.clefal.lootbeams.modules.beam;

import com.clefal.lootbeams.Constants;
import com.clefal.lootbeams.config.Config;
import com.clefal.lootbeams.config.ConfigurationManager;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BeamRenderType {

    public static final ResourceLocation LOOT_BEAM_TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/loot_beam.png");
    public static final ResourceLocation WHITE_TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/white.png");
    public static final ResourceLocation GLOW_TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/glow.png");
    protected static final RenderType GLOW = ConfigurationManager.<Boolean>request(Config.SOLID_BEAM) ? RenderType.entityTranslucentEmissive(GLOW_TEXTURE) : RenderType.entityCutout(GLOW_TEXTURE);
    public static final RenderType LOOT_BEAM_RENDERTYPE = ConfigurationManager.<Boolean>request(Config.SOLID_BEAM) ? RenderType.lightning() : createRenderType();


    public static RenderType createRenderType() {
        ResourceLocation texture = !ConfigurationManager.<Boolean>request(Config.SOLID_BEAM) ? LOOT_BEAM_TEXTURE : WHITE_TEXTURE;
        RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_BEACON_BEAM_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                .setDepthTestState(new RenderStateShard.DepthTestStateShard("lb_always", GL11.GL_ALWAYS))
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .createCompositeState(false);

        return RenderType.create("loot_beam", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 256, false, true, state);
    }
}
