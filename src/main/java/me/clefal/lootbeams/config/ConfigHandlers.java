package me.clefal.lootbeams.config;

import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.config.impl.IConfigReloadable;
import me.clefal.lootbeams.config.impl.ModifyingConfigHandler;
import me.clefal.lootbeams.data.lbitementity.rarity.ConfigColorOverride;
import me.clefal.lootbeams.events.ConfigReloadEvent;
import lombok.Getter;

import java.util.List;

public class ConfigHandlers implements IConfigReloadable {
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

    @SubscribeEvent
    @Override
    public void onReload(ConfigReloadEvent event){
        INSTANCE.handlers.clear();
        INSTANCE.registerAll();
    }
}
