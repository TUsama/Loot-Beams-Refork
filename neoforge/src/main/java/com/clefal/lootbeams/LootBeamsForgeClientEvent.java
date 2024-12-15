package com.clefal.lootbeams;

import com.clefal.lootbeams.events.LBClientTickEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;


@EventBusSubscriber(modid = LootBeamsConstants.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class LootBeamsForgeClientEvent {

    @SubscribeEvent
    public static void FireSelfClientTickEvent(ClientTickEvent.Post event) {

        LootBeamsConstants.EVENT_BUS.post(new LBClientTickEvent());

    }
}
