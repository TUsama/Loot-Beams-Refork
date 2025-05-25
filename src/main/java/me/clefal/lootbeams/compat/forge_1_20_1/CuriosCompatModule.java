//? if forge {
/*package me.clefal.lootbeams.compat.forge_1_20_1;

import com.clefal.nirvana_lib.utils.ModUtils;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.modules.ILBCompatModule;
import me.clefal.lootbeams.events.RegisterConfigConditionEvent;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosCompatModule implements ILBCompatModule {
    public final static CuriosCompatModule INSTANCE = new CuriosCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded(CuriosApi.MODID);
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
        event.conditions.add(lbItemEntity -> CuriosApi.getCurio(lbItemEntity.item().getItem()).isPresent());
    }
}
*///?}
