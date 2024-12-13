package com.clefal.lootbeams.modules;

import com.clefal.lootbeams.modules.beam.BeamModule;
import com.clefal.lootbeams.modules.compat.apothesis.ApotheosisCompatModule;
import com.clefal.lootbeams.modules.compat.mine_and_slash.MineAndSlashCompatModule;
import com.clefal.lootbeams.modules.compat.obscuretooltips.ObscureTooltipsCompatModule;
import com.clefal.lootbeams.modules.dynamicprovider.DynamicProviderModule;
import com.clefal.lootbeams.modules.sound.SoundModule;
import com.clefal.lootbeams.modules.tooltip.TooltipsModule;

import java.util.ArrayList;
import java.util.List;

public class ModulesManager {
    private final static List<ILBModule> list = new ArrayList<>();


    public static void registerAll(){
        list.forEach(ILBModule::tryEnable);
    }

    static {
        list.add(BeamModule.INSTANCE);
        list.add(TooltipsModule.INSTANCE);
        list.add(new SoundModule());
        list.add(DynamicProviderModule.INSTANCE);


        list.add(new ApotheosisCompatModule());
        list.add(MineAndSlashCompatModule.INSTANCE);
        list.add(ObscureTooltipsCompatModule.INSTANCE);
    }
}
