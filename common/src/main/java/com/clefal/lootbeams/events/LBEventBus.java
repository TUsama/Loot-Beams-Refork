package com.clefal.lootbeams.events;


import net.neoforged.bus.BusBuilderImpl;
import net.neoforged.bus.EventBus;

public class LBEventBus extends EventBus {

    public LBEventBus(BusBuilderImpl busBuilder) {
        super(busBuilder);
    }
}
