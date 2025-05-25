//? if neoforge {
package me.clefal.lootbeams.loaders.neoforge;

import me.clefal.lootbeams.events.LBClientTickEvent;
import me.clefal.lootbeams.LootBeamsConstants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;


@EventBusSubscriber(modid = LootBeamsConstants.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class LootBeamsNeoforgeClientEvent {

    @SubscribeEvent
    public static void FireSelfClientTickEvent(ClientTickEvent.Post event) {

        LootBeamsConstants.EVENT_BUS.post(new LBClientTickEvent());

    }
}
//?}