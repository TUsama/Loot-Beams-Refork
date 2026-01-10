package me.clefal.lootbeams.events;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.Event;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.ICancellableEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.modules.Holder;

public abstract class EntityRenderDispatcherHookEvent extends Event {
    public LBItemEntity LBItemEntity;
    public double worldX;
    public double worldY;
    public double worldZ;
    public float entityYRot;
    public float partialTicks;
    public PoseStack poseStack;
    public Holder holder;

    public int light;


    public EntityRenderDispatcherHookEvent(LBItemEntity LBItemEntity, double worldX, double worldY, double worldZ, float entityYRot, float partialTicks, PoseStack poseStack, Holder holder, int light) {

        this.LBItemEntity = LBItemEntity;
        this.worldX = worldX;
        this.worldY = worldY;
        this.worldZ = worldZ;
        this.entityYRot = entityYRot;
        this.partialTicks = partialTicks;
        this.poseStack = poseStack;
        this.holder = holder;
        this.light = light;
    }

    public static class RenderLBTooltipsEvent extends EntityRenderDispatcherHookEvent implements ICancellableEvent {

        public RenderLBTooltipsEvent(LBItemEntity LBItemEntity, double worldX, double worldY, double worldZ, float entityYRot, float partialTicks, PoseStack poseStack, Holder holder, int light) {
            super(LBItemEntity, worldX, worldY, worldZ, entityYRot, partialTicks, poseStack, holder, light);
        }
    }

    public static class RenderLootBeamEvent extends EntityRenderDispatcherHookEvent implements ICancellableEvent {
        public RenderLootBeamEvent(LBItemEntity LBItemEntity, double worldX, double worldY, double worldZ, float entityYRot, float partialTicks, PoseStack poseStack, Holder holder, int light) {
            super(LBItemEntity, worldX, worldY, worldZ, entityYRot, partialTicks, poseStack, holder, light);
        }
    }
}

