package com.clefal.lootbeams.compat.curios;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.lootbeams.events.RegisterConfigConditionEvent;
import net.minecraftforge.fml.ModList;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosCompatModule implements ILBCompatModule {
    public final static CuriosCompatModule INSTANCE = new CuriosCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModList.get().isLoaded(CuriosApi.MODID);
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
        event.conditions.add(itemStack -> CuriosApi.getCurio(itemStack).isPresent());
    }
}
