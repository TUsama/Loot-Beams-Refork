package com.clefal.lootbeams;

import com.clefal.lootbeams.config.configs.ConfigManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;


@Mod(value = LootBeamsConstants.MODID, dist = Dist.CLIENT)
public class LootBeams {

    public LootBeams(Dist dist) {
        ConfigManager.init();
    }

}
