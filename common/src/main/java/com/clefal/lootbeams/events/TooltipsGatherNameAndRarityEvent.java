package com.clefal.lootbeams.events;

import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import net.minecraft.network.chat.Component;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.Event;

import java.util.EnumMap;
import java.util.Map;

public class TooltipsGatherNameAndRarityEvent extends Event {

    public enum Case{
        NAME,
        RARITY
    }
    public LBItemEntity lbItemEntity;
    public Map<Case, Component> gather = new EnumMap<>(Case.class);

    public TooltipsGatherNameAndRarityEvent(LBItemEntity lbItemEntity) {
        this.lbItemEntity = lbItemEntity;
    }
}
