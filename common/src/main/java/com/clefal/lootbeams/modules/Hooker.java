package com.clefal.lootbeams.modules;

import com.clefal.lootbeams.Constants;
import com.clefal.lootbeams.config.Config;
import com.clefal.lootbeams.config.ConfigurationManager;
import com.clefal.lootbeams.config.services.StringListHandler;
import com.clefal.lootbeams.data.equipment.EquipmentConditions;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.data.lbitementity.LBItemEntityCache;
import com.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
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


        if (lbItemEntity1.canBeRender() == LBItemEntity.RenderState.PASS || (checkRenderable(itemEntity, lbItemEntity1) && (!(ConfigurationManager.<Boolean>request(Config.REQUIRE_ON_GROUND)) || itemEntity.onGround()))) {
            if (ConfigurationManager.request(Config.ENABLE_BEAM)) {

                EntityRenderDispatcherHookEvent.RenderLootBeamEvent renderLootBeamEvent = new EntityRenderDispatcherHookEvent.RenderLootBeamEvent(lbItemEntity1, worldX, worldY, worldZ, entityYRot, partialTicks, poseStack, buffers, light);
                Constants.EVENT_BUS.post(renderLootBeamEvent);
            }

            TooltipsEnableStatus.TooltipsStatus request = ConfigurationManager.request(Config.ENABLE_TOOLTIPS);
            if (request != TooltipsEnableStatus.TooltipsStatus.NONE) {
                EntityRenderDispatcherHookEvent.RenderLBTooltipsEvent renderLBTooltipsEvent = new EntityRenderDispatcherHookEvent.RenderLBTooltipsEvent(lbItemEntity1, worldX, worldY, worldZ, entityYRot, partialTicks, poseStack, buffers, light);
                Constants.EVENT_BUS.post(renderLBTooltipsEvent);
            }
            lbItemEntity1.updateCanBeRender();
        }
    }

    private static @NotNull Boolean checkRenderable(ItemEntity itemEntity, LBItemEntity lbItemEntity1) {
        boolean onlyEquipment = ConfigurationManager.<Boolean>request(Config.ONLY_EQUIPMENT) && EquipmentConditions.isEquipment(itemEntity.getItem());
        boolean isRare = ConfigurationManager.<Boolean>request(Config.ONLY_RARE) && lbItemEntity1.isRare();
        return ConfigurationManager.<Boolean>request(Config.ALL_ITEMS)
                || onlyEquipment
                || isRare
                || (StringListHandler.RenderList.checkInWhiteList(lbItemEntity1)
                && !StringListHandler.RenderList.checkInBlackList(lbItemEntity1));
    }
}
