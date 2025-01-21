package com.clefal.lootbeams;

import com.clefal.lootbeams.compat.apothesis.ApotheosisCompatModule;
import com.clefal.lootbeams.compat.curios.CuriosCompatModule;
import com.clefal.lootbeams.compat.mine_and_slash.MineAndSlashCompatModule;
import com.clefal.lootbeams.compat.mine_and_slash.OrbOfCraftingCompatModule;
import com.clefal.lootbeams.compat.obscuretooltips.ObscureTooltipsCompatModule;
import com.clefal.lootbeams.compat.spartanweaponry.SpartanWeaponryCompatModule;
import com.clefal.lootbeams.config.ConfigHandlers;
import com.clefal.lootbeams.modules.ModulesManager;
import com.clefal.lootbeams.modules.tooltip.overlay.AdvanceTooltipOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = LootBeamsConstants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LootBeamsForgeModClientEvent {


    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.CHAT_PANEL.id(), LootBeamsConstants.MODID + "lb_tooltips", (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> AdvanceTooltipOverlay.INSTANCE.render(guiGraphics, partialTick, screenWidth, screenHeight));
    }


    @SubscribeEvent
    public static void registerModules(FMLClientSetupEvent event) {
        LootBeamsConstants.LOGGER.info("register all modules");
        ModulesManager.registerModules(
                new ApotheosisCompatModule(),
                MineAndSlashCompatModule.INSTANCE,
                ObscureTooltipsCompatModule.INSTANCE,
                CuriosCompatModule.INSTANCE,
                SpartanWeaponryCompatModule.INSTANCE,
                OrbOfCraftingCompatModule.INSTANCE
        );
        ModulesManager.enableAll();
        ConfigHandlers.init();
    }

}
