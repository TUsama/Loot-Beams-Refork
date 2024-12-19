package com.clefal.lootbeams;

import com.clefal.lootbeams.config.configs.ConfigManager;
import net.fabricmc.api.ClientModInitializer;

public class LootBeamsClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        ConfigManager.init();
        LootBeamsFabricClientEvent.FireSelfClientTickEvent();
        LootBeamsFabricModClientEvent.init();
    }
}
