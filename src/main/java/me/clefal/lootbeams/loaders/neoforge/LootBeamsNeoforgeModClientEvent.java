//? if neoforge {
package me.clefal.lootbeams.loaders.neoforge;


import me.clefal.lootbeams.LootBeamsConstants;
//? if =1.21.1 {
/*import me.clefal.lootbeams.compat.common_1_21_1.AccessoriesCompatModule;
import me.clefal.lootbeams.compat.common_1_21_1.SubtleEffectCompatModule;
import me.clefal.lootbeams.compat.common_1_21_1.TieredReforgedCompatModule;
import me.clefal.lootbeams.compat.neoforged_1_21_1.ApotheosisCompatModule;
import me.clefal.lootbeams.compat.neoforged_1_21_1.CuriosContinuationAndAdornedCompatModule;
*///?}

//? if =1.21.4 {
import me.clefal.lootbeams.compat.common_1_21_4.SubtleEffectCompatModule;
//?}
import me.clefal.lootbeams.config.ConfigHandlers;
import me.clefal.lootbeams.modules.ModulesManager;
import me.clefal.lootbeams.modules.tooltip.overlay.AdvanceTooltipOverlay;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = LootBeamsConstants.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LootBeamsNeoforgeModClientEvent {


    @SubscribeEvent
    public static void registerOverlay(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.CROSSHAIR, ResourceLocation.fromNamespaceAndPath(LootBeamsConstants.MODID, "lb_tooltips") , (AdvanceTooltipOverlay.INSTANCE::render));
    }


    @SubscribeEvent
    public static void registerModules(FMLClientSetupEvent event) {
        LootBeamsConstants.LOGGER.info("register all modules");
        ModulesManager.registerModules(
                //? if =1.21.1 {
                /*CuriosContinuationAndAdornedCompatModule.INSTANCE,
                AccessoriesCompatModule.INSTANCE,
                ApotheosisCompatModule.INSTANCE,
                TieredReforgedCompatModule.INSTANCE,
                SubtleEffectCompatModule.INSTANCE
                *///?}

                //? if =1.21.4 {
                SubtleEffectCompatModule.INSTANCE
                //?}
        );
        ModulesManager.enableAll();
        ConfigHandlers.init();
    }

}
//?}
