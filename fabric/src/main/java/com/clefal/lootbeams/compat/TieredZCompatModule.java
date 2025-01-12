package com.clefal.lootbeams.compat;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import com.clefal.lootbeams.events.RegisterLBRarityEvent;
import com.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.clefal.nirvana_lib.relocated.io.vavr.control.Option;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import draylar.tiered.Tiered;
import draylar.tiered.api.PotentialAttribute;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;


import java.awt.*;
import java.util.ArrayList;

public class TieredZCompatModule implements ILBCompatModule {

    public final static TieredZCompatModule INSTANCE = new TieredZCompatModule();

    private List<String> rarities;
    @Override
    public boolean shouldBeEnable() {
        return FabricLoader.getInstance().isModLoaded("tiered");
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected TieredZ, enable TieredZCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
            this.rarities = List.of(
                    "common",
                    "uncommon",
                    "rare",
                    "epic",
                    "legendary",
                    "unique");
        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity -> {
            //copy from ItemStackClientMixin getName
                    ItemStack item = itemEntity.getItem();
                    if (item.hasTag() && item.getTagElement("display") == null && item.getTagElement("Tiered") != null) {
                        ResourceLocation tier = new ResourceLocation(item.getTagElement("Tiered").getString("Tier"));
                        PotentialAttribute potentialAttribute = Tiered.ATTRIBUTE_DATA_LOADER.getItemAttributes().get(tier);
                        if (potentialAttribute != null) {
                            System.out.println("hover name is " + item.getHoverName().plainCopy().getString());
                            String id = potentialAttribute.getID();

                            Option<String> find = this.rarities.find(id::contains);

                            return Option.some(LBItemEntity.of(itemEntity, LBRarity.of(
                                    Component.translatable(id + ".label"),
                                    new Color(potentialAttribute.getStyle().getColor().getValue()),
                                    find.isEmpty() ? 0 : this.rarities.indexOf(find.get())
                            )));
                        }
                    }

                    return Option.none();

                }


        );

    }
}