package com.clefal.lootbeams.modules.compat.subtle_effect;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.lootbeams.utils.ResourceLocationHelper;
import com.clefal.nirvana_lib.platform.Services;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;

public class SubtleEffectCompatModule implements ILBCompatModule {

    public static final SubtleEffectCompatModule INSTANCE = new SubtleEffectCompatModule();
    public boolean isEnabled = false;

    public static SubtleEffectCompatModule.SubtleEffectCompatConfig getConfig() {
        if (SubtleEffectCompatModule.SubtleEffectCompatConfig.config == null) {
            SubtleEffectCompatModule.SubtleEffectCompatConfig.config = ConfigApiJava.registerAndLoadConfig(SubtleEffectCompatModule.SubtleEffectCompatConfig::new, RegisterType.CLIENT);
        }
        return SubtleEffectCompatModule.SubtleEffectCompatConfig.config;
    }

    @Override
    public boolean shouldBeEnable() {
        return Services.PLATFORM.isModLoaded("photon") && Services.PLATFORM.isModLoaded("subtle_effects");
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.warn("Detected Subtle Effect, its Item Rarity and Enable Particle Culling feature of SE will be disabled for the compatibility when you also enable the config 'enableFX'! you can disable these features in this mod's config!");
            getConfig();
            isEnabled = true;
        }

    }

    public static class SubtleEffectCompatConfig extends Config {
        private static SubtleEffectCompatConfig config;
        public boolean forceDisableItemRarity = true;
        public boolean forceDisableParticleCull = true;

        public SubtleEffectCompatConfig() {
            super(ResourceLocationHelper.fromNameAndPath(LootBeamsConstants.MODID, "se_compat_config"));
        }
    }
}
