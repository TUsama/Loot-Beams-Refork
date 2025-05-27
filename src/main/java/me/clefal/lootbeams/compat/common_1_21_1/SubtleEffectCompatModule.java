//? if =1.21.1 {
package me.clefal.lootbeams.compat.common_1_21_1;

import com.clefal.nirvana_lib.utils.ModUtils;
import me.clefal.lootbeams.CommonClass;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.modules.ILBCompatModule;
import einstein.subtle_effects.SubtleEffects;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;

public class SubtleEffectCompatModule implements ILBCompatModule {
    public static boolean isEnabled = false;
    public final static SubtleEffectCompatModule INSTANCE = new SubtleEffectCompatModule();

    public static SubtleEffectConfig getConfig() {
        return SubtleEffectConfig.getConfig();
    }

    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded(SubtleEffects.MOD_ID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.warn("Detected Subtle Effect, its Item Rarity Function will be disabled for the compatibility! you can disable this feature in this mod's config!");
            isEnabled = true;
            getConfig();
        }
    }

    public static class SubtleEffectConfig extends Config {
        private static SubtleEffectConfig config;
        public boolean forceDisableItemRarity = true;

        public static SubtleEffectConfig getConfig() {
            if (config == null) config = ConfigApiJava.registerAndLoadConfig(SubtleEffectConfig::new, RegisterType.CLIENT);
            return config;
        }

        public SubtleEffectConfig() {
            super(CommonClass.id("subtle_effect_compat"));
        }
    }
}
//?}
