package com.clefal.lootbeams.config;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.config.impl.ModifyingConfigHandler;
import com.clefal.lootbeams.data.lbitementity.rarity.ConfigColorOverride;
import lombok.Getter;

import java.util.List;

public class ConfigHandlers {
    public static ConfigHandlers INSTANCE = new ConfigHandlers();
    @Getter
    private List<ModifyingConfigHandler> handlers;

    public ConfigHandlers() {
        registerAll();

    }

    public static void init(){
        LootBeamsConstants.EVENT_BUS.register(INSTANCE);
    }


    public void registerAll(){
        this.handlers = List.of(
                new ConfigColorOverride()
        );
    }

}
