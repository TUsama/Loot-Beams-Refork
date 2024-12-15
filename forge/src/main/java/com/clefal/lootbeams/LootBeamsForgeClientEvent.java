package com.clefal.lootbeams;

import com.clefal.lootbeams.events.LBClientTickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LootBeamsConstants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class LootBeamsForgeClientEvent {

    @SubscribeEvent
    public static void FireSelfClientTickEvent(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            LootBeamsConstants.EVENT_BUS.post(new LBClientTickEvent());
        }
    }
}
