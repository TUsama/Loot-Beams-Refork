package com.clefal.lootbeams.data.equipment;

import com.clefal.lootbeams.Constants;
import com.clefal.lootbeams.events.RegisterEquipmentItemEvent;
import io.vavr.collection.List;
import net.minecraft.world.item.*;

import java.util.function.Predicate;

public class EquipmentConditions {
    private final static List<Predicate<ItemStack>> conditions;

    static {

        RegisterEquipmentItemEvent registerEquipmentItemEvent = new RegisterEquipmentItemEvent();
        Constants.EVENT_BUS.post(registerEquipmentItemEvent);
        conditions = List.ofAll(registerEquipmentItemEvent.conditions)
                .prepend(item1 -> {
                    var item = item1.getItem();
                    return item instanceof TieredItem || item instanceof ArmorItem || item instanceof ShieldItem || item instanceof BowItem || item instanceof CrossbowItem;
                });
    }

    public static boolean isEquipment(ItemStack itemStack) {
        return conditions
                .find(x -> x.test(itemStack))
                .isDefined();
    }
}
