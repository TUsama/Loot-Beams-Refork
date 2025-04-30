package com.clefal.lootbeams.compat;

import com.clefal.lootbeams.CommonClass;
import com.clefal.lootbeams.modules.ILBCompatModule;
import einstein.subtle_effects.SubtleEffects;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.config.Config;
import net.neoforged.fml.ModList;

public class SubtleEffectCompatModule implements ILBCompatModule {
    public static boolean isEnabled = false;
    public final static SubtleEffectCompatModule INSTANCE = new SubtleEffectCompatModule();

    public static SubtleEffectConfig getConfig() {
        SubtleEffectConfig config = SubtleEffectConfig.config;
        if (config == null) {
            config = ConfigApiJava.registerAndLoadConfig(SubtleEffectConfig::new);
        }
        return config;
    }

    @Override
    public boolean shouldBeEnable() {
        return ModList.get().isLoaded(SubtleEffects.MOD_ID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            isEnabled = true;
            getConfig();
        }
    }

    public static class SubtleEffectConfig extends Config {
        private static SubtleEffectConfig config;
        public boolean disableItemRarityParticleRender = true;

        public SubtleEffectConfig() {
            super(CommonClass.id("subtle_effect_compat"));
        }
    }
}
