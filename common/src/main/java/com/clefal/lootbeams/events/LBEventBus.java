package com.clefal.lootbeams.events;


import com.clefal.nirvana_lib.relocated.net.neoforged.bus.BusBuilderImpl;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.EventBus;

public class LBEventBus extends EventBus {

    public LBEventBus(BusBuilderImpl busBuilder) {
        super(busBuilder);
    }
}
