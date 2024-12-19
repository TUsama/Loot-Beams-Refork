package com.clefal.lootbeams.config;

import com.clefal.lootbeams.config.services.IServiceCollector;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModInfo;

import java.util.List;

public class NeoForgeCollector implements IServiceCollector {


    @Override
    public List<String> gatherModIDList() {
        return ModList.get().getMods().stream().map(IModInfo::getModId).toList();
    }
}
