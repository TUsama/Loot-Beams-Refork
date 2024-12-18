package com.clefal.lootbeams.config.configs;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.modules.tooltip.TooltipsEnableStatus;
import com.clefal.lootbeams.utils.ResourceLocationHelper;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import net.minecraft.resources.ResourceLocation;

public class TooltipsConfig extends Config {

    public static TooltipsConfig tooltipsConfig = ConfigApiJava.registerAndLoadConfig(TooltipsConfig::new, RegisterType.CLIENT);

    public nameTagSection nameTagSection = new nameTagSection();
    public TooltipsSection tooltipsSection = new TooltipsSection();
    public TooltipsEnableStatus.TooltipsStatus tooltips_enable_status = TooltipsEnableStatus.TooltipsStatus.NAME_AND_RARITY_IN_NAMETAG;

    public TooltipsConfig() {
        super(ResourceLocationHelper.fromNameAndPath(LootBeamsConstants.MODID , "tooltips_config"));
    }
    public static void init(){

    }
    public static class nameTagSection extends ConfigSection {
        public boolean render_name_tag_on_look = true;
        public boolean name_tag_need_crouch = false;
        public boolean add_text_border = true;
        public boolean render_stack_count = true;
        public ValidatedFloat name_tag_look_sensitivity = new ValidatedFloat(0.018f);
        public ValidatedFloat name_tag_text_alpha = new ValidatedFloat(1, 1, 0);
        public ValidatedFloat name_tag_background_alpha = new ValidatedFloat(0.5f, 1, 0);
        public ValidatedFloat name_tag_scale = new ValidatedFloat(1);
        public ValidatedFloat name_tag_y_offset = new ValidatedFloat(0.75f);
    }

    public static class TooltipsSection extends ConfigSection{
        public boolean render_tooltips_on_crouch = true;
    }

}
