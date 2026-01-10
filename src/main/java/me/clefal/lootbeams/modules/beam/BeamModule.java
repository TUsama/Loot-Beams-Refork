package me.clefal.lootbeams.modules.beam;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.data.new_render.LootBeamRenderState;
import me.clefal.lootbeams.duck.LootBeamRenderStateSubmitter;
import me.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
import me.clefal.lootbeams.modules.ILBModule;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.EventPriority;


public class BeamModule implements ILBModule {

    public static final BeamModule INSTANCE = new BeamModule();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEnableModule(EntityRenderDispatcherHookEvent.RenderLootBeamEvent event) {
        //? <1.21.10 {
        BeamRenderer.renderLootBeam(event.poseStack, event.holder.get(), event.partialTicks, event.LBItemEntity);
        //? } else {
        /*LootBeamRenderStateSubmitter lootBeamRenderStateSubmitter = (LootBeamRenderStateSubmitter) event.holder.get();
        lootBeamRenderStateSubmitter.loot_Beams_Refork$submitBeam(LootBeamRenderState.BeamRenderState.fromLBEntity(event.LBItemEntity, event.poseStack.last().copy(), event.partialTicks));
        *///? }

    }


    @Override
    public void tryEnable() {
        LootBeamsConstants.EVENT_BUS.register(INSTANCE);
    }
}
