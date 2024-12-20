package com.clefal.lootbeams.config.configs;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.config.services.IServiceCollector;
import com.clefal.lootbeams.utils.ResourceLocationHelper;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.util.AllowableStrings;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class SoundConfig extends Config {
    public static SoundConfig soundConfig = ConfigApiJava.registerAndLoadConfig(SoundConfig::new, RegisterType.CLIENT);
    public SoundSection sound = new SoundSection();
    public SoundFilter soundFilter = new SoundFilter();

    public SoundConfig() {
        super(ResourceLocationHelper.fromNameAndPath(LootBeamsConstants.MODID, "sound_config"));
    }
    public static void init(){

    }
    public static class SoundSection extends ConfigSection {
        public boolean enable_sound = true;
        public ValidatedFloat sound_volume = new ValidatedFloat(1, 1, 0);
    }

    public static class SoundFilter extends ConfigSection {
        public ValidatedList<ResourceLocation> blacklist_by_name = ValidatedIdentifier.ofRegistry(BuiltInRegistries.ITEM.getDefaultKey(), BuiltInRegistries.ITEM).toList();
        public ValidatedList<String> blacklist_by_tag = new ValidatedString("#minecraft:air", "#.+:.+").toList();
        public ValidatedList<String> blacklist_by_modid = new ValidatedString("lootbeams", new AllowableStrings(x -> !x.isBlank() && !x.contains("#"), IServiceCollector.COLLECTOR::gatherModIDList)).toList();
    }
}
