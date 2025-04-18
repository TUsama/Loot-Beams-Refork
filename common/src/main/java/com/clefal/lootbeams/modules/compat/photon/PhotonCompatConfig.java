package com.clefal.lootbeams.modules.compat.photon;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.utils.ResourceLocationHelper;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedIdentifierMap;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedMap;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class PhotonCompatConfig extends Config {
    private static PhotonCompatConfig config;
    public FXEnable fxEnable = new FXEnable();
    public FXControl fxControl = new FXControl();

    public PhotonCompatConfig() {
        super(ResourceLocationHelper.fromNameAndPath(LootBeamsConstants.MODID, "photon_compat_config"));
    }

    public static PhotonCompatConfig getConfig() {
        if (PhotonCompatConfig.config == null) {
            PhotonCompatConfig.config = ConfigApiJava.registerAndLoadConfig(PhotonCompatConfig::new, RegisterType.CLIENT);
        }
        return PhotonCompatConfig.config;
    }

    public static class FXEnable extends ConfigSection {
        public boolean enableFX = false;

        public boolean affectAllBeam = false;
        public ConfigGroup affectSetting = new ConfigGroup("replace_setting");
        @ConfigGroup.Pop
        public ValidatedIdentifier baseFX = new ValidatedIdentifier(ResourceLocationHelper.fromNameAndPath(LootBeamsConstants.MODID,
                "common"));

        public PhotonCompatModule.Strategies affectStrategy = PhotonCompatModule.Strategies.Merge;

        public ValidatedIdentifierMap<ResourceLocation> byItemName = new ValidatedIdentifierMap.Builder<ResourceLocation>()
                .keyHandler(ValidatedIdentifier.ofRegistry(BuiltInRegistries.ITEM.getDefaultKey(), BuiltInRegistries.ITEM))
                .valueHandler(new ValidatedIdentifier())
                .build();
    }

    public static class FXControl extends ConfigSection {
        public ValidatedMap<String, PhotonCompatModule.ReplaceConfig> ifThisNameShouldBeReplacedColor = new ValidatedMap.Builder<String, PhotonCompatModule.ReplaceConfig>().keyHandler(new ValidatedString())
                .valueHandler(new ValidatedAny<>(new PhotonCompatModule.ReplaceConfig(true, true)))
                .defaults(
                        Map.of("rarityParticle", new PhotonCompatModule.ReplaceConfig(false, true),
                                "halo", new PhotonCompatModule.ReplaceConfig(true, true),
                                "haloVertical", new PhotonCompatModule.ReplaceConfig(true, true))
                ).build();
    }
}
