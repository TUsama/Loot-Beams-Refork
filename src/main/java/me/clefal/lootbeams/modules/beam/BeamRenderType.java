package me.clefal.lootbeams.modules.beam;

//? <1.21.4
import com.clefal.nirvana_lib.client.render.rendertype.RenderTypeCreator;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import me.clefal.lootbeams.CommonClass;
import me.clefal.lootbeams.config.configs.LightConfig;
import com.mojang.blaze3d.vertex.VertexFormat;


import net.minecraft.Util;
import net.minecraft.client.renderer.GameRenderer;
//? >=1.21.10
//import net.minecraft.client.renderer.RenderPipelines;
//? <1.21.11
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
//? >= 1.21.11 {
/*import net.minecraft.client.renderer.RenderTypes;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.RenderSetup;
*///?}

import java.util.function.Function;

//? <1.21.8 {
public class BeamRenderType extends RenderType {
public BeamRenderType(String $$0, VertexFormat $$1, VertexFormat.Mode $$2, int $$3, boolean $$4, boolean $$5, Runnable $$6, Runnable $$7) {
    super($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
}
//?} elif 1.21.10 {
/*public abstract class BeamRenderType extends RenderType {
    public BeamRenderType(String name, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }
    *///?} else {
    /*public abstract class BeamRenderType {
    *///?}

    public static final ResourceLocation LOOT_BEAM_TEXTURE = CommonClass.id("textures/entity/loot_beam.png");
    public static final ResourceLocation WHITE_TEXTURE = CommonClass.id("textures/entity/white.png");
    public static final ResourceLocation GLOW_TEXTURE = CommonClass.id("textures/entity/glow.png");
    //public static final RenderType LOOT_BEAM_RENDERTYPE = RenderType.lightning();
    //protected static final RenderType GLOW = LightConfig.lightConfig.beam.solid_beam ? RenderType.entityTranslucentEmissive(GLOW_TEXTURE) : RenderType.entityCutout(GLOW_TEXTURE);
    //? <1.21.4 {
    public static final Function<ResourceLocation, RenderType> beamOnShader = Util.memoize(location -> RenderTypeCreator.createRenderType("lb_beam", DefaultVertexFormat.PARTICLE, VertexFormat.Mode.QUADS, 256, false, true, CompositeState.builder()
            .setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getParticleShader))
            .setTextureState(new RenderStateShard.TextureStateShard(location, false, false))
            .setTransparencyState(TransparencyStateShard.TRANSLUCENT_TRANSPARENCY)
            .setDepthTestState(new RenderStateShard.DepthTestStateShard("lb_depth", 515)).createCompositeState(false)));
    //?}

    //? >=1.21.10 {
    /*private static final Function<ResourceLocation, RenderType> beamOnShader = Util.memoize(location -> {
        //? >=1.21.11{
        /^RenderSetup rendersetup = RenderSetup.builder(RenderPipelines.TRANSLUCENT_PARTICLE)
                .withTexture("Sampler0", location)
                .useLightmap()
                .useOverlay()
                .affectsCrumbling()
                .sortOnUpload()
                .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                .createRenderSetup();
        return RenderType.create("lb_beam", rendersetup);

        ^///? } else {
        CompositeState compositeState = CompositeState.builder()
                .setTextureState(new TextureStateShard(location, false))
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .setLayeringState(VIEW_OFFSET_Z_LAYERING).createCompositeState(true);
        return create("translucent_beam", 1536, true, true, RenderPipelines.TRANSLUCENT_PARTICLE, compositeState);
        //?}
    });
    *///?}



    public static RenderType getBeamRendertype(ResourceLocation location, boolean isShaderOn){

        if (isShaderOn){
            //? <1.21.4 || >=1.21.10{
            return beamOnShader.apply(location);
            //? } else {
            /*return RenderType.translucentParticle(location);
            *///?}
        } else {
            //? < 1.21.11 {
            return RenderType.beaconBeam(location, true);
            //? } else
            //return RenderTypes.beaconBeam(location, true);
        }

    }
}
