//? if fabric {
/*package me.clefal.lootbeams.loaders.fabric;

import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.events.LBClientTickEvent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class LootBeamsFabricClientEvent {

    public static void FireSelfClientTickEvent() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> LootBeamsConstants.EVENT_BUS.post(new LBClientTickEvent()));
    }
}
*///?}
