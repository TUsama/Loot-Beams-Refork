package com.clefal.lootbeams.config.configs;

import com.clefal.lootbeams.LootBeamsConstants;
import com.google.common.collect.Maps;
import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedIdentifierMap;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedMap;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class LightConfig extends Config {
    public static LightConfig lightConfig = ConfigApiJava.registerAndLoadConfig(LightConfig::new, RegisterType.CLIENT);

    public Beam beamSection = new Beam();
    public Glow glowSection = new Glow();
    public CustomColorSetting customColorSetting = new CustomColorSetting();
    public LightEffectFilter lightEffectFilter = new LightEffectFilter();


    public LightConfig() {
        super(new ResourceLocation(LootBeamsConstants.MODID + ":light_config"));
    }

    public static void init() {

    }

    public static class Beam extends ConfigSection {
        public boolean enable_beam = true;
        public ValidatedInt beam_fade_in_time = new ValidatedInt(10, 100, 1);
        public ValidatedFloat beam_fade_in_distance = new ValidatedFloat(15f, 100f, 1f);


        public ValidatedFloat beam_radius = new ValidatedFloat(0.55f, 5f, 0f);
        public ValidatedFloat beam_height = new ValidatedFloat(1.5f, 10f, 0f);
        public ValidatedFloat beam_y_offset = new ValidatedFloat(0.5f, 30f, -30f);
        public ValidatedFloat beam_alpha = new ValidatedFloat(0.75f, 1f, 0f);
        public boolean common_shorter_beam = true;
        public boolean solid_beam = true;

        public boolean require_on_ground = true;

    }

    public static class Glow extends ConfigSection {
        public boolean enable_glow = true;
        public ValidatedFloat glow_effect_radius = new ValidatedFloat(0.5f, 1f, 0.00001f);

    }

    public static class CustomColorSetting extends ConfigSection{
        public boolean enable_custom_color = false;
        @RequiresAction(action = Action.RESTART)
        @Comment(value = "support item, tag and modid")
        public ValidatedIdentifierMap<ValidatedColor.ColorHolder> color_override = new ValidatedIdentifierMap<>(
                new LinkedHashMap<>(),
                new ValidatedIdentifier(),
                new ValidatedColor(255, 255, 255, 255)
        );
    }

    public static class LightEffectFilter extends ConfigSection {
        public boolean all_item = false;
        public boolean only_rare = false;
        public ValidatedInt rare_ordinal_min = new ValidatedInt(3);
        public boolean only_equipment = true;
        @RequiresAction(action = Action.RESTART)
        @Comment(value = "support item, tag and modid")
        public ValidatedList<ResourceLocation> whitelist = new ValidatedIdentifier().toList();
        @RequiresAction(action = Action.RESTART)
        @Comment(value = "support item, tag and modid")
        public ValidatedList<ResourceLocation> blacklist = new ValidatedIdentifier().toList();

    }


}
