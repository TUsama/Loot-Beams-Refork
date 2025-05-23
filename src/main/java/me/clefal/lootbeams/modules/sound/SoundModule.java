package me.clefal.lootbeams.modules.sound;

import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.config.configs.SoundConfig;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
import me.clefal.lootbeams.modules.ILBModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;

public class SoundModule implements ILBModule {

    public static final SoundModule INSTANCE = new SoundModule();

    private static boolean canSound(EntityRenderDispatcherHookEvent.RenderLootBeamEvent event, ItemEntity itemEntity) {
        if (SoundConfigHandler.checkInBlackList(event.LBItemEntity)) return false;
        return event.LBItemEntity.isRare();
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
                Minecraft.getInstance().level.playSound(Minecraft.getInstance().player, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), SoundEvent.createFixedRangeEvent(LootBeamsConstants.LOOT_DROP, 8.0f), SoundSource.AMBIENT, 0.1f * SoundConfig.soundConfig.sound.sound_volume.get(), 1.0f);
                event.LBItemEntity.updateSounded();
            }
        }
    }

    @Override
    public void tryEnable() {
        if (SoundConfig.soundConfig.sound.enable_sound) {
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }
}
