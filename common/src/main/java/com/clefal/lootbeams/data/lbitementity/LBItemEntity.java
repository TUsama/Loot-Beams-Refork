package com.clefal.lootbeams.data.lbitementity;

import com.clefal.lootbeams.config.configs.LightConfig;
import com.clefal.lootbeams.config.configs.SoundConfig;
import com.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.minecraft.world.entity.item.ItemEntity;

@Getter
@Accessors(fluent = true)
public class LBItemEntity {

    private final ItemEntity item;
    private LBRarity rarity;
    private boolean isSounded;
    private RenderState canBeRender = RenderState.NONE;
    private int fadeIn;
    private LBItemEntity(ItemEntity item, LBRarity rarity, boolean isSounded, int fadeIn) {
        this.item = item;
        this.rarity = rarity;
        this.isSounded = isSounded;
        this.fadeIn = fadeIn;
    }

    public static LBItemEntity of(ItemEntity item, LBRarity rarity) {
        return new LBItemEntity(item, rarity, false, 0);
    }

    public LBItemEntity to(LBRarity rarity) {
        return LBItemEntity.of(item, rarity);
    }

    public void updateFade() {
        this.fadeIn++;
    }

    public void passThis() {
        if (this.canBeRender != RenderState.PASS) this.canBeRender = RenderState.PASS;
    }

    public void rejectThis() {
        if (this.canBeRender != RenderState.REJECT) this.canBeRender = RenderState.REJECT;
    }

    public void updateSounded() {
        this.isSounded = true;
    }

    public boolean isCommon() {
        return this.rarity.absoluteOrdinal() <= 0;
    }



    public boolean isRare() {
        int min = LightConfig.lightConfig.lightEffectFilter.rare_ordinal_min.get();

        return this.rarity.absoluteOrdinal() >= min;
    }

    public enum RenderState {
        PASS,
        REJECT,
        NONE
    }
}
