//? if =1.21.1 && neoforge {
/*package me.clefal.lootbeams.compat.neoforged_1_21_1;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.events.RegisterConfigConditionEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
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
        event.conditions.add(itemStack -> CuriosApi.getCurio(itemStack.item().getItem()).isPresent());
    }
}
*///?}
