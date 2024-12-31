package com.clefal.lootbeams.config.configs;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.modules.tooltip.LootInformationEnableStatus;
import com.clefal.lootbeams.utils.ResourceLocationHelper;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;

public class LootInfomationConfig extends Config {

    public static LootInfomationConfig lootInfomationConfig = ConfigApiJava.registerAndLoadConfig(LootInfomationConfig::new, RegisterType.CLIENT);
    public LootInformationControlSection lootInformationControl = new LootInformationControlSection();
    public raritySection rarity = new raritySection();
    public nameTagSection nameTag = new nameTagSection();
    public TooltipsSection tooltips = new TooltipsSection();



    public LootInfomationConfig() {
        super(ResourceLocationHelper.fromNameAndPath(LootBeamsConstants.MODID , "loot_information_config"));
    }
    public static void init(){

    }
    public enum ShowRarityTarget{
        NONE,
        RARE,
        ALL
    }

    public static class LootInformationControlSection extends ConfigSection{
        public LootInformationEnableStatus.LootInformationStatus loot_information_status = LootInformationEnableStatus.LootInformationStatus.NAME_AND_RARITY_IN_NAMETAG;
        public boolean showInfoForAllItem = true;
    }


    public static class raritySection extends ConfigSection{
        public ShowRarityTarget showRarityFor = ShowRarityTarget.RARE;
    }


    public static class nameTagSection extends ConfigSection {
        public boolean render_name_tag_on_look = true;
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
