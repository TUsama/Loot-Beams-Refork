package me.clefal.lootbeams.compat.multiversion_compat;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.clefal.nirvana_lib.utils.ModUtils;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import net.irisshaders.iris.api.v0.IrisApi;

public class IrisCompatModule implements ILBCompatModule {
    public static final IrisCompatModule INSTANCE = new IrisCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded("iris");
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Iris, enable IrisCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }

    @SubscribeEvent
    public void checkShader(EntityRenderDispatcherHookEvent.RenderLootBeamEvent event){
        event.isShaderOn = IrisApi.getInstance().isShaderPackInUse();
    }
}
