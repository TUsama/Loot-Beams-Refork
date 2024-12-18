package com.clefal.lootbeams.compat.mine_and_slash;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.compat.ILBCompatModule;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import com.clefal.lootbeams.events.RegisterEquipmentItemEvent;
import com.clefal.lootbeams.events.RegisterLBRarityEvent;
import com.robertx22.mine_and_slash.database.data.gear_slots.GearSlot;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.ModList;
import net.neoforged.bus.api.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static io.vavr.API.*;

public class MineAndSlashCompatModule implements ILBCompatModule {

    public final static MineAndSlashCompatModule INSTANCE = new MineAndSlashCompatModule();

    private final List<String> rarities = new ArrayList<>();

    public static LBRarity getNonSoulRarity() {
        return LBRarity.of(Component.translatable("lootbeams.mod_rarity.non_soul"), new Color(121, 121, 121), -1);
    }

    @Override
    public boolean shouldBeEnable() {
        return ModList.get().isLoaded(SlashRef.MODID);
    }

    @Override
    public void tryEnable() {
        if (shouldBeEnable()) {
            LootBeamsConstants.LOGGER.info("Detected Mine and Slash, enable MineAndSlashCompatModule!");
            LootBeamsConstants.EVENT_BUS.register(INSTANCE);
            INSTANCE.rarities.add("common");
            INSTANCE.rarities.add("uncommon");
            INSTANCE.rarities.add("rare");
            INSTANCE.rarities.add("epic");
            INSTANCE.rarities.add("legendary");
            INSTANCE.rarities.add("mythic");
            INSTANCE.rarities.add("unique");
            INSTANCE.rarities.add("runeword");

        }
    }

    @SubscribeEvent
    public void onEnable(RegisterLBRarityEvent.Pre event) {
        event.register(itemEntity ->

                Match(Match(itemEntity.getItem()).option(
                        //ItemEntity -> GearRarity
                        Case($(stack -> StackSaving.GEARS.has(stack)), stack -> StackSaving.GEARS.loadFrom(stack).getRarity())
                )).option(
                        //GearRarity -> LBRarity
                        Case($(option -> !option.isEmpty()), option -> LBItemEntity.of(itemEntity, LBRarity.of(
                                option.get().locName(),
                                new Color(option.get().textFormatting().getColor()),
                                rarities.indexOf(option.get().guid)
                        ))),
                        Case($(option -> GearSlot.getSlotOf(itemEntity.getItem()) != null), option -> LBItemEntity.of(itemEntity, getNonSoulRarity()))
                ));
    }

    @SubscribeEvent
    public void registerEquipmentCondition(RegisterEquipmentItemEvent event) {
        event.conditions.add(itemStack -> itemStack.getItem().getClass().toString().contains("com.robertx22.mine_and_slash.vanilla_mc.items.gearitems"));
    }

}
