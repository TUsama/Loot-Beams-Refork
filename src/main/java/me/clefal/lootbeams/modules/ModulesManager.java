package me.clefal.lootbeams.modules;

import me.clefal.lootbeams.modules.beam.BeamModule;
import me.clefal.lootbeams.modules.dynamicprovider.DynamicProviderModule;
import me.clefal.lootbeams.modules.sound.SoundModule;
import me.clefal.lootbeams.modules.tooltip.TooltipsModule;

import java.util.ArrayList;
import java.util.List;

public class ModulesManager {
    private final static List<ILBModule> list = new ArrayList<>();

    static {
        list.add(BeamModule.INSTANCE);
        list.add(TooltipsModule.INSTANCE);
        list.add(new SoundModule());
        list.add(DynamicProviderModule.INSTANCE);

    }

    public static void enableAll() {
        list.forEach(ILBModule::tryEnable);
    }

    public static void registerModules(ILBModule... module) {
        list.addAll(List.of(module));
    }
}
