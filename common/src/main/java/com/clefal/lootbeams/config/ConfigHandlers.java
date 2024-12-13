package com.clefal.lootbeams.config;

import com.clefal.lootbeams.Constants;
import com.clefal.lootbeams.config.impl.IConfigReloadable;
import com.clefal.lootbeams.config.impl.ModifyingConfigHandler;
import com.clefal.lootbeams.data.rarity.ConfigColorOverride;
import com.clefal.lootbeams.events.ConfigReloadEvent;
import lombok.Getter;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.List;

public class ConfigHandlers implements IConfigReloadable {
    public static ConfigHandlers INSTANCE = new ConfigHandlers();
    @Getter
    private List<ModifyingConfigHandler> handlers;

    public ConfigHandlers() {
        registerAll();

    }

    public static void init(){
        Constants.EVENT_BUS.register(INSTANCE);
    }


    public void registerAll(){
        this.handlers = List.of(
                new ConfigColorOverride()
        );
    }

    @SubscribeEvent
    @Override
    public void onReload(ConfigReloadEvent event){
        INSTANCE.handlers.clear();
        INSTANCE.registerAll();
    }
}
