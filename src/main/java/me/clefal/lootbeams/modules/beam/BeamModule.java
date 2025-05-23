package me.clefal.lootbeams.modules.beam;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
import me.clefal.lootbeams.modules.ILBModule;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.EventPriority;


public class BeamModule implements ILBModule {

    public static final BeamModule INSTANCE = new BeamModule();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEnableModule(EntityRenderDispatcherHookEvent.RenderLootBeamEvent event) {
        BeamRenderer.renderLootBeam(event.poseStack, event.buffers, event.partialTicks, event.LBItemEntity);
    }


    @Override
    public void tryEnable() {
        LootBeamsConstants.EVENT_BUS.register(INSTANCE);
    }
}
