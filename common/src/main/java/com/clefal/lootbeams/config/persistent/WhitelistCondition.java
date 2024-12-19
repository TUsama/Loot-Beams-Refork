package com.clefal.lootbeams.config.persistent;

import com.clefal.lootbeams.events.RegisterConfigConditionEvent;
import net.minecraft.world.item.ItemStack;

public class WhitelistCondition extends PersistentConfigData<RegisterConfigConditionEvent.RegisterWhitelistEvent>{
    public final static WhitelistCondition INSTANCE = new WhitelistCondition();

    public static boolean isInSpecialWhitelist(ItemStack itemStack) {
        return INSTANCE.conditions
                .stream()
                .anyMatch(x -> x.test(itemStack));
    }

    @Override
    public RegisterConfigConditionEvent.RegisterWhitelistEvent getEvent() {
        return new RegisterConfigConditionEvent.RegisterWhitelistEvent();
    }
}
