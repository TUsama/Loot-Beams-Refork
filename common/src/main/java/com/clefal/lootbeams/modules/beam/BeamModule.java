package com.clefal.lootbeams.modules.beam;

import com.clefal.lootbeams.Constants;
import com.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
import com.clefal.lootbeams.modules.ILBModule;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;

public class BeamModule implements ILBModule {

    public static final BeamModule INSTANCE = new BeamModule();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEnableModule(EntityRenderDispatcherHookEvent.RenderLootBeamEvent event) {
        BeamRenderer.renderLootBeam(event.poseStack, event.buffers, event.partialTicks, event.LBItemEntity);
    }


    @Override
    public void tryEnable() {
        Constants.EVENT_BUS.register(INSTANCE);
    }
}
