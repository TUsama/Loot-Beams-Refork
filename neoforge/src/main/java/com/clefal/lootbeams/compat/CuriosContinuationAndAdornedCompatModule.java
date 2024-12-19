package com.clefal.lootbeams.compat;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.events.RegisterConfigConditionEvent;
import com.clefal.lootbeams.modules.ILBCompatModule;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosContinuationAndAdornedCompatModule implements ILBCompatModule {
    public final static CuriosContinuationAndAdornedCompatModule INSTANCE = new CuriosContinuationAndAdornedCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModList.get().isLoaded(CuriosApi.MODID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Curios Continuation/Adorned, enable CuriosContinuationAndAdornedCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }

    @SubscribeEvent
    public void registerEquipmentCondition(RegisterConfigConditionEvent.RegisterEquipmentItemEvent event) {
        event.conditions.add(itemStack -> CuriosApi.getCurio(itemStack).isPresent());
    }
}
