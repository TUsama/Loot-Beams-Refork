package com.clefal.lootbeams.modules.compat.photon;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.config.configs.LightConfig;
import com.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
import com.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.lootbeams.utils.ResourceLocationHelper;
import com.clefal.nirvana_lib.platform.Services;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.lowdragmc.photon.client.fx.EntityEffect;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.lowdragmc.photon.client.fx.FXRuntime;
import com.lowdragmc.photon.client.gameobject.emitter.beam.BeamConfig;
import com.lowdragmc.photon.client.gameobject.emitter.beam.BeamEmitter;
import com.lowdragmc.photon.client.gameobject.emitter.data.number.color.Color;
import com.lowdragmc.photon.client.gameobject.emitter.particle.ParticleConfig;
import com.lowdragmc.photon.client.gameobject.emitter.particle.ParticleEmitter;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.util.EnumTranslatable;
import me.fzzyhmstrs.fzzy_config.util.Walkable;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedIdentifierMap;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedMap;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.item.ItemEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PhotonCompatModule implements ILBCompatModule {
    public static PhotonCompatModule INSTANCE = new PhotonCompatModule();

    public static PhotonCompatConfig getConfig() {
        if (PhotonCompatConfig.config == null) {
            PhotonCompatConfig.config = ConfigApiJava.registerAndLoadConfig(PhotonCompatConfig::new, RegisterType.CLIENT);
        }
        return PhotonCompatConfig.config;
    }

    public static int getRGB(int argb) {
        return argb & 0xFFFFFF;
    }

    public static int makeARGB(int alpha, int rgb) {
        return (alpha << 24) | (rgb & 0xFFFFFF);
    }

    @Override
    public boolean shouldBeEnable() {
        return Services.PLATFORM.isModLoaded("photon");
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Photon, enable PhotonCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
            getConfig();
        }
    }

    @SubscribeEvent
    public void onHook(EntityRenderDispatcherHookEvent.RenderLootBeamEvent event) {
        PhotonCompatConfig.FXEnable fxEnable = getConfig().fxEnable;
        if (!fxEnable.enableFX) return;
        ValidatedIdentifierMap<ResourceLocation> byItemName = fxEnable.byItemName;
        ItemEntity item = event.LBItemEntity.item();
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(item.getItem().getItem());
        boolean isSpecial = false;
        if (byItemName.containsKey(key)) {
            ResourceLocation s = byItemName.get(key);
            if (s != null) {
                FX fx = FXHelper.getFX(s);
                if (fx != null) {
                    replaceFXColor(event, fx, item);
                    isSpecial = true;
                    if (getConfig().fxEnable.affectStrategy.equals(Strategies.CompletelyReplace)) event.setCanceled(true);
                }

            }

        }
        if (fxEnable.affectAllBeam && !isSpecial) {
            ResourceLocation resourceLocation = fxEnable.baseFX.get();
            if (resourceLocation != null) {
                FX fx = FXHelper.getFX(resourceLocation);
                if (fx != null) {
                    replaceFXColor(event, fx, item);
                    if (getConfig().fxEnable.affectStrategy.equals(Strategies.CompletelyReplace)) event.setCanceled(true);
                }
            }
        }

    }

    private void replaceFXColor(EntityRenderDispatcherHookEvent.RenderLootBeamEvent event, FX fx, ItemEntity item) {
        EntityEffect entityEffect = new DeepCopyEntityEffect(fx, Minecraft.getInstance().level, item);
        entityEffect.setForcedDeath(true);
        entityEffect.setAllowMulti(false);
        entityEffect.start();
        FXRuntime runtime = entityEffect.getRuntime();
        if (runtime != null) {
            runtime.getObjects().values()
                    .stream()
                    .filter(x -> getConfig().fxControl.ifThisNameShouldBeReplacedColor.containsKey(x.getName()))
                    .forEach(x -> {
                        ReplaceConfig replaceConfig = getConfig().fxControl.ifThisNameShouldBeReplacedColor.get(x.getName());
                        java.awt.Color color = event.LBItemEntity.rarity().color();
                        if (x instanceof ParticleEmitter particleEmitter) {
                            ParticleConfig config = particleEmitter.config;
                            if (config.getStartColor() instanceof Color constantColor) {
                                int i = constantColor.getNumber().intValue();
                                int newAlpha = ((int) (LightConfig.lightConfig.beam.beam_alpha.get() * 255));
                                int newRGB = getRGB(color.getRGB());
                                int oldAlpha = FastColor.ARGB32.alpha(i);
                                int oldRGB = getRGB(i);
                                if (replaceConfig.replaceAlpha) {
                                    if (replaceConfig.replaceColor) {
                                        config.setStartColor(new Color(makeARGB(newAlpha, newRGB)));
                                    } else {

                                        config.setStartColor(new Color(makeARGB(newAlpha, oldRGB)));
                                    }
                                } else {
                                    if (replaceConfig.replaceColor) {
                                        config.setStartColor(new Color(makeARGB(oldAlpha, newRGB)));
                                    }
                                }


                            }

                        } else if (x instanceof BeamEmitter beamEmitter) {
                            BeamConfig config = beamEmitter.getConfig();
                            if (config.getColor() instanceof Color constantColor) {
                                int i = constantColor.getNumber().intValue();
                                int newAlpha = ((int) (LightConfig.lightConfig.beam.beam_alpha.get() * 255));
                                int newRGB = getRGB(color.getRGB());
                                int oldAlpha = FastColor.ARGB32.alpha(i);
                                int oldRGB = getRGB(i);
                                if (replaceConfig.replaceAlpha) {
                                    if (replaceConfig.replaceColor) {
                                        config.setColor(new Color(makeARGB(newAlpha, newRGB)));
                                    } else {

                                        config.setColor(new Color(makeARGB(newAlpha, oldRGB)));
                                    }
                                } else {
                                    if (replaceConfig.replaceColor) {
                                        config.setColor(new Color(makeARGB(oldAlpha, newRGB)));
                                    }
                                }


                            }
                        }
                    });

        }
    }

    public static class ReplaceConfig implements Walkable {
        public boolean replaceAlpha = true;
        public boolean replaceColor = true;

        public ReplaceConfig() {
        }

        public ReplaceConfig(boolean replaceAlpha, boolean replaceColor) {
            this.replaceAlpha = replaceAlpha;
            this.replaceColor = replaceColor;
        }
    }

    public enum Strategies implements EnumTranslatable {
        CompletelyReplace,
        Merge;

        @Override
        public @NotNull String prefix() {
            return LootBeamsConstants.MODID + ".affect_strategy";
        }
    }

    public static class PhotonCompatConfig extends Config {
        private static PhotonCompatConfig config;
        public FXEnable fxEnable = new FXEnable();
        public FXControl fxControl = new FXControl();

        public PhotonCompatConfig() {
            super(ResourceLocationHelper.fromNameAndPath(LootBeamsConstants.MODID, "photon_compat_config"));
        }

        public static class FXEnable extends ConfigSection {
            public boolean enableFX = false;

            public boolean affectAllBeam = false;
            public ConfigGroup affectSetting = new ConfigGroup("replace_setting");
            @ConfigGroup.Pop
            public ValidatedIdentifier baseFX = new ValidatedIdentifier(ResourceLocationHelper.fromNameAndPath(LootBeamsConstants.MODID,
                    "common"));
            
            public Strategies affectStrategy = Strategies.Merge;

            public ValidatedIdentifierMap<ResourceLocation> byItemName = new ValidatedIdentifierMap.Builder<ResourceLocation>()
                    .keyHandler(ValidatedIdentifier.ofRegistry(BuiltInRegistries.ITEM.getDefaultKey(), BuiltInRegistries.ITEM))
                    .valueHandler(new ValidatedIdentifier())
                    .build();
        }

        public static class FXControl extends ConfigSection {
            public ValidatedMap<String, ReplaceConfig> ifThisNameShouldBeReplacedColor = new ValidatedMap.Builder<String, ReplaceConfig>().keyHandler(new ValidatedString())
                    .valueHandler(new ValidatedAny<>(new ReplaceConfig(true, true)))
                    .defaults(
                            Map.of("rarityParticle", new ReplaceConfig(false, true),
                                    "halo", new ReplaceConfig(true, true),
                                    "haloVertical", new ReplaceConfig(true, true))
                    ).build();
        }
    }
}
