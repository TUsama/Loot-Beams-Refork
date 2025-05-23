package me.clefal.lootbeams.config.persistent;

import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.events.RegisterConfigConditionEvent;
import net.minecraft.world.item.ItemStack;
//this class can actually merge the config whitelist part.
public class WhitelistCondition extends PersistentConfigData<RegisterConfigConditionEvent.RegisterWhitelistEvent>{
    public final static WhitelistCondition INSTANCE = new WhitelistCondition();

    public static boolean isInSpecialWhitelist(LBItemEntity lbItemEntity) {
        return INSTANCE.conditions
                .stream()
                .anyMatch(x -> x.test(lbItemEntity));
    }

    @Override
    public RegisterConfigConditionEvent.RegisterWhitelistEvent getEvent() {
        return new RegisterConfigConditionEvent.RegisterWhitelistEvent();
    }
}
