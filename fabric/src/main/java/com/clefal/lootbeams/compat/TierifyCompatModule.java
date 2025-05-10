package com.clefal.lootbeams.compat;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import com.clefal.lootbeams.events.RegisterLBRarityEvent;
import com.clefal.lootbeams.modules.ILBCompatModule;
import com.clefal.nirvana_lib.relocated.io.vavr.collection.List;
import com.clefal.nirvana_lib.relocated.io.vavr.control.Option;
import com.clefal.nirvana_lib.relocated.net.neoforged.bus.api.SubscribeEvent;
import draylar.tiered.api.PotentialAttribute;
import elocindev.tierify.Tierify;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class TierifyCompatModule implements ILBCompatModule {

    public final static TierifyCompatModule INSTANCE = new TierifyCompatModule();

    private List<String> rarities;

    @Override
    public boolean shouldBeEnable() {
        if (!FabricLoader.getInstance().isModLoaded("tiered")) return false;
        System.out.println(this.getClass().getName());
        try {
            Class.forName("elocindev.tierify.Tierify");
        } catch (ClassNotFoundException ignored) {
            return false;
        }
        return true;
    }

    @Override
    public void tryEnable() {
        System.out.println("try enable TierifyCompatModule!");
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Tierify, enable TierifyCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
            this.rarities = List.of(
                    "common",
                    "uncommon",
                    "rare",
                    "epic",
                    "legendary",
                    "mythic");
        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        System.out.println("22222");
        event.register(itemEntity -> {
                    //copy from ItemStackClientMixin getName
                    //they don't change this part nice
                    ItemStack item = itemEntity.getItem();
                    if (item.hasTag() && item.getTagElement("display") == null && item.getTagElement("Tiered") != null) {
                        ResourceLocation tier = new ResourceLocation(item.getTagElement("Tiered").getString("Tier"));
                        PotentialAttribute potentialAttribute = Tierify.ATTRIBUTE_DATA_LOADER.getItemAttributes().get(tier);

                        if (potentialAttribute != null) {
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
