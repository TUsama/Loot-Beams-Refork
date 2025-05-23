//? if fabric {
/*package me.clefal.lootbeams.loaders.fabric;

import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.loaders.fabric.compat.TieredZCompatModule;
import me.clefal.lootbeams.loaders.fabric.compat.TierifyCompatModule;
import me.clefal.lootbeams.loaders.fabric.compat.TrinketCompatModule;
import me.clefal.lootbeams.loaders.fabric.compat.ZenithCompatModule;
import me.clefal.lootbeams.config.ConfigHandlers;
import me.clefal.lootbeams.modules.ModulesManager;
import me.clefal.lootbeams.modules.tooltip.overlay.AdvanceTooltipOverlay;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class LootBeamsFabricModClientEvent {

    public static void init() {
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
                TrinketCompatModule.INSTANCE,
                ZenithCompatModule.INSTANCE,
                TieredZCompatModule.INSTANCE,
                TierifyCompatModule.INSTANCE
        );
        ModulesManager.enableAll();
        ConfigHandlers.init();
    }

}
*///?}
