package me.clefal.lootbeams.modules;

import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.config.configs.LightConfig;
import me.clefal.lootbeams.config.configs.LootInfomationConfig;
import me.clefal.lootbeams.config.persistent.EquipmentConditions;
import me.clefal.lootbeams.config.persistent.WhitelistCondition;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.LBItemEntityCache;
import me.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
import me.clefal.lootbeams.modules.beam.LightConfigHandler;
import me.clefal.lootbeams.modules.tooltip.LootInformationEnableStatus;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Hooker {

    public static void lootBeamEntityDispatcherHook(Entity entity, double worldX, double worldY, double worldZ, float entityYRot, float partialTicks, PoseStack poseStack, MultiBufferSource buffers, int light, CallbackInfo ci) {
        if (!(entity instanceof ItemEntity itemEntity)) return;

        LBItemEntity lbItemEntity1 = LBItemEntityCache.ask(itemEntity);

        if (LootInfomationConfig.lootInfomationConfig.lootInformationControl.showInfoForAllItem){
            renderLootInformation(worldX, worldY, worldZ, entityYRot, partialTicks, poseStack, buffers, light, lbItemEntity1);
        }

        if (lbItemEntity1.canBeRender() == LBItemEntity.RenderState.REJECT) return;
        LightConfig.Beam beamSection = LightConfig.lightConfig.beam;

        var OnGroundCondition = (!beamSection.require_on_ground || itemEntity.onGround()) || itemEntity.isInWater();

        if (lbItemEntity1.canBeRender() == LBItemEntity.RenderState.PASS || checkRenderable(lbItemEntity1) && OnGroundCondition) {
            if (beamSection.enable_beam) {
                EntityRenderDispatcherHookEvent.RenderLootBeamEvent renderLootBeamEvent = new EntityRenderDispatcherHookEvent.RenderLootBeamEvent(lbItemEntity1, worldX, worldY, worldZ, entityYRot, partialTicks, poseStack, buffers, light);
                LootBeamsConstants.EVENT_BUS.post(renderLootBeamEvent);
            }

            if (!LootInfomationConfig.lootInfomationConfig.lootInformationControl.showInfoForAllItem){
                renderLootInformation(worldX, worldY, worldZ, entityYRot, partialTicks, poseStack, buffers, light, lbItemEntity1);
            }

            lbItemEntity1.passThis();
        } else {
            if (OnGroundCondition) lbItemEntity1.rejectThis();
        }
    }

    private static void renderLootInformation(double worldX, double worldY, double worldZ, float entityYRot, float partialTicks, PoseStack poseStack, MultiBufferSource buffers, int light, LBItemEntity lbItemEntity1) {
        var tooltipsConfig = LootInfomationConfig.lootInfomationConfig.lootInformationControl.loot_information_status;
        if (tooltipsConfig != LootInformationEnableStatus.LootInformationStatus.NONE) {
            EntityRenderDispatcherHookEvent.RenderLBTooltipsEvent renderLBTooltipsEvent = new EntityRenderDispatcherHookEvent.RenderLBTooltipsEvent(lbItemEntity1, worldX, worldY, worldZ, entityYRot, partialTicks, poseStack, buffers, light);
            LootBeamsConstants.EVENT_BUS.post(renderLBTooltipsEvent);
        }
    }

    private static boolean checkRenderable(LBItemEntity lbItemEntity1) {
        var filter = LightConfig.lightConfig.lightEffectFilter;
        if (LightConfigHandler.checkInBlackList(lbItemEntity1)) return false;
        if (filter.all_item || LightConfigHandler.checkInWhiteList(lbItemEntity1) || lbItemEntity1.rarity().context().hasBeenModified()) return true;
        if (WhitelistCondition.isInSpecialWhitelist(lbItemEntity1)) return true;
        boolean equipmentCondition = filter.only_equipment;

        if (equipmentCondition) {
            boolean isEquipment = EquipmentConditions.isEquipment(lbItemEntity1);
            if (isEquipment) {
                boolean rareCondition = filter.only_rare;
                if (rareCondition) {
                    boolean isRare = lbItemEntity1.isRare();
                    return isRare;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            boolean rareCondition = filter.only_rare;
            if (rareCondition) {
                boolean isRare = lbItemEntity1.isRare();
                return isRare;
            } else {
                return false;
            }
        }
    }


}
