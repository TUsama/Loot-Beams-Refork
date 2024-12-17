package com.clefal.lootbeams;

import com.clefal.lootbeams.events.LBClientTickEvent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class LootBeamsForgeClientEvent {

    public static void FireSelfClientTickEvent() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> LootBeamsConstants.EVENT_BUS.post(new LBClientTickEvent()));
    }
}
