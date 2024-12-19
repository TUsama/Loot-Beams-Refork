package com.clefal.lootbeams.config.persistent;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.events.RegisterConfigConditionEvent;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Predicate;

public abstract class PersistentConfigData<T extends RegisterConfigConditionEvent> {
    protected List<Predicate<ItemStack>> conditions;

    public PersistentConfigData() {
        T event = getEvent();
        LootBeamsConstants.EVENT_BUS.post(event);
        this.conditions = event.conditions;
    }

    public abstract T getEvent();
}
