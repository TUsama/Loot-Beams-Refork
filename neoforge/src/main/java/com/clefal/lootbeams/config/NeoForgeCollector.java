package com.clefal.lootbeams.config;

import com.clefal.lootbeams.config.services.IServiceCollector;
import com.clefal.lootbeams.config.services.IServicesChecker;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModInfo;

import java.util.List;

public class NeoForgeCollector implements IServiceCollector {


    @Override
    public List<String> gatherModIDList() {
        return ModList.get().getMods().stream().map(IModInfo::getModId).toList();
    }
}
