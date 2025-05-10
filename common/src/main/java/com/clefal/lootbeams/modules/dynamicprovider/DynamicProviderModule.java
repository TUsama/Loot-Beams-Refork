package com.clefal.lootbeams.modules.dynamicprovider;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.config.configs.DynamicConfig;
import com.clefal.lootbeams.modules.ILBModule;
import com.clefal.nirvana_lib.relocated.io.vavr.control.Option;

public class DynamicProviderModule implements ILBModule {

    public final static DynamicProviderModule INSTANCE = new DynamicProviderModule();

    private DynamicProvider dynamicProvider;

    public DynamicProviderModule() {
    }

    @Override
    public void tryEnable() {
        if (DynamicConfig.dynamicConfig.enable_dynamic) {
            //only initialize the dynamicProvider when enabled.
            this.dynamicProvider = new DynamicProvider();
            LootBeamsConstants.EVENT_BUS.register(this.dynamicProvider);

        }
    }

    public static Option<DynamicProvider> getDynamicProvider() {
        //System.out.println(INSTANCE.dynamicProvider == null);
        return Option.of(INSTANCE.dynamicProvider);
    }

}
