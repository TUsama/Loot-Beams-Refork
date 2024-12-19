package com.clefal.lootbeams.events;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class RegisterConfigConditionEvent extends Event {
    public final List<Predicate<ItemStack>> conditions = new ArrayList<>();

    public static class RegisterEquipmentItemEvent extends RegisterConfigConditionEvent {
    }


    public static class RegisterWhitelistEvent extends RegisterConfigConditionEvent {
    }
}
