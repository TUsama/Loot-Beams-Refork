package com.clefal.lootbeams.modules;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.config.configs.LightConfig;
import com.clefal.lootbeams.config.configs.TooltipsConfig;
import com.clefal.lootbeams.data.equipment.EquipmentConditions;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.data.lbitementity.LBItemEntityCache;
import com.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
import com.clefal.lootbeams.modules.beam.LightConfigHandler;
import com.clefal.lootbeams.modules.tooltip.TooltipsEnableStatus;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Hooker {

    public static void lootBeamEntityDispatcherHook(Entity entity, double worldX, double worldY, double worldZ, float entityYRot, float partialTicks, PoseStack poseStack, MultiBufferSource buffers, int light, CallbackInfo ci) {
        if (!(entity instanceof ItemEntity itemEntity)) return;

        LBItemEntity lbItemEntity1 = LBItemEntityCache.ask(itemEntity);
        if (lbItemEntity1.canBeRender() == LBItemEntity.RenderState.REJECT) return;
        LightConfig.Beam beamSection = LightConfig.lightConfig.beamSection;


        if (lbItemEntity1.canBeRender() == LBItemEntity.RenderState.PASS || (checkRenderable(itemEntity, lbItemEntity1) && (!(beamSection.require_on_ground) || itemEntity.onGround()))) {
            if (beamSection.enable_beam) {

                EntityRenderDispatcherHookEvent.RenderLootBeamEvent renderLootBeamEvent = new EntityRenderDispatcherHookEvent.RenderLootBeamEvent(lbItemEntity1, worldX, worldY, worldZ, entityYRot, partialTicks, poseStack, buffers, light);
                LootBeamsConstants.EVENT_BUS.post(renderLootBeamEvent);
            }

            var tooltipsConfig = TooltipsConfig.tooltipsConfig.tooltips_enable_status;
            if (tooltipsConfig != TooltipsEnableStatus.TooltipsStatus.NONE) {
                EntityRenderDispatcherHookEvent.RenderLBTooltipsEvent renderLBTooltipsEvent = new EntityRenderDispatcherHookEvent.RenderLBTooltipsEvent(lbItemEntity1, worldX, worldY, worldZ, entityYRot, partialTicks, poseStack, buffers, light);
                LootBeamsConstants.EVENT_BUS.post(renderLBTooltipsEvent);
            }
            lbItemEntity1.passThis();
        } else {
            lbItemEntity1.rejectThis();
        }
    }

    private static @NotNull Boolean checkRenderable(ItemEntity itemEntity, LBItemEntity lbItemEntity1) {
        var filter = LightConfig.lightConfig.lightEffectFilter;
        boolean onlyEquipment = filter.only_equipment && EquipmentConditions.isEquipment(itemEntity.getItem());
        boolean isRare = filter.only_rare && lbItemEntity1.isRare();
        return filter.all_item
                || onlyEquipment
                || isRare
                || (LightConfigHandler.checkInWhiteList(lbItemEntity1)
                && !LightConfigHandler.checkInBlackList(lbItemEntity1));
    }
}
