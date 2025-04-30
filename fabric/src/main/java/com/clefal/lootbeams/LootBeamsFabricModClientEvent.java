package com.clefal.lootbeams;

import com.clefal.lootbeams.compat.*;
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
        HudRenderCallback.EVENT.register(AdvanceTooltipOverlay.INSTANCE::render);
    }


    public static void registerModules() {
        LootBeamsConstants.LOGGER.info("register all modules");
        ModulesManager.registerModules(
                TrinketCompatModule.INSTANCE,
                AccessoriesCompatModule.INSTANCE,
                TieredZCompatModule.INSTANCE,
                TieredReforgedCompatModule.INSTANCE,
                SubtleEffectCompatModule.INSTANCE
        );
        ModulesManager.enableAll();
        ConfigHandlers.init();
    }

}
