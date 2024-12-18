package com.clefal.lootbeams.config;

import com.clefal.lootbeams.config.services.IServiceCollector;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.util.List;

public class FabricCollector implements IServiceCollector {
    @Override
    public List<String> gatherModIDList() {
        return FabricLoader.getInstance().getAllMods().stream().map(x -> x.getMetadata().getId()).toList();
    }
}
