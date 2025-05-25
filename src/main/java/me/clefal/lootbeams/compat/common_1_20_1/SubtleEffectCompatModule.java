//? if =1.20.1 {
/*package me.clefal.lootbeams.compat.common_1_20_1;

import com.clefal.nirvana_lib.utils.ModUtils;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.modules.ILBCompatModule;
import me.clefal.lootbeams.utils.ResourceLocationHelper;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;

public class SubtleEffectCompatModule implements ILBCompatModule {

    public static final SubtleEffectCompatModule INSTANCE = new SubtleEffectCompatModule();
    public static boolean isEnabled = false;

    public static SubtleEffectCompatConfig getConfig() {
        if (SubtleEffectCompatConfig.config == null) {
            SubtleEffectCompatConfig.config = ConfigApiJava.registerAndLoadConfig(SubtleEffectCompatConfig::new, RegisterType.CLIENT);
        }
        return SubtleEffectCompatConfig.config;
    }

    @Override
    public boolean shouldBeEnable() {
        return ModUtils.isModLoaded("photon") && ModUtils.isModLoaded("subtle_effects");
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
*///?}
