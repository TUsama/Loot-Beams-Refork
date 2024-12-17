package com.clefal.lootbeams.modules.sound;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.config.configs.SoundConfig;
import com.clefal.lootbeams.data.equipment.EquipmentConditions;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
import com.clefal.lootbeams.modules.ILBModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;

public class SoundModule implements ILBModule {

    public static final SoundModule INSTANCE = new SoundModule();

    private static boolean canSound(EntityRenderDispatcherHookEvent.RenderLootBeamEvent event, ItemEntity itemEntity) {
        if (SoundConfigHandler.checkInBlackList(event.LBItemEntity)) return false;
        if (SoundConfig.soundConfig.soundSection.sound_all_items || SoundConfigHandler.checkInWhiteList(event.LBItemEntity)) return true;
        boolean equipmentCondition = SoundConfig.soundConfig.soundSection.sound_only_equipment;
        boolean isEquipment = EquipmentConditions.isEquipment(itemEntity.getItem());
        boolean rareCondition = SoundConfig.soundConfig.soundSection.sound_only_rare;
        boolean isRare = event.LBItemEntity.shouldPlayRareSound();
        if(equipmentCondition){
            if(isEquipment){
                if(rareCondition){
                    return isRare;
                } else {
                    return true;
                }
            } else{
                return false;
            }
        } else {
            if(rareCondition){
                return isRare;
            } else {
                return false;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEnableModule(EntityRenderDispatcherHookEvent.RenderLootBeamEvent event) {
        if (event.LBItemEntity.isSounded()) return;
        ItemEntity itemEntity = event.LBItemEntity.item();

        if (event.LBItemEntity.canBeRender() == LBItemEntity.RenderState.REJECT) return;

        if (event.LBItemEntity.canBeRender() == LBItemEntity.RenderState.PASS && canSound(event, itemEntity))
        {

            WeighedSoundEvents sound = Minecraft.getInstance().getSoundManager().getSoundEvent(LootBeamsConstants.LOOT_DROP);

            if (sound != null && Minecraft.getInstance().level != null) {
                Minecraft.getInstance().level.playSound(Minecraft.getInstance().player, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), SoundEvent.createFixedRangeEvent(LootBeamsConstants.LOOT_DROP, 8.0f), SoundSource.AMBIENT, 0.1f * SoundConfig.soundConfig.soundSection.sound_volume.get(), 1.0f);
                event.LBItemEntity.updateSounded();
            }
        }
    }

    @Override
    public void tryEnable() {
        if (SoundConfig.soundConfig.soundSection.enable_sound) {
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }
}
