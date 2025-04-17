package com.clefal.lootbeams.modules;

import com.clefal.lootbeams.modules.beam.BeamModule;
import com.clefal.lootbeams.modules.compat.photon.PhotonCompatModule;
import com.clefal.lootbeams.modules.compat.subtle_effect.SubtleEffectCompatModule;
import com.clefal.lootbeams.modules.dynamicprovider.DynamicProviderModule;
import com.clefal.lootbeams.modules.sound.SoundModule;
import com.clefal.lootbeams.modules.tooltip.TooltipsModule;

import java.util.ArrayList;
import java.util.List;

public class ModulesManager {
    private final static List<ILBModule> list = new ArrayList<>();

    static {
        list.add(BeamModule.INSTANCE);
        list.add(TooltipsModule.INSTANCE);
        list.add(new SoundModule());
        list.add(DynamicProviderModule.INSTANCE);
        list.add(PhotonCompatModule.INSTANCE);
        list.add(SubtleEffectCompatModule.INSTANCE);

    }

    public static void enableAll() {
        list.forEach(ILBModule::tryEnable);
    }

    public static void registerModules(ILBModule... module) {
        list.addAll(List.of(module));
    }
}
