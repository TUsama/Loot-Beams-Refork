package com.clefal.lootbeams.config.configs;

import com.clefal.lootbeams.Constants;
import com.clefal.lootbeams.config.ConfigCustomInput;
import com.clefal.lootbeams.data.lbitementity.rarity.Order;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedMap;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;

public class LightConfig extends Config {
    public static LightConfig lightConfig = ConfigApiJava.registerAndLoadConfig(LightConfig::new, RegisterType.CLIENT);

    public BeamSection beamSection = new BeamSection();
    public GlowSection glowSection = new GlowSection();
    public LightEffectFilter lightEffectFilter = new LightEffectFilter();


    public LightConfig() {
        super(new ResourceLocation(Constants.MODID + ":light_config"));
    }
    public static void init(){

    }

    public static class BeamSection extends ConfigSection {
        public boolean enable_beam = true;
        public ValidatedInt beam_fade_in_time = new ValidatedInt(10, 1, 100);
        public ValidatedFloat beam_fade_in_distance = new ValidatedFloat(15f, 100f, 1f);
        public ValidatedList<Order> color_apply_order = new ValidatedEnum<>(Order.ITEM).toList(Order.ITEM, Order.TAG, Order.MODID);

        public ValidatedMap<ConfigCustomInput, ValidatedColor.ColorHolder> color_override = new ValidatedMap<>(
                new LinkedHashMap<>(),
                new ValidatedAny<>(new ConfigCustomInput("")),
                new ValidatedColor(255, 128, 0, 255)
        );

        public ValidatedFloat beam_radius = new ValidatedFloat(0.55f, 5f, 0f);
        public ValidatedFloat beam_height = new ValidatedFloat(1.5f, 10f, 0f);
        public ValidatedFloat beam_y_offset = new ValidatedFloat(0.5f, 30f, -30f);
        public ValidatedFloat beam_alpha = new ValidatedFloat(0.75f, 1f, 0f);
        public boolean common_shorter_beam = true;
        public boolean solid_beam = true;

        public boolean require_on_ground = true;

    }

    public static class GlowSection extends ConfigSection {
        public boolean enable_glow = true;
        public ValidatedFloat glow_effect_radius = new ValidatedFloat(0.5f, 1f, 0.00001f);
        ;
    }

    public static class LightEffectFilter extends ConfigSection {
        public boolean all_item = false;
        public boolean only_rare = true;
        public ValidatedInt rare_ordinal_min = new ValidatedInt(3);
        public boolean only_equipment = true;
        public ValidatedList<ConfigCustomInput> whitelist = new ValidatedAny<>(new ConfigCustomInput("")).toList();
        public ValidatedList<ConfigCustomInput> blacklist = new ValidatedAny<>(new ConfigCustomInput("")).toList();

    }


}
