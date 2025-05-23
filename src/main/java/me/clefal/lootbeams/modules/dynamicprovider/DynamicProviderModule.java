package me.clefal.lootbeams.modules.dynamicprovider;

import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.config.configs.DynamicConfig;
import me.clefal.lootbeams.config.impl.IConfigReloadable;
import me.clefal.lootbeams.events.ConfigReloadEvent;
import me.clefal.lootbeams.modules.ILBModule;
import com.clefal.nirvana_lib.relocated.io.vavr.control.Option;
import net.neoforged.bus.api.SubscribeEvent;

public class DynamicProviderModule implements ILBModule, IConfigReloadable {

    public final static DynamicProviderModule INSTANCE = new DynamicProviderModule();

    private DynamicProvider dynamicProvider;

    public DynamicProviderModule() {
    }

    @Override
    public void tryEnable() {
        if (DynamicConfig.dynamicConfig.enable_dynamic) {
            //only initialize the dynamicProvider when enabled.
            this.dynamicProvider = new DynamicProvider(DynamicConfig.dynamicConfig.half_round_ticks.get());
            LootBeamsConstants.EVENT_BUS.register(this.dynamicProvider);

        }
    }

    public static Option<DynamicProvider> getDynamicProvider() {
        //System.out.println(INSTANCE.dynamicProvider == null);
        return Option.of(INSTANCE.dynamicProvider);
    }

    @Override
    @SubscribeEvent
    public void onReload(ConfigReloadEvent event) {
        LootBeamsConstants.EVENT_BUS.unregister(this.dynamicProvider);
        this.dynamicProvider = new DynamicProvider(DynamicConfig.dynamicConfig.half_round_ticks.get());
        LootBeamsConstants.EVENT_BUS.register(this.dynamicProvider);
    }
}
