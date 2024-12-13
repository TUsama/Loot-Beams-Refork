package com.clefal.lootbeams.events;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RegisterEquipmentItemEvent extends Event {
    public final List<Predicate<ItemStack>> conditions = new ArrayList<>();
}
