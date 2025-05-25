//? if =1.21.1 && fabric {
/*package me.clefal.lootbeams.compat.fabric_1_21_1;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.clefal.nirvana_lib.utils.ModUtils;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.events.RegisterConfigConditionEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import dev.emi.trinkets.TrinketsMain;
import dev.emi.trinkets.api.TrinketsApi;

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
        event.conditions.add(itemStack -> TrinketsApi.getTrinket(itemStack.item().getItem().getItem()) != TrinketsApi.getDefaultTrinket());
    }
}
*///?}
