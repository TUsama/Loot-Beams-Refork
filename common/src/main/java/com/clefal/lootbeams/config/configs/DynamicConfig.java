package com.clefal.lootbeams.config.configs;

import com.clefal.lootbeams.Constants;
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
        super(new ResourceLocation(Constants.MODID + ":dynamic_config"));
    }
    public static void init(){

    }
}
