//? if forge {
/*package me.clefal.lootbeams.loaders.forge;


import me.clefal.lootbeams.compat.forge_1_20_1.*;
import me.clefal.lootbeams.compat.forge_1_20_1.mine_and_slash.MineAndSlashCompatModule;
import me.clefal.lootbeams.compat.forge_1_20_1.mine_and_slash.OrbOfCraftingCompatModule;
import me.clefal.lootbeams.compat.multiversion_compat.MalumCompatModule;
import me.clefal.lootbeams.config.ConfigHandlers;
import me.clefal.lootbeams.modules.ModulesManager;
import me.clefal.lootbeams.modules.tooltip.overlay.AdvanceTooltipOverlay;
import me.clefal.lootbeams.LootBeamsConstants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod.EventBusSubscriber(modid = LootBeamsConstants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LootBeamsForgeModClientEvent {


    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.CHAT_PANEL.id(), LootBeamsConstants.MODID + "lb_tooltips", (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> AdvanceTooltipOverlay.INSTANCE.render(guiGraphics, partialTick));
    }


    @SubscribeEvent
    public static void registerModules(FMLLoadCompleteEvent event) {
        LootBeamsConstants.LOGGER.info("register all modules");
        ModulesManager.registerModules(
                new ApotheosisCompatModule(),
                MineAndSlashCompatModule.INSTANCE,
                ObscureTooltipsCompatModule.INSTANCE,
                CuriosCompatModule.INSTANCE,
                SpartanWeaponryCompatModule.INSTANCE,
                OrbOfCraftingCompatModule.INSTANCE,
                TieredReforgedCompatModule.INSTANCE,
                MalumCompatModule.INSTANCE
                //,BiomancyCompatModule.INSTANCE
        );
        ModulesManager.enableAll();
        ConfigHandlers.init();
    }

}
*///?}
