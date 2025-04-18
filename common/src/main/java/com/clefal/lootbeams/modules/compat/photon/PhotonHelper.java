package com.clefal.lootbeams.modules.compat.photon;

import com.clefal.lootbeams.config.configs.LightConfig;
import com.clefal.lootbeams.events.EntityRenderDispatcherHookEvent;
import com.lowdragmc.photon.client.fx.EntityEffect;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.lowdragmc.photon.client.fx.FXRuntime;
import com.lowdragmc.photon.client.gameobject.emitter.beam.BeamConfig;
import com.lowdragmc.photon.client.gameobject.emitter.beam.BeamEmitter;
import com.lowdragmc.photon.client.gameobject.emitter.data.number.color.Color;
import com.lowdragmc.photon.client.gameobject.emitter.particle.ParticleConfig;
import com.lowdragmc.photon.client.gameobject.emitter.particle.ParticleEmitter;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedIdentifierMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.item.ItemEntity;

public class PhotonHelper {
    public static void replaceFXColor(EntityRenderDispatcherHookEvent.RenderLootBeamEvent event, FX fx, ItemEntity item) {
        EntityEffect entityEffect = new DeepCopyEntityEffect(fx, Minecraft.getInstance().level, item);
        entityEffect.setForcedDeath(true);
        entityEffect.setAllowMulti(false);
        entityEffect.start();
        FXRuntime runtime = entityEffect.getRuntime();
        if (runtime != null) {
            runtime.getObjects().values()
                    .stream()
                    .filter(x -> PhotonCompatConfig.getConfig().fxControl.ifThisNameShouldBeReplacedColor.containsKey(x.getName()))
                    .forEach(x -> {
                        PhotonCompatModule.ReplaceConfig replaceConfig = PhotonCompatConfig.getConfig().fxControl.ifThisNameShouldBeReplacedColor.get(x.getName());
                        java.awt.Color color = event.LBItemEntity.rarity().color();
                        if (x instanceof ParticleEmitter particleEmitter) {
                            ParticleConfig config = particleEmitter.config;
                            if (config.getStartColor() instanceof Color constantColor) {
                                int i = constantColor.getNumber().intValue();
                                int newAlpha = ((int) (LightConfig.lightConfig.beam.beam_alpha.get() * 255));
                                int newRGB = PhotonCompatModule.getRGB(color.getRGB());
                                int oldAlpha = FastColor.ARGB32.alpha(i);
                                int oldRGB = PhotonCompatModule.getRGB(i);
                                if (replaceConfig.replaceAlpha) {
                                    if (replaceConfig.replaceColor) {
                                        config.setStartColor(new Color(PhotonCompatModule.makeARGB(newAlpha, newRGB)));
                                    } else {

                                        config.setStartColor(new Color(PhotonCompatModule.makeARGB(newAlpha, oldRGB)));
                                    }
                                } else {
                                    if (replaceConfig.replaceColor) {
                                        config.setStartColor(new Color(PhotonCompatModule.makeARGB(oldAlpha, newRGB)));
                                    }
                                }


                            }

                        } else if (x instanceof BeamEmitter beamEmitter) {
                            BeamConfig config = beamEmitter.getConfig();
                            if (config.getColor() instanceof Color constantColor) {
                                int i = constantColor.getNumber().intValue();
                                int newAlpha = ((int) (LightConfig.lightConfig.beam.beam_alpha.get() * 255));
                                int newRGB = PhotonCompatModule.getRGB(color.getRGB());
                                int oldAlpha = FastColor.ARGB32.alpha(i);
                                int oldRGB = PhotonCompatModule.getRGB(i);
                                if (replaceConfig.replaceAlpha) {
                                    if (replaceConfig.replaceColor) {
                                        config.setColor(new Color(PhotonCompatModule.makeARGB(newAlpha, newRGB)));
                                    } else {

                                        config.setColor(new Color(PhotonCompatModule.makeARGB(newAlpha, oldRGB)));
                                    }
                                } else {
                                    if (replaceConfig.replaceColor) {
                                        config.setColor(new Color(PhotonCompatModule.makeARGB(oldAlpha, newRGB)));
                                    }
                                }


                            }
                        }
                    });

        }
    }

    public static void handleFX(EntityRenderDispatcherHookEvent.RenderLootBeamEvent event) {
        PhotonCompatConfig.FXEnable fxEnable = PhotonCompatConfig.getConfig().fxEnable;
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
                    if (PhotonCompatConfig.getConfig().fxEnable.affectStrategy.equals(PhotonCompatModule.Strategies.CompletelyReplace))
                        event.setCanceled(true);
                }

            }

        }
        if (fxEnable.affectAllBeam && !isSpecial) {
            ResourceLocation resourceLocation = fxEnable.baseFX.get();
            if (resourceLocation != null) {
                FX fx = FXHelper.getFX(resourceLocation);
                if (fx != null) {
                    replaceFXColor(event, fx, item);
                    if (PhotonCompatConfig.getConfig().fxEnable.affectStrategy.equals(PhotonCompatModule.Strategies.CompletelyReplace))
                        event.setCanceled(true);
                }
            }
        }
    }
}
