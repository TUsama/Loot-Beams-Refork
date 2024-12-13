package com.clefal.lootbeams.modules.beam;

import com.clefal.lootbeams.Constants;
import com.clefal.lootbeams.config.Config;
import com.clefal.lootbeams.config.ConfigurationManager;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class BeamRenderType extends RenderType {

    public static final ResourceLocation LOOT_BEAM_TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/loot_beam.png");
    public static final ResourceLocation WHITE_TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/white.png");
    public static final ResourceLocation GLOW_TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/glow.png");
    public static final RenderType LOOT_BEAM_RENDERTYPE = RenderType.lightning();
    protected static final RenderType GLOW = ConfigurationManager.<Boolean>request(Config.SOLID_BEAM) ? RenderType.entityTranslucentEmissive(GLOW_TEXTURE) : RenderType.entityCutout(GLOW_TEXTURE);

    public BeamRenderType(String $$0, VertexFormat $$1, VertexFormat.Mode $$2, int $$3, boolean $$4, boolean $$5, Runnable $$6, Runnable $$7) {
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
    }


}
