package com.clefal.lootbeams.config.persistent;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.events.RegisterConfigConditionEvent;
import io.vavr.collection.List;
import net.minecraft.world.item.*;

import java.util.function.Predicate;

public class EquipmentConditions extends PersistentConfigData<RegisterConfigConditionEvent.RegisterEquipmentItemEvent>{
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
