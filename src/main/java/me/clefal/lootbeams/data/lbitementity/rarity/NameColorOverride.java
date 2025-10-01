package me.clefal.lootbeams.data.lbitementity.rarity;

import me.clefal.lootbeams.config.configs.LightConfig;
import me.clefal.lootbeams.config.impl.ModifyingConfigHandler;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import net.minecraft.network.chat.TextColor;

public class NameColorOverride extends ModifyingConfigHandler {
    @Override
    public LBItemEntity modify(LBItemEntity lbItemEntity) {
        if (LightConfig.lightConfig.beam.allow_use_name_color_as_beam_color){
            TextColor color = lbItemEntity.item().getItem().getHoverName().getStyle().getColor();
            if (color != null && lbItemEntity.rarity().color().argb() == 0){
                lbItemEntity = lbItemEntity.to(lbItemEntity.rarity().modifyColor(LBColor.of(color.getValue())));
            }
        }
        return lbItemEntity;
    }
}
