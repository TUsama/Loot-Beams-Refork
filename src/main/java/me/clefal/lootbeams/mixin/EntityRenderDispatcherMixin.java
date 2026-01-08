package me.clefal.lootbeams.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.clefal.lootbeams.modules.Hooker;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
//? if =1.21.4
/*import net.minecraft.client.renderer.entity.state.EntityRenderState;*/

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? =1.20.1 || fabric {
/*@Mixin(value = EntityRenderDispatcher.class)
*///?} else {
@Mixin(value = EntityRenderDispatcher.class, remap = false)
//?}
public abstract class EntityRenderDispatcherMixin {


    //? if =1.21.4 {
    /*@Inject(
            method = "render(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;"
                    + "Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render"
                    + "(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;"
                    + "Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            shift = At.Shift.AFTER
    )
    )
    public void render(
            Entity entity,
            double xOffset,
            double yOffset,
            double zOffset,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            EntityRenderer<? super Entity, EntityRenderState> renderer,
            CallbackInfo ci
    ) {
        Hooker.lootBeamEntityDispatcherHook(entity, xOffset, yOffset, zOffset, 0.0f, partialTick, poseStack, bufferSource, packedLight, ci);
    }
    *///?}
    //? if <1.21.4 {
    /**
     * From Neat
     *
     */
    @Inject(
            method = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;render(Lnet/minecraft/world/entity/Entity;DDDFFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/world/entity/Entity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
                    shift = At.Shift.AFTER)
    )

    private void lootBeamHook(Entity entity, double worldX, double worldY, double worldZ, float entityYRot, float partialTicks, PoseStack poseStack, MultiBufferSource buffers, int light, CallbackInfo ci) {
        Hooker.lootBeamEntityDispatcherHook(entity, worldX, worldY, worldZ, entityYRot, partialTicks, poseStack, buffers, light, ci);


    }
    //?}

    //? if =1.21.8 {
    /*@Inject(
            method = "render(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;render(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;DDDLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V",
            shift = At.Shift.AFTER
    )
    )
    public void render(
            Entity entity, double xOffset, double yOffset, double zOffset, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, EntityRenderer<? super Entity, EntityRenderState> renderer, CallbackInfo ci, @Local EntityRenderState s
    ) {
        Hooker.lootBeamEntityDispatcherHookWithOffset(entity, xOffset, yOffset, zOffset, 0.0f, partialTick, poseStack, bufferSource, packedLight, renderer, ci, s);
    }
    *///?}
}
