package com.clefal.lootbeams;

import com.clefal.lootbeams.config.configs.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class LootBeams implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        ConfigManager.init();
        LootBeamsForgeClientEvent.FireSelfClientTickEvent();
        LootBeamsForgeModClientEvent.init();
    }
}
