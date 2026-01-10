//? >= 1.21.10 {
/*package me.clefal.lootbeams.mixin.refactor;

import com.clefal.nirvana_lib.relocated.io.vavr.Tuple;
import com.clefal.nirvana_lib.relocated.io.vavr.Tuple3;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.lootbeams.modules.Hooker;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Shadow
    @Final
    private EntityRenderDispatcher entityRenderDispatcher;

    @Shadow
    protected abstract EntityRenderState extractEntity(Entity entity, float partialTick);

    private final List<Tuple3<ItemEntity, Float, Vec3>> retainEntities = new ArrayList<>();
    @Inject(
            method = "extractVisibleEntities", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/LevelRenderer;extractEntity(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/client/renderer/entity/state/EntityRenderState;",
            shift = At.Shift.AFTER
    ))
    private void grabEntity(Camera camera, Frustum frustum, DeltaTracker deltaTracker, LevelRenderState renderState, CallbackInfo ci, @Local Entity entity, @Local float f){
        if (entity instanceof ItemEntity item) {
            var x = Mth.lerp((double)f, entity.xOld, entity.getX());
            var y = Mth.lerp((double)f, entity.yOld, entity.getY());
            var z = Mth.lerp((double)f, entity.zOld, entity.getZ());
            this.retainEntities.add(Tuple.of(item, deltaTracker.getGameTimeDeltaTicks(), new Vec3(x, y, z)));
        }
    }

    @Inject(
            method = "submitEntities", at = @At(
            value = "RETURN"

    ))
    private void submitEntity(PoseStack poseStack, LevelRenderState renderState, SubmitNodeCollector nodeCollector, CallbackInfo ci){
        for (var tuple : this.retainEntities) {
            Hooker.handleTuple(poseStack, renderState, nodeCollector, ci, tuple, this.entityRenderDispatcher);
        }
        this.retainEntities.clear();
    }

}
*///? }