package com.clefal.lootbeams.config.persistent;

import com.clefal.lootbeams.events.RegisterConfigConditionEvent;
import net.minecraft.world.item.ItemStack;

public class EquipmentConditions extends PersistentConfigData<RegisterConfigConditionEvent.RegisterEquipmentItemEvent> {
    public final static EquipmentConditions INSTANCE = new EquipmentConditions();


    public static boolean isEquipment(ItemStack itemStack) {
        return INSTANCE.conditions
                .stream()
                .anyMatch(x -> x.test(itemStack));
    }

    @Override
    public RegisterConfigConditionEvent.RegisterEquipmentItemEvent getEvent() {
        return new RegisterConfigConditionEvent.RegisterEquipmentItemEvent();
    }
}
