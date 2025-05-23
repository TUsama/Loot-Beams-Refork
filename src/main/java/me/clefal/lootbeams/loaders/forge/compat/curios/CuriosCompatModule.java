//? if forge {

package me.clefal.lootbeams.loaders.forge.compat.curios;

import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.modules.ILBCompatModule;
import me.clefal.lootbeams.events.RegisterConfigConditionEvent;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
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
        event.conditions.add(lbItemEntity -> CuriosApi.getCurio(lbItemEntity.item().getItem()).isPresent());
    }
}

 //?}
