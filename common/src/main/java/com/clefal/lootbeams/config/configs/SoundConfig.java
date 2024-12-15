package com.clefal.lootbeams.config.configs;

import com.clefal.lootbeams.LootBeamsConstants;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.resources.ResourceLocation;

public class SoundConfig extends Config {
    public static SoundConfig soundConfig = ConfigApiJava.registerAndLoadConfig(SoundConfig::new, RegisterType.CLIENT);
    public SoundSection soundSection = new SoundSection();
    public SoundFilter soundFilter = new SoundFilter();

    public SoundConfig() {
        super(new ResourceLocation(LootBeamsConstants.MODID + ":sound_config"));
    }
    public static void init(){

    }
    public static class SoundSection extends ConfigSection {
        public boolean enable_sound = true;
        public ValidatedFloat sound_volume = new ValidatedFloat(1, 1, 0);
        public boolean sound_all_items = true;
        public boolean sound_only_rare = true;
        public ValidatedInt sound_rare_ordinal_min = new ValidatedInt(3);
        public boolean sound_only_equipment = false;
    }

    public static class SoundFilter extends ConfigSection {
        public ValidatedList<ResourceLocation> whitelist = new ValidatedIdentifier().toList();
        public ValidatedList<ResourceLocation> blacklist = new ValidatedIdentifier().toList();
    }
}
