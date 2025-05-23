package me.clefal.lootbeams.config.persistent;

import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.events.RegisterConfigConditionEvent;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Predicate;

public abstract class PersistentConfigData<T extends RegisterConfigConditionEvent> {
    protected List<Predicate<LBItemEntity>> conditions;

    public PersistentConfigData() {
        T event = getEvent();
        LootBeamsConstants.EVENT_BUS.post(event);
        this.conditions = event.conditions;
    }

    public abstract T getEvent();
}
