package com.clefal.lootbeams.events;

import com.clefal.lootbeams.data.LBItemEntity;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.Event;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.TreeMap;

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
