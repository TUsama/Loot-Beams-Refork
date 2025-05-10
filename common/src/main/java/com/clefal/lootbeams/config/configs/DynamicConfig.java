package com.clefal.lootbeams.config.configs;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.modules.dynamicprovider.DynamicProviderModule;
import com.clefal.lootbeams.utils.ResourceLocationHelper;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.resources.ResourceLocation;

public class DynamicConfig extends Config {
    public static DynamicConfig dynamicConfig = ConfigApiJava.registerAndLoadConfig(DynamicConfig::new, RegisterType.CLIENT);
    public boolean enable_dynamic = true;
    public ValidatedInt half_round_ticks = new ValidatedInt(30);
    public DynamicConfig() {
        super(ResourceLocationHelper.fromNameAndPath(LootBeamsConstants.MODID , "dynamic_config"));
    }


    @Override
    public void onUpdateClient() {
        super.onUpdateClient();
        DynamicProviderModule.getDynamicProvider()
                .forEach(x -> x.setHalfRoundTicks(this.half_round_ticks.get()));
    }

    public static void init(){

    }
}
