package me.clefal.lootbeams.utils;

import lombok.experimental.UtilityClass;
import net.minecraftforge.fml.ModList;
//? if fabric
/*import net.fabricmc.loader.api.FabricLoader;*/


import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ModListUtil {

    public List<String> getModList(){
        ArrayList<String> strings = new ArrayList<>();
        //? if fabric {
        /*FabricLoader.getInstance().getAllMods().forEach(x -> strings.add(x.getMetadata().getId()));
        *///?}

        //? if forge {
        ModList.get().getMods().forEach(x -> strings.add(x.getModId()));
        //?}


        return strings;
    }
}
