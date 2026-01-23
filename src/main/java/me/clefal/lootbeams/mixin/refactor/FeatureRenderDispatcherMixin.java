//? >= 1.21.10 {
/*package me.clefal.lootbeams.mixin.refactor;

import com.llamalad7.mixinextras.sugar.Local;
import me.clefal.lootbeams.data.new_render.LootBeamRenderState;
import me.clefal.lootbeams.duck.LootBeamRenderStateStorage;
import me.clefal.lootbeams.duck.LootBeamRenderStateSubmitter;
import me.clefal.lootbeams.modules.beam.BeamRenderer;
import me.clefal.lootbeams.modules.tooltip.nametag.NameTagRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FeatureRenderDispatcher.class)
public class FeatureRenderDispatcherMixin {

    @Shadow
    @Final
    private MultiBufferSource.BufferSource bufferSource;

    @Inject(
            method = "renderAllFeatures", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/feature/ParticleFeatureRenderer;render(Lnet/minecraft/client/renderer/SubmitNodeCollection;)V",
            shift = At.Shift.AFTER

    ))
    private void doLBRender(CallbackInfo ci, @Local SubmitNodeCollection submitnodecollection){
        LootBeamRenderStateStorage lbSubmitter = (LootBeamRenderStateStorage) submitnodecollection;
        for (LootBeamRenderState.BeamRenderState lootBeamRenderState : lbSubmitter.loot_Beams_Refork$getBeamRenderStates()) {
            BeamRenderer.INSTANCE.renderLootBeam(this.bufferSource, lootBeamRenderState);
        }

        for (LootBeamRenderState.NameTagRenderState lootBeamsRefork$getNameTagRenderState : lbSubmitter.loot_Beams_Refork$getNameTagRenderStates()) {
            NameTagRenderer.renderNameTag(this.bufferSource, lootBeamsRefork$getNameTagRenderState);
        }
    }
}
*///? }