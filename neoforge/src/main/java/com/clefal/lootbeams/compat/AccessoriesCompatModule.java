package com.clefal.lootbeams.compat;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.events.RegisterConfigConditionEvent;
import com.clefal.lootbeams.modules.ILBCompatModule;
import io.wispforest.accessories.Accessories;
import io.wispforest.accessories.api.AccessoriesAPI;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;

public class AccessoriesCompatModule implements ILBCompatModule {
    public final static AccessoriesCompatModule INSTANCE = new AccessoriesCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModList.get().isLoaded(Accessories.MODID);
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
        event.conditions.add(itemStack -> AccessoriesAPI.isValidAccessory(itemStack, Minecraft.getInstance().level));
    }
}
