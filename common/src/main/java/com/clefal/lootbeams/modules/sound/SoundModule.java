package com.clefal.lootbeams.modules.sound;

import com.clefal.lootbeams.Constants;
import com.clefal.lootbeams.config.Config;
import com.clefal.lootbeams.config.ConfigurationManager;
import com.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
import com.clefal.lootbeams.modules.ILBModule;
import com.clefal.lootbeams.utils.Checker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;

public class SoundModule implements ILBModule {

    public static final SoundModule INSTANCE = new SoundModule();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEnableModule(EntityRenderDispatcherHookEvent.RenderLootBeamEvent event) {
        if (event.LBItemEntity.isSounded()) return;
        ItemEntity itemEntity = event.LBItemEntity.item();
        Item item = itemEntity.getItem().getItem();

        if ((ConfigurationManager.<Boolean>request(Config.SOUND_ALL_ITEMS) && !Checker.isItemInRegistryList(ConfigurationManager.request(Config.BLACKLIST), item))
                || (ConfigurationManager.<Boolean>request(Config.SOUND_ONLY_EQUIPMENT) && Checker.isEquipmentItem(item))
                || (ConfigurationManager.<Boolean>request(Config.SOUND_ONLY_RARE) && event.LBItemEntity.isRare())
                || Checker.isItemInRegistryList(ConfigurationManager.request(Config.SOUND_ONLY_WHITELIST), item)
        ) {

            WeighedSoundEvents sound = Minecraft.getInstance().getSoundManager().getSoundEvent(Constants.LOOT_DROP);

            if (sound != null && Minecraft.getInstance().level != null) {
                Minecraft.getInstance().level.playSound(Minecraft.getInstance().player, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), SoundEvent.createFixedRangeEvent(Constants.LOOT_DROP, 8.0f), SoundSource.AMBIENT, 0.1f * ConfigurationManager.<Float>request(Config.SOUND_VOLUME), 1.0f);
                event.LBItemEntity.updateSounded();
            }
        }
    }


    @Override
    public void tryEnable() {
        if (ConfigurationManager.<Boolean>request(Config.SOUND)){
            Constants.EVENT_BUS.register(INSTANCE);
        }
    }
}
