package com.clefal.lootbeams;


import com.clefal.lootbeams.compat.AccessoriesCompatModule;
import com.clefal.lootbeams.compat.CuriosContinuationAndAdornedCompatModule;
import com.clefal.lootbeams.config.ConfigHandlers;
import com.clefal.lootbeams.modules.ModulesManager;
import com.clefal.lootbeams.modules.tooltip.overlay.AdvanceTooltipOverlay;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = LootBeamsConstants.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LootBeamsForgeModClientEvent {


    @SubscribeEvent
    public static void registerOverlay(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.CROSSHAIR, ResourceLocation.fromNamespaceAndPath(LootBeamsConstants.MODID, "lb_tooltips") , ((guiGraphics, deltaTracker) -> {
            AdvanceTooltipOverlay.INSTANCE.render(guiGraphics, deltaTracker);
        }));
    }


    @SubscribeEvent
    public static void registerModules(FMLClientSetupEvent event) {
        LootBeamsConstants.LOGGER.info("register all modules");
        ModulesManager.registerModules(
                CuriosContinuationAndAdornedCompatModule.INSTANCE,
                AccessoriesCompatModule.INSTANCE
        );
        ModulesManager.enableAll();
        ConfigHandlers.init();
    }

}
