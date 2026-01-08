package me.clefal.lootbeams.modules.beam;

import me.clefal.lootbeams.CommonClass;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.config.configs.LightConfig;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
//? <1.21.8 {
public class BeamRenderType extends RenderType {
//?} else {
/*public abstract class BeamRenderType extends RenderType {
    *///?}

    public static final ResourceLocation LOOT_BEAM_TEXTURE = CommonClass.id("textures/entity/loot_beam.png");
    public static final ResourceLocation WHITE_TEXTURE = CommonClass.id("textures/entity/white.png");
    public static final ResourceLocation GLOW_TEXTURE = CommonClass.id("textures/entity/glow.png");
    public static final RenderType LOOT_BEAM_RENDERTYPE = RenderType.lightning();
    protected static final RenderType GLOW = LightConfig.lightConfig.beam.solid_beam ? RenderType.entityTranslucentEmissive(GLOW_TEXTURE) : RenderType.entityCutout(GLOW_TEXTURE);
    //? <1.21.8 {
    public BeamRenderType(String $$0, VertexFormat $$1, VertexFormat.Mode $$2, int $$3, boolean $$4, boolean $$5, Runnable $$6, Runnable $$7) {
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
    }
//?} else {

    /*public BeamRenderType(String name, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }

*///?}
}
