package com.clefal.lootbeams.compat.tiered_reforged;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import com.clefal.lootbeams.events.RegisterLBRarityEvent;
import com.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.nirvana_lib.relocated.io.vavr.control.Option;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import com.stereowalker.tiered.Tiered;
import com.stereowalker.tiered.api.PotentialAttribute;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

import java.awt.*;

public class TieredReforgedCompatModule implements ILBCompatModule {
    public static final TieredReforgedCompatModule INSTANCE = new TieredReforgedCompatModule();

    @Override
    public boolean shouldBeEnable() {
        return ModList.get().isLoaded("tiered");
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Tiered Reforge, enable TieredReforgeCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity -> {
                    //copy from ItemStackClientMixin getName
                    ItemStack item = itemEntity.getItem();
                    if (item.hasTag() && item.getTagElement("display") == null && item.getTagElement("Tiered") != null) {
                        ResourceLocation tier = new ResourceLocation(item.getTagElement("Tiered").getString("Tier"));
                        PotentialAttribute attribute = Tiered.TIER_DATA.getTiers().get(tier);
                        if (attribute != null) {
                            String id = attribute.getLiteralName();


                            return Option.some(LBItemEntity.of(itemEntity, LBRarity.of(
                                    attribute.getLiteralName() != null ? Component.literal(attribute.getLiteralName()) : Component.translatable(Util.makeDescriptionId("tier", Tiered.getKey(attribute))),
                                    new Color(attribute.getStyle().getColor().getValue()),
                                    -1
                            )));
                        }
                    }

                    return Option.none();

                }


        );

    }
}
