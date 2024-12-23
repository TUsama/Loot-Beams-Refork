package com.clefal.lootbeams.compat.TrinketCompatModule;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.events.RegisterConfigConditionEvent;
import com.clefal.lootbeams.modules.ILBCompatModule;
import dev.emi.trinkets.TrinketsMain;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.loader.api.FabricLoader;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;

public class TrinketCompatModule implements ILBCompatModule {
    public final static TrinketCompatModule INSTANCE = new TrinketCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return FabricLoader.getInstance().isModLoaded(TrinketsMain.MOD_ID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Curios, enable CuriosCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }

    @SubscribeEvent
    public void registerEquipmentCondition(RegisterConfigConditionEvent.RegisterEquipmentItemEvent event) {
        event.conditions.add(lbItemEntity -> TrinketsApi.getTrinket(lbItemEntity.item().getItem().getItem()) != TrinketsApi.getDefaultTrinket());
    }
}
