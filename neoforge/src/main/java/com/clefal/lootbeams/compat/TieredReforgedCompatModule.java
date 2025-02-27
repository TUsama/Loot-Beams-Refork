package com.clefal.lootbeams.compat;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import com.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import com.clefal.lootbeams.events.RegisterLBRarityEvent;
import com.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.nirvana_lib.relocated.io.vavr.control.Option;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.stereowalker.tiered.Reforged;
import com.stereowalker.tiered.api.PotentialAttribute;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

public class TieredReforgedCompatModule implements ILBCompatModule {
    public static final TieredReforgedCompatModule INSTANCE  = new TieredReforgedCompatModule();
    @Override
    public boolean shouldBeEnable() {
        try {
            Class.forName("com.stereowalker.tiered.Reforged");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return ModList.get().isLoaded("tiered");
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Tiered Reforged, enable TieredReforgedCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity -> {
                    //copy from ItemStackClientMixin getName
                    ItemStack item1 = itemEntity.getItem();
                    if (item1.get(DataComponents.CUSTOM_NAME) == null && Reforged.hasModifier(item1)) {
                        ResourceLocation tier = item1.get(Reforged.ComponentsRegistry.MODIFIER);
                        PotentialAttribute potentialAttribute = (PotentialAttribute) Reforged.TIER_DATA.getTiers().get(tier);
                        if (potentialAttribute != null) {
                            return Option.some(LBItemEntity.of(itemEntity, LBRarity.of(
                                    potentialAttribute.getLiteralName() != null ? Component.literal(potentialAttribute.getLiteralName()) : Component.translatable(Util.makeDescriptionId("tier", Reforged.getKey(potentialAttribute))),
                                    LBColor.of(potentialAttribute.getStyle().getColor().getValue()),
                                    0)
                            ));

                        }
                    }

                    return Option.none();
                }
        );
    }
}
