package com.clefal.lootbeams.config;

import com.clefal.lootbeams.config.services.IServiceCollector;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;

import java.util.List;

public class ForgeCollector implements IServiceCollector {


    @Override
    public List<String> gatherModIDList() {
        return ModList.get().getMods().stream().map(IModInfo::getModId).toList();
    }
}
