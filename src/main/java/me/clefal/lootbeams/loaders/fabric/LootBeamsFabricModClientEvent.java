//? if fabric {
/*package me.clefal.lootbeams.loaders.fabric;

import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.compat.multiversion_compat.IrisCompatModule;
//? if =1.21.1 {

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import me.clefal.lootbeams.compat.fabric_1_21_1.TieredZCompatModule;
import me.clefal.lootbeams.compat.fabric_1_21_1.TrinketCompatModule;
import me.clefal.lootbeams.compat.common_1_21_1.AccessoriesCompatModule;
import me.clefal.lootbeams.compat.common_1_21_1.SubtleEffectCompatModule;
import me.clefal.lootbeams.compat.common_1_21_1.TieredReforgedCompatModule;
//?}


//? if =1.20.1 {
/^import me.clefal.lootbeams.compat.fabric_1_20_1.TieredZCompatModule;
import me.clefal.lootbeams.compat.fabric_1_20_1.TierifyCompatModule;
import me.clefal.lootbeams.compat.fabric_1_20_1.TrinketCompatModule;
import me.clefal.lootbeams.compat.fabric_1_20_1.ZenithCompatModule;

^///?}

//? if =1.21.4 {
/^import me.clefal.lootbeams.compat.common_1_21_4.SubtleEffectCompatModule;^/
//?}
//? malum
import me.clefal.lootbeams.compat.multiversion_compat.MalumCompatModule;
//? simplesword
import me.clefal.lootbeams.compat.multiversion_compat.SimpleSwordCompatModule;
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
        HudRenderCallback.EVENT.register(AdvanceTooltipOverlay.INSTANCE::render);
    }


    public static void registerModules() {
        LootBeamsConstants.LOGGER.info("register all modules");
        ModulesManager.registerModules(
                //? if =1.21.1 {
                SubtleEffectCompatModule.INSTANCE,
                AccessoriesCompatModule.INSTANCE,
                TieredReforgedCompatModule.INSTANCE,
                TieredZCompatModule.INSTANCE,
                TrinketCompatModule.INSTANCE
                //?}
                //? if =1.20.1 {
                /^TrinketCompatModule.INSTANCE,
                ZenithCompatModule.INSTANCE,
                TieredZCompatModule.INSTANCE,
                TierifyCompatModule.INSTANCE

                ^///?}

                //? if malum
                ,MalumCompatModule.INSTANCE
                //? if simplesword
                , SimpleSwordCompatModule.INSTANCE


        );
        //? if =1.21.4
        //ModulesManager.registerModules(SubtleEffectCompatModule.INSTANCE);

        ModulesManager.registerModules(IrisCompatModule.INSTANCE);
        ModulesManager.enableAll();
        ConfigHandlers.init();
    }

}
*///?}

