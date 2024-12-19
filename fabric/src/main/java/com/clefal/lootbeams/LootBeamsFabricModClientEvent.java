package com.clefal.lootbeams;

import com.clefal.lootbeams.compat.TrinketCompatModule;
import com.clefal.lootbeams.config.ConfigHandlers;
import com.clefal.lootbeams.modules.ModulesManager;
import com.clefal.lootbeams.modules.tooltip.overlay.AdvanceTooltipOverlay;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class LootBeamsFabricModClientEvent {

    public static void init(){
        registerOverlay();
        registerModules();
    }


    public static void registerOverlay() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            AdvanceTooltipOverlay.INSTANCE.render(drawContext, tickDelta, drawContext.guiWidth(), drawContext.guiHeight());
        });
    }


    public static void registerModules() {
        LootBeamsConstants.LOGGER.info("register all modules");
        ModulesManager.registerModules(
                TrinketCompatModule.INSTANCE
        );
        ModulesManager.enableAll();
        ConfigHandlers.init();
    }

}
