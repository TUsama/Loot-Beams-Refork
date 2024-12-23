package com.clefal.lootbeams.compat.spartanweaponry;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.config.configs.Checker;
import com.clefal.lootbeams.events.RegisterConfigConditionEvent;
import com.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.google.common.collect.Sets;
import com.oblivioussp.spartanweaponry.api.SpartanWeaponryAPI;
import net.minecraftforge.fml.ModList;

import java.util.Set;

public class SpartanWeaponryCompatModule implements ILBCompatModule {

    public final static SpartanWeaponryCompatModule INSTANCE = new SpartanWeaponryCompatModule();
    //https://github.com/ObliviousSpartan/SpartanWeaponry/blob/1.20.1/src/main/java/com/oblivioussp/spartanweaponry/api/tags/ModItemTags.java
    public final Set<String> allAvailablePath = Sets.newHashSet(
            "daggers",
            "parrying_daggers",
            "longswords",
            "katanas",
            "sabers",
            "rapiers",
            "greatswords",
            "clubs",
            "cestusae",
            "battle_hammers",
            "warhammers",
            "spears",
            "halberds",
            "pikes",
            "lances",
            "longbows",
            "heavy_crossbows",
            "throwing_knives",
            "tomahawks",
            "javelins",
            "boomerangs",
            "battleaxes",
            "flanged_maces",
            "glaives",
            "quarterstaves",
            "scythes"
    );

    @Override
    public boolean shouldBeEnable() {
        return ModList.get().isLoaded(SpartanWeaponryAPI.MOD_ID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Spartan Weaponry, enable SpartanWeaponryCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }

    @SubscribeEvent
    public void registerEquipmentCondition(RegisterConfigConditionEvent.RegisterEquipmentItemEvent event) {
        event.conditions.add(lbItemEntity -> {
            if (Checker.checkIsThisMod(lbItemEntity.item().getItem(), SpartanWeaponryAPI.MOD_ID)) return false;
            String path = lbItemEntity.resourceLocation().getPath();
            //I think this might be the fastest way to check if this item is using the SP tag.
            return this.allAvailablePath.contains(path);
        });
    }
}
