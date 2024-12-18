package com.clefal.lootbeams.config;

import com.clefal.lootbeams.config.services.IServiceCollector;

import java.util.List;

public class FabricCollector implements IServiceCollector {
    @Override
    public List<String> gatherModIDList() {
        return List.of();
    }
}
