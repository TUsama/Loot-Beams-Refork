//? if =1.21.1 {
package me.clefal.lootbeams.compat.common_1_21_1;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.clefal.nirvana_lib.utils.ModUtils;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.events.RegisterConfigConditionEvent;
import me.clefal.lootbeams.modules.ILBCompatModule;
import io.wispforest.accessories.Accessories;
import io.wispforest.accessories.api.AccessoriesAPI;
import net.minecraft.client.Minecraft;

public class AccessoriesCompatModule implements ILBCompatModule {
    public final static AccessoriesCompatModule INSTANCE = new AccessoriesCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded(Accessories.MODID);
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
        //I still don't know why there need a Level para...
        //but this is a client-only mod, so I guess the only thing I can do is pass a ClientLevel.
        event.conditions.add(itemStack -> AccessoriesAPI.isValidAccessory(itemStack.item().getItem(), Minecraft.getInstance().level));
    }
}
//?}
