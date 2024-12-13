package com.clefal.lootbeams.utils;

import lombok.experimental.UtilityClass;
import net.minecraft.world.item.*;

@UtilityClass
public class Checker {

    public boolean isEquipmentItem(Item item) {
        return item instanceof TieredItem || item instanceof ArmorItem || item instanceof ShieldItem || item instanceof BowItem || item instanceof CrossbowItem;
    }

}
