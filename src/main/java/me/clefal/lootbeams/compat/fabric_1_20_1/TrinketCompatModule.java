//? if =1.20.1 && fabric {
/*package me.clefal.lootbeams.compat.fabric_1_20_1;

import com.clefal.nirvana_lib.utils.ModUtils;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.events.RegisterConfigConditionEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import dev.emi.trinkets.TrinketsMain;
import dev.emi.trinkets.api.TrinketsApi;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;

public class TrinketCompatModule implements ILBCompatModule {
    public final static TrinketCompatModule INSTANCE = new TrinketCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded(TrinketsMain.MOD_ID);
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
*///?}