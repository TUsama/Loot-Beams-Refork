package me.clefal.lootbeams.events;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.Event;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class RegisterConfigConditionEvent extends Event {
    public final List<Predicate<LBItemEntity>> conditions = new ArrayList<>();

    public static class RegisterEquipmentItemEvent extends RegisterConfigConditionEvent {
    }


    public static class RegisterWhitelistEvent extends RegisterConfigConditionEvent {
    }
}
