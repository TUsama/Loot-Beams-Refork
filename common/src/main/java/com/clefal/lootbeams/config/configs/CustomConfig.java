package com.clefal.lootbeams.config.configs;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.config.services.IServiceCollector;
import com.clefal.lootbeams.utils.ResourceLocationHelper;
import com.google.common.collect.ImmutableList;
import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.util.AllowableStrings;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@RequiresAction(action = Action.RESTART)
public class CustomConfig extends Config {
    public static CustomConfig customConfig = ConfigApiJava.registerAndLoadConfig(CustomConfig::new, RegisterType.CLIENT);

    public EquipmentRegister equipmentRegister = new EquipmentRegister();
    public CustomConfig() {
        super(ResourceLocationHelper.fromNameAndPath(LootBeamsConstants.MODID, "custom_config"));
    }

    public static class EquipmentRegister extends ConfigSection{
        private List<String> defaultTags = ImmutableList.of("#minecraft:swords", "#minecraft:axes", "#forge:tools/tridents", "#forge:tools/bows", "#forge:tools/crossbows");
        private List<String> defaultmodid = ImmutableList.of("weaponmod");
        private List<ResourceLocation> defaultBlockItems = ImmutableList.of(
                "weaponmod:bullet",
                "weaponmod:cannonball",
                "weaponmod:shot",
                "weaponmod:bolt",
                "weaponmod:shell",
                "weaponmod:musket-ironpart",
                "weaponmod:blunder-ironpart",
                "weaponmod:gun-stock",
                "weaponmod:mortar-ironpart",
                "weaponmod:dummy"
        ).stream().map(ResourceLocationHelper::fromWholeName).toList();

        public ValidatedSet<ResourceLocation> by_name = ValidatedIdentifier.ofRegistry(BuiltInRegistries.ITEM.getDefaultKey(), BuiltInRegistries.ITEM).toSet();

        public ValidatedSet<String> by_tag = new ValidatedString("#minecraft:air", "#.+:.+").toSet(defaultTags);

        public ValidatedSet<String> by_modid = new ValidatedString("lootbeams", new AllowableStrings(x -> !x.isBlank() && !x.contains("#"), IServiceCollector.COLLECTOR::gatherModIDList)).toSet(defaultmodid);

        public ValidatedSet<ResourceLocation> block_item_in_modid = ValidatedIdentifier.ofRegistry(BuiltInRegistries.ITEM.getDefaultKey(), BuiltInRegistries.ITEM).toSet(defaultBlockItems);
    }

    public static void init(){}

}
