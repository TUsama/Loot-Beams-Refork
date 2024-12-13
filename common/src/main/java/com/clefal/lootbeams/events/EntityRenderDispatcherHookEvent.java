package com.clefal.lootbeams.events;

import com.clefal.lootbeams.data.LBItemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

public abstract class EntityRenderDispatcherHookEvent extends Event {
    public LBItemEntity LBItemEntity;
    public double worldX;
    public double worldY;
    public double worldZ;
    public float entityYRot;
    public float partialTicks;
    public PoseStack poseStack;
    public MultiBufferSource buffers;
    public int light;


    public EntityRenderDispatcherHookEvent(LBItemEntity LBItemEntity, double worldX, double worldY, double worldZ, float entityYRot, float partialTicks, PoseStack poseStack, MultiBufferSource buffers, int light) {
        this.LBItemEntity = LBItemEntity;
        this.worldX = worldX;
        this.worldY = worldY;
        this.worldZ = worldZ;
        this.entityYRot = entityYRot;
        this.partialTicks = partialTicks;
        this.poseStack = poseStack;
        this.buffers = buffers;
        this.light = light;
    }

    public static class RenderLBTooltipsEvent extends EntityRenderDispatcherHookEvent implements ICancellableEvent {

        public RenderLBTooltipsEvent(LBItemEntity LBItemEntity, double worldX, double worldY, double worldZ, float entityYRot, float partialTicks, PoseStack poseStack, MultiBufferSource buffers, int light) {
            super(LBItemEntity, worldX, worldY, worldZ, entityYRot, partialTicks, poseStack, buffers, light);
        }
    }

    public static class RenderLootBeamEvent extends EntityRenderDispatcherHookEvent implements ICancellableEvent {
        public RenderLootBeamEvent(LBItemEntity LBItemEntity, double worldX, double worldY, double worldZ, float entityYRot, float partialTicks, PoseStack poseStack, MultiBufferSource buffers, int light) {
            super(LBItemEntity, worldX, worldY, worldZ, entityYRot, partialTicks, poseStack, buffers, light);
        }
    }
}
