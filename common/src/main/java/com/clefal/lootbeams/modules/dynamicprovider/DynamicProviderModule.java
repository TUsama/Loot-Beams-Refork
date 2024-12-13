package com.clefal.lootbeams.modules.dynamicprovider;

import com.clefal.lootbeams.Constants;
import com.clefal.lootbeams.config.Config;
import com.clefal.lootbeams.config.ConfigurationManager;
import com.clefal.lootbeams.config.impl.IConfigReloadable;
import com.clefal.lootbeams.events.ConfigReloadEvent;
import com.clefal.lootbeams.modules.ILBModule;
import io.vavr.control.Option;
import net.neoforged.bus.api.SubscribeEvent;

public class DynamicProviderModule implements ILBModule, IConfigReloadable {

    public final static DynamicProviderModule INSTANCE = new DynamicProviderModule();

    private DynamicProvider dynamicProvider;

    public DynamicProviderModule() {
    }

    @Override
    public void tryEnable() {
        if (ConfigurationManager.request(Config.ENABLE_DYNAMIC_PROVIDER)) {
            //only initialize the dynamicProvider when enabled.
            this.dynamicProvider = new DynamicProvider(ConfigurationManager.request(Config.HALF_ROUND_TICKS));
            Constants.EVENT_BUS.register(this.dynamicProvider);

        }
    }

    public static Option<DynamicProvider> getDynamicProvider() {
        //System.out.println(INSTANCE.dynamicProvider == null);
        return Option.of(INSTANCE.dynamicProvider);
    }

    @Override
    @SubscribeEvent
    public void onReload(ConfigReloadEvent event) {
        Constants.EVENT_BUS.unregister(this.dynamicProvider);
        this.dynamicProvider = new DynamicProvider(ConfigurationManager.request(Config.HALF_ROUND_TICKS));
        Constants.EVENT_BUS.register(this.dynamicProvider);
    }
}
